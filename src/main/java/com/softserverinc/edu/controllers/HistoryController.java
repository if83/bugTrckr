package com.softserverinc.edu.controllers;

import com.softserverinc.edu.services.HistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HistoryController {

    public static final Logger LOGGER = LoggerFactory.getLogger(HistoryController.class);

    public String index() {
        return "history";
    }

    @Autowired
    HistoryService historyService;
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public String listOfHistory(ModelMap model){
        model.addAttribute("listOfHistory", this.historyService.findAll());
        LOGGER.debug("History list");
        return "history";
    }

}
