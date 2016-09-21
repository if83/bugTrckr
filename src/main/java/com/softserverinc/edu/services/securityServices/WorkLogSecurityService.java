package com.softserverinc.edu.services.securityServices;

import com.softserverinc.edu.entities.*;
import com.softserverinc.edu.services.IssueService;
import com.softserverinc.edu.services.ProjectReleaseService;
import com.softserverinc.edu.services.ProjectService;
import com.softserverinc.edu.services.WorkLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkLogSecurityService extends BasicSecurityService {

    @Autowired
    private WorkLogService workLogService;

    @Autowired
    private IssueService issueService;

    @Autowired
    private ProjectReleaseService projectReleaseService;

    @Autowired
    private ProjectService projectService;

    public Boolean hasPermissionToRemoveWorkLog(Long workLogId) {
        if(getActiveUserRole().isAdmin() ||
                getActiveUser().equals(workLogService.findOne(workLogId).getUser())||
                getActiveUser().equals(getProjectManager(workLogId))) {
            return true;
        }
        return false;
    }

    public Boolean hasPermissionToSaveWorkLog(Long issueId, WorkLog workLog) {
        if(getActiveUserRole().isAdmin() ||
                getActiveUser().equals(issueService.findById(issueId).getAssignee()) ||
                getActiveUser().equals(workLog.getUser())||
                getActiveUser().equals(getProjectManager(workLog.getId()))) {
            return true;
        }
        return false;
    }

    public Boolean hasPermissionToEditWorkLog(Long workLogId) {
        if(getActiveUserRole().isAdmin() ||
                getActiveUser().equals(workLogService.findOne(workLogId).getUser()) ||
                getActiveUser().equals(getProjectManager(workLogId))) {
            return true;
        }
        return false;
    }

    private User getProjectManager(Long workLogId){
        WorkLog workLog = workLogService.findOne(workLogId);
        Issue issue = issueService.findById(workLog.getIssue().getId());
        ProjectRelease projectRelease = projectReleaseService.findByIssues(issue);
        Project project = projectService.findByProjectReleases(projectRelease);
        for (User user: project.getUsers()) {
            if(user.getRole().equals("PROJECT_MANAGER")){
                return user;
            }
        }
        return null;
    }

    public String getPermissionToCreateWorkLog(Long issueId){
        if(!isAuthenticated()) {
            return null;
        }
        if (getActiveUser().equals(workLogService.getCurrentIssue(issueId).getAssignee())){
            return (getActiveUser().getRole().toString().toUpperCase().replace(' ', '_'));
        }
        return null;
    }

    public String getPermissionToEditWorkLog(Long issueId){
        if(!isAuthenticated()) {
            return null;
        }
        if (didCurrentUserWorkOnCurrentIssue(getActiveUser(), issueId)){
            return (getActiveUser().getRole().toString().toUpperCase().replace(' ', '_'));
        }
        return null;
    }

    public Boolean didCurrentUserWorkOnCurrentIssue(User currentUser, Long issueId){
        List<WorkLog> workLog = workLogService.findByIssue(issueService.findById(issueId));
        for (WorkLog workLogIterator: workLog) {
            if(workLogIterator.getUser().equals(currentUser)) {
                return true;
            }
        }
        return false;
    }
}