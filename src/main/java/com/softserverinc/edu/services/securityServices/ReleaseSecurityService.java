package com.softserverinc.edu.services.securityServices;

import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.services.ProjectReleaseService;
import com.softserverinc.edu.services.ProjectService;
import com.softserverinc.edu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * Determines access rights to resources related with release
 */
@Service
public class ReleaseSecurityService extends BasicSecurityService {

    @Autowired
    private ProjectReleaseService releaseService;

    @Autowired
    private ProjectSecurityService projectSecurityService;


    public boolean hasPermissionToViewRelease(Long currentReleasetId) {
        return projectSecurityService.hasPermissionToViewProject(releaseService.findById(currentReleasetId).getProject().getId());
    }

    public boolean hasPermissionToAddRelease(Long currentProjectId) {
        return projectSecurityService.hasPermissionToProjectManagement(currentProjectId);
    }

    public boolean hasPermissionToEditRelease(Long currentReleasetId) {
        return projectSecurityService.hasPermissionToProjectManagement(releaseService.findById(currentReleasetId).getProject().getId());
    }

    public boolean hasPermissionToRemoveRelease(Long currentReleasetId) {
        return projectSecurityService.hasPermissionToProjectManagement(releaseService.findById(currentReleasetId).getProject().getId());
    }

}