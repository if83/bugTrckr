package com.softserverinc.edu.controllers;

import com.mysql.cj.mysqlx.protobuf.MysqlxDatatypes;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.forms.UserFormValidator;
import com.softserverinc.edu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
     *
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(userFormValidator);
    }

    @GetMapping(value = "/users")
    public String userForm(Model model) {
        model.addAttribute("userList", this.userService.findAll());
        populateDefaultModel(model);
        LOGGER.debug("User list");
        return "users";
    }


    @GetMapping(value = "/user/{id}/remove")
    public String removeUser(@PathVariable("id") long id, final RedirectAttributes redirectAttributes) {

        this.userService.delete(id);

        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "User is deleted!");

        LOGGER.debug("User remove");
        return "redirect:/users";
    }


    @GetMapping(value = "/user/{id}/edit")
    public String editUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", this.userService.findOne(id));
        model.addAttribute("formaction", "edit");
        populateDefaultModel(model);
        LOGGER.debug("User edit\\" + id);
        return "userform";
    }


    @GetMapping(value = "/user/add")
    public String addUser(Model model) {
        User user = new User();
        user.setId(0L);
        model.addAttribute("user", user);
        model.addAttribute("formaction", "new");
        populateDefaultModel(model);
        LOGGER.debug("User add form");
        return "userform";
    }

    @PostMapping(value = "/user/add")
    public String addUserPost(@ModelAttribute("user") @Validated User user, BindingResult result, Model model,
                              final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            populateDefaultModel(model);
            return "userform";
        } else {

            // Add message to flash scope
            redirectAttributes.addFlashAttribute("css", "success");
            if (user.isNewuser()) {
                redirectAttributes.addFlashAttribute("msg", "User added successfully!");
            } else {
                redirectAttributes.addFlashAttribute("msg", "User updated successfully!");
            }
        }

        userService.save(user);

        LOGGER.debug("User updated or saved " + user.getId());
        return "redirect:/users";
    }


    @GetMapping(value = "/user/{id}/view")
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

    @PostMapping(value = "/users/searchByName")
    public String userSearchByName(@RequestParam(value = "firstName") String firstName,
                                   @RequestParam(value = "lastName") String lastName,
                                   Model model) {
        if(!firstName.isEmpty() && !lastName.isEmpty())
            model.addAttribute("userList", this.userService.findByFirstNameContainingAndLastNameContaining(firstName, lastName));
        else if (!firstName.isEmpty())
            model.addAttribute("userList", this.userService.findByFirstNameContaining(firstName));
        else
            model.addAttribute("userList", this.userService.findByLastNameContaining(lastName));

        LOGGER.debug("User search list ByName");
        return "users";
    }

    @PostMapping(value = "/users/searchByEmail")
    public String userSearchByEmailPost (@RequestParam(value = "email") String userEmail, Model model) {
        model.addAttribute("userList", this.userService.findByEmailContaining(userEmail));
        LOGGER.debug("User search list ByEmail");
        return "users";
    }


    @PostMapping(value = "/users/searchByRole")
    public String userSearchByRole (@RequestParam(value = "role") UserRole role, Model model) {
        model.addAttribute("userList", this.userService.findByRole(role));
        populateDefaultModel(model);
        LOGGER.debug("User search list ByRole POST");
        return "users";
    }

    /**
     * Set default values
     *
     * @param model
     */
    private void populateDefaultModel(Model model) {
        model.addAttribute("roles", UserRole.values());
    }

}
