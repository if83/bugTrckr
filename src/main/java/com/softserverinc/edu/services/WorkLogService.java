package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.WorkLog;
import com.softserverinc.edu.repositories.WorkLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class WorkLogService {

    @Autowired
    private WorkLogRepository workLogRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private IssueService issueService;

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

    public List<WorkLog> findAll() {
        return workLogRepository.findAll();
    }

    public Page<WorkLog> findAll(Pageable pageable) {
        return workLogRepository.findAll(pageable);
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

    public String getPermissionToCreateWorkLog(Principal principal, Long issueId){
        if ((userService.findByEmail(principal.getName()).get(0)).equals(getCurrentIssue(issueId).getAssignee())){
            return (userService.findByEmail(principal.getName()).get(0)).getRole().toString().toUpperCase().replace(' ', '_');
        }
        return null;
    }

    public String getPermissionToEditWorkLog(Principal principal, Long issueId){
        if (didCurrentUserWorkOnCurrentIssue(principal, issueId)){
            return (userService.findByEmail(principal.getName()).get(0)).getRole().toString().toUpperCase().replace(' ', '_');
        }
        return null;
    }

    public Boolean didCurrentUserWorkOnCurrentIssue(Principal principal, Long issueId){
        List<WorkLog> workLog = findByIssue(issueService.findById(issueId));
        for (WorkLog workLogIterator: workLog) {
            if(workLogIterator.getUser().equals(userService.findByEmail(principal.getName()).get(0))) {
                return true;
            }
        }
        return false;
    }

    public void forNewWorkLogModel(ModelMap model, Long issueId, Principal principal, Pageable pageable) {
        model.addAttribute("stage", "new");
        model.addAttribute("workLogAction", issueId + "/worklog/save");
        model.addAttribute("permissionToUseWorkLogForm", getPermissionToCreateWorkLog(principal, issueId));
        model.addAttribute("workLog", getNewWorkLog(issueId, principal));
        model.addAttribute("startDate", parseDateToSQLFormat(issueService.findById(issueId).getCreateTime()));
        model.addAttribute("endDate", getCurrentDate());
        populateWorkLogModel(model, issueId, principal, pageable);
    }

    public void forEditWorkLogModel(ModelMap model, Long workLogId, Long issueId, Principal principal, Pageable pageable) {
        WorkLog currentWorkLog = findOne(workLogId);
        model.addAttribute("workLogAction", "../../worklog/save");
        model.addAttribute("stage", "expelliarmus");
        model.addAttribute("permissionToUseWorkLogForm", getPermissionToEditWorkLog(principal, issueId));
        model.addAttribute("id", currentWorkLog.getId());
        model.addAttribute("workLog", currentWorkLog);
        model.addAttribute("startDate", currentWorkLog.getStartDate());
        model.addAttribute("endDate", currentWorkLog.getEndDate());
        populateWorkLogModel(model, currentWorkLog.getIssue().getId(), principal, pageable);
    }

    public void populateWorkLogModel(ModelMap model, Long issueId, Principal principal, Pageable pageable) {
        model.addAttribute("currentUser", userService.findByEmail(principal.getName()).get(0));
        model.addAttribute("issue", issueService.findById(issueId));
        model.addAttribute("parsedDueDate", parseDateToSQLFormat(issueService.findById(issueId).getDueDate()));
        model.addAttribute("totalSpentTimeByAllUsers", getTotalSpentTimeForIssueByAllUsers(issueId));
        model.addAttribute("workLogsOfCurrentIssueByAllUsers", findByIssue(getCurrentIssue(issueId), pageable));
    }

    public WorkLog getNewWorkLog(Long issueId, Principal principal) {
        WorkLog workLog = new WorkLog();
        workLog.setIssue(issueService.findById(issueId));
        workLog.setUser(userService.findByEmail(principal.getName()).get(0));
        return workLog;
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