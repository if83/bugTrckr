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

    private Issue getIssueById(Long currentIssueId) {
        return issueService.findById(currentIssueId);
    }

    private Project getProjectByIssue(Long currentIssueId) {
        return issueService.findById(currentIssueId).getProject();
    }

    public boolean hasPermissionToViewIssue(Long currentIssueId) {
        return (isAuthenticated() || (!isAuthenticated() && getProjectByIssue(currentIssueId).getGuestView()));
    }

    public boolean hasPermissionToRemoveIssue(Long currentIssueId) {
        return isAdmin() || (isProjectManager() && isUserOnProject(currentIssueId))
                || ((isDeveloper() || isQA()) && isUserOnProject(currentIssueId) && isAssigned(currentIssueId));
    }

    public boolean hasPermissionToEditIssue(Long currentIssueId) {
        return isAdmin() || ((isDeveloper() || isQA() || isProjectManager()) && isUserOnProject(currentIssueId));
    }

    private boolean isAssigned(Long currentIssueId) {
        boolean isAssigned = false;
        if (getActiveUser() != null && !getActiveUserRole().isAdmin()) {
            isAssigned = getActiveUser().equals(getIssueById(currentIssueId).getAssignee());
        }
        return isAssigned;
    }

    private boolean isUserOnProject(Long currentIssueId) {
        boolean isUserOnProject = false;
        if (getActiveUser() != null && !getActiveUserRole().isAdmin() && currentIssueId != 0) {
            isUserOnProject = getActiveUser().getProject().equals(getProjectByIssue(currentIssueId));
        }
        return isUserOnProject;
    }

    private boolean isAdmin() {
        return getActiveUserRole().isAdmin();
    }

    private boolean isProjectManager() {
        return getActiveUserRole().isProjectManager();
    }

    private boolean isDeveloper() {
        return getActiveUserRole().isDeveloper();
    }

    private boolean isQA() {
        return getActiveUserRole().isQA();
    }

}