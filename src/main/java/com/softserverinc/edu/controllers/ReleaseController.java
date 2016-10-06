package com.softserverinc.edu.controllers;

import com.softserverinc.edu.constants.PageConstant;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.enums.ReleaseStatus;
import com.softserverinc.edu.services.IssueService;
import com.softserverinc.edu.services.ProjectReleaseService;
import com.softserverinc.edu.services.ProjectService;
import com.softserverinc.edu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ReleaseController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectReleaseService releaseService;

    /**
     * This method receives release id and put the corresponding release object to the model.
     * It also retrieves issues and users list (of all available users of project)
     * for selected release and return release.jsp.
     * Validates if current user has permission to view release.
     *
     * @param releaseId id of release
     * @param model     Spring model
     * @param pageable  represents the total number of elements on the page
     * @return          release.jsp
     */
    @PreAuthorize("@releaseSecurityService.hasPermissionToViewRelease(#releaseId)")
    @GetMapping("/project/{projectId}/release/{releaseId}")
    public String viewRelease(@PathVariable @P("releaseId") Long releaseId, Model model,
                              @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageable) {
        ProjectRelease release = releaseService.findById(releaseId);
        model.addAttribute("release", release);
        model.addAttribute("issueList", issueService.findIssuesForRelease(release, pageable));
        model.addAttribute("users",userService.findUsersForRelease(release));
        return "release";
    }

    /**
     * This method searches issues by name for current release. As result this method returns release.jsp,
     * so besides searched list of issues it returns users list of all available users of project.
     *
     * @param releaseId         id of release
     * @param searchedString    substring of issue title
     * @param model             Spring model
     * @param pageable          represents the total number of elements on the page
     * @return                  release.jsp
     */
    @PostMapping("/project/{projectId}/release/{releaseId}/issuesSearch")
    public String searchByIssueName(@PathVariable Long releaseId,
                                    @RequestParam String searchedString, Model model,
                                    @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageable) {
        ProjectRelease release = releaseService.findById(releaseId);
        model.addAttribute("release", release);
        model.addAttribute("issueList",
                issueService.findByReleaseAndIssueTitle(release, searchedString, pageable));
        model.addAttribute("users", userService.findUsersForRelease(release));
        return "release";
    }

    /**
     * This method is invoking when somebody presses the "add release" button.
     * It puts empty object of release and list of available statuses to the model
     * and then returns release form page.
     * Validates if current user has permission to add release.
     *
     * @param projectId project in which wil be created new release
     * @param model     Spring model
     * @return          releaseform.jsp
     */
    @PreAuthorize("@releaseSecurityService.hasPermissionToAddRelease(#projectId)")
    @GetMapping("/project/{projectId}/release/add")
    public String addReleaseGet(@PathVariable @P("projectId") Long projectId,
                                Model model) {
        model.addAttribute("project", projectService.findById(projectId));
        model.addAttribute("release", new ProjectRelease());
        model.addAttribute("formAction", "new");
        populateModelByReleaseStatuses(model);
        return "releaseform";
    }

    /**
     * This method is invoking when somebody presses the "edit release" button.
     * It puts current object of release and list of available statuses to the model
     * and then returns release form page.
     * Validates if current user has permission to edit release.
     *
     * @param projectId project of current release
     * @param model     Spring model
     * @return          releaseform.jsp
     */
    @PreAuthorize("@releaseSecurityService.hasPermissionToEditRelease(#releaseId)")
    @GetMapping("/project/{projectId}/release/{releaseId}/edit")
    public String editReleaseGet(@PathVariable Long projectId,
                                 @PathVariable @P("releaseId") Long releaseId,
                                 Model model) {
        model.addAttribute("project", projectService.findById(projectId));
        model.addAttribute("release", releaseService.findById(releaseId));
        model.addAttribute("formAction", "edit");
        populateModelByReleaseStatuses(model);
        return "releaseform";
    }

    /**
     * This method receives filled attributes of release (from release form).
     * If received data is invalid method returns object of release back to the form.
     * If data is valid method puts message with notification of success to the model
     * and redirects user to project page.
     *
     * @param projectId             project of current release
     * @param release               current release object
     * @param result                Binding result object for registration and holding errors from form
     * @param model                 Spring model
     * @param redirectAttributes    flash attributes
     * @return                      redirects to project page
     */
    @PreAuthorize("@releaseSecurityService.hasPermissionToAddRelease(#projectId)")
    @PostMapping("/project/{projectId}/release/add")
    public String addReleasePost(@PathVariable @P("projectId") Long projectId,
                                 @ModelAttribute("release") @Valid ProjectRelease release,
                                 BindingResult result, Model model,
                                 RedirectAttributes redirectAttributes) {
        Project project = projectService.findById(projectId);
        if (result.hasErrors()) {
            model.addAttribute("project", project);
            model.addAttribute("formAction", "edit");
            model.addAttribute("release", release);
            populateModelByReleaseStatuses(model);
            return "releaseform";
        }
        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("msg", "Success!");
        releaseService.setProjectAndSaveRelease(project, release);
        return "redirect:/projects/project/{projectId}";
    }

    /**
     * This method is invoking when somebody presses the "remove release" button.
     * It invokes {@see com.softserverinc.edu.services.ReleaseService#delete(Long id)}
     * that removes release by his id. Puts notification about success to flash attribute
     * and redirects user ro project page.
     *
     * @param releaseId             id of release which must be deleted
     * @param redirectAttributes    flash attributes
     * @return                      redirects to project page
     */
    @PreAuthorize("@releaseSecurityService.hasPermissionToRemoveRelease(#releaseId)")
    @GetMapping("/project/{projectId}/release/{releaseId}/remove")
    public String removeReleaseGet(@PathVariable @P("releaseId") Long releaseId,
                                   RedirectAttributes redirectAttributes) {
        releaseService.delete(releaseId);
        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("msg", "Release is deleted!");
        return "redirect:/projects/project/{projectId}";
    }

    /**
     * Puts to the current model list of all available release statuses
     *
     * @param model Spring model which must holds list of statuses
     */
    private void populateModelByReleaseStatuses(Model model) {
        model.addAttribute("releaseStatuses", ReleaseStatus.values());
    }

}
