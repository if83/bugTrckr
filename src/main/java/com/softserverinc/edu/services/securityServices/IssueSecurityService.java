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
        if (isAuthenticated() || (!isAuthenticated() && getProjectByIssue(currentIssueId).getGuestView()) ) {
            return true;
        }
        return false;
    }

    public boolean hasPermissionToRemoveIssue(Long currentIssueId) {
        if ( (getActiveUserRole().isAdmin()) || (getActiveUserRole().isProjectManager()
                && getActiveUser().getProject().equals(getProjectByIssue(currentIssueId)))
                || ((getActiveUserRole().isDeveloper() || getActiveUserRole().isQA())
                && getActiveUser().getProject().equals(getProjectByIssue(currentIssueId) )
                && getActiveUser().equals(getIssueById(currentIssueId).getAssignee())) ) {
            return true;
        }
        return false;
    }

    public boolean hasPermissionToEditIssue(Long currentIssueId) {
        if ( (getActiveUserRole().isAdmin()) || (getActiveUserRole().isProjectManager()
                && getActiveUser().getProject().equals(getProjectByIssue(currentIssueId)))
                || ((getActiveUserRole().isDeveloper() || getActiveUserRole().isQA())
                && (getIssueById(currentIssueId).getEditAbility())
                && getActiveUser().getProject().equals(getProjectByIssue(currentIssueId) )
                && getActiveUser().equals(getIssueById(currentIssueId).getAssignee())) ) {
            return true;
        }
        return false;
    }

}
