package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return userRepository.findOne(id);
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

    public List<User> findByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    public User getProjectManagerOfProject(Project project) {
        return userRepository.findByProjectAndRoleIsAndIsDeleted(project, UserRole.ROLE_PROJECT_MANAGER, false);
    }

    public List<User> findUsersInProject(Project project, boolean isDeleted, int enabled) {
        List<User> users = userRepository.findByProjectAndIsDeletedAndEnabledIs(project, isDeleted, enabled);
        users.sort((user1, user2) -> Integer.valueOf(user1.getRole().ordinal()).compareTo
                (Integer.valueOf(user2.getRole().ordinal())));
        return users;
    }

    public Page<User> findUsersInProjectPageable(Project project, boolean isDeleted, int enabled, Pageable pageable) {
        return userRepository.findByProjectAndRoleNotAndIsDeletedAndEnabledIs(project, UserRole.ROLE_PROJECT_MANAGER,
                isDeleted, enabled, pageable);
    }

    public Page<User> findByProject(Project project, int enabled, Pageable pageable) {
        return userRepository.findByProjectAndIsDeletedFalseAndEnabledIs(project, enabled, pageable);
    }

    public List<User> findByFirstNameContainingAndLastNameContaining(String firstName, String lastName) {
        return userRepository.findByFirstNameContainingAndLastNameContaining(firstName, lastName);
    }

    public List<User> findByFirstNameContaining(String firstName) {
        return userRepository.findByFirstNameContaining(firstName);
    }

    public List<User> findByLastNameContaining(String lastName) {
        return userRepository.findByLastNameContaining(lastName);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User save(User user) {
        return userRepository.saveAndFlush(user);
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

    public Page<User> findByIsDeletedFalseAndEnabledIs(int enabled, Pageable pageable) {
        return userRepository.findByIsDeletedFalseAndEnabledIs(enabled, pageable);
    }

    public Page<User> findNotDeletedUsersByRole(UserRole role, boolean isDeleted, int enabled, Pageable pageable) {
        return userRepository.findByRoleAndIsDeletedAndEnabledIs(role, isDeleted, enabled, pageable);
    }

    public Page<User> userSearch(Project project, String searchParam, UserRole role, String searchedString,
                                 Pageable pageable) {
        if (role == null) {
            if (searchParam.equals("First Name")) {
                return userRepository.findByProjectAndFirstNameContainingAndRoleNotAndIsDeletedAndEnabledIs(
                        project, searchedString, UserRole.ROLE_PROJECT_MANAGER, false, 1, pageable);
            } else if (searchParam.equals("Last Name")) {
                return userRepository.findByProjectAndLastNameContainingAndRoleNotAndIsDeletedAndEnabledIs(
                        project, searchedString, UserRole.ROLE_PROJECT_MANAGER, false, 1, pageable);
            } else if (searchParam.equals("Email")) {
                return userRepository.findByEmailContainingAndRoleNotAndIsDeletedAndEnabledIs(searchedString,
                        UserRole.ROLE_PROJECT_MANAGER, false, 1, pageable);
            } else return userRepository.findByProjectAndRoleNotAndIsDeletedAndEnabledIs(project,
                    UserRole.ROLE_PROJECT_MANAGER, false, 1, pageable);
        }
        if (searchParam.equals("First Name")) {
            return userRepository.findByProjectAndFirstNameContainingAndRoleAndIsDeletedAndEnabledIs(
                    project, searchedString, role, false, 1, pageable);
        } else if (searchParam.equals("Last Name")) {
            return userRepository.findByProjectAndLastNameContainingAndRoleAndIsDeletedAndEnabledIs(
                    project, searchedString, role, false, 1, pageable);
        } else if (searchParam.equals("Email")) {
            return userRepository.findByEmailContainingAndRoleAndIsDeletedAndEnabledIs(searchedString,
                    role, false, 1, pageable);
        } else return userRepository.findByProjectAndRoleAndIsDeletedAndEnabledIs(project, role, false, 1, pageable);
    }

    public User getAvaliableUser(User user) {
        if (user.isDeleted()) {
            User mockUser = new User();
            mockUser.setFirstName(user.getFirstName());
            mockUser.setLastName(user.getLastName());
            return mockUser;
        }
        return user;
    }

    @Transactional
    public User deleteUserFromProject(Long id, RedirectAttributes redirectAttributes) {
        User user = userService.findOne(id);
        redirectAttributes.addFlashAttribute("msg", String.format("%s was removed from project", user.getFullName()));
        user.setRole(UserRole.ROLE_USER);
        user.setProject(null);
        return userService.save(user);
    }

    @Transactional
    public User projectManagerAppointment(Long userId, Long projectId, RedirectAttributes redirectAttributes) {
        User user = userService.findOne(userId);
        Project project = projectService.findById(projectId);
        User exProjectManager = userService.getProjectManagerOfProject(project);
        redirectAttributes.addFlashAttribute("msg", String.format("%s is Project Manager", user.getFullName()));
        if (!project.getUsers().isEmpty() && exProjectManager != null) {
            exProjectManager.setProject(null);
            exProjectManager.setRole(UserRole.ROLE_USER);
            userService.save(exProjectManager);
        }
        user.setProject(project);
        user.setRole(UserRole.ROLE_PROJECT_MANAGER);
        return userService.save(user);
    }

    @Transactional
    public User changeUserRole(User user, Project project, UserRole role, RedirectAttributes redirectAttributes) {
        if (user.getProject() == project) {
            if (user.getRole().isDeveloper()) {
                redirectAttributes.addFlashAttribute("msg", String.format("position of %s role was changed to QA",
                        user.getFullName()));
                user.setRole(UserRole.ROLE_QA);
            } else {
                redirectAttributes.addFlashAttribute("msg", String.format("position of %s was changed to Developer",
                        user.getFullName()));
                user.setRole(UserRole.ROLE_DEVELOPER);
            }
            return userService.save(user);
        }
        redirectAttributes.addFlashAttribute("msg", String.format("%s %s was added to %s ", role, user.getFullName(),
                project.getTitle()));
        user.setRole(role);
        user.setProject(project);
        return userService.save(user);
    }
}
