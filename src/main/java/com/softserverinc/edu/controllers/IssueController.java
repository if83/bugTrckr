package com.softserverinc.edu.controllers;

import com.softserverinc.edu.services.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Issue controller
 */

@Controller
public class IssueController {

    public static final Logger LOGGER = LoggerFactory.getLogger(IssueController.class);

    @Autowired
    IssueService issueService;

    @RequestMapping(value = "/issue", method = RequestMethod.GET)
    public String listOfIssues(ModelMap model) {
        model.addAttribute("issueList", this.issueService.findAll());
        LOGGER.debug("Issue list");
        return "issue";
    }
}
