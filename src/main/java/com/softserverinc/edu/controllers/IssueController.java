package com.softserverinc.edu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/issue")
public class IssueController {

    public String Index() {
        return "issue";
    }

}
