package com.softserverinc.edu.services.securityServices;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.services.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueSecurityService extends BasicSecurityService {

    @Autowired
    private IssueService issueService;

    private Issue getIssueById(Long currentIssueId){
        return issueService.findById(currentIssueId);
    }

    private Project getProjectByIssue(Long currentIssueId){
        return issueService.findById(currentIssueId).getProject();
    }

    public boolean hasPermissionToViewIssue(Long currentIssueId) {
        return (isAuthenticated() || (!isAuthenticated() && getProjectByIssue(currentIssueId).getGuestView()));
    }

    public boolean hasPermissionToRemoveIssue(Long currentIssueId) {
         boolean isAdmin = getActiveUserRole().isAdmin();
         boolean isProjectManager = getActiveUserRole().isProjectManager();
         boolean isDeveloper = getActiveUserRole().isDeveloper();
         boolean isQA = getActiveUserRole().isQA();
         boolean isUserOnProject = false;
         boolean isAssigned = false;
        if (getActiveUser() != null && !getActiveUserRole().isAdmin()){
            isUserOnProject = getActiveUser().getProject().equals(getProjectByIssue(currentIssueId));
            isAssigned = getActiveUser().equals(getIssueById(currentIssueId).getAssignee());
        }
        return  isAdmin || (isProjectManager && isUserOnProject)
                || ((isDeveloper || isQA) && isUserOnProject && isAssigned) ;
    }

    public boolean hasPermissionToEditIssue(Long currentIssueId) {
        boolean isAdmin = getActiveUserRole().isAdmin();
        boolean isProjectManager = getActiveUserRole().isProjectManager();
        boolean isDeveloper = getActiveUserRole().isDeveloper();
        boolean isQA = getActiveUserRole().isQA();
        boolean isUserOnProject = false;
        if (getActiveUser()!= null && !getActiveUserRole().isAdmin()){
            isUserOnProject = getActiveUser().getProject().equals(getProjectByIssue(currentIssueId));
        }
        return  isAdmin || ((isDeveloper || isQA || isProjectManager) && isUserOnProject) ;
    }

}