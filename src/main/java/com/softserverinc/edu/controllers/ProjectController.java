package com.softserverinc.edu.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/project")
public class ProjectController {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    public String index() {
        return "project";
    }

}
