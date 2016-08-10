package com.softserverinc.edu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/release")
public class ReleaseController {

    public String Index() {
        return "release";
    }

}
