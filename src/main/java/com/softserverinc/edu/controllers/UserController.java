package com.softserverinc.edu.controllers;

import com.softserverinc.edu.constants.PageConstant;
import com.softserverinc.edu.entities.HistoryDto;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.forms.FileUploadForm;
import com.softserverinc.edu.forms.UserFormValidator;
import com.softserverinc.edu.repositories.UserRepository;
import com.softserverinc.edu.services.HistoryService;
import com.softserverinc.edu.services.ProjectService;
import com.softserverinc.edu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * User controller
 */
@Controller
@SessionAttributes("fileUploadForm")
public class UserController {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserFormValidator userFormValidator;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public String userForm(Model model, Pageable pageable) {
        populateDefaultModel(model);
        model.addAttribute("userList", userService.findByIsDeletedFalseAndEnabledIs(1, pageable));
        return "users";
    }

    @GetMapping("/user/{id}/remove")
    public String removeUser(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        userService.setIsDeletedTrue(id);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "User is deleted!");
        return "redirect:/users";
    }

    @GetMapping("/user/{id}/edit")
    public String editUser(@PathVariable("id") long id, Model model) {
        User user = userService.findOne(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", userService.getAvailableRolesForUser(user.getRole()));
        model.addAttribute("formaction", "edit");
        return "userform";
    }

    @GetMapping("/user/add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("formaction", "new");
        populateDefaultModel(model);
        return "userform";
    }

    @PostMapping("/user/add")
    public String addUserPost(@ModelAttribute @Valid User user, BindingResult result,
                              RedirectAttributes redirectAttributes) {
        userFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "userform";
        }
        userService.saveUser(user, redirectAttributes);
        return "redirect:/users";
    }

    @GetMapping("/user/{id}/view")
    public String viewUser(@PathVariable("id") long id, Model model,
                           @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageable) {
        User user = userService.findOne(id);
        model.addAttribute("allHistory", historyService.findAllHistoryForUser(user, pageable));
        model.addAttribute("commentHistory", historyService.findCommentHistoryForUser(user, pageable));
        model.addAttribute("user", user);
        return "userview";
    }

    @GetMapping("/user/details")
    public String viewUserByDetails(Principal principal) {
        String loggedInUserName = principal.getName();
        User user = userService.findByEmailIs(loggedInUserName);
        long id = user.getId();
        return "redirect:/user/" + id + "/view";
    }

    @PostMapping("/users/searchByName")
    public String userSearchByName(@RequestParam String firstName, @RequestParam String lastName, Model model,
                                   Pageable pageable) {
        if (!firstName.isEmpty() && !lastName.isEmpty())
            model.addAttribute("userList", userService.findByFullName(firstName, lastName, pageable));
        else if (!firstName.isEmpty())
            model.addAttribute("userList", userService.findByFirstNameContaining(firstName, pageable));
        else
            model.addAttribute("userList", userService.findByLastNameContaining(lastName, pageable));
        populateDefaultModel(model);
        return "users";
    }

    @PostMapping("/users/searchByEmail")
    public String userSearchByEmailPost(@RequestParam(value = "email") String userEmail, Model model) {
        model.addAttribute("userList", userService.findByEmailContaining(userEmail));
        populateDefaultModel(model);
        LOGGER.debug("User search list ByEmail");
        return "users";
    }

    @PostMapping(value = "/users/searchByRole")
    public String userSearchByRole(@RequestParam(value = "role") UserRole role, Model model) {
        model.addAttribute("userList", userService.findByRole(role));
        populateDefaultModel(model);
        LOGGER.debug("User search list ByRole POST");
        return "users";
    }

    @PostMapping(value = "/user/addimage")
    public String fileUploadPost(@RequestParam("userId") long userId,
                                 @RequestPart("fileImage") MultipartFile fileImage,
                                 Model model) throws IOException {

        String redirectPath = "redirect:/user/add";

        if (userId != 0L) {
            redirectPath = "redirect:/user/" + userId + "/edit";
        }

        //save an image for redirection
        FileUploadForm fileUploadForm = new FileUploadForm();
        fileUploadForm.setUserId(userId);
        fileUploadForm.setFileImage(fileImage.getBytes());
        fileUploadForm.setFileName(fileImage.getOriginalFilename());
        model.addAttribute("fileUploadForm", fileUploadForm);

        LOGGER.debug("formUser() id: ", userId);
        return redirectPath;
    }

    private void populateDefaultModel(Model model) {
        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.ROLE_USER);
        roles.add(UserRole.ROLE_QA);
        roles.add(UserRole.ROLE_DEVELOPER);
        roles.add(UserRole.ROLE_PROJECT_MANAGER);
        model.addAttribute("roles", roles);
        model.addAttribute("projects", projectService.findAll());
    }

}