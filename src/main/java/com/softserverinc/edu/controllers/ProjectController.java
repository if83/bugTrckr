package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.ReleaseStatus;
import com.softserverinc.edu.forms.ProjectFormValidator;
import com.softserverinc.edu.services.ProjectReleaseService;
import com.softserverinc.edu.services.ProjectService;
import com.softserverinc.edu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        binder.setValidator(projectFormValidator);
//    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String listOfProjects(ModelMap model, Pageable pageable) {
        Page<Project> project = projectService.findAll(pageable);
        model.addAttribute("listOfProjects", project);
        model.addAttribute("totalPagesCount", project.getTotalPages());
        model.addAttribute("isControllerPagable", true);
        LOGGER.debug("List pf projects");
        return "projects";
    }

    @PostMapping(value = "/projects/searchByTitle")
    public String projectSearchByTitle(@RequestParam(value = "title") String title, Model model) {
        model.addAttribute("listOfProjects", projectService.findByTitle(title));
        LOGGER.debug("Project search list ByTitle");
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

        redirectAttributes.addFlashAttribute("msg", "Project added successfully!");
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

}