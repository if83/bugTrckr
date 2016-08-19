package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.services.ProjectService;
import com.softserverinc.edu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String listOfProjects(ModelMap model) {
        model.addAttribute("listOfProjects", projectService.findAll());
        return "projects";
    }

    @RequestMapping(value = "/projects/project{id}", method = RequestMethod.GET)
    public String projectById(@PathVariable("id") Long id, Model model) {
        Project project = projectService.findById(id);
        List<User> users = userService.findByProject(project);
        model.addAttribute("usersList", users);
        model.addAttribute("project", project);
        return "project";
    }
}