package com.softserverinc.edu.controllers;

import com.softserverinc.edu.constants.PageConstant;
import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.enums.IssuePriority;
import com.softserverinc.edu.entities.enums.IssueStatus;
import com.softserverinc.edu.entities.enums.IssueType;
import com.softserverinc.edu.services.*;
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
import java.util.Date;
import java.util.Map;

/**
 * Serve requsets used for working with Issue entity
 */
@Controller
public class IssueController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IssueController.class);

    @Autowired
    private IssueService issueService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private IssueCommentService issueCommentService;

    @Autowired
    private WorkLogService workLogService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private ProjectReleaseService projectReleaseService;

    /**
     * Shows all possible issues
     *
     * @param model         holder for model attributes
     * @param principal     represents the user, who is authenticated
     * @param pageableIssue represents the total number of pages in the set of allIssues
     * @param pageableUser  represents the total number of pages in the set of myIssues
     * @return list of issues
     */
    @GetMapping("/issue")
    public String listOfIssues(Model model, Principal principal,
                               @Qualifier("issue")
                               @PageableDefault(PageConstant.AMOUNT_ISSUE_ELEMENTS) Pageable pageableIssue,
                               @Qualifier("user")
                               @PageableDefault(PageConstant.AMOUNT_ISSUE_ELEMENTS) Pageable pageableUser) {
        model.addAttribute("listOfIssues", issueService.findAll(pageableIssue));
        if (principal != null) {
            model.addAttribute("userIssues", issueService.findByUser(principal, pageableUser));
        }
        return "issue";
    }

    @PostMapping("/issue/search")
    public String issueSearchByTitle(@RequestParam(value = "title") String title, Model model,
                                     @PageableDefault(PageConstant.AMOUNT_ISSUE_ELEMENTS) Pageable pageable) {
        model.addAttribute("listOfIssues", issueService.findByTitleContaining(title, pageable));
        populateDefaultModel(model);
        return "issue";
    }


    /**
     * Show specific issue, according to id
     *
     * @param issueId         represents issue by this id
     * @param model           holder for model attributes
     * @param workLogPageable represents the total number of pages in the set of worklog
     * @param historyPageable represents the total number of pages in the set of history
     * @return specific issue by id
     */
    @GetMapping("issue/{issueId}")
    public String issueById(@PathVariable Long issueId, ModelMap model,
                            @Qualifier("worklog")
                            @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable workLogPageable,
                            @Qualifier("history") Pageable historyPageable) {
        Issue issue = issueService.findById(issueId);
        model.addAttribute("issue", issue);
        model.addAttribute("issueCommentsList", issueCommentService.findByIssueId(issueId));
        model.addAttribute("commentsAction", issueId + "/comment/save");
        model.addAttribute("allHistory", historyService.findAllHistoryForIssue(issue, historyPageable));
        model.addAttribute("issueComment", issueCommentService.getNewIssueComment(issueId));
        workLogService.forNewWorkLogModel(model, issueId, workLogPageable);
        return "issue_view";
    }

    /**
     * Worklog by id on the specific issue page
     *
     * @param issueId         represents issue by this id
     * @param workLogId       represents worklog id on this issue page
     * @param model           holder for model attributes
     * @param workLogPageable represents the total number of pages in the set of worklog
     * @return specific issue page by id
     */
    @PreAuthorize("@workLogSecurityService.hasPermissionToEditWorkLog(#workLogId)")
    @GetMapping("issue/{issueId}/worklog/{workLogId}/edit")
    public String issueByIdEditWorklog(@PathVariable Long issueId,
                                       @PathVariable Long workLogId, ModelMap model,
                                       @Qualifier("worklog")
                                       @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable workLogPageable) {
        model.addAttribute("issue", issueService.findById(issueId));
        model.addAttribute("issueCommentsList", issueCommentService.findByIssueId(issueId));
        model.addAttribute("issueComment", issueCommentService.getNewIssueComment(issueId));
        workLogService.forEditWorkLogModel(model, workLogId, issueId, workLogPageable);
        return "issue_view";
    }

    /**
     * Comments by id on the specific issue page
     *
     * @param issueId         represents issue by this id
     * @param issueCommentId  represents comment id on this issue page
     * @param model           holder for model attributes
     * @param workLogPageable represents the total number of pages in the set of worklog
     * @return specific issue page by id
     */
    @PreAuthorize("@issueCommentSecurityService.hasPermissionToEditIssueComment(#issueCommentId)")
    @GetMapping("issue/{issueId}/comment/{issueCommentId}/edit")
    public String issueByIdEditComment(@PathVariable Long issueId,
                                       @PathVariable Long issueCommentId,
                                       ModelMap model,
                                       @Qualifier("worklog") Pageable workLogPageable) {
        model.addAttribute("issue", issueService.findById(issueId));
        model.addAttribute("issueCommentsList", issueCommentService.findByIssueId(issueId));
        model.addAttribute("issueComment", issueCommentService.getEditedCommentById(issueCommentId));
        model.addAttribute("commentsAction", "../save");
        workLogService.forNewWorkLogModel(model, issueId, workLogPageable);
        return "issue_view";
    }

    /**
     *  Remove issue by id
     *  IssueSecurityService checks for permission of current user to remove this issue
     *
     * @param id                 represents issue by this id
     * @param redirectAttributes represents attributes for a redirect scenario
     * @return list of all issues with information about issue removal
     */
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
    public String editIssue(@PathVariable @P("id") long id, Model model) {
        Issue issue = issueService.findById(id);
        model.addAttribute("issue", issue);
        model.addAttribute("formAction", "edit");
        model.addAttribute("statuses", issueService.getAvailableStatusesForStatus(issue.getStatus()));
        populateDefaultModel(model);
        return "issue_form";
    }

    @GetMapping("/issue/add")
    public String addIssue(Model model) {
        populateDefaultModel(model);
        model.addAttribute("sampleDate", new Date());
        model.addAttribute("issue", new Issue());
        model.addAttribute("formAction", "new");
        return "issue_form";
    }

    @PostMapping("/issue/add")
    public String addIssuePost(@ModelAttribute("issue") @Valid Issue issue, BindingResult result, Model model,
                               RedirectAttributes redirectAttributes) {
        populateDefaultModel(model);
        if (result.hasErrors()) {
            model.addAttribute("formAction", "new");
            return "issue_form";
        }
        addAttributes(issue, redirectAttributes);
        issueService.saveIssueChanges(issue);
        LOGGER.debug("Issue is updated or saved " + issue.getId());
        return "redirect:/issue";
    }

    /**
     * This method is invoking when somebody is changing issue (status
     * or assignee) from release page (by ajax). It checks if data is valid and saves changed issue.
     *
     * @param issueId   represents id of modified issue
     * @param action    represents the action (what's changed)
     * @param inputData represents the input data (selected status or assignee)
     */
    @PostMapping("/issue/changeIssue")
    @ResponseBody
    public void changeIssueFromAjax(@RequestParam Long issueId, @RequestParam String action,
                                    @RequestParam String inputData) {
        issueService.saveIssueChangesFromAjax(issueId, inputData, action);
    }

    /**
     * Returns all available issue statuses (as map of status constatnts and their string representations)
     * for current selected status.
     * Invokes {@see com.softserverinc.edu.services.UserService#getMapOfIssueStatuses(String selectedStatus)}
     *
     * @param selectedStatus represents selected status
     * @return               Map with issue statuses and their string representation (it's simpler for JSon parsing)
     */
    @PostMapping("/getAvailableIssueStatuses")
    @ResponseBody
    public Map<IssueStatus, String> getAvailableIssueStatuses(@RequestParam String selectedStatus) {
        return issueService.getMapOfIssueStatuses(selectedStatus);
    }

    /**
     * Method for adding attributes into model
     *
     * @param model holder for model attributes
     */
    private void populateDefaultModel(Model model) {
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("types", IssueType.values());
        model.addAttribute("priority", IssuePriority.values());
        model.addAttribute("allLabels", labelService.findAll());
        model.addAttribute("users", issueService.checkAuthentication());
        model.addAttribute("projectReleases", projectReleaseService.findAll());
    }

    /**
     * Method for adding redirectAttributes into view
     *
     * @param issue              represents specific issue
     * @param redirectAttributes represents attributes for a redirect scenario
     */
    private void addAttributes(Issue issue, RedirectAttributes redirectAttributes) {
        if (issue.getId() == null) {
            redirectAttributes.addFlashAttribute("msg", String.format("%s added successfully!", issue.getTitle()));
        } else {
            redirectAttributes.addFlashAttribute("msg", String.format("%s updated successfully!", issue.getTitle()));
        }
    }

}