package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.IssueComment;
import com.softserverinc.edu.services.HistoryService;
import com.softserverinc.edu.services.IssueCommentService;
import com.softserverinc.edu.services.IssueService;
import com.softserverinc.edu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Serve requests used for working with IssueComment entity
 */
@Controller
public class IssueCommentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IssueCommentController.class);

    @Autowired
    private IssueCommentService issueCommentService;

    @Autowired
    private IssueService issueService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserService userService;

    /**
     * Serve IssueComment saving requests
     *
     * @param issueId issue's id
     * @param issueComment instance of IssueComment accepted from UI
     * @param result contains issueComment validating results
     * @return redirecting URL to previous page
     */
    @PreAuthorize("@issueCommentSecurityService.hasPermissionToCreateIssueComment(#issueId)")
    @RequestMapping(value = "issue/{issueId}/comment/save", method = RequestMethod.POST)
    public String addIssueComment(@PathVariable @P("issueId")Long issueId,
                                  @ModelAttribute("issueComment") @Valid IssueComment issueComment,
                                  BindingResult result) {
        if (!issueCommentService.validateIssueCommentUI(issueComment) || result.hasErrors()) {
            return "redirect:/issue/" + issueId;
        }
        historyService.writeToHistory(issueService.findById(issueId), userService.getAuthorOfIssueComment(issueComment),
                issueComment);
        issueCommentService.preSaveIssueComment(issueComment);
        issueCommentService.save(issueComment);
        LOGGER.info("Comment saved, id= " + issueComment.getId());
        return "redirect:/issue/" + issueId;
    }

    /**
     * Serve IssueComment removing requests
     *
     * @param issueCommentId IssueComment entry's id
     * @param issueId  issue's id
     * @return redirecting URL to previous page
     */
    @PreAuthorize("@issueCommentSecurityService.hasPermissionToRemoveIssueComment(#issueCommentId)")
    @RequestMapping(value = "issue/{issueId}/comment/{issueCommentId}/remove", method = RequestMethod.GET)
    public String removeIssueComment(@PathVariable @P("issueCommentId") long issueCommentId,
                                     @PathVariable Long issueId) {
        issueCommentService.delete(issueCommentId);
        LOGGER.info("Comment " + issueCommentId + " is removed!");
        return "redirect:/issue/" + issueId;
    }
}