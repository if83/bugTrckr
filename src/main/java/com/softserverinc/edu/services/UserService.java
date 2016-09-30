package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.IssueComment;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.IssueStatus;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public User findByPrincipal(Principal principal) {
        if (principal == null) {
            return null;
        }
        return userRepository.findByEmailIs(principal.getName());
    }

    public List<User> findUsersByRelease(ProjectRelease release) {
        return findUsersInProject(projectService.findById(release.getProject().getId()), false, 1);
    }

    public List<User> findByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    public User getProjectManager(Project project) {
        return userRepository.findByProjectAndRole(project, UserRole.ROLE_PROJECT_MANAGER);
    }

    public List<User> findUsersInProject(Project project, boolean isDeleted, int enabled) {
        List<User> users = userRepository.findByProjectAndIsDeletedAndEnabledIs(project, isDeleted, enabled);
        users.sort((user1, user2) -> Integer.valueOf(user1.getRole().ordinal()).compareTo(user2.getRole().ordinal()));
        return users;
    }

    public Page<User> findUsersByProjectPageable(Project project, Pageable pageable) {
        return userRepository.findByProjectAndRoleNot(project, UserRole.ROLE_PROJECT_MANAGER, pageable);
    }

    public Page<User> findByProject(Project project, int enabled, Pageable pageable) {
        return userRepository.findByProjectAndIsDeletedFalseAndEnabledIs(project, enabled, pageable);
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

    @Transactional
    public User saveUser(User user, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("css", "success");
        if (user.isNewuser()) {
            redirectAttributes.addFlashAttribute("msg", "User added successfully!");
        } else {
            redirectAttributes.addFlashAttribute("msg", "User updated successfully!");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        return userRepository.save(user);
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

    public Page<User> findUsersByRole(UserRole role, Pageable pageable) {
        return userRepository.findByRole(role, pageable);
    }

    public Page<User> searchByUsers(Project project, String searchParam, UserRole role, String searchedString,
                                    Pageable pageable) {
        if (role == null) {
            if (searchParam.equals("First Name")) {
                return userRepository.findByProjectAndFirstNameContainingAndRoleNot(project, searchedString,
                        UserRole.ROLE_PROJECT_MANAGER, pageable);
            }
            if (searchParam.equals("Last Name")) {
                return userRepository.findByProjectAndLastNameContainingAndRoleNot(project, searchedString,
                        UserRole.ROLE_PROJECT_MANAGER, pageable);
            }
            if (searchParam.equals("Email")) {
                return userRepository.findByEmailContainingAndProjectAndRoleNot(searchedString, project,
                        UserRole.ROLE_PROJECT_MANAGER, pageable);
            }
            return userRepository.findByProjectAndRoleNot(project, UserRole.ROLE_PROJECT_MANAGER, pageable);
        }
        if (searchParam.equals("First Name")) {
            return userRepository.findByProjectAndFirstNameContainingAndRole(project, searchedString, role, pageable);
        }
        if (searchParam.equals("Last Name")) {
            return userRepository.findByProjectAndLastNameContainingAndRole(project, searchedString, role, pageable);
        }
        if (searchParam.equals("Email")) {
            return userRepository.findByEmailContainingAndRole(searchedString, role, pageable);
        }
        return userRepository.findByProjectAndRole(project, role, pageable);
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

    @Transactional
    public User deleteUserFromProject(Long id, RedirectAttributes redirectAttributes) {
        User user = userService.findOne(id);
        redirectAttributes.addFlashAttribute("msg", String.format("%s was removed from project", user.getFullName()));
        user.setRole(UserRole.ROLE_USER);
        user.setProject(null);
        return userRepository.save(user);
    }

    @Transactional
    public User appointmentOfProjectManager(Long userId, Long projectId, RedirectAttributes redirectAttributes) {
        User user = userService.findOne(userId);
        Project project = projectService.findById(projectId);
        User exProjectManager = userService.getProjectManager(project);
        redirectAttributes.addFlashAttribute("msg", String.format("%s is Project Manager", user.getFullName()));
        if (!project.getUsers().isEmpty() && exProjectManager != null) {
            exProjectManager.setProject(null);
            exProjectManager.setRole(UserRole.ROLE_USER);
            userRepository.save(exProjectManager);
        }
        user.setProject(project);
        user.setRole(UserRole.ROLE_PROJECT_MANAGER);
        return userRepository.save(user);
    }

    @Transactional
    public User changeUserRoleInProject(User user, Project project, UserRole role, RedirectAttributes redirectAttributes) {
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
            return userRepository.save(user);
        }
        redirectAttributes.addFlashAttribute("msg", String.format("%s %s was added to %s ", role, user.getFullName(),
                project.getTitle()));
        user.setRole(role);
        user.setProject(project);
        return userRepository.save(user);
    }

    @Transactional
    public User setIsDeletedTrue(long id) {
        User user = userService.findOne(id);
        user.setIsDeleted(true);
        user.setProject(null);
        user.setRole(UserRole.ROLE_USER);
        return userRepository.save(user);
    }

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
}
