package com.softserverinc.edu.services.securityServices;

import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.services.ProjectService;
import com.softserverinc.edu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProjectSecurityService extends BasicSecurityService {

    @Autowired
    private ProjectService projectService;

    public boolean hasPermissionToViewProject(Long currentProjectId) {
        if (isAuthenticated()) {
            return true;
        }
        return projectService.findById(currentProjectId).getGuestView();
    }

    public boolean hasPermissionToProjectManagement(Long currentProjectId) {
        if (getActiveUserRole().isAdmin() ||
                (getActiveUserRole().isProjectManager() && getActiveUser().getProject().getId() == currentProjectId)) {
            return true;
        }
        return false;
    }

}