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
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class IssueCommentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IssueCommentController.class);

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
    public String addIssueComment(@PathVariable @P("issueId")Long issueId,
                                  @ModelAttribute("newIssueComment") @Valid IssueComment newIssueComment,
                                  BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/issue/" + issueId;
        }
        historyService.writeToHistory(issueService.findById(issueId), userService.getAuthorOfIssueComment(newIssueComment),
                newIssueComment);
        if (newIssueComment.getId() == null) {
            newIssueComment.setTimeStamp(new Date());
        } else {
            Date timeStamp = issueCommentService.findOne(newIssueComment.getId()).getTimeStamp();
            newIssueComment.setTimeStamp(timeStamp);
            newIssueComment.setIsEdited(true);
        }
        issueCommentService.save(newIssueComment);
        LOGGER.info("Comment saved, id= " + newIssueComment.getId());
        return "redirect:/issue/" + issueId;
    }

    @PreAuthorize("@issueCommentSecurityService.hasPermissionToRemoveIssueComment(#issueCommentId)")
    @RequestMapping(value = "issue/{issueId}/comment/{issueCommentId}/remove", method = RequestMethod.GET)
    public String removeIssueComment(@PathVariable @P("issueCommentId") long issueCommentId,
                                     @PathVariable Long issueId) {
        issueCommentService.delete(issueCommentId);
        LOGGER.info("Comment " + issueCommentId + " is removed!");
        return "redirect:/issue/" + issueId;
    }
}