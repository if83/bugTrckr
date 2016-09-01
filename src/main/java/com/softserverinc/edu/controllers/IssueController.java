package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.HistoryAction;
import com.softserverinc.edu.entities.enums.IssuePriority;
import com.softserverinc.edu.entities.enums.IssueStatus;
import com.softserverinc.edu.entities.enums.IssueType;
import com.softserverinc.edu.forms.IssueFormValidator;
import com.softserverinc.edu.services.HistoryService;
import com.softserverinc.edu.services.IssueService;
import com.softserverinc.edu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class IssueController {

    public static final Logger LOGGER = LoggerFactory.getLogger(IssueController.class);


    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private IssueFormValidator issueFormValidator;

    @InitBinder("issueCommand")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(issueFormValidator);
    }

    @RequestMapping(value = "/issue", method = RequestMethod.GET)
    public String listOfIssues(Model model) {
        model.addAttribute("listOfIssues", this.issueService.findAll());
        populateDefaultModel(model);
        LOGGER.debug("Issue list controller");
        return "issue";
    }

    @RequestMapping(value = "/issue/{id}/remove", method = RequestMethod.GET)
    public String removeIssue(@PathVariable("id") long id, final RedirectAttributes redirectAttributes) {
        this.issueService.delete(id);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Issue is removed!");
        LOGGER.debug("Issue is removed", "Issue id = " + id);
        return "redirect:/issue";
    }

    @RequestMapping(value = "/issue/{id}/edit", method = RequestMethod.GET)
    public String editIssue(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
        model.addAttribute("issue", this.issueService.findById(id));
        model.addAttribute("formAction", "edit");
        LOGGER.debug("Issue edit" + id);
        return "issue_form";
    }


    @RequestMapping(value = "/issue/add", method = RequestMethod.GET)
    public String addIssue(Model model) {
        Issue issue = new Issue();
        model.addAttribute("sampleDate", new Date());
        issue.setId(0L);
        issue.setIsDeleted(false);
        model.addAttribute("issue", issue);
        model.addAttribute("formAction", "new");
        populateDefaultModel(model);
        LOGGER.debug("Issue add form");
        return "issue_form";
    }

    @RequestMapping(value = "/issue/add", method = RequestMethod.POST)
    public String addIssuePost(@ModelAttribute("issue") @Validated Issue issue, BindingResult result, Model model,
                               RedirectAttributes redirectAttributes, Principal principal) {

        if (result.hasErrors()) {
            populateDefaultModel(model);
            return "issue";
        } else {

            // Add message to flash scope
            redirectAttributes.addFlashAttribute("css", "success");
            if (issue.isNewIssue()) {
                redirectAttributes.addFlashAttribute("msg", "Issue added successfully!");
            } else {
                redirectAttributes.addFlashAttribute("msg", "Issue updated successfully!");
            }
        }
        User changedByUser = userService.findByEmailIs(principal.getName());

        if (issue.isNewIssue()) {
            historyService.writeToTheHistory(HistoryAction.CREATE_ISSUE, issueService.save(issue), changedByUser, getCurrentTime());
        } else {
            issueService.save(issue);
        }
        LOGGER.debug("Issue updated or saved " + issue.getId());
        return "redirect:/issue";
    }

    @RequestMapping(value = "/issue/{issueId}/changeAssignee", method = RequestMethod.POST)
    public void changeAssignee(@PathVariable("issueId") Long issueId,
                               @RequestParam("userId") String userId,
                               Principal principal) {
        Issue issue = issueService.findById(issueId);
        User changedByUser = userService.findByEmailIs(principal.getName());
        User assignedToUser = userService.findOne(Long.valueOf(userId));
        issue.setAssignee(assignedToUser);
        issue = issueService.save(issue);
        historyService.writeToTheHistory(HistoryAction.CHANGE_ISSUE_ASSIGNEE, issue, changedByUser, getCurrentTime());
    }

    @RequestMapping(value = "/issue/{issueId}/changeStatus", method = RequestMethod.POST)
    public void changeStatus(@PathVariable("issueId") Long issueId,
                             @RequestParam("status") String status,
                             Principal principal) {
        Issue issue = issueService.findById(issueId);
        User changedByUser = userService.findByEmailIs(principal.getName());
        IssueStatus issueStatus = IssueStatus.valueOf(status);
        issue.setStatus(issueStatus);
        issue = issueService.save(issue);
        historyService.writeToTheHistory(HistoryAction.CHANGE_ISSUE_STATUS, issue, changedByUser, getCurrentTime());
    }

    private void populateDefaultModel(Model model) {
        model.addAttribute("types", IssueType.values());
        model.addAttribute("priority", IssuePriority.values());
        model.addAttribute("statuses", IssueStatus.values());
    }

    private String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}