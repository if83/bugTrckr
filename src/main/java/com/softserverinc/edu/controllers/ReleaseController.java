package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.enums.ReleaseStatus;
import com.softserverinc.edu.services.IssueService;
import com.softserverinc.edu.services.ProjectReleaseService;
import com.softserverinc.edu.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ReleaseController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private IssueService issueService;

    @Autowired
    private ProjectReleaseService releaseService;

    @RequestMapping(value = "/project/{projectId}/release/{releaseId}", method = RequestMethod.GET)
    public String viewRelease(@PathVariable("releaseId") Long releaseId,
                              Model model) {
        ProjectRelease release = releaseService.findById(releaseId);
        List<Issue> issues =  issueService.findByProjectRelease(release);
        model.addAttribute("issues", issues);
        model.addAttribute("release", release);
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
        populateDefaultModel(model);
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
        populateDefaultModel(model);
        return "releaseform";
    }

    @RequestMapping(value = "/project/{projectId}/release/add", method = RequestMethod.POST)
    public String addReleasePost(@PathVariable("projectId") Long projectId,
                                 @ModelAttribute("release") ProjectRelease release,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            populateDefaultModel(model);
            return "releaseform";
        }
        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("msg", "Success!");
        Project project = projectService.findById(projectId);
        release.setProject(project);
        releaseService.save(release);
        return "redirect:/project/{projectId}";

    }

    @RequestMapping(value = "/project/{projectId}/release/{releaseId}/remove", method = RequestMethod.GET)
    public String removeReleaseGet(@PathVariable("releaseId") Long releaseId,
                             RedirectAttributes redirectAttributes) {
        releaseService.delete(releaseId);
        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("msg", "Release is deleted!");
        return "redirect:/project/{projectId}";
    }

    private void populateDefaultModel(Model model) {
        model.addAttribute("statuses", ReleaseStatus.values());
    }

}
