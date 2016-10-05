package com.softserverinc.edu.controllers;

import com.softserverinc.edu.forms.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@SessionAttributes("loginForm")
public class LoginController {

    @GetMapping("/")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "about";
    }

    @PostMapping("/")
    public String loginPost(@ModelAttribute @Valid LoginForm loginForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "about";
        }
        model.addAttribute("loginForm", loginForm);
        return "about";
    }
}
