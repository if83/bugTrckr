package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.forms.ProjectFormValidator;
import com.softserverinc.edu.entities.enums.ReleaseStatus;
import com.softserverinc.edu.services.ProjectReleaseService;
import com.softserverinc.edu.services.ProjectService;
import com.softserverinc.edu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProjectController {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectReleaseService releaseService;

    @Autowired
    private ProjectFormValidator projectFormValidator;

   /* @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(projectFormValidator);
    }*/

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String listOfProjects(ModelMap model) {
        model.addAttribute("listOfProjects", projectService.findAll());
        return "projects";
    }

    @RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
    public String projectById(@PathVariable("id") Long id,
                              Model model) {
        Project project = projectService.findById(id);
        List<User> users = userService.findByProject(project);
        List<ProjectRelease> releases = releaseService.findByProject(project);
        model.addAttribute("usersList", users);
        model.addAttribute("project", project);
        model.addAttribute("releases", releases);
        return "project";
    }

    @RequestMapping(value = "/project/{projectId}/release/{releaseId}", method = RequestMethod.GET)
    public String viewRelease(@PathVariable("projectId") Long projectId,
                              @PathVariable("releaseId") Long releaseId,
                              RedirectAttributes redirectAttributes) {
        ProjectRelease release = releaseService.findById(releaseId);
        redirectAttributes.addFlashAttribute("release", release);
        return "redirect:/project/{projectId}";
    }

    @RequestMapping(value = "/project/{projectId}/release/add", method = RequestMethod.GET)
    public String addReleaseGet(@PathVariable("projectId") Long projectId,
                                Model model) {
        Project project = projectService.findById(projectId);
        ProjectRelease release = new ProjectRelease();
        release.setId(0L);
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
                                 @ModelAttribute("release") @Validated ProjectRelease release,
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
        Long releaseId =  (releaseService.save(release)).getId();
        return "redirect:/project/{projectId}";

    }

    @GetMapping(value = "/projects/add")
    public String addProject(Model model) {
        Project project = new Project();
        model.addAttribute("project", project);
        model.addAttribute("formaction", "new");
        LOGGER.debug("Adding project: " + project.toString());
        return "project_form";
    }

    @PostMapping(value = "/projects/add")
    public String addProjectPost(@ModelAttribute("project") @Validated Project project, BindingResult result,
                                 Model model, final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "project_form";
        } else {
            redirectAttributes.addFlashAttribute("css", "success");
            if (project.isNewProject()) {
                redirectAttributes.addFlashAttribute("msg", "Project added successfully!");
            } else {
                redirectAttributes.addFlashAttribute("msg", "Project updated successfully!");
            }
        }
        projectService.save(project);
        LOGGER.info("Project saved(id != null), id= " + project.getId());
        return "redirect:/project/" + project.getId();
    }

    @GetMapping(value = "/projects/{id}/remove")
    public String removeProject(@PathVariable("id") Long id, final RedirectAttributes redirectAttributes) {
        projectService.delete(id);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Project is deleted!");
        LOGGER.debug("Project is deleted, id=: " + id);
        return "redirect:/projects";
    }

    @GetMapping(value = "/projects/{id}/edit")
    public String editProject(@PathVariable("id") Long id, Model model) {
        model.addAttribute("project", projectService.findById(id));
        model.addAttribute("formaction", "edit");
        LOGGER.debug("Project edit\\" + id);
        return "project_form";
    }

    @RequestMapping(value = "/project/{projectId}/release/{releaseId}/remove", method = RequestMethod.GET)
    public String addRelease(@PathVariable("projectId") Long projectId,
                             @PathVariable("releaseId") Long releaseId,
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