package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.IssueComment;
import com.softserverinc.edu.services.HistoryService;
import com.softserverinc.edu.services.IssueCommentService;
import com.softserverinc.edu.services.IssueService;
import com.softserverinc.edu.services.UserService;
import com.softserverinc.edu.services.securityServices.IssueCommentSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Date;

@Controller
public class IssueCommentController {

    public static final Logger LOGGER = LoggerFactory.getLogger(IssueCommentController.class);

    @Autowired
    private IssueCommentService issueCommentService;

    @Autowired
    private IssueService issueService;

    @Autowired
    private IssueCommentSecurityService issueCommentSecurityService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserService userService;

    @PreAuthorize("@issueCommentSecurityService.hasPermissionToCreateIssueComment(#issueId)")
    @RequestMapping(value = "issue/{issueId}/comment/save", method = RequestMethod.POST)
    public String addIssueComment(@PathVariable Long issueId,
                                  @ModelAttribute("newIssueComment") @Valid IssueComment newIssueComment,
                                  BindingResult result,
                                  ModelMap model,
                                  Principal principal) {
        if (result.hasErrors()) {
            return "redirect:/issue/" + issueId;
        }
        historyService.writeToHistory(issueService.findById(issueId), userService.getAuthorOfIssueComment(newIssueComment),
                newIssueComment);
        newIssueComment.setTimeStamp(new Date());
        issueCommentService.save(newIssueComment);
        LOGGER.info("Comment saved, id= " + newIssueComment.getId());
        return "redirect:/issue/" + issueId;
    }

    @PreAuthorize("@issueCommentSecurityService.hasPermissionToRemoveIssueComment(#issueCommentId)")
    @RequestMapping(value = "issue/{issueId}/comment/{issueCommentId}/remove", method = RequestMethod.GET)
    public String removeIssueComment(@PathVariable("issueCommentId") long issueCommentId,
                                     @PathVariable("issueId") long issueId) {
        issueCommentService.delete(issueCommentId);
        LOGGER.debug("Comment " + issueCommentId + " is removed!");
        return "redirect:/issue/" + issueId;
    }
}