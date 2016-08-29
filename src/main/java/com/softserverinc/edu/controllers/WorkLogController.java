package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.WorkLog;
import com.softserverinc.edu.repositories.UserRepository;
import com.softserverinc.edu.services.IssueService;
import com.softserverinc.edu.services.ProjectService;
import com.softserverinc.edu.services.UserService;
import com.softserverinc.edu.services.WorkLogService;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@Transactional
public class WorkLogController {

    public static final Logger LOGGER = LoggerFactory.getLogger(WorkLogController.class);

    @Autowired
    private WorkLogService workLogService;

    @Autowired
    private IssueService issueService;

    /*@Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;
    */
    /*@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(projectFormValidator);
    }*/

    @RequestMapping(value = "issue/{issueId}/worklog", method = RequestMethod.GET)
    public String workLog(@PathVariable Long issueId, ModelMap model) {

        //TODO: work with date

        newWorkLogModelMap(model, issueId);
        return "worklog";

    }

    @RequestMapping(value = "issue/{issueId}/worklog/save", method = RequestMethod.POST)
    public String addWorkLogPost(@PathVariable Long issueId,
                                 @ModelAttribute("worklog") @Validated WorkLog workLog,
                                 BindingResult result,
                                 ModelMap model,
                                 final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", "Worklog saved successfully!");
        if (result.hasErrors()) {
            newWorkLogModelMap(model, issueId);
            return "worklog";
        } else {
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "Worklog saved successfully!");
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
    public String removeIssue(@PathVariable("worklogId") long worklogId,
                              final RedirectAttributes redirectAttributes) {
        this.workLogService.delete(worklogId);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Worklog is removed!");
        LOGGER.debug("Worklog " + worklogId + " is removed!");
        return "redirect:/issue/{issueId}/worklog";
    }

    private void newWorkLogModelMap(ModelMap model, Long issueId) {
        model.addAttribute("id", 0);
        model.addAttribute("issue", getCurrentIssue(issueId));
        model.addAttribute("workLog", getNewWorkLog(issueId));
        model.addAttribute("user", getCurrentIssue(issueId).getAssignee());
        model.addAttribute("date", getCurrentDateString());

        populateWorkLogModelMap(model, issueId);
    }

    private void editWorkLogModelMap(ModelMap model, Long workLogId) {
        WorkLog currentWorkLog = workLogService.findOne(workLogId);
        model.addAttribute("id", currentWorkLog.getId());
        model.addAttribute("issue", currentWorkLog.getIssue());
        model.addAttribute("workLog", currentWorkLog);
        model.addAttribute("date", currentWorkLog.getStartTime());
        /*model.addAttribute("user", currentWorkLog.getUser());*/
        populateWorkLogModelMap(model, currentWorkLog.getIssue().getId());
    }

    private void populateWorkLogModelMap(ModelMap model, Long issueId) {
        model.addAttribute("totalSpentTimeByAllUsers", getTotalSpentTimeForIssueByAllUsers(issueId));
        model.addAttribute("workLogsOfCurrentIssueByAllUsers", getWorkLogOfCurrentIssueByAllUsers(issueId));
    }

    private WorkLog getNewWorkLog(Long issueId) {
        WorkLog workLog = new WorkLog();
        /*workLog.setId(0L);*/
        /*workLog.setIssue(getCurrentIssue(issueId));
        workLog.setUser(getCurrentIssue(issueId).getAssignee());*/
        return workLog;
    }

    private Issue getCurrentIssue(Long issueId) {
        return issueService.findById(issueId);
    }

    private String getCurrentDateString() {
        SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormatSQL.format(new Date());
    }

    private List<WorkLog> getWorkLogOfCurrentIssueByAllUsers(Long issueId) {
        return workLogService.findByIssue(getCurrentIssue(issueId));
    }

    private Long getTotalSpentTimeForIssueByAllUsers(Long issueId) {
        Long totalSpentTime = new Long(0);
        List<WorkLog> workLogList = getWorkLogOfCurrentIssueByAllUsers(issueId);
        for (WorkLog worklogIterator : workLogList) {
            totalSpentTime += worklogIterator.getAmountOfTime();
        }
        return totalSpentTime;
    }
}