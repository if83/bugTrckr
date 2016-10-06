package com.softserverinc.edu.services;

import com.softserverinc.edu.constants.PageConstant;
import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.WorkLog;
import com.softserverinc.edu.repositories.WorkLogRepository;
import com.softserverinc.edu.services.securityServices.WorkLogSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Contain methods for working with workLog database table and auxiliary methods
 */
@Service
public class WorkLogService {

    @Autowired
    private WorkLogRepository workLogRepository;

    @Autowired
    private IssueService issueService;

    @Autowired
    private WorkLogSecurityService workLogSecurityService;

    /**
     * Find work log entry by id
     *
     * @param id work log entry's id
     * @return WorkLog entry with specified id or null if there is no such entry
     */
    public WorkLog findOne(Long id) {
        return workLogRepository.findOne(id);
    }

    /**
     * Find work log entry by issue and user.
     *
     * @param user user instance
     * @param issue issue instance
     * @return List of WorkLog entries with specified user and issue or empty list if there is no such entries
     */
    public List<WorkLog> findByUserAndIssue(User user, Issue issue) {
        return workLogRepository.findByUserAndIssue(user, issue);
    }

    /**
     * Find work log entry by issue
     *
     * @param issue issue instance
     * @return List of WorkLog entries with specified issue or empty list if there is no such entries
     */
    public List<WorkLog> findByIssue(Issue issue) {
        return workLogRepository.findByIssue(issue);
    }

    /**
     * Find work log entry by issue.
     *
     * @param issue issue instance
     * @param pageable Pageable object
     * @return Pageable list of WorkLog entries with specified issue or empty list if there is no such entries
     */
    public Page<WorkLog> findByIssue(Issue issue, Pageable pageable) {
        return workLogRepository.findByIssue(issue, pageable);
    }

    /**
     * Find work log entry by issue's id
     *
	 * <p>invoke {@link #getCurrentIssue(Long)}</p>
     * @param issueId issue's id
     * @return List of WorkLog entry which issue's field id is specified or empty list if there is no such entries
     */
    public List<WorkLog> findByIssueId(Long issueId) {
        return workLogRepository.findByIssue(getCurrentIssue(issueId));
    }

    /**
     * Save WorkLog entry
     *
     * @param workLog WorkLog instance
     * @return saved WorkLog instance
     */
    @Transactional
    public WorkLog save(WorkLog workLog) {
        return workLogRepository.saveAndFlush(workLog);
    }

    /**
     * Remove WorkLog entry by specified id
     *
     * @param id WorkLog entry's id
     */
    @Transactional
    public void delete(Long id) {
        workLogRepository.delete(id);
    }

    /**
     * Fill model with values required for saving new WorkLog entry
     *
     * <p>invoke {@link #populateWorkLogModel(ModelMap, Long, Pageable)}</p>
     * <p>invoke {@link #editorRequests(Long)}</p>
     * @param model ModelMap instance
     * @param issueId issue's id
     * @param pageable Pageable object
     */
    public void forNewWorkLogModel(ModelMap model, Long issueId, Pageable pageable) {
        if (!editorRequests(issueId)) {
            if (workLogSecurityService.isAuthenticated()) {
                model.addAttribute("workLogAction", issueId + "/worklog/save");
                model.addAttribute("workLog", getNewWorkLog(issueId));
                model.addAttribute("startDate", formatDate(getCurrentIssue(issueId).getCreateTime()));
                model.addAttribute("endDate", formatDate(new Date()));
                model.addAttribute("permissionToUseWorkLogForm",
                        workLogSecurityService.getPermissionToCreateWorkLog(issueId));
            }
            model.addAttribute("stage", "new");
            populateWorkLogModel(model, issueId, pageable);
        }
    }

    /**
     * Fill model with values required for editing WorkLog entry
     *
     * <p>invoke {@link #populateWorkLogModel(ModelMap, Long, Pageable)}</p>
     * @param model ModelMap instance
     * @param workLogId workLog's id
     * @param issueId issue's id
     * @param pageable Pageable object
     */
    public void forEditWorkLogModel(ModelMap model, Long workLogId, Long issueId, Pageable pageable) {
        WorkLog currentWorkLog = findOne(workLogId);
        if (workLogSecurityService.isAuthenticated()) {
            model.addAttribute("stage", "edit");
            model.addAttribute("workLogAction", "../../worklog/save");
            model.addAttribute("id", currentWorkLog.getId());
            model.addAttribute("workLog", currentWorkLog);
            model.addAttribute("startDate", formatDate(currentWorkLog.getStartDate()));
            model.addAttribute("endDate", formatDate(currentWorkLog.getEndDate()));
            model.addAttribute("permissionToUseWorkLogForm", workLogSecurityService.getPermissionToEditWorkLog(issueId));
        }
        populateWorkLogModel(model, issueId, pageable);
    }

    /**
     * Fill model with values required for creating and editing WorkLog entry
     *
     * <p>invoke {@link #formatDate(Date)}</p>
     * <p>invoke {@link #getTotalSpentTimeForIssueByAllUsers(Long)}</p>
     * <p>invoke {@link #getNewWorkLog(Long)}</p>
     * @param model  ModelMap instance
     * @param issueId issue's id
     * @param pageable Pageable object
     */
    private void populateWorkLogModel(ModelMap model, Long issueId, Pageable pageable) {
        model.addAttribute("currentUser", workLogSecurityService.getActiveUser());
        model.addAttribute("parsedDueDate", formatDate(getCurrentIssue(issueId).getDueDate()));
        model.addAttribute("totalSpentTimeByAllUsers", getTotalSpentTimeForIssueByAllUsers(issueId));
        model.addAttribute("workLogsOfCurrentIssueByAllUsers", findByIssue(getCurrentIssue(issueId), pageable));
    }

    /**
     * Prepare WorkLog instance ready to be used in form
     *
     * <p>invoke {@link #getCurrentIssue(Long)}</p>
     * @param issueId issue's id
     * @return WorkLog instance with specified issue and user fields
     */
    private WorkLog getNewWorkLog(Long issueId) {
        if (getCurrentIssue(issueId).getAssignee().equals(workLogSecurityService.getActiveUser())) {
            WorkLog workLog = new WorkLog();
            workLog.setIssue(getCurrentIssue(issueId));
            workLog.setUser(workLogSecurityService.getActiveUser());
            return workLog;
        }
        return null;
    }

    /**
     * Search issue by id
     *
     * @param issueId issue's id
     * @return issue with specified id
     */
    public Issue getCurrentIssue(Long issueId) {
        return issueService.findById(issueId);
    }

    /**
     * Return total spent work time for issue with specified id by all users
     *
     * <p>invoke {@link #getCurrentIssue(Long)}</p>
     * @param issueId issue's id
     * @return total spent work time for issue with specified id by all users
     */
    private Long getTotalSpentTimeForIssueByAllUsers(Long issueId) {
        Long totalSpentTime = 0L;
        List<WorkLog> workLogList = findByIssue(getCurrentIssue(issueId));
        for (WorkLog worklogIterator : workLogList) {
            totalSpentTime += worklogIterator.getAmountOfTime();
        }
        return totalSpentTime;
    }

    /**
     * Convert Date objects to String type
     *
     * @param date date
     * @return the String representation of date
     */
    public String formatDate(Date date) {
        return new SimpleDateFormat(PageConstant.DATE_FORMAT).format(date);
    }

    /**
     * Prevent requests from CKEditor tool
     *
     * @param issueId issue's id
     * @return Boolean representation of prevention
     */
    public Boolean editorRequests(Long issueId) {
        return issueId == 0 || issueId == null;
    }
}