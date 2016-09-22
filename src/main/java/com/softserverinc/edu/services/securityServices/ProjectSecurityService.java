package com.softserverinc.edu.services.securityServices;

import com.softserverinc.edu.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return getActiveUserRole().isAdmin() || getActiveUserRole().isProjectManager() &&
                getActiveUser().getProject().getId() == currentProjectId;

    }

}