package com.softserverinc.edu.services.securityServices;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.services.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provide access control to controller methods and UI features
 */
@Service
public class IssueSecurityService extends BasicSecurityService {

    @Autowired
    private IssueService issueService;

    private Issue getIssueById(Long currentIssueId) {
        return issueService.findById(currentIssueId);
    }

    /**
     * Check on which project issue is assigned
     *
     * @param currentIssueId Represents current issue by this id
     * @return Project, where this issue is located
     */
    private Project getProjectByIssue(Long currentIssueId) {
        return issueService.findById(currentIssueId).getProject();
    }

    /**
     * Check for user permission to view an issue
     *
     * @param currentIssueId Represents current issue by this id
     * @return True, if user can view this issue, otherwise false
     */
    public boolean hasPermissionToViewIssue(Long currentIssueId) {
        return (isAuthenticated() || (!isAuthenticated() && getProjectByIssue(currentIssueId).getGuestView()));
    }

    /**
     * Check for user permission to remove an issue
     *
     * @param currentIssueId Represents current issue by this id
     * @return True, if user can remove this issue, otherwise false
     */
    public boolean hasPermissionToRemoveIssue(Long currentIssueId) {
        return isAdmin() || (isProjectManager() && isUserOnProject(currentIssueId))
                || ((isDeveloper() || isQA()) && isUserOnProject(currentIssueId) && isAssigned(currentIssueId));
    }

    /**
     * Check for user permission to edit an issue
     *
     * @param currentIssueId Represents current issue by this id
     * @return True, if user can edit this issue, otherwise false
     */
    public boolean hasPermissionToEditIssue(Long currentIssueId) {
        return isAdmin() || ((isDeveloper() || isQA() || isProjectManager()) && isUserOnProject(currentIssueId));
    }

    /**
     * Check if user is assigned to this issue
     *
     * @param currentIssueId Represents current issue by this id
     * @return True, if user assigned to this issue, otherwise false
     */
    private boolean isAssigned(Long currentIssueId) {
        boolean isAssigned = false;
        if (getActiveUser() != null && !getActiveUserRole().isAdmin()) {
            isAssigned = getActiveUser().equals(getIssueById(currentIssueId).getAssignee());
        }
        return isAssigned;
    }

    /**
     * Check if user is assigned to the project this issue id
     *
     * @param currentIssueId Represents current issue by this id
     * @return True, if user assigned to this project, otherwise false
     */
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