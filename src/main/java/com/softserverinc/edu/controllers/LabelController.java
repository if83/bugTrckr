package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Label;
import com.softserverinc.edu.services.LabelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LabelController {

    public static final Logger LOGGER = LoggerFactory.getLogger(LabelController.class);

    @Autowired
    private LabelService labelService;

    @RequestMapping(value = "/saveNewLabel", method = RequestMethod.POST)
    public @ResponseBody
    Long saveNewLabelByAjax(@RequestParam("labelName") String labelName) {
        Label label = new Label();
        label.setTitle(labelName);
        label = labelService.save(label);
        return label.getId();
    }

}
