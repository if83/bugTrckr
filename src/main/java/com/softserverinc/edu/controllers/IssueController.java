package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.enums.IssuePriority;
import com.softserverinc.edu.entities.enums.IssueStatus;
import com.softserverinc.edu.entities.enums.IssueType;
import com.softserverinc.edu.forms.IssueFormValidator;
import com.softserverinc.edu.services.IssueService;
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

import java.util.Date;

@Controller
public class IssueController {

    public static final Logger LOGGER = LoggerFactory.getLogger(IssueController.class);

    @Autowired
    IssueService issueService;

    @Autowired
    IssueFormValidator issueFormValidator;

    @InitBinder("issueCommand")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(issueFormValidator);
    }

    @RequestMapping(value = "/issue", method = RequestMethod.GET)
    public String listOfIssues(Model model) {
        model.addAttribute("listOfIssues", this.issueService.findAll());
        populateDefaultModel(model);
        LOGGER.debug("Issue list");
        return "issue";
    }

    @RequestMapping(value = "/issue/{id}/remove", method = RequestMethod.GET)
    public String removeIssue(@PathVariable("id") long id, final RedirectAttributes redirectAttributes) {
        this.issueService.delete(id);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Issue is removed!");
        LOGGER.debug("Issue is removed");
        return "redirect:/issue";
    }

    @RequestMapping(value = "/issue/{id}/edit", method = RequestMethod.GET)
    public String editIssue(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
        model.addAttribute("issue", this.issueService.findById(id));
        model.addAttribute("formaction", "edit");
        LOGGER.debug("Issue edit" + id);
        return "issueform";
    }


    @RequestMapping(value = "/issue/add", method = RequestMethod.GET)
    public String addIssue(Model model) {
        Issue issue = new Issue();
        model.addAttribute("sampleDate", new Date());
        issue.setId(0L);
        issue.setIsDeleted(false);
        model.addAttribute("issue", issue);
        model.addAttribute("formaction", "new");
        populateDefaultModel(model);
        LOGGER.debug("Issue add form");
        return "issueform";
    }

    @RequestMapping(value = "/issue/add", method = RequestMethod.POST)
    public String addIssuePost(@ModelAttribute("issue") @Validated Issue issue, BindingResult result, Model model,
                               RedirectAttributes redirectAttributes) {

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
        issueService.save(issue);

        LOGGER.debug("Issue updated or saved " + issue.getId());
        return "redirect:/issue";
    }

    private void populateDefaultModel(Model model) {
        model.addAttribute("types", IssueType.values());
        model.addAttribute("priority", IssuePriority.values());
        model.addAttribute("statuses", IssueStatus.values());
    }
}