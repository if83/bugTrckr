package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.WorkLog;
import com.softserverinc.edu.forms.WorkLogFormValidator;
import com.softserverinc.edu.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class WorkLogController {

    public static final Logger LOGGER = LoggerFactory.getLogger(WorkLogController.class);

    @Autowired
    private WorkLogService workLogService;

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectReleaseService projectReleaseService;

    @Autowired
    private WorkLogFormValidator workLogFormValidator;

    @PreAuthorize("@projectService.findByProjectReleases(@projectReleaseService." +
            "findByIssues(@issueService.findById(#issueId))).getGuestView() == true")
    @RequestMapping(value = "issue/{issueId}/worklog", method = RequestMethod.GET)
    public String addWorkLogGET(@PathVariable Long issueId, ModelMap model,
                                Principal principal,
                                @PageableDefault(value = 12) Pageable pageable) {
        workLogService.forNewWorkLogModel(model, issueId, principal, pageable);
        return "worklog";
    }

    @PreAuthorize("@userService.findByEmail(#principal.getName()).get(0) == " +
            "@issueService.findById(#issueId).getAssignee() or " +
            "@userService.findByEmail(#principal.getName()).get(0) == " +
            "#workLog.getUser()")
    @RequestMapping(value = "issue/{issueId}/worklog/save", method = RequestMethod.POST)
    public String addWorkLogPOST(@PathVariable Long issueId,
                                 @ModelAttribute("worklog") @Valid WorkLog workLog,
                                 BindingResult result,
                                 Principal principal,
                                 Pageable pageable) {
        if (/*!workLogFormValidator.validateAmountOfTime(workLog) ||
                !workLogFormValidator.validateWorkingOnIssueDates(workLog,
                        userService.findByEmail(principal.getName()).get(0), issueId) ||*/
                result.hasErrors()) {
            return "redirect:/issue/" + issueId + "/worklog";
        }
        workLogService.save(workLog);
        LOGGER.info("Worklog saved, id= " + workLog.getId());
        return "redirect:/issue/" + issueId + "/worklog";
    }

    /*@PreAuthorize("@userService.findByEmail(#principal.getName()).get(0) == " +
            "@workLogService.findOne(#workLogId).getUser()")
    @RequestMapping(value = "issue/{issueId}/worklog/{workLogId}/edit", method = RequestMethod.GET)
    public String editWorkLog(@PathVariable Long workLogId, @PathVariable Long issueId,
                              ModelMap model, Principal principal, Pageable pageable)  {
        model.addAttribute("issueId", issueId);
        workLogService.forEditWorkLogModel(model, workLogId, issueId, principal, pageable);
        return "worklog";
    }*/

    @PreAuthorize("@userService.findByEmail(#principal.getName()).get(0) ==" +
            "@workLogService.findOne(#worklogId).getUser()")
    @RequestMapping(value = "issue/{issueId}/worklog/{worklogId}/remove", method = RequestMethod.GET)
    public String removeWorkLog(@PathVariable("worklogId") long worklogId, Principal principal) {
        workLogService.delete(worklogId);
        LOGGER.debug("Worklog " + worklogId + " is removed!");
        return "redirect:/issue/{issueId}/worklog";
    }
}