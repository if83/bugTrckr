package com.softserverinc.edu.controllers;

import com.softserverinc.edu.forms.LoginForm;
import com.softserverinc.edu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@SessionAttributes("loginForm")
public class LoginController {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String loginPost(@ModelAttribute("loginForm") @Valid LoginForm loginForm, Model model) {
        model.addAttribute("loginForm", loginForm);
        LOGGER.debug("Login username: " + loginForm.getUsername());
        LOGGER.debug("Login password: " + loginForm.getPassword());
        return "index";
    }
}
