package com.softserverinc.edu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/worklog")
public class WorkLogController {

    public String Index() {
        return "worklog";
    }

}
