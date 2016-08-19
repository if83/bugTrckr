package com.softserverinc.edu.controllers;

import com.softserverinc.edu.services.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IssueController {

    public static final Logger LOGGER = LoggerFactory.getLogger(IssueController.class);

    @Autowired
    IssueService issueService;

    @RequestMapping(value = "/issue", method = RequestMethod.GET)
    public String listOfIssues(ModelMap model) {
        model.addAttribute("listOfIssues", this.issueService.findAll());
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
}
