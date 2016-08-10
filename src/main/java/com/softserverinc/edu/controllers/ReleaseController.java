package com.softserverinc.edu.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/release")
public class ReleaseController {

    public static final Logger LOGGER = LoggerFactory.getLogger(ReleaseController.class);

    public String index() {
        return "release";
    }

}
