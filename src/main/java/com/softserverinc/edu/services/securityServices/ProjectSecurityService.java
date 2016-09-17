package com.softserverinc.edu.services.securityServices;

import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.services.ProjectService;
import com.softserverinc.edu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProjectSecurityService {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    public boolean hasPermissionToViewProject(Long currentProjectId) {
        if (getActiveUserRole().isAdmin()
                || getActiveUserRole().isProjectManager()
                || getActiveUserRole().isDeveloper()
                || getActiveUserRole().isQA()
                || getActiveUserRole().isUser()) {
            return true;
        } else if (getActiveUserRole().isGuest()) {
            return projectService.findById(currentProjectId).getGuestView();
        }
        return false;
    }

    public boolean hasPermissionToEditProject(Long currentProjectId) {
        if (getActiveUserRole().isAdmin()) {
            return true;
        } else if (getActiveUserRole().isProjectManager()) {
            User user = userService.getProjectManagerOfProject(projectService.findById(currentProjectId));
            return getActiveUser().equals(user);
        }
        return false;
    }

    private UserRole getActiveUserRole() {
        return UserRole.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString());
    }

    private User getActiveUser() {
        return userService.findByEmailIs(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}