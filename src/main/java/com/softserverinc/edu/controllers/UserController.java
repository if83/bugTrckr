package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.forms.UserFormValidator;
import com.softserverinc.edu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * User controller
 */
@Controller
public class UserController {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    UserFormValidator userFormValidator;

    /**
     * Set a form validator
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(userFormValidator);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String userForm(Model model) {
        model.addAttribute("userList", this.userService.getAll());
        LOGGER.debug("User list");
        return "users";
    }

    @RequestMapping(value = "/user/{id}/remove")
    public String removeUser(@PathVariable("id") long id, final RedirectAttributes redirectAttributes){

        this.userService.delete(id);

        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "User is deleted!");

        LOGGER.debug("User remove");
        return "redirect:/users";
    }


    @RequestMapping(value = "/user/{id}/edit", method = RequestMethod.GET)
    public String editUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", this.userService.findOne(id));
        model.addAttribute("formaction", "edit");
        populateDefaultModel(model);
        LOGGER.debug("User edit\\" + id);
        return "userform";
    }


    @RequestMapping(value = "/user/add", method = RequestMethod.GET)
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("formaction", "new");
        populateDefaultModel(model);
        LOGGER.debug("User add form");
        return "userform";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String addUserPost(@ModelAttribute("user") @Validated User user, BindingResult result, Model model,
                              final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            populateDefaultModel(model);
            return "userform";
        } else {

            // Add message to flash scope
            redirectAttributes.addFlashAttribute("css", "success");
            if(user.isNewuser()){
                redirectAttributes.addFlashAttribute("msg", "User added successfully!");
            }else{
                redirectAttributes.addFlashAttribute("msg", "User updated successfully!");
            }
        }

        userService.saveOrUpdate(user);

        LOGGER.debug("User updated or saved " + user.getId());
        return "redirect:/users";
    }


    @RequestMapping(value = "/user/{id}/view", method = RequestMethod.GET)
    public String viewUser(@PathVariable("id") long id, Model model) {

        LOGGER.debug("viewUser() id: {}", id);

        User user = userService.findOne(id);
        if (user == null) {
            model.addAttribute("css", "danger");
            model.addAttribute("msg", "User not found");
        }
        model.addAttribute("user", user);

        return "userview";

    }

    /**
     * Set default values
     * @param model
     */
    private void populateDefaultModel(Model model) {
        model.addAttribute("roles", UserRole.values());
    }

}
