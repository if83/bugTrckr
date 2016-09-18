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

    @Autowired
    private WorkLogService workLogService;

    @RequestMapping(value = "/issue", method = RequestMethod.GET)
    public String listOfIssues(Model model, @PageableDefault(value = 20) Pageable pageable, Principal principal) {
        model.addAttribute("listOfIssues", issueService.findAll(pageable));
        model.addAttribute("userIssues", issueService
                .findByAssignee((userService.findByEmail(principal.getName()).get(0)), pageable));
        LOGGER.debug("Issue list controller");
        return "issue";
    }

    @PostMapping(value = "/issue/search")
    public String issueSearchByTitle(@RequestParam(value = "title") String title, Model model,
                                     Pageable pageable, Issue issue, Principal principal) {
        model.addAttribute("listOfIssues", issueService.findByTitleContaining(title, pageable));
        populateDefaultModel(model, issue, principal);
        return "issue";
    }

    @GetMapping(value = "issue/{issueId}")
    public String issueById(@PathVariable("issueId") Long issueId, ModelMap model, Principal principal, Pageable pageable) {
        Issue issue = issueService.findById(issueId);
        model.addAttribute("issue", issue);
        model.addAttribute("issueCommentsList", issueCommentService.findByIssue(issueService.findById(issueId)));
        model.addAttribute("historyList", historyService.findByIssue(issueService.findById(issueId)));
        model.addAttribute("newIssueComment", getNewIssueComment(principal, issueId));
        workLogService.forNewWorkLogModel(model, issueId, principal, pageable);
        return "issue_view";
    }

    @PreAuthorize("hasRole('ADMIN') or @userService.findByEmail(#principal.getName()).get(0) == " +
            "@workLogService.findOne(#workLogId).getUser()")
    @GetMapping(value = "issue/{issueId}/worklog/{workLogId}/edit")
    public String issueByIdEditWorklog(@PathVariable("issueId") Long issueId,
                                       @PathVariable("workLogId") Long workLogId,
                                       ModelMap model, Principal principal,
                                       Pageable pageable) {
        Issue issue = issueService.findById(issueId);
        model.addAttribute("issue", issue);
        model.addAttribute("issueCommentsList", issueCommentService.findByIssue(issueService.findById(issueId)));
        model.addAttribute("newIssueComment", getNewIssueComment(principal, issueId));
        workLogService.forEditWorkLogModel(model, workLogId, issueId, principal, pageable);
        return "issue_view";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER') and " +
            "@userService.findByEmail(#principal.getName()).get(0).getProject().getId() " +
            "== @issueService.findById(#id).getProject().getId()" +
            "or hasAnyRole('DEVELOPER', 'QA') and " +
            " @userService.findByEmail(#principal.getName()).get(0).getProject().getId() " +
            "== @issueService.findById(#id).getProject().getId() and " +
            "@userService.findByEmail(#principal.getName()).get(0).getId() " +
            "== @issueService.findById(#id).getAssignee().getId()")
    @RequestMapping(value = "/issue/{id}/remove", method = RequestMethod.GET)
    public String removeIssue(@PathVariable @P("id") long id, final RedirectAttributes redirectAttributes,
                              @Param("principal") Principal principal) {
        this.issueService.delete(id);
        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("msg", "Issue removed successfully!");
        LOGGER.debug("Issue removed successfully!", "Issue id = " + id);
        return "redirect:/issue";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER') and " +
            "@userService.findByEmail(#principal.getName()).get(0).getProject().getId() " +
            "== @issueService.findById(#id).getProject().getId()" +
            "or hasAnyRole('DEVELOPER', 'QA') and " +
            "@issueService.findById(#id).editAbility and " +
            "@userService.findByEmail(#principal.getName()).get(0).getProject().getId() " +
            "== @issueService.findById(#id).getProject().getId() and" +
            "@userService.findByEmail(#principal.getName()).get(0).getId()" +
            "== @issueService.findById(#id).getAssignee().getId()")
    @RequestMapping(value = "/issue/{id}/edit", method = RequestMethod.GET)
    public String editIssue(@PathVariable @P("id") long id, Model model,
                            RedirectAttributes redirectAttrs, @Param("principal") Principal principal) {
        model.addAttribute("issue", this.issueService.findById(id));
        Issue issue = issueService.findById(id);
        model.addAttribute("issue", issue);
        model.addAttribute("formAction", "edit");
        model.addAttribute("statuses", issueService.getAvaliableIssueStatusesForStatus(issue.getStatus()));
        model.addAttribute("users", userService.findUsersInProject(projectService.findById(issue.getProjectRelease().getProject().getId()), false, 1));
        populateDefaultModel(model, issue, principal);
        LOGGER.debug("Issue edit" + id);
        return "issue_form";
    }

    @RequestMapping(value = "/issue/add", method = RequestMethod.GET)
    public String addIssue(Model model, Principal principal) {
        Issue issue = new Issue();
        populateDefaultModel(model, issue, principal);
        model.addAttribute("sampleDate", new Date());
        model.addAttribute("issue", issue);
        model.addAttribute("formAction", "new");
        LOGGER.debug("Issue add form");
        return "issue_form";
    }

    @RequestMapping(value = "/issue/add", method = RequestMethod.POST)
    public String addIssuePost(@ModelAttribute("issue") @Valid Issue issue, BindingResult result, Model model,
                               RedirectAttributes redirectAttributes, Principal principal) {
        User changedByUser = userService.findByEmailIs(principal.getName());
        populateDefaultModel(model, issue, principal);
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
                                  @RequestParam("inputData") String inputData,
                                  Principal principal) {
        Issue issue = issueService.findById(issueId);
        User changedByUser = userService.findByEmailIs(principal.getName());
        if (action.equals("changeAssignee")) {
            issue.setAssignee(userService.findOne(Long.valueOf(inputData)));
            issue = issueService.save(issue);
            historyService.writeToTheHistory(HistoryAction.CHANGE_ISSUE_ASSIGNEE, issue, changedByUser, getCurrentTime());
        } else {
            issue.setStatus(IssueStatus.valueOf(inputData));
            issue = issueService.save(issue);
            historyService.writeToTheHistory(HistoryAction.CHANGE_ISSUE_STATUS, issue, changedByUser, getCurrentTime());
        }
    }

    @RequestMapping(value = "/getAvaliableIssueStatuses", method = RequestMethod.POST)
    public
    @ResponseBody
    List<IssueStatus> getAvaliableIssueStatuses(@RequestParam("selectedStatus") String selectedStatus) {
        return issueService.getAvaliableIssueStatusesForStatus(IssueStatus.valueOf(selectedStatus));
    }

    private void populateDefaultModel(Model model, Issue issue, Principal principal) {
        if (userService.findByEmail(principal.getName()).get(0).getProject() == null){
            model.addAttribute("users", userService.findAll());
            model.addAttribute("projectReleases", projectReleaseService.findAll());
        } else {
            issue.setProject(userService.findByEmail(principal.getName()).get(0).getProject());
            model.addAttribute("users", userService.findUsersInProject(issue.getProject(), false, 1));
            model.addAttribute("projectReleases", projectReleaseService.findByProject(issue.getProject()));
        }
        model.addAttribute("projects", projectService.findAll());
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