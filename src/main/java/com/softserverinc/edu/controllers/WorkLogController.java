package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.WorkLog;
import com.softserverinc.edu.services.IssueService;
import com.softserverinc.edu.services.UserService;
import com.softserverinc.edu.services.WorkLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class WorkLogController {

    public static final Logger LOGGER = LoggerFactory.getLogger(WorkLogController.class);

    @Autowired
    private WorkLogService workLogService;

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "issue/{issueId}/worklog", method = RequestMethod.GET)
    public String workLog(@PathVariable Long issueId, ModelMap model, Principal principal) {
        newWorkLogModelMap(model, issueId, principal);
        return "worklog";
    }

    @RequestMapping(value = "issue/{issueId}/worklog/save", method = RequestMethod.POST)
    public String addWorkLogPost(@PathVariable Long issueId,
                                 @ModelAttribute("worklog") @Valid WorkLog workLog,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/issue/" + issueId + "/worklog";
        } else {
            workLogService.save(workLog);
            LOGGER.info("Worklog saved, id= " + workLog.getId());
            return "redirect:/issue/" + issueId + "/worklog";
        }
    }

    @RequestMapping(value = "issue/{issueId}/worklog/{workLogId}/edit", method = RequestMethod.GET)
    public String editWorkLog(@PathVariable Long workLogId, @PathVariable Long issueId, ModelMap model) {
        model.addAttribute("issueId", issueId);
        editWorkLogModelMap(model, workLogId);
        return "worklog";
    }

    @RequestMapping(value = "issue/{issueId}/worklog/{worklogId}/remove", method = RequestMethod.GET)
    public String removeWorkLog(@PathVariable("worklogId") long worklogId,
                                final RedirectAttributes redirectAttributes) {
        workLogService.delete(worklogId);
        LOGGER.debug("Worklog " + worklogId + " is removed!");
        return "redirect:/issue/{issueId}/worklog";
    }

    private void newWorkLogModelMap(ModelMap model, Long issueId, Principal principal) {
        model.addAttribute("action", "worklog/save");
        model.addAttribute("workLog", getNewWorkLog(issueId, principal));
        model.addAttribute("date", getCurrentDate());
        populateWorkLogModelMap(model, issueId);
    }

    private void editWorkLogModelMap(ModelMap model, Long workLogId) {
        WorkLog currentWorkLog = workLogService.findOne(workLogId);
        model.addAttribute("action", "../../worklog/save");
        model.addAttribute("id", currentWorkLog.getId());
        model.addAttribute("workLog", currentWorkLog);
        model.addAttribute("date", currentWorkLog.getStartTime());
        populateWorkLogModelMap(model, currentWorkLog.getIssue().getId());
    }

    private void populateWorkLogModelMap(ModelMap model, Long issueId) {
        model.addAttribute("issue", issueService.findById(issueId));
        model.addAttribute("totalSpentTimeByAllUsers", getTotalSpentTimeForIssueByAllUsers(issueId));
        model.addAttribute("workLogsOfCurrentIssueByAllUsers", workLogService.findByIssue(getCurrentIssue(issueId)));
    }

    private WorkLog getNewWorkLog(Long issueId, Principal principal) {
        WorkLog workLog = new WorkLog();
        workLog.setId(0L);
        workLog.setIssue(issueService.findById(issueId));
        workLog.setUser(userService.findOne(userService.findByEmailIs(principal.getName()).getId()));
        return workLog;
    }

    private Issue getCurrentIssue(Long issueId) {
        return issueService.findById(issueId);
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormatSQL.format(new Date());
    }

    private Long getTotalSpentTimeForIssueByAllUsers(Long issueId) {
        Long totalSpentTime = new Long(0);
        List<WorkLog> workLogList = workLogService.findByIssue(getCurrentIssue(issueId));
        for (WorkLog worklogIterator : workLogList) {
            totalSpentTime += worklogIterator.getAmountOfTime();
        }
        return totalSpentTime;
    }
}