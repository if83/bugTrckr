package com.softserverinc.edu.controllers;

import com.softserverinc.edu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userForm(Model model) {
        model.addAttribute("userList", userService.getAll());
        return "user";
    }

    @RequestMapping(value = "/user/remove/{id}")
    public String removeUser(@PathVariable("id") long id){
        this.userService.delete(id);
        return "redirect:/user";
    }

}
