package com.softserverinc.edu.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/label")
public class LabelController {

    public static final Logger LOGGER = LoggerFactory.getLogger(LabelController.class);

    public String index() {
        return "label";
    }

}
