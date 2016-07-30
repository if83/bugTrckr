package com.softserverinc.edu.controller;

import com.softserverinc.edu.form.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by volodymyr on 7/30/16.
 */
@Controller
public class RootController {

    public  static final Logger LOGGER = LoggerFactory.getLogger(RootController.class);

    @RequestMapping(value="/")
    public String index() {
        return "index";
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login(Map<String, Object> model) {
        model.put("loginForm", new LoginForm());
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String processLogin(@ModelAttribute("loginForm") LoginForm loginForm,
                               Map<String, Object> model) {

        LOGGER.debug("Login email: " + loginForm.getEmail());
        LOGGER.debug("Login password: " + loginForm.getPassword());

        return "index";
    }

    @RequestMapping(value = "/", params = "logout", method = RequestMethod.GET)
    public String logout(Map<String, Object> model) {
        model.put("loginForm", new LoginForm());
        return "index";
    }

    @RequestMapping(value = "/", params = "logout", method = RequestMethod.POST)
    public String processLogout(@ModelAttribute("loginForm") LoginForm loginForm,
                                Map<String, Object> model) {

        LOGGER.debug("Logout email: " + loginForm.getEmail());
        LOGGER.debug("Logout password: " + loginForm.getPassword());

        loginForm.setPassword("");
        loginForm.setEmail("");

        return "index";
    }

}