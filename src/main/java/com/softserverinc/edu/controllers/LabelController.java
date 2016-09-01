package com.softserverinc.edu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/label")
public class LabelController {

    public String index() {
        return "label";
    }

}
