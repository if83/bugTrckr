package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.services.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Set;

/**
 * Project controller
 */

@Controller
public class ProjectController {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    ProjectService projectService;


    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public String listOfProjects(ModelMap model) {
        model.addAttribute("projectList", projectService.findAll());
        return "project";
    }

    @RequestMapping(value = "/project/{id}/usersOnProject", method = RequestMethod.GET)
    public String viewUserOnProject(@PathVariable("id") int id, Model model) {
        List<Project> projects = projectService.findAll();
        Set<User> users = projects.get(id - 1).getUsers();
        String projectTitle = projects.get(id - 1).getTitle();
        if (users == null) {
            model.addAttribute("css", "danger");
            model.addAttribute("msg", "Users not found");
        }
        model.addAttribute("users", users);
        model.addAttribute("name", projectTitle);
        return "project_users";
    }
}
