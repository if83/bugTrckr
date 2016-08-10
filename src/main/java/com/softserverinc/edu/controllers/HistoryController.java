package com.softserverinc.edu.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/history", method = RequestMethod.GET)
public class HistoryController {

    public static final Logger LOGGER = LoggerFactory.getLogger(HistoryController.class);

    public String index() {
        return "history";
    }

}
