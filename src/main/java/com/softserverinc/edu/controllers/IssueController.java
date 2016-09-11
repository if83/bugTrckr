package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.IssueComment;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.HistoryAction;
import com.softserverinc.edu.entities.enums.IssuePriority;
import com.softserverinc.edu.entities.enums.IssueStatus;
import com.softserverinc.edu.entities.enums.IssueType;
import com.softserverinc.edu.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    private ProjectService projectService;

    @Autowired
    private ProjectReleaseService projectReleaseService;

    @RequestMapping(value = "/issue", method = RequestMethod.GET)
    public String listOfIssues(Model model, Pageable pageable) {
        model.addAttribute("listOfIssues", issueService.findAll(pageable));
        LOGGER.debug("Issue list controller");
        return "issue";
    }

    @PostMapping(value = "/issue/search")
    public String issueSearchByTitle(@RequestParam(value = "title") String title, Model model, Pageable pageable) {
        model.addAttribute("listOfIssues", issueService.findByTitleContaining(title, pageable));
        populateDefaultModel(model);
        return "issue";
    }
    @GetMapping(value = "issue/{issueId}")
    public String issueById(@PathVariable("issueId") Long issueId, Model model, Principal principal) {
        Issue issue = issueService.findById(issueId);
        model.addAttribute("issue", issue);
        model.addAttribute("issueCommentsList", issueCommentService.findByIssue(issueService.findById(issueId)));
        model.addAttribute("newIssueComment", getNewIssueComment(principal, issueId));
        return "issue_view";
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    @RequestMapping(value = "/issue/{id}/remove", method = RequestMethod.GET)
    public String removeIssue(@PathVariable("id") long id, final RedirectAttributes redirectAttributes) {
        this.issueService.delete(id);
        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("msg", "Issue removed successfully!");
        LOGGER.debug("Issue removed successfully!", "Issue id = " + id);
        return "redirect:/issue";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER') or (hasAnyRole('DEVELOPER', 'QA') and " +
            "(@issueService.findById(#id).editAbility) and " +
            "(@userService.findByEmail(#principal.getName()).get(0).getId() " +
            "== @issueService.findById(#id).getAssignee().getId()))")
    @RequestMapping(value = "/issue/{id}/edit", method = RequestMethod.GET)
    public String editIssue(@PathVariable @P("id") long id, Model model,
                            RedirectAttributes redirectAttrs , @Param("principal") Principal principal) {
        model.addAttribute("issue", this.issueService.findById(id));
        Issue issue = issueService.findById(id);
        model.addAttribute("issue", issue);
        model.addAttribute("formAction", "edit");
        model.addAttribute("statuses", issueService.getAvaliableIssueStatusesForStatus(issue.getStatus()));
        populateDefaultModel(model);
        LOGGER.debug("Issue edit" + id);
        return "issue_form";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER', 'DEVELOPER', 'QA')")
    @RequestMapping(value = "/issue/add", method = RequestMethod.GET)
    public String addIssue(Model model) {
        Issue issue = new Issue();
        model.addAttribute("sampleDate", new Date());
        model.addAttribute("issue", issue);
        populateDefaultModel(model);
        model.addAttribute("formAction", "new");
        LOGGER.debug("Issue add form");
        return "issue_form";
    }

    @RequestMapping(value = "/issue/add", method = RequestMethod.POST)
    public String addIssuePost(@ModelAttribute("issue") @Valid Issue issue, BindingResult result, Model model,
                               RedirectAttributes redirectAttributes, Principal principal) {
        User changedByUser = userService.findByEmailIs(principal.getName());
        populateDefaultModel(model);
        if (result.hasErrors()) {
            model.addAttribute("formAction", "new");
            return "issue_form";
        }
        redirectAttributes.addFlashAttribute("alert", "success");
        if (issue.isNewIssue()) {
            issue = issueService.save(issue);
            historyService.writeToTheHistory(HistoryAction.CREATE_ISSUE, issue, changedByUser, getCurrentTime());
            redirectAttributes.addFlashAttribute("msg", "Issue is added successfully!");
        } else {
            if (issueService.isStatusChanged(issue)) {
                historyService.writeToTheHistory(HistoryAction.CHANGE_ISSUE_STATUS, issue, changedByUser, getCurrentTime());
            }
            if (issueService.isAssigneeChanged(issue)) {
                historyService.writeToTheHistory(HistoryAction.CHANGE_ISSUE_ASSIGNEE, issue, changedByUser, getCurrentTime());
            }
            redirectAttributes.addFlashAttribute("msg", "Issue is updated successfully!");
            issueService.save(issue);
        }
        LOGGER.debug("Issue updated or saved " + issue.getId());
        return "redirect:/issue";
    }

    @RequestMapping(value = "/issue/changeIssue", method = RequestMethod.POST)
    public void changeIssueByAjax(@RequestParam("issueId") Long issueId,
                                  @RequestParam("action") String action,
                                  @RequestParam("data") String data,
                                  Principal principal) {
        Issue issue = issueService.findById(issueId);
        User changedByUser = userService.findByEmailIs(principal.getName());
        if (action.equals("changeAssignee")) {
            issue.setAssignee(userService.findOne(Long.valueOf(data)));
            issue = issueService.save(issue);
            historyService.writeToTheHistory(HistoryAction.CHANGE_ISSUE_ASSIGNEE, issue, changedByUser, getCurrentTime());
        } else {
            issue.setStatus(IssueStatus.valueOf(data));
            issue = issueService.save(issue);
            historyService.writeToTheHistory(HistoryAction.CHANGE_ISSUE_STATUS, issue, changedByUser, getCurrentTime());
        }
    }

    @RequestMapping(value = "/getAvaliableIssueStatuses", method = RequestMethod.POST)
    public @ResponseBody List<IssueStatus> getAvaliableIssueStatuses(@RequestParam("selectedStatus") String selectedStatus) {
        return issueService.getAvaliableIssueStatusesForStatus(IssueStatus.valueOf(selectedStatus));
    }

    private void populateDefaultModel(Model model) {
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("types", IssueType.values());
        model.addAttribute("priority", IssuePriority.values());
        model.addAttribute("allLabels", labelService.findAll());
    }

    private IssueComment getNewIssueComment(Principal principal, Long issueId) {
        IssueComment issueComment = new IssueComment();
        issueComment.setId(0L);
        issueComment.setUser(userService.findOne(userService.findByEmailIs(principal.getName()).getId()));
        issueComment.setIssue(issueService.findById(issueId));
        return issueComment;
    }

    private String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}