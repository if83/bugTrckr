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

    @PreAuthorize("@releaseSecurityService.hasPermissionToViewRelease(#releaseId)")
    @GetMapping("/project/{projectId}/release/{releaseId}")
    public String viewRelease(@PathVariable @P("releaseId") Long releaseId, Model model,
                              @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageable) {
        ProjectRelease release = releaseService.findById(releaseId);
        model.addAttribute("release", release);
        model.addAttribute("issueList", issueService.findIssuesByRelease(release, pageable));
        model.addAttribute("users",userService.findUsersForRelease(release));
        return "release";
    }

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

    @PreAuthorize("@releaseSecurityService.hasPermissionToRemoveRelease(#releaseId)")
    @GetMapping("/project/{projectId}/release/{releaseId}/remove")
    public String removeReleaseGet(@PathVariable @P("releaseId") Long releaseId,
                                   RedirectAttributes redirectAttributes) {
        releaseService.delete(releaseId);
        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("msg", "Release is deleted!");
        return "redirect:/projects/project/{projectId}";
    }

    private void populateModelByReleaseStatuses(Model model) {
        model.addAttribute("releaseStatuses", ReleaseStatus.values());
    }

}
