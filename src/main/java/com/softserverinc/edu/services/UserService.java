package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.IssueComment;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    public User findOne(Long id) {
        return (id == null ? null : userRepository.findOne(id));
    }

    public List<User> findByEmailContaining(String email) {
        return userRepository.findByEmailContaining(email);
    }

    public List<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByEmailIs(String email) {
        return userRepository.findByEmailIs(email);
    }

    public User getAuthorOfIssueComment(IssueComment comment) {
        if (comment.getUser() == null) {
            User mockUser = new User();
            mockUser.setFirstName(comment.getAnonymousName());
            return mockUser;
        }
        return comment.getUser();
    }

    public List<User> findUsersForRelease(ProjectRelease release) {
        Project project = projectService.findById(release.getProject().getId());
        return findUsersInProject(project, false, 1);
    }

    public List<User> findByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    public User getProjectManagerOfProject(Long projectId) {
        Project project = projectService.findById(projectId);
        return userRepository.findByProjectAndRole(project, UserRole.ROLE_PROJECT_MANAGER);
    }

    public List<User> findUsersInProject(Project project, boolean isDeleted, int enabled) {
        List<User> users = userRepository.findByProjectAndIsDeletedAndEnabledIs(project, isDeleted, enabled);
        users.sort((user1, user2) -> Integer.valueOf(user1.getRole().ordinal()).compareTo(user2.getRole().ordinal()));
        return users;
    }

    /**
     * Find all users in Project except Project Manager.
     * @param project the instance of Project entity
     * @param pageable the paging information
     * @return the paginated list of users in Project except Project Manager
     */
    public Page<User> findUsersByProjectPageable(Project project, Pageable pageable) {
        return userRepository.findByProjectAndRoleNotAndIsDeleted(project, UserRole.ROLE_PROJECT_MANAGER, false,
                pageable);
    }

    public Page<User> findByFullName(String firstName, String lastName, Pageable pageable) {
        return userRepository.findByFirstNameContainingAndLastNameContaining(firstName, lastName, pageable);
    }

    public Page<User> findByFirstNameContaining(String firstName, Pageable pageable) {
        return userRepository.findByFirstNameContaining(firstName, pageable);
    }

    public Page<User> findByLastNameContaining(String lastName, Pageable pageable) {
        return userRepository.findByLastNameContaining(lastName, pageable);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Set encoded password to the User instance.
     * @param user the instance of User entity
     */
    public void passwordEncoder(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    /**
     * Save created User into database.
     * <p>invoke {@link #passwordEncoder(User user)}</p>
     * <p>If user's role is ROLE_PROJECT_MANAGER it invokes {@link #saveProjectManager(Long, Long)}</p>
     * @param user the instance of User entity
     */
    @Transactional
    public void saveUser(User user) {
        if(user.getRole().isProjectManager()){
            passwordEncoder(user);
            user.setEnabled(1);
            userService.saveProjectManager(user.getId(), user.getProject().getId());
            return;
        }
        passwordEncoder(user);
        user.setEnabled(1);
        userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Transactional
    public User update(User user) {
        return userRepository.saveAndFlush(user);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('PROJECT_MANAGER')")
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> findUsersByRole(UserRole role, Pageable pageable) {
        return userRepository.findByRole(role, pageable);
    }

    public Page<User> findAllUsers(Pageable pageable){
        return userRepository.findByRoleNot(UserRole.ROLE_ADMIN, pageable);
    }

    /**
     * Search users by different combinations of selected parameters
     * @param project instance of Project entity, may be null only if role is ROLE_USER
     * @param searchParam the search parameter that corresponds for the user's fields (firstName, lastName, email),
     *                    may be null
     * @param role the search parameter that corresponds for the user's role, may be null if project not null
     * @param searchedString the string that entered by the user from UI, may be null
     * @param pageable pageable Object
     * @return list of found users
     */
    public Page<User> searchByUsers(Project project, String searchParam, UserRole role, String searchedString,
                                    Pageable pageable) {
        if (role == null) {
            if (searchParam.equals("First Name")) {
                return userRepository.findByProjectAndFirstNameContainingAndRoleNotAndIsDeleted(project, searchedString,
                        UserRole.ROLE_PROJECT_MANAGER, false, pageable);
            }
            if (searchParam.equals("Last Name")) {
                return userRepository.findByProjectAndLastNameContainingAndRoleNotAndIsDeleted(project, searchedString,
                        UserRole.ROLE_PROJECT_MANAGER, false, pageable);
            }
            if (searchParam.equals("Email")) {
                return userRepository.findByEmailContainingAndProjectAndRoleNotAndIsDeleted(searchedString, project,
                        UserRole.ROLE_PROJECT_MANAGER, false, pageable);
            }
            return userRepository.findByProjectAndRoleNotAndIsDeleted(project, UserRole.ROLE_PROJECT_MANAGER, false,
                    pageable);
        }
        if (searchParam.equals("First Name")) {
            return userRepository.findByProjectAndFirstNameContainingAndRoleAndIsDeleted(project, searchedString, role,
                    false, pageable);
        }
        if (searchParam.equals("Last Name")) {
            return userRepository.findByProjectAndLastNameContainingAndRoleAndIsDeleted(project, searchedString, role,
                    false, pageable);
        }
        if (searchParam.equals("Email")) {
            return userRepository.findByEmailContainingAndRoleAndIsDeleted(searchedString, role, false, pageable);
        }
        return userRepository.findByProjectAndRoleAndIsDeleted(project, role, false, pageable);
    }

    public User getAvaliableUser(User user) {
        User mockUser;
        if (user == null) {
            mockUser = new User();
            mockUser.setFirstName("Anonymous user");
            return mockUser;
        }
        if (user.isDeleted()) {
            mockUser = new User();
            mockUser.setFirstName(user.getFirstName());
            mockUser.setLastName(user.getLastName());
            return mockUser;
        }
        return user;
    }

    /**
     * Save user into database with role ROLE_PROJECT_MANAGER.
     * <ul>
     *    <li>if project's id is null or, if there is project manager in the project and his id equals to userId then
     *     method does nothing</li>
     *    <li>otherwise invoke {@link #userManagementInProject(User, Project, UserRole)}</li>
     * </ul>
     * @param userId the id of user
     * @param projectId the id of project
     */
    @Transactional
    public void saveProjectManager(Long userId, Long projectId) {
        if(projectId == 0){
            return;
        }
        Project project = projectService.findById(projectId);
        User user = userService.findOne(userId);
        if(user.getRole().isProjectManager() && user.getProject() == project){
            return;
        }
        User exProjectManager = userService.getProjectManagerOfProject(projectId);

        if (!projectService.findById(projectId).getUsers().isEmpty() && exProjectManager != null) {
            userService.userManagementInProject(exProjectManager, null, UserRole.ROLE_USER);
        }
        userService.userManagementInProject(user, project, UserRole.ROLE_PROJECT_MANAGER);
    }

    /**
     * Change user's role amd project and save user into database
     * <p>invoke {@link #userManagementInProject(User, Project, UserRole)} if chosen role not null</p>
     * @param userId the id of user
     * @param projectId the id of project
     * @param role value of user role from UI, may be null
     */
    @Transactional
    public void changeUserRoleInProject(Long userId, Long projectId, UserRole role) {
        User user = userService.findOne(userId);
        if (role == null) {
            if (user.getRole().isDeveloper()) {
                user.setRole(UserRole.ROLE_QA);
            } else {
                user.setRole(UserRole.ROLE_DEVELOPER);
            }
            userRepository.save(user);
            return;
        }
        Project project = projectService.findById(projectId);
        userService.userManagementInProject(user, project, role);
    }

    /**
     * Remove user from project and set isDeleted true
     * <p>invoke {@link #userManagementInProject(User, Project, UserRole)}</p>
     * @param id the id value of user's instance
     */
    @Transactional
    public void setIsDeleted(long id, boolean toDelete) {
        User user = userService.findOne(id);
        user.setIsDeleted(toDelete);
        userService.userManagementInProject(user, null, UserRole.ROLE_USER);
    }

    /**
     * Return list of available roles for user
     * @param role current user's role
     * @return list of available roles for user
     */
    public List<UserRole> getAvailableRolesForUser(UserRole role) {
        List<UserRole> result = new ArrayList<>();
        switch (role) {
            case ROLE_USER:
                result.add(UserRole.ROLE_DEVELOPER);
                result.add(UserRole.ROLE_QA);
                result.add(UserRole.ROLE_PROJECT_MANAGER);
                return result;
            case ROLE_DEVELOPER:
                result.add(UserRole.ROLE_USER);
                result.add(UserRole.ROLE_QA);
                result.add(UserRole.ROLE_PROJECT_MANAGER);
                return result;
            case ROLE_QA:
                result.add(UserRole.ROLE_USER);
                result.add(UserRole.ROLE_DEVELOPER);
                result.add(UserRole.ROLE_PROJECT_MANAGER);
                return result;
            case ROLE_PROJECT_MANAGER:
                result.add(UserRole.ROLE_USER);
                result.add(UserRole.ROLE_DEVELOPER);
                result.add(UserRole.ROLE_PROJECT_MANAGER);
                return result;
            default:
                return result;
        }
    }

    /**
     * Set the following parameters that come from UI to user instance and save it into database.
     * <p>invoke {@link #userManagementInProject(User, Project, UserRole)}</p>
     * <p>if role is ROLE_PROJECT_MANAGER invokes {@link #saveProjectManager(Long, Long)}</p>
     * @param userId the id of the user
     * @param email the email address of the user
     * @param firstName the first name of user
     * @param lastName the last name of user
     * @param projectId the id of user's project, null if user not assigned to any Project or user's role is ROLE_USER
     * @param role the role of user
     * @param description the description of user
     */
    @Transactional
    public void saveEditedUser(Long userId, String email, String firstName, String lastName, Long projectId,
                               UserRole role, String description) {
        User user = userService.findOne(userId);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDescription(description);
        if(role.isProjectManager()){
            userService.saveProjectManager(userId, projectId);
            return;
        }
        Project project = projectService.findById(projectId);
        userService.userManagementInProject(user, project, role);
    }

    /**
     * Check if entered email is unique
     * @param email the entered email address
     * @param userId the id of the user that changes email address
     * @return true if there is a user with this email address and the id of the user not equals to the id of
     * existed user, otherwise false
     */
    public boolean isEmailExists(String email, Long userId){
        return userService.findByEmailIs(email).getId() != null && userService.findByEmailIs(email).getId() != userId;
    }

    /**
     * Change fields of role and project in User instance when user assigned or dropped from project or changed
     * his role in project
     * <p>invoke {@link #userRoleAndProjectValidator(User, Project, UserRole)}</p>
     * @param user the instance of User entity
     * @param project the instance of Project entity
     * @param role the user's role
     */
    @Transactional
    public void userManagementInProject(User user, Project project, UserRole role){
        user.setProject(project);
        user.setRole(role);
        userService.userRoleAndProjectValidator(user, project, role);
        userRepository.save(user);
    }

    /**
     * Validate values of user's project and role entered from UI
     * @param user the instance of User entity
     * @param project the instance of Project Entity
     * @param role the user's role
     */
    public void userRoleAndProjectValidator(User user, Project project, UserRole role){
        if(role.isUser() && project != null || !role.isUser() && project == null){
            user.setRole(UserRole.ROLE_USER);
            user.setProject(null);
        }
    }

    public Page<User> search (String firstName, String lastName, String email,UserRole role, Pageable pageable) {
        return userRepository.findByFirstNameContainingAndLastNameContainingAndEmailContainingAndRoleIs(firstName,
                lastName, email, role, pageable);
    }
}