package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.forms.LoginForm;
import com.softserverinc.edu.services.securityServices.BasicSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@SessionAttributes("userInSystem")
public class LoginController {

    @Autowired
    private BasicSecurityService basicSecurityService;

    @GetMapping("/")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "about";
    }

    @PostMapping("/")
    public String loginPost(@ModelAttribute LoginForm loginForm, Model model) {
        if (basicSecurityService.isAuthenticated()) {
            model.addAttribute("userInSystem", basicSecurityService.getActiveUser());
        }
        return "about";
    }
}