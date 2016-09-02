package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.IssueComment;
import com.softserverinc.edu.services.IssueCommentService;
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

import javax.validation.Valid;

@Controller
public class IssueCommentController {

    public static final Logger LOGGER = LoggerFactory.getLogger(IssueCommentController.class);

    @Autowired
    private IssueCommentService issueCommentService;

    @RequestMapping(value = "issue/{issueId}/comment/save", method = RequestMethod.POST)
    public String addIssueComment(@PathVariable Long issueId,
                                  @ModelAttribute("newIssueComment") @Valid IssueComment newIssueComment,
                                  BindingResult result,
                                  ModelMap model) {
        if (result.hasErrors())
            return "redirect:/issue/" + issueId;
        issueCommentService.save(newIssueComment);
        LOGGER.info("Comment saved, id= " + newIssueComment.getId());
        return "redirect:/issue/" + issueId;
    }

    @RequestMapping(value = "issue/{issueId}/comment/{commentId}/remove", method = RequestMethod.GET)
    public String removeIssueComment(@PathVariable("commentId") long commentId,
                                     @PathVariable("issueId") long issueId) {
        issueCommentService.delete(commentId);
        LOGGER.debug("Comment " + commentId + " is removed!");
        return "redirect:/issue/" + issueId;
    }
}