package com.softserverinc.edu.services;

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

@Service
public class WorkLogService {

    @Autowired
    private WorkLogRepository workLogRepository;

    @Autowired
    private IssueService issueService;

    @Autowired
    private WorkLogSecurityService workLogSecurityService;

    public WorkLog findOne(Long id) {
        return workLogRepository.findOne(id);
    }

    public List<WorkLog> findByUserAndIssue(User user, Issue issue) {
        return workLogRepository.findByUserAndIssue(user, issue);
    }

    public List<WorkLog> findByIssue(Issue issue) {
        return workLogRepository.findByIssue(issue);
    }

    public Page<WorkLog> findByIssue(Issue issue, Pageable pageable) {
        return workLogRepository.findByIssue(issue, pageable);
    }
    public List<WorkLog> findByIssueId(Long issueId) {
        return workLogRepository.findByIssue(issueService.findById(issueId));
    }

    @Transactional
    public WorkLog save(WorkLog workLog) {
        return workLogRepository.saveAndFlush(workLog);
    }

    @Transactional
    public void delete(Long id) {
        workLogRepository.delete(id);
    }

    @Transactional
    public WorkLog update(WorkLog workLog) {
        return workLogRepository.saveAndFlush(workLog);
    }

    public void forNewWorkLogModel(ModelMap model, Long issueId, Pageable pageable) {
        if (!editorRequests(issueId)) {
            if (workLogSecurityService.isAuthenticated()) {
                model.addAttribute("workLogAction", issueId + "/worklog/save");
                model.addAttribute("workLog", getNewWorkLog(issueId));
                model.addAttribute("startDate", parseDateToSQLFormat(getCurrentIssue(issueId).getCreateTime()));
                model.addAttribute("endDate", getCurrentDate());
                model.addAttribute("permissionToUseWorkLogForm", workLogSecurityService.getPermissionToCreateWorkLog(issueId));
            }
            model.addAttribute("stage", "new");
            populateWorkLogModel(model, issueId, pageable);
        }
    }

    public Boolean editorRequests (Long issueId){
        return issueId == 0;
    }

    public void forEditWorkLogModel(ModelMap model, Long workLogId, Long issueId, Pageable pageable) {
        WorkLog currentWorkLog = findOne(workLogId);
        if (workLogSecurityService.isAuthenticated()) {
            model.addAttribute("stage", "edit");
            model.addAttribute("workLogAction", "../../worklog/save");
            model.addAttribute("id", currentWorkLog.getId());
            model.addAttribute("workLog", currentWorkLog);
            model.addAttribute("startDate", currentWorkLog.getStartDate());
            model.addAttribute("endDate", currentWorkLog.getEndDate());
            model.addAttribute("permissionToUseWorkLogForm", workLogSecurityService.getPermissionToEditWorkLog(issueId));
        }
        populateWorkLogModel(model, issueId, pageable);
    }

    public void populateWorkLogModel(ModelMap model, Long issueId, Pageable pageable) {
        model.addAttribute("currentUser", workLogSecurityService.getActiveUser());
        model.addAttribute("parsedDueDate", parseDateToSQLFormat(getCurrentIssue(issueId).getDueDate()));
        model.addAttribute("totalSpentTimeByAllUsers", getTotalSpentTimeForIssueByAllUsers(issueId));
        model.addAttribute("workLogsOfCurrentIssueByAllUsers", findByIssue(getCurrentIssue(issueId), pageable));
    }

    public WorkLog getNewWorkLog(Long issueId) {
        if (getCurrentIssue(issueId).getAssignee().equals(workLogSecurityService.getActiveUser())) {
            WorkLog workLog = new WorkLog();
            workLog.setIssue(getCurrentIssue(issueId));
            workLog.setUser(workLogSecurityService.getActiveUser());
            return workLog;
        }
        return null;
    }

    public Issue getCurrentIssue(Long issueId) {
        return issueService.findById(issueId);
    }

    public String getCurrentDate() {
        SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormatSQL.format(new Date());
    }

    public Long getTotalSpentTimeForIssueByAllUsers(Long issueId) {
        Long totalSpentTime = new Long(0);
        List<WorkLog> workLogList = findByIssue(getCurrentIssue(issueId));
        for (WorkLog worklogIterator : workLogList) {
            totalSpentTime += worklogIterator.getAmountOfTime();
        }
        return totalSpentTime;
    }

    public static String parseDateToSQLFormat(Date date) {
        SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormatSQL.format(date);
    }
}