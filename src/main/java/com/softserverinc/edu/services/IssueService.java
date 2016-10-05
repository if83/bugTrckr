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
     * List of all available issue statuses
     *
     * @param status Current issue status
     * @return list of issue statuses
     */
    public List<IssueStatus> getAvaliableStatusesForStatus(IssueStatus status) {
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
     * Returns the map of issue statuses
     *
     * @param selectedStatus Represents an issue status
     * @return The map of issue statuses
     */
    public Map<IssueStatus, String> getMapOfIssueStatuses(String selectedStatus) {
        Map<IssueStatus, String> result = new HashMap<>();
        for (IssueStatus status : getAvaliableStatusesForStatus(IssueStatus.valueOf(selectedStatus))) {
            result.put(status, status.toString());
        }
        return result;
    }

    /**
     * Saves changes in issue
     *
     * @param issue Represents the current issue
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
     * Saves issue changes from ajax
     *
     * @param issueId   Represents current issue id
     * @param inputData Represents input data
     * @param action    Represents action
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
     * Checks if input data is valid
     *
     * @param issue     Represents current issue
     * @param inputData Represents input data
     * @param action    Represents action
     * @return True, if the data is valid, false otherwise
     */
    private boolean isIssueInputDataValid(Issue issue, String inputData, HistoryAction action) {
        switch (action) {
            case CHANGE_ISSUE_ASSIGNEE:
                return isUserValidForIssue(issue, Long.valueOf(inputData));
            case CHANGE_ISSUE_STATUS:
                return isStatusValidForIssue(issue, IssueStatus.IN_PROGRESS.valueOf(inputData));
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

    private boolean isStatusValidForIssue(Issue issue, IssueStatus updatedStatus) {
        IssueStatus previousStatus = issue.getStatus();
        List<IssueStatus> avaliableStatuses = getAvaliableStatusesForStatus(previousStatus);
        for (IssueStatus status : avaliableStatuses) {
            if (updatedStatus.equals(status)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds an issue by release
     *
     * @param projectRelease Represents current release
     * @param pageable       Represents the total number of pages in the set of issues
     * @return Page of issues by release
     */
    @Transactional
    public Page<Issue> findIssuesByRelease(ProjectRelease projectRelease, Pageable pageable) {
        return issueRepository.findByProjectRelease(projectRelease, pageable);
    }

    /**
     * Checks if user is valid for current issue
     *
     * @param issue  Represents current issue
     * @param userId Represents user id
     * @return True, if an user is valid, false otherwise
     */
    private boolean isUserValidForIssue(Issue issue, Long userId) {
        User updatedUser = userService.findOne(userId);
        List<User> avaliableUsers = userService.findUsersForRelease(issue.getProjectRelease());
        for (User user : avaliableUsers) {
            if (updatedUser.equals(user)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds an issue by release and by issue title
     *
     * @param projectRelease Represents release for search
     * @param searchedString Represents issues title for search
     * @param pageable       Represents the total number of pages in the set of issues
     * @return Page of issues by release and by issue title
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
     * Removed specified issue
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
