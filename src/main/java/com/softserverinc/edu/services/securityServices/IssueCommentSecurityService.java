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

    public Boolean hasPermissionToCreateIssueComment() {
        return true; //TODO: PavlivAndriy add abilityToComment field to issue entity
    }

    public Boolean hasPermissionToRemoveIssueComment(Long issueCommentId) {
        if(getActiveUserRole().isAdmin() ||
                (getActiveUser().equals(issueCommentService.findOne(issueCommentId).getUser()) &&
                !getActiveUser().getRole().equals("USER") && isAuthenticated())||
                getActiveUser().equals(getProjectManager(issueCommentId))) {
            return true;
        }
        return false;
    }

    public Boolean hasPermissionToEditIssueComment(Long issueCommentId) {
        return hasPermissionToRemoveIssueComment(issueCommentId);
    }

    private User getProjectManager(Long issueCommentId){
        Issue issue = issueService.findById(issueCommentService.findOne(issueCommentId).getIssue().getId());
        ProjectRelease projectRelease = projectReleaseService.findByIssues(issue);
        Project project = projectService.findByProjectReleases(projectRelease);
        for (User user: project.getUsers()) {
            if(user.getRole().equals("PROJECT_MANAGER")){
                return user;
            }
        }
        return null;
    }
}
