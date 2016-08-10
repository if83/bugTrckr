package com.softserverinc.edu.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/issue")
public class IssueController {

    public static final Logger LOGGER = LoggerFactory.getLogger(IssueController.class);


    public String index() {
        return "issue";
    }

}
