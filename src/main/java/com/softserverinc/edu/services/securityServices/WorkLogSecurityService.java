package com.softserverinc.edu.services.securityServices;

import com.softserverinc.edu.entities.*;
import com.softserverinc.edu.entities.enums.UserRole;
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
        return getActiveUserRole().isAdmin() ||
                workLogService.findOne(workLogId).getUser().equals(getActiveUser())||
                getProjectManager(workLogId).equals(getActiveUser());

    }

    public Boolean hasPermissionToSaveWorkLog(Long issueId, WorkLog workLog) {
        return getActiveUserRole().isAdmin() ||
                issueService.findById(issueId).getAssignee().equals(getActiveUser()) ||
                workLog.getUser().equals(getActiveUser())||
                getProjectManager(workLog.getId()).equals(getActiveUser());
        }

    public Boolean hasPermissionToEditWorkLog(Long workLogId) {
        return getActiveUserRole().isAdmin() ||
                workLogService.findOne(workLogId).getUser().equals(getActiveUser()) ||
                getProjectManager(workLogId).equals(getActiveUser());

    }

    private User getProjectManager(Long workLogId){
        WorkLog workLog = workLogService.findOne(workLogId);
        Issue issue = issueService.findById(workLog.getIssue().getId());
        ProjectRelease projectRelease = projectReleaseService.findByIssues(issue);
        Project project = projectService.findByProjectReleases(projectRelease);
        for (User user: project.getUsers()) {
            return user.getRole().isProjectManager()? user: null;
        }
                return null;
    }

    public String getPermissionToCreateWorkLog(Long issueId){
        if (workLogService.getCurrentIssue(issueId).getAssignee().equals(getActiveUser())){
            return roleToSectionAuthorizeUseableString(getActiveUserRole());
        }
        return null;
    }

    public String getPermissionToEditWorkLog(Long issueId){
        if (didCurrentUserWorkOnCurrentIssue(getActiveUser(), issueId)){
            return roleToSectionAuthorizeUseableString(getActiveUserRole());
        }
        if (getActiveUserRole().isAdmin()) {
            return roleToSectionAuthorizeUseableString(getActiveUserRole());
        }
        return null;
    }

    public Boolean didCurrentUserWorkOnCurrentIssue(User currentUser, Long issueId){
        List<WorkLog> workLog = workLogService.findByIssueId(issueId);
        for (WorkLog workLogIterator: workLog) {
            if(workLogIterator.getUser().equals(currentUser)) {
                return true;
            }
        }
        return false;
    }
     public String roleToSectionAuthorizeUseableString (UserRole userRole) {
         return userRole.toString().toUpperCase().replace(' ', '_');
     }
}