package com.softserverinc.edu.controllers;

import com.softserverinc.edu.forms.LoginForm;
import com.softserverinc.edu.services.securityServices.BasicSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private BasicSecurityService basicSecurityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    /**
     *
     * @param model holder for loginForm object
     * @return about page
     */
    @GetMapping("/")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "about";
    }

    @PostMapping("/")
    public String loginPost(@ModelAttribute @Valid LoginForm loginForm,
                            BindingResult result, Model model) {
        if (basicSecurityService.isAuthenticated()) {
            model.addAttribute("firstName", basicSecurityService.getActiveUser().getFirstName());
            model.addAttribute("loginForm", loginForm);
        }
        return "about";
    }
}