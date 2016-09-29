package com.softserverinc.edu.controllers;

import com.softserverinc.edu.services.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LabelController {

    @Autowired
    private LabelService labelService;

    @PostMapping("/saveNewLabel")
    public @ResponseBody
    Long saveNewLabelByAjax(@RequestParam("labelName") String labelName) {
        return labelService.saveNewLabel(labelName).getId();
    }

}