package com.softserverinc.edu.services.securityServices;

import com.softserverinc.edu.entities.*;
import com.softserverinc.edu.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provide access control to controller methods and UI features
 */
@Service
public class IssueCommentSecurityService extends BasicSecurityService {

    @Autowired
    private IssueCommentService issueCommentService;

    @Autowired
    private IssueService issueService;

    @Autowired
    private ProjectReleaseService projectReleaseService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private WorkLogService workLogService;

    /**
     * Check if current user has permission to create new IssueComment entry
     *
     * @param issueId issue's id
     * @return Boolean representation of permission to create IssueComment entry
     */
    public Boolean hasPermissionToCreateIssueComment(Long issueId) {
        if (workLogService.editorRequests(issueId)) {
            return false;
        }
        else {
            return isAuthenticated() ||
                    projectService.findByProjectReleases(
                            projectReleaseService.findByIssues(
                                    issueService.findById(issueId))).getGuestAddComment();
        }
    }

    /**
     * Check if current user has permission to remove IssueComment entry
     * <p>invoke {@link #getProjectManager(Long)}</p>
     *
     * @param issueCommentId issueComment's id
     * @return Boolean representation of permission to remove IssueComment entry
     */
    public Boolean hasPermissionToRemoveIssueComment(Long issueCommentId) {
        return isAuthenticated() &&
                (getActiveUserRole().isAdmin() ||
                (getActiveUser().equals(issueCommentService.findOne(issueCommentId).getUser()) &&
                        !getActiveUser().getRole().isUser()) ||
                getActiveUser().equals(getProjectManager(issueCommentId)));
    }

    /**
     * Check if current user has permission to edit IssueComment entry
     * <p>invoke {@link #hasPermissionToRemoveIssueComment(Long)}</p>
     *
     * @param issueCommentId issueComment's id
     * @return Boolean representation of permission to edit IssueComment entry
     */
    public Boolean hasPermissionToEditIssueComment(Long issueCommentId) {
        return hasPermissionToRemoveIssueComment(issueCommentId);
    }

    /**
     * Search project manager user of project which issue is commented
     *
     * @param issueCommentId issueComment's id
     * @return user which is project manager of project which issue is commented
     */
    private User getProjectManager(Long issueCommentId) {
        Issue issue = issueService.findById(issueCommentService.findOne(issueCommentId).getIssue().getId());
        ProjectRelease projectRelease = projectReleaseService.findByIssues(issue);
        Project project = projectService.findByProjectReleases(projectRelease);
        for (User user : project.getUsers()) {
            if (user.getRole().isProjectManager()) {
                return user;
            }
        }
        return null;
    }
}
