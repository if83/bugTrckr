package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.ReleaseStatus;
import com.softserverinc.edu.services.IssueService;
import com.softserverinc.edu.services.ProjectReleaseService;
import com.softserverinc.edu.services.ProjectService;
import com.softserverinc.edu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

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

    @RequestMapping(value = "/project/{projectId}/release/{releaseId}", method = RequestMethod.GET)
    public String viewRelease(@PathVariable("releaseId") Long releaseId,
                              Model model,
                              @PageableDefault(value = 10) Pageable pageable) {
        ProjectRelease release = releaseService.findById(releaseId);
        Page<Issue> pageableIssues = issueService.findByProjectRelease(release, pageable);
        List<User> users = userService.findByProjectAndIsDeletedAndEnabledIs(projectService.findById(release.getProject().getId()), false, 1);
        model.addAttribute("issueList", pageableIssues);
        model.addAttribute("release", release);
        model.addAttribute("users", users);
        return "release";
    }

    @RequestMapping(value = "/project/{projectId}/release/{releaseId}/issuesSearch", method = RequestMethod.POST)
    public String searchByIssueName(@RequestParam(value = "searchedString") String searchedString,
                                    @PathVariable("releaseId") Long releaseId,
                                    Model model,
                                    @PageableDefault(value = 10) Pageable pageable) {
        ProjectRelease release = releaseService.findById(releaseId);
        Page<Issue> pageableIssues = issueService.findByProjectReleaseAndTitleContaining(release, searchedString, pageable);
        List<User> users = userService.findByProjectAndIsDeletedAndEnabledIs(projectService.findById(release.getProject().getId()), false, 1);
        model.addAttribute("issueList", pageableIssues);
        model.addAttribute("release", release);
        model.addAttribute("users", users);
        return "release";
    }

    @RequestMapping(value = "/project/{projectId}/release/add", method = RequestMethod.GET)
    public String addReleaseGet(@PathVariable("projectId") Long projectId,
                                Model model) {
        Project project = projectService.findById(projectId);
        ProjectRelease release = new ProjectRelease();
        model.addAttribute("project", project);
        model.addAttribute("release", release);
        model.addAttribute("formAction", "new");
        populateDefaultModelByReleaseStatuses(model);
        return "releaseform";
    }

    @RequestMapping(value = "/project/{projectId}/release/{releaseId}/edit", method = RequestMethod.GET)
    public String editReleaseGet(@PathVariable("projectId") Long projectId,
                                 @PathVariable("releaseId") Long releaseId,
                                 Model model) {
        ProjectRelease release = releaseService.findById(releaseId);
        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("release", release);
        model.addAttribute("formAction", "edit");
        populateDefaultModelByReleaseStatuses(model);
        return "releaseform";
    }

    @RequestMapping(value = "/project/{projectId}/release/add", method = RequestMethod.POST)
    public String addReleasePost(@PathVariable("projectId") Long projectId,
                                 @ModelAttribute("release") @Valid ProjectRelease release,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        Project project = projectService.findById(projectId);
        if (result.hasErrors()) {
            model.addAttribute("project", project);
            model.addAttribute("formAction", "edit");
            model.addAttribute("release", release);
            populateDefaultModelByReleaseStatuses(model);
            return "releaseform";
        }
        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("msg", "Success!");
        release.setProject(project);
        releaseService.save(release);
        return "redirect:/projects/project/{projectId}";
    }

    @RequestMapping(value = "/project/{projectId}/release/{releaseId}/remove", method = RequestMethod.GET)
    public String removeReleaseGet(@PathVariable("releaseId") Long releaseId,
                                   @PathVariable("projectId") Long projectId,
                                   RedirectAttributes redirectAttributes) {
        releaseService.delete(releaseId);
        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("msg", "Release is deleted!");
        return "redirect:/projects/project/{projectId}";
    }

    private void populateDefaultModelByReleaseStatuses(Model model) {
        model.addAttribute("releaseStatuses", ReleaseStatus.values());
    }

}
