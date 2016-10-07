package com.softserverinc.edu.controllers;

import com.softserverinc.edu.constants.PageConstant;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.forms.UserFormValidator;
import com.softserverinc.edu.services.HistoryService;
import com.softserverinc.edu.services.ProjectService;
import com.softserverinc.edu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserFormValidator userFormValidator;

    @PreAuthorize("@userSecurityService.hasPermissionToUserManagement()")
    @GetMapping("/users")
    public String userForm(Model model, Pageable pageable) {
        populateDefaultModel(model);
        model.addAttribute("userList", userService.findAllUsers(pageable));
        return "users";
    }

    @PreAuthorize("@userSecurityService.hasPermissionToUserManagement()")
    @GetMapping("/user/{id}/remove")
    public String removeUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.setIsDeleted(id, true);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "User is deleted!");
        return "redirect:/users";
    }

    @PreAuthorize("@userSecurityService.hasPermissionToUserManagement()")
    @GetMapping("/user/{id}/restore")
    public String restoreUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.setIsDeleted(id, false);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "User is restored!");
        return "redirect:/users";
    }

    @PreAuthorize("@userSecurityService.hasPermissionToUserManagement()")
    @GetMapping("/user/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        populateDefaultModel(model);
        model.addAttribute("user", userService.findOne(id));
        return "user_form_edit";
    }

    @PreAuthorize("@userSecurityService.hasPermissionToUserManagement()")
    @PostMapping("/user/{id}/edit")
    public String editUserPost(@PathVariable Long id, @RequestParam String email,
                               @RequestParam String firstName, @RequestParam String lastName,
                               @RequestParam Long projectId, @RequestParam UserRole role,
                               @RequestParam String description, RedirectAttributes redirectAttributes, Model model){
        populateDefaultModel(model);
        if(userService.isEmailExists(email, id)){
            redirectAttributes.addFlashAttribute("msg", "User with the same email address already exists");
            return"redirect:/user/" + id + "/edit";
        }
        redirectAttributes.addFlashAttribute("msg", String.format("user %s %s is edited", firstName, lastName));
        User user = userService.findOne(id);
        userService.userRoleAndProjectValidator(user, projectService.findById(projectId), role);
        userService.saveEditedUser(id, email, firstName, lastName, projectId, role, description);
        return "redirect:/users";
    }

    @PreAuthorize("@userSecurityService.hasPermissionToUserManagement()")
    @GetMapping("/user/add")
    public String addUser(Model model) {
        populateDefaultModel(model);
        model.addAttribute("user", new User());
        return "userform";
    }

    @PreAuthorize("@userSecurityService.hasPermissionToUserManagement()")
    @PostMapping("/user/add")
    public String addUserPost(@ModelAttribute @Valid User user, BindingResult result,
                              RedirectAttributes redirectAttributes, Model model) {
        populateDefaultModel(model);
        userFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "userform";
        }
        userService.userRoleAndProjectValidator(user, user.getProject(), user.getRole());
        userService.saveUser(user);
        notificationOfAddingNewUser(user, redirectAttributes);
        return "redirect:/users";
    }

    @PreAuthorize("@userSecurityService.hasPermissionToViewUserProfile()")
    @GetMapping("/user/{id}/view")
    public String viewUser(@PathVariable long id, Model model,
                           @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageable) {
        User user = userService.findOne(id);
        model.addAttribute("allHistory", historyService.findAllHistoryForUser(user, pageable));
        model.addAttribute("commentHistory", historyService.findCommentHistoryForUser(user, pageable));
        model.addAttribute("user", user);
        return "userview";
    }

    @PostMapping("/users/search")
    public String userSearch(@RequestParam String firstName, @RequestParam String lastName,
                                   @RequestParam String email, @RequestParam String role,
                                   Model model, Pageable pageable) {
        model.addAttribute("userList", userService.search(firstName, lastName,
                email, UserRole.valueOf(role), pageable));
        populateDefaultModel(model);
        return "users";
    }

    @PreAuthorize("@userSecurityService.hasPermissionToViewUserProfile()")
    @GetMapping("/user/details")
    public String viewUserByDetails(Principal principal) {
        String loggedInUserName = principal.getName();
        User user = userService.findByEmailIs(loggedInUserName);
        long id = user.getId();
        return "redirect:/user/" + id + "/view";
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

    private void notificationOfAddingNewUser(User user, RedirectAttributes redirectAttributes){
        if(user.getRole().isProjectManager()){
            redirectAttributes.addFlashAttribute("msg", String.format("%s is Project Manager of %s", user.getFullName(),
                    user.getProject().getTitle()));
        }
        else {
            redirectAttributes.addFlashAttribute("msg", String.format("%s is added", user.getFullName()));
        }
    }
}