package com.softserverinc.edu.services.securityServices;

import com.softserverinc.edu.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Determines access rights to resources related with project
 */
@Service
public class ProjectSecurityService extends BasicSecurityService {

    @Autowired
    private ProjectService projectService;

    public boolean hasPermissionToViewProject(Long currentProjectId) {
        return isAuthenticated() ? true : projectService.findById(currentProjectId).getGuestView();
    }

    /**
     * Checks user permission for editing users in project
     *
     * @param currentProjectId the id of current project
     * @return True, if user is Admin or user is Project Manager and id od his project equal to currentProjectId,
     * otherwise false
     */
    public boolean hasPermissionToProjectManagement(Long currentProjectId) {
        return getActiveUserRole().isAdmin() || getActiveUserRole().isProjectManager() &&
                getActiveUser().getProject().getId().equals(currentProjectId);
    }

}