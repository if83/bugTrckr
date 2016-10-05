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

/**
 * Provide access control to controller methods and UI features
 */
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

    private WorkLog getWorkLogById(Long workLogId) {
        return workLogService.findOne(workLogId);
    }

    /**
     * Check if current user has permission to remove WorkLog entry
     *
     * <p>invoke {@link #getProjectManager(Long)}</p>
     * @param workLogId workLog's id
     * @return Boolean representation of permission to remove WorkLog entry
     */
    public Boolean hasPermissionToRemoveWorkLog(Long workLogId) {
        return getActiveUserRole().isAdmin() || getWorkLogById(workLogId).getUser().equals(getActiveUser())||
                getProjectManager(workLogId).equals(getActiveUser());
    }

    /**
     * Check if current user has permission to save WorkLog entry
     *
     * <p>invoke {@link #getProjectManager(Long)}</p>
     * @param issueId issue's id for which is work logging
     * @param workLog WorkLog instance
     * @return Boolean representation of permission to remove WorkLog entry
     */
    public Boolean hasPermissionToSaveWorkLog(Long issueId, WorkLog workLog) {
        return getActiveUserRole().isAdmin() || issueService.findById(issueId).getAssignee().equals(getActiveUser()) ||
                workLog.getUser().equals(getActiveUser()) ||
                getProjectManager(workLog.getId()).equals(getActiveUser());
        }

    /**
     * Check if current user has permission to edit WorkLog entry
     *
     * <p>invoke {@link #getProjectManager(Long)}</p>
     * @param workLogId workLog's id
     * @return Boolean representation of permission to edit WorkLog entry
     */
    public Boolean hasPermissionToEditWorkLog(Long workLogId) {
        return getActiveUserRole().isAdmin() || getWorkLogById(workLogId).getUser().equals(getActiveUser()) ||
                getProjectManager(workLogId).equals(getActiveUser());
    }

    /**
     * Search project manager user of project for which is work logging
     *
     * @param workLogId workLog's id
     * @return user which is project manager of project for which is work logging
     */
    private User getProjectManager(Long workLogId){
        WorkLog workLog = getWorkLogById(workLogId);
        Issue issue = issueService.findById(workLog.getIssue().getId());
        ProjectRelease projectRelease = projectReleaseService.findByIssues(issue);
        Project project = projectService.findByProjectReleases(projectRelease);
        for (User user: project.getUsers()) {
            return user.getRole().isProjectManager()? user: null;
        }
                return null;
    }

    /**
     * Check if current user has permission to use work log form for creating new WorkLog entry
     *
     * @param issueId issue's id
     * @return String representation of role of user who has permission to create WorkLog entry
     */
    public String getPermissionToCreateWorkLog(Long issueId){
        if (workLogService.getCurrentIssue(issueId).getAssignee().equals(getActiveUser())){
            return roleToSectionAuthorizeUseableString(getActiveUserRole());
        }
        return null;
    }

    /**
     * Check if current user has permission to use work log form for editing WorkLog entry
     *
     * <p>invoke {@link #roleToSectionAuthorizeUseableString(UserRole)}</p>
     * @param issueId issue's id
     * @return String representation of user's role which has permission to edit WorkLog entry
     */
    public String getPermissionToEditWorkLog(Long issueId){
        if (didCurrentUserWorkOnCurrentIssue(getActiveUser(), issueId)){
            return roleToSectionAuthorizeUseableString(getActiveUserRole());
        }
        if (getActiveUserRole().isAdmin()) {
            return roleToSectionAuthorizeUseableString(getActiveUserRole());
        }
        return null;
    }

    /**
     * Check whether current user worked on issue
     *
     * @param user current user
     * @param issueId issue's id
     * @return Boolean representation of checking
     */
    public Boolean didCurrentUserWorkOnCurrentIssue(User user, Long issueId){
        List<WorkLog> workLog = workLogService.findByIssueId(issueId);
        for (WorkLog workLogIterator: workLog) {
            if(workLogIterator.getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Convert UserRole object to authorize sections usable format
     *
     * @param userRole user's role
     * @return String representation of user's role usable for authorize sections on jsp
     */
    public String roleToSectionAuthorizeUseableString (UserRole userRole) {
        return userRole.toString().toUpperCase().replace(' ', '_');
    }
}