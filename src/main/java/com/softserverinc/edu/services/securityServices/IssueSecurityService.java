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
        return ( (getActiveUserRole().isAdmin()) || (getActiveUserRole().isProjectManager()
                && getActiveUser().getProject().equals(getProjectByIssue(currentIssueId)))
                || ((getActiveUserRole().isDeveloper() || getActiveUserRole().isQA())
                && getActiveUser().getProject().equals(getProjectByIssue(currentIssueId) )
                && getActiveUser().equals(getIssueById(currentIssueId).getAssignee())) );
    }

    public boolean hasPermissionToEditIssue(Long currentIssueId) {
        return ( (getActiveUserRole().isAdmin()) || (getActiveUserRole().isProjectManager()
                && getActiveUser().getProject().equals(getProjectByIssue(currentIssueId)))
                || (((getActiveUserRole().isDeveloper() || getActiveUserRole().isQA()
                || getActiveUserRole().isProjectManager() )
                && (getIssueById(currentIssueId).getCreatedBy().equals(getActiveUser())))
                || ((getActiveUserRole().isDeveloper() || getActiveUserRole().isQA())
                && (getIssueById(currentIssueId).getEditAbility()
                && getActiveUser().getProject().equals(getProjectByIssue(currentIssueId) )
                && getActiveUser().equals(getIssueById(currentIssueId).getAssignee())))) );
    }

}
