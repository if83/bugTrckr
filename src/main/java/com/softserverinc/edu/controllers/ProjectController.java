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

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public String listOfProjects(ModelMap model) {
        model.addAttribute("projectList", projectService.findAll());
        return "project";
    }

    @RequestMapping(value = "/project/{id}/usersOnProject", method = RequestMethod.GET)
    public String viewUserOnProject(@PathVariable("id") int id, Model model) {
        List<Project> projects = projectService.findAll();
        String projectTitle = projects.get(id - 1).getTitle();
        List<User> users = userService.findByProject(projects.get(id - 1));
        model.addAttribute("usersList", users);
        model.addAttribute("name", projectTitle);
        return "users_on_project";
    }
}