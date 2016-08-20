package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.services.ProjectReleaseService;
import com.softserverinc.edu.services.ProjectService;
import com.softserverinc.edu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String listOfProjects(ModelMap model) {
        model.addAttribute("listOfProjects", projectService.findAll());
        return "projects";
    }

    @RequestMapping(value = "/projects/project/{id}", method = RequestMethod.GET)
    public String projectById(@PathVariable("id") Long id, Model model) {
        Project project = projectService.findById(id);
        List<User> users = userService.findByProject(project);
        List<ProjectRelease> releases = releaseService.findByProject(project);
        model.addAttribute("usersList", users);
        model.addAttribute("project", project);
        model.addAttribute("releases", releases);
        return "project";
    }

    @RequestMapping(value = "/project/{projectId}/release/{releaseId}", method = RequestMethod.GET)
    public String viewRelease(@PathVariable("projectId") Long projectId, @PathVariable("releaseId") Long releaseId, Model model) {
        Project project = projectService.findById(projectId);
        List<User> users = userService.findByProject(project);
        List<ProjectRelease> releases = releaseService.findByProject(project);
        ProjectRelease release = releaseService.findById(releaseId);
        model.addAttribute("usersList", users);
        model.addAttribute("project", project);
        model.addAttribute("releases", releases);
        model.addAttribute("release", release);
        return "project";
    }

    @GetMapping(value = "/projects/add")
    public String addUser(Model model) {
        Project project = new Project();
        project.setId(0L);
        model.addAttribute("project", project);
        model.addAttribute("formaction", "new");
        LOGGER.debug("Project add form");
        return "project_form";
    }

}