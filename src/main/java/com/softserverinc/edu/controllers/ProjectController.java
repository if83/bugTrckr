package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.services.ProjectReleaseService;
import com.softserverinc.edu.services.ProjectService;
import com.softserverinc.edu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ProjectController {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectReleaseService releaseService;

    @GetMapping(value = "/projects")
    public String listOfProjects(ModelMap model, Pageable pageable) {
        model.addAttribute("listOfProjects", projectService.findAll(pageable));
        return "projects";
    }

    @PostMapping(value = "/search")
    public String projectSearchByTitle(@RequestParam(value = "title") String title, Model model, Pageable pageable) {
        model.addAttribute("listOfProjects", projectService.findByTitleContaining(title, pageable));
        return "projects";
    }

    @GetMapping(value = "projects/project/{projectId}")
    public String projectPage(@PathVariable("projectId") Long projectId, Model model) {
        model.addAttribute("usersList",
                userService.findByProjectAndIsDeletedAndEnabledIs(projectService.findById(projectId), false, 1));
        model.addAttribute("project", projectService.findById(projectId));
        model.addAttribute("releases", releaseService.findByProject(projectService.findById(projectId)));
        return "project";
    }

    @GetMapping(value = "/projects/add")
    public String addProject(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("formaction", "new");
        return "project_form";
    }

    @PostMapping(value = "/projects/add")
    public String addProjectPost(@ModelAttribute("project") @Valid Project project, BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("alert", "success");
        if(result.hasErrors()) {
            return "project_form";
        }
        if (project.getId() == null) {
            redirectAttributes.addFlashAttribute("msg", String.format("%s added successfully!", project.getTitle()));

        } else {
            redirectAttributes.addFlashAttribute("msg", String.format("%s updated successfully!", project.getTitle()));
        }
        projectService.save(project);
        return "redirect:/projects/project/" + project.getId();
    }

    @GetMapping(value = "/projects/{id}/remove")
    public String removeProject(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        projectService.delete(id);
        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("msg", "Project was deleted!");
        return "redirect:/projects";
    }

    @GetMapping(value = "/projects/{id}/edit")
    public String editProject(@PathVariable("id") Long id, Model model) {
        model.addAttribute("project", projectService.findById(id));
        model.addAttribute("formaction", "edit");
        return "project_form";
    }

    @GetMapping(value = "/projects/project/{projectId}/usersWithoutProject")
    public String addUsersToProject(@PathVariable("projectId") Long projectId, Model model, Pageable pageable){
        model.addAttribute("userList",
                userService.findByRoleAndIsDeletedAndEnabledIs(UserRole.ROLE_USER, false, 1, pageable));
        model.addAttribute("project", projectService.findById(projectId));
        return "users_without_project";
    }

    @PostMapping(value = "/projects/project/{projectId}/usersWithoutProject/search")
    public String searchUsersWithoutProjects(@RequestParam(value = "searchedParam") String searchedParam,
                                            @RequestParam(value = "searchedString") String searchedString,
                                            @PathVariable("projectId") Long projectId, Model model,
                                            Pageable pageable) {
        model.addAttribute("project", projectService.findById(projectId));
        model.addAttribute("userList", userService.searchByUsersWithoutProject(searchedParam, searchedString,
                pageable));
        return "users_without_project";
    }

    @GetMapping(value = "/projects/project/{projectId}/removeUser/{userId}")
    public String removeUserFromProject(@PathVariable("userId") Long userId) {
        userService.deleteFromProject(userId);
        return "redirect: /projects/project/{projectId}";
    }

    @GetMapping(value = "/projects/project/{projectId}/usersWithoutProject/{userId}/role")
    public String changeUserRoleGet(@PathVariable("projectId") Long projectId,
                                    @PathVariable("userId") Long userId, Model model) {
        User user = userService.findOne(userId);
        Project project = projectService.findById(projectId);
        if(user.getProject() != null){
            userService.changeUserRoleInProject(user);
            return "redirect:/projects/project/" + projectId;
        }else if(project.getUsers().isEmpty()){
            userService.changeUserRole(user, project, UserRole.ROLE_PROJECT_MANAGER);
            return "redirect:/projects/project/" + projectId;
        }else{
            usersRolesInProject(model);
            model.addAttribute("project", project);
            model.addAttribute("user", user);
            return "user_role_form";
        }
    }

    @PostMapping(value = "/projects/project/{projectId}/usersWithoutProject/{userId}/role")
    public String changeUserRolePost(@ModelAttribute("role") UserRole role,
                                     @PathVariable("projectId") Long projectId,
                                     @PathVariable("userId") Long userId, Model model,
                                     RedirectAttributes redirectAttributes) {
        User user = userService.findOne(userId);
        Project project = projectService.findById(projectId);
        model.addAttribute("user", user);
        model.addAttribute("project", project);
        redirectAttributes.addFlashAttribute("alert", "success");
        if(user.getProject() == project){
            redirectAttributes.addFlashAttribute("msg", String.format("%s %s's role was changed to %s",
                    user.getLastName(), user.getFirstName(), role));
        } else{
            redirectAttributes.addFlashAttribute("msg", String.format("%s %s %s was added to %s ", role,
                    user.getLastName(), user.getFirstName(), project.getTitle()));
        }
        userService.changeUserRole(user, project, role);
        return "redirect:/projects/project/" + projectId;
    }

    private void usersRolesInProject(Model model) {
        model.addAttribute("PM", UserRole.ROLE_PROJECT_MANAGER);
        model.addAttribute("DEV", UserRole.ROLE_DEVELOPER);
        model.addAttribute("QA", UserRole.ROLE_QA);
        model.addAttribute("USER", UserRole.ROLE_USER);
    }
}