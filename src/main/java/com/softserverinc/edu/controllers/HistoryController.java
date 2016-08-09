package com.softserverinc.edu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/history", method = RequestMethod.GET)
public class HistoryController {

    public String Index() {
        return "history";
    }

}
