package com.softserverinc.edu.services.securityServices;

import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.services.ProjectReleaseService;
import com.softserverinc.edu.services.ProjectService;
import com.softserverinc.edu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ReleaseSecurityService {

    @Autowired
    private ProjectReleaseService releaseService;

    @Autowired
    private ProjectSecurityService projectSecurityService;


    public boolean hasPermissionToViewRelease(Long currentReleasetId) {
        return projectSecurityService.hasPermissionToViewProject(releaseService.findById(currentReleasetId).getProject().getId());
    }

    public boolean hasPermissionToAddRelease(Long currentProjectId) {
        return projectSecurityService.hasPermissionToEditProject(currentProjectId);
    }

    public boolean hasPermissionToEditRelease(Long currentReleasetId) {
        return projectSecurityService.hasPermissionToEditProject(releaseService.findById(currentReleasetId).getProject().getId());
    }

    public boolean hasPermissionToRemoveRelease(Long currentReleasetId) {
        return projectSecurityService.hasPermissionToEditProject(releaseService.findById(currentReleasetId).getProject().getId());
    }

}