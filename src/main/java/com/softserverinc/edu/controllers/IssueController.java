package com.softserverinc.edu.controllers;

import com.softserverinc.edu.constants.PageConstant;
import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.IssueComment;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.IssueStatus;
import com.softserverinc.edu.services.*;
import com.softserverinc.edu.services.securityServices.IssueCommentSecurityService;
import com.softserverinc.edu.services.securityServices.WorkLogSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private IssueCommentService issueCommentService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private ProjectReleaseService projectReleaseService;

    @Autowired
    private WorkLogService workLogService;

    @Autowired
    private WorkLogSecurityService workLogSecurityService;

    @Autowired
    private IssueCommentSecurityService issueCommentSecurityService;

    @GetMapping("/issue")
    public String listOfIssues(Model model, Principal principal,
                               @Qualifier("issue") @PageableDefault(PageConstant.AMOUNT_ISSUE_ELEMENTS) Pageable pageableIssue,
                               @Qualifier("user") @PageableDefault(PageConstant.AMOUNT_ISSUE_ELEMENTS) Pageable pageableUser) {
        model.addAttribute("listOfIssues", issueService.findAll(pageableIssue));
        if (principal != null) {
            model.addAttribute("userIssues", issueService
                    .findByAssignee((userService.findByEmailIs(principal.getName())), pageableUser));
        }
        LOGGER.debug("Issue list controller");
        return "issue";
    }

    @PostMapping("/issue/search")
    public String issueSearchByTitle(@RequestParam(value = "title") String title, Model model, Pageable pageable) {
        model.addAttribute("listOfIssues", issueService.findByTitleContaining(title, pageable));
        issueService.populateDefaultModel(model);
        return "issue";
    }

    @GetMapping("issue/{issueId}")
    public String issueById(@PathVariable Long issueId, ModelMap model,
                            @Qualifier("worklog") Pageable workLogPageable,
                            @Qualifier("history") Pageable historyPageable) {
        Issue issue = issueService.findById(issueId);
        model.addAttribute("issue", issue);
        //model.addAttribute("issueCommentsList", issueCommentService.findByIssue(issueService.findById(issueId)));
        //model.addAttribute("commentsAction", issueId + "/comment/save");
        model.addAttribute("allHistory", historyService.findAllHistoryForIssue(issue, historyPageable));
        //if (principal != null) {
            //model.addAttribute("newIssueComment", getNewIssueComment(principal, issueId));
        //}
        workLogService.forNewWorkLogModel(model, issueId, workLogPageable);
        return "issue_view";
    }

    @PreAuthorize("@workLogSecurityService.hasPermissionToEditWorkLog(#workLogId)")
    @GetMapping("issue/{issueId}/worklog/{workLogId}/edit")
    public String issueByIdEditWorklog(@PathVariable Long issueId,
                                       @PathVariable Long workLogId,
                                       ModelMap model,
                                       @Qualifier("worklog") Pageable workLogPageable) {
        Issue issue = issueService.findById(issueId);
        model.addAttribute("issue", issue);
        model.addAttribute("issueCommentsList", issueCommentService.findByIssue(issueService.findById(issueId)));
        //model.addAttribute("newIssueComment", getNewIssueComment(principal, issueId));
        workLogService.forEditWorkLogModel(model, workLogId, issueId, workLogPageable);
        return "issue_view";
    }

    @PreAuthorize("@issueCommentSecurityService.hasPermissionToEditIssueComment(#issueCommentId)")
    @GetMapping("issue/{issueId}/comment/{issueCommentId}/edit")
    public String issueByIdEditComment(@PathVariable Long issueId,
                                       @PathVariable Long issueCommentId,
                                       ModelMap model,
                                       @Qualifier("worklog") Pageable workLogPageable) {
        IssueComment issueComment = issueCommentService.findOne(issueCommentId);
        issueComment.setIsEdited(true);
        model.addAttribute("issueCommentsList", issueCommentService.findByIssue(issueService.findById(issueId)));
        model.addAttribute("newIssueComment", issueComment);
        model.addAttribute("commentsAction", "../save");
        workLogService.forNewWorkLogModel(model, issueId, workLogPageable);
        return "issue_view";
    }

    @PreAuthorize("@issueSecurityService.hasPermissionToRemoveIssue(#id)")
    @GetMapping("/issue/{id}/remove")
    public String removeIssue(@PathVariable @P("id") long id, final RedirectAttributes redirectAttributes) {
        this.issueService.delete(id);
        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("msg", "Issue is  removed successfully!");
        LOGGER.debug("Issue removed successfully!", "Issue id = " + id);
        return "redirect:/issue";
    }

    @PreAuthorize("@issueSecurityService.hasPermissionToEditIssue(#id)")
    @GetMapping("/issue/{id}/edit")
    public String editIssue(@PathVariable @P("id") long id, Model model, RedirectAttributes redirectAttrs) {
        Issue issue = issueService.findById(id);
        model.addAttribute("issue", issue);
        model.addAttribute("formAction", "edit");
        model.addAttribute("statuses", issueService.getAvaliableStatusesForStatus(issue.getStatus()));
        issueService.populateDefaultModel(model);
        LOGGER.debug("Issue edit" + id);
        return "issue_form";
    }

    @GetMapping("/issue/add")
    public String addIssue(Model model) {
        Issue issue = new Issue();
        issueService.populateDefaultModel(model);
        model.addAttribute("sampleDate", new Date());
        model.addAttribute("issue", issue);
        model.addAttribute("formAction", "new");
        LOGGER.debug("Issue add form");
        return "issue_form";
    }

    @PostMapping("/issue/add")
    public String addIssuePost(@ModelAttribute("issue") @Valid Issue issue, BindingResult result, Model model,
                               RedirectAttributes redirectAttributes, Principal principal) {
        User changedByUser = null;
        if (principal != null){
            changedByUser = userService.findByEmailIs(principal.getName());
        }
        issue.setCreatedBy(changedByUser);
        issueService.populateDefaultModel(model);
        if (result.hasErrors()) {
            model.addAttribute("formAction", "new");
            return "issue_form";
        }
        redirectAttributes.addFlashAttribute("alert", "success");
        historyService.writeToHistory(issue, changedByUser);
        issueService.save(issue);
        LOGGER.debug("Issue updated or saved " + issue.getId());
        return "redirect:/issue";
    }

    @PostMapping("/issue/changeIssue")
    public void changeIssueByAjax(@RequestParam Long issueId,
                                  @RequestParam String action,
                                  @RequestParam String inputData,
                                  Principal principal) {
        Issue issue = issueService.findById(issueId);
        User changedByUser = userService.findByEmailIs(principal.getName());
        historyService.writeToHistory(issue, changedByUser, inputData, action);
        issueService.save(issue);
    }

    @PostMapping("/getAvaliableIssueStatuses")
    public
    @ResponseBody
    Map<IssueStatus, String> getAvaliableIssueStatuses(@RequestParam String selectedStatus) {
        Map<IssueStatus, String> result = new HashMap<>();
        for (IssueStatus status : issueService.getAvaliableStatusesForStatus(IssueStatus.valueOf(selectedStatus))) {
            result.put(status, status.toString());
        }
        return result;
    }

    /*private IssueComment getNewIssueComment(Principal principal, Long issueId) {
        IssueComment issueComment = new IssueComment();
        issueComment.setId(0L);
        issueComment.setUser(userService.findOne(userService.findByEmailIs(principal.getName()).getId()));
        issueComment.setIssue(issueService.findById(issueId));
        issueComment.setIsEdited(false);
        return issueComment;
    }*/
    
	private String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}