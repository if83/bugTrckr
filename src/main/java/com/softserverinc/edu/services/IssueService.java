package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.HistoryAction;
import com.softserverinc.edu.entities.enums.IssueStatus;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.repositories.IssueRepository;
import com.softserverinc.edu.services.securityServices.BasicSecurityService;
import com.softserverinc.edu.services.securityServices.IssueSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contain methods for working with issue database table and auxiliary methods
 */
@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private IssueService issueService;

    @Autowired
    private BasicSecurityService basicSecurityService;

    @Autowired
    private IssueSecurityService issueSecurityService;

    public Issue findById(Long id) {
        return issueRepository.findOne(id);
    }

    /**
     * Saves issue changes, that come from ajax, into database.
     * Before that checks is input data valid (for this it invokes
     * {@see #isIssueInputDataValid(Issue issue, String inputData, HistoryAction action)}).
     * Writes changes to the history.
     *
     * @param issueId   Represents id of current issue
     * @param inputData Represents changed data (updated status, assignee etc.)
     * @param action    Represents action (what's changed)
     */
    public void saveIssueChangesFromAjax(Long issueId, String inputData, String action) {
        Issue issue = findById(issueId);
        HistoryAction historyAction = HistoryAction.valueOf(action);
        User changedByUser = basicSecurityService.getActiveUser();
        if (isIssueInputDataValid(issue, inputData, historyAction)) {
            historyService.writeToHistory(issue, changedByUser, inputData, historyAction);
            save(issue);
        }
    }

    /**
     * Returns all available issue statuses for selected status
     *
     * @param status current issue status
     * @return list of issue statuses
     */
    public List<IssueStatus> getAvailableStatusesForStatus(IssueStatus status) {
        List<IssueStatus> result = new ArrayList<>();
        switch (status) {
            case OPEN:
                result.add(IssueStatus.IN_PROGRESS);
                result.add(IssueStatus.INVALID);
                return result;
            case IN_PROGRESS:
                result.add(IssueStatus.OPEN);
                result.add(IssueStatus.QA_VALIDATION);
                result.add(IssueStatus.INVALID);
                return result;
            case INVALID:
                result.add(IssueStatus.OPEN);
                return result;
            case QA_VALIDATION:
                result.add(IssueStatus.OPEN);
                result.add(IssueStatus.RESOLVED);
                return result;
            case RESOLVED:
                result.add(IssueStatus.OPEN);
                result.add(IssueStatus.INVALID);
                return result;
            default:
                return result;
        }
    }

    /**
     * Returns statuses from {@see #getAvailableStatusesForStatus(IssueStatus status)}
     * and wrap them into map (it's simpler for JSon parsing)
     *
     * @param selectedStatus represents selected status
     * @return Map with issue statuses and their string representation
     */
    public Map<IssueStatus, String> getMapOfIssueStatuses(String selectedStatus) {
        Map<IssueStatus, String> result = new HashMap<>();
        for (IssueStatus status : getAvailableStatusesForStatus(IssueStatus.valueOf(selectedStatus))) {
            result.put(status, status.toString());
        }
        return result;
    }

    /**
     * Saves issue changes and write them to the history.
     *
     * @param issue represents the current issue
     */
    public void saveIssueChanges(Issue issue) {
        User changedByUser = basicSecurityService.getActiveUser();
        if (issueService.isNewIssue(issue)) {
            issue.setCreatedBy(changedByUser);
        }
        historyService.writeToHistory(issue, changedByUser);
        save(issue);
    }

    /**
     * Checks if input data is valid for selected issue.
     * For this it invokes more specific methods.
     *
     * @param issue     Represents current issue
     * @param inputData Represents input data
     * @param action    Represents action (what's changed)
     * @return True, if the data is valid, false otherwise
     */
    private boolean isIssueInputDataValid(Issue issue, String inputData, HistoryAction action) {
        switch (action) {
            case CHANGE_ISSUE_ASSIGNEE:
                return isUserValidForIssue(issue, Long.valueOf(inputData));
            case CHANGE_ISSUE_STATUS:
                return isStatusValidForIssue(issue, IssueStatus.valueOf(inputData));
        }
        return false;
    }

    /**
     * Checks if current issue is new
     *
     * @param issue Represents current issue
     * @return True, if an issue is new, false otherwise
     */
    boolean isNewIssue(Issue issue) {
        return (issue.getId() == null || issue.getId() == 0L);
    }

    /**
     * Checks if changed status is valid for current issue.
     * Invokes {@see #getAvailableStatusesForStatus(IssueStatus status)}
     * and compare input status with available statuses for it.
     *
     * @param issue         current issue
     * @param updatedStatus selected status
     * @return true if status is valid, false otherwise
     */
    private boolean isStatusValidForIssue(Issue issue, IssueStatus updatedStatus) {
        IssueStatus previousStatus = issue.getStatus();
        List<IssueStatus> availableStatuses = getAvailableStatusesForStatus(previousStatus);
        for (IssueStatus status : availableStatuses) {
            if (updatedStatus.equals(status)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds an issue for release
     *
     * @param projectRelease Represents current release
     * @param pageable       Represents the total number of pages in the set of issues
     * @return Page of issues by release
     */
    @Transactional
    public Page<Issue> findIssuesForRelease(ProjectRelease projectRelease, Pageable pageable) {
        return issueRepository.findByProjectRelease(projectRelease, pageable);
    }

    /**
     * Checks if changed assignee is valid for current issue.
     * Invokes {@see com.softserverinc.edu.services.UserService#findUsersForRelease(ProjectRelease release)}
     * and compare input assignee with available users on release.
     *
     * @param issue  current issue
     * @param userId represents id of selected user
     * @return true, if the user is valid, false otherwise
     */
    private boolean isUserValidForIssue(Issue issue, Long userId) {
        User updatedUser = userService.findOne(userId);
        List<User> availableUsers = userService.findUsersForRelease(issue.getProjectRelease());
        for (User user : availableUsers) {
            if (updatedUser.equals(user)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds issues by title (or title substring) for selected release.
     *
     * @param projectRelease Represents release for search
     * @param searchedString Represents substring of title for search
     * @param pageable       Represents the total number of pages in the set of issues
     * @return Page of issues
     */
    @Transactional
    public Page<Issue> findByReleaseAndIssueTitle(ProjectRelease projectRelease, String searchedString, Pageable pageable) {
        return issueRepository.findByProjectReleaseAndTitleContaining(projectRelease, searchedString, pageable);
    }

    /**
     * Finds an issue by project
     *
     * @param project        Represents project for search
     * @param searchedString Represents issues title for search
     * @param pageable       Represents the total number of pages in the set of issues
     * @return Page of issues by project and by issue title
     */
    @Transactional
    public Page<Issue> findIssuesByProject(Project project, String searchedString, Pageable pageable) {
        return issueRepository.findByProjectAndTitleContaining(project, searchedString, pageable);
    }

    /**
     * Finds all issues
     *
     * @return List of all issues
     */
    public List<Issue> findAll() {
        return issueRepository.findAll();
    }

    /**
     * Saves specified issue
     *
     * @param issue Represents current issue
     * @return Issue, that has been saved
     */
    @Transactional
    public Issue save(Issue issue) {
        return issueRepository.saveAndFlush(issue);
    }

    /**
     * Removes specified issue
     *
     * @param id Represents an issue id
     */
    @Transactional
    public void delete(Long id) {
        issueRepository.delete(id);
    }

    /**
     * Updates and saves specified issue
     *
     * @param issue Represents an issue
     * @return Issue, that has been updated and saved
     */
    @Transactional
    public Issue update(Issue issue) {
        return issueRepository.saveAndFlush(issue);
    }

    /**
     * Finds an issue by containing title
     *
     * @param title    Represents the title for search
     * @param pageable Represents the total number of pages in the set of issues
     * @return Page of issues with this title
     */
    public Page<Issue> findByTitleContaining(String title, Pageable pageable) {
        return issueRepository.findByTitleContaining(title, pageable);
    }

    /**
     * Finds page of all issues
     *
     * @param pageable Represents the total number of pages in the set of issues
     * @return Page of all issues
     */
    public Page<Issue> findAll(Pageable pageable) {
        return issueRepository.findAll(pageable);
    }

    /**
     * Finds page of issues by project
     *
     * @param project  Represents the project
     * @param pageable Represents the total number of pages in the set of issues
     * @return Page of issue by a project
     */
    @Transactional
    public Page<Issue> findByProject(Project project, Pageable pageable) {
        return issueRepository.findByProject(project, pageable);
    }

    /**
     * Finds page of issues by user
     *
     * @param principal Represents a user, who is authenticated right now
     * @param pageable  Represents the total number of pages in the set of issues
     * @return Page of issues by user
     */
    public Page<Issue> findByUser(Principal principal, Pageable pageable) {
        return issueRepository.findByAssignee((userService.findByEmailIs(principal.getName())), pageable);
    }

    /**
     * Checks users authentication
     *
     * @return List of users, or list of Project managers, if user is not authenticated
     */
    public List<User> checkAuthentication() {
        if (!issueSecurityService.isAuthenticated()) {
            return userService.findByRole(UserRole.ROLE_PROJECT_MANAGER);
        }
        return userService.findAll();
    }

}
