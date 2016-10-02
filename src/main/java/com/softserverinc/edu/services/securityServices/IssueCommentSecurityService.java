package com.softserverinc.edu.services.securityServices;

import com.softserverinc.edu.entities.*;
import com.softserverinc.edu.services.IssueCommentService;
import com.softserverinc.edu.services.IssueService;
import com.softserverinc.edu.services.ProjectReleaseService;
import com.softserverinc.edu.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Boolean hasPermissionToCreateIssueComment(Long issueId) {
        if (issueId == null) { // TODO: prokhorenkovkv use method
            return false;
        }
        else {
            return isAuthenticated() ||
                    projectService.findByProjectReleases(
                            projectReleaseService.findByIssues(
                                    issueService.findById(issueId))).getGuestAddComment();
        }
    }

    public Boolean hasPermissionToRemoveIssueComment(Long issueCommentId) {
        return isAuthenticated() &&
                (getActiveUserRole().isAdmin() ||
                (getActiveUser().equals(issueCommentService.findOne(issueCommentId).getUser()) &&
                        !getActiveUser().getRole().isUser()) ||
                getActiveUser().equals(getProjectManager(issueCommentId)));
    }

    public Boolean hasPermissionToEditIssueComment(Long issueCommentId) {
        return hasPermissionToRemoveIssueComment(issueCommentId);
    }

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
