package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.ReleaseStatus;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.services.ProjectReleaseService;
import com.softserverinc.edu.services.ProjectService;
import com.softserverinc.edu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class ProjectController {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectReleaseService releaseService;

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String listOfProjects(ModelMap model, Pageable pageable) {
        Page<Project> project = projectService.findAll(pageable);
        model.addAttribute("listOfProjects", project);
        model.addAttribute("totalPagesCount", project.getTotalPages());
        model.addAttribute("isControllerPagable", true);
        return "projects";
    }

    @PostMapping(value = "/search")
    public String projectSearchByTitle(@RequestParam(value = "title") String title, Model model) {
        model.addAttribute("listOfProjects", projectService.findByTitle(title));
        return "projects";
    }

    @GetMapping(value = "projects/project/{projectId}")
    public String projectById(@PathVariable("projectId") Long projectId, Model model) {
        Project project = projectService.findById(projectId);
        List<User> users = userService.findByProject(project);
        List<ProjectRelease> releases = releaseService.findByProject(project);
        model.addAttribute("usersList", users);
        model.addAttribute("project", project);
        model.addAttribute("releases", releases);
        return "project";
    }

    @GetMapping(value = "/projects/add")
    public String addProject(Model model) {
        Project project = new Project();
        model.addAttribute("project", project);
        model.addAttribute("formaction", "new");
        return "project_form";
    }

    @PostMapping(value = "/projects/add")
    public String addProjectPost(@ModelAttribute("project") Project project,
                                 RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", "Project added successfully!");
        redirectAttributes.addFlashAttribute("css", "success");
        if (project.getId() == null) {
            redirectAttributes.addFlashAttribute("msg", "Project added successfully!");
            projectService.save(project);
        } else {
            redirectAttributes.addFlashAttribute("msg", "Project updated successfully!");
            projectService.update(project);
        }
        return "redirect:/projects/project/" + project.getId();
    }

    @GetMapping(value = "/projects/{id}/remove")
    public String removeProject(@PathVariable("id") Long id, final RedirectAttributes redirectAttributes) {
        for(User user: projectService.findById(id).getUsers()){
            user.setRole(UserRole.ROLE_USER);
            user.setProject(null);
        }
        projectService.delete(id);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Project was deleted!");
        return "redirect:/projects";
    }

    @GetMapping(value = "/projects/{id}/edit")
    public String editProject(@PathVariable("id") Long id, Model model) {
        Project project = projectService.findById(id);
        model.addAttribute("project", project);
        model.addAttribute("formaction", "edit");
        return "project_form";
    }

    @GetMapping(value = "/projects/project/{projectId}/usersWithoutProject")
    public String addUsersToProject(@PathVariable("projectId") Long projectId, Model model){
        model.addAttribute("userList", userService.findByRole(UserRole.ROLE_USER));
        model.addAttribute("project", projectService.findById(projectId));
        return "users_without_project";
    }

    @PostMapping(value = "/projects/project/{projectId}/usersWithoutProject")
    public String searchUserByEmail(@PathVariable("projectId") Long projectId,
                                    @RequestParam(value = "email") String email,
                                    @RequestParam(value = "role") UserRole role, Model model) {
        model.addAttribute("project", projectService.findById(projectId));
        model.addAttribute("userList", userService.findByEmailContainingAndRole(email, role));
        populateDefaultModel(model);
        return "users_without_project";
    }

    @GetMapping(value="/projects/project/{projectId}/removeUser/{userId}")
    public String removeUserFromProject(@PathVariable("userId") Long userId) {
        User user = userService.findOne(userId);
        user.setRole(UserRole.ROLE_USER);
        user.setProject(null);
        userService.save(user);
        return "redirect: /projects/project/{projectId}";
    }

    @GetMapping(value = "/projects/project/{projectId}/usersWithoutProject/{userId}/role")
    public String changeUserRoleGet(@PathVariable("projectId") Long projectId,
                                 @PathVariable("userId") Long userId, Model model) {
        model.addAttribute("formaction", "new");
        Project project = projectService.findById(projectId);
        populateDefaultModel(model);
        User user = userService.findOne(userId);
        UserRole role = null;
        model.addAttribute("role", role);
        model.addAttribute("project", project);
        model.addAttribute("user", user);
        return "user_role_form";
    }

    @PostMapping(value = "/projects/project/{projectId}/usersWithoutProject/{userId}/role")
    public String changeUserRolePost(@ModelAttribute("role") UserRole role, @PathVariable("projectId") Long projectId,
                             @PathVariable("userId") Long userId, Model model, RedirectAttributes redirectAttributes) {
        Project project = projectService.findById(projectId);
        User user = userService.findOne(userId);
        model.addAttribute("user", user);
        model.addAttribute("project", project);
        user.setRole(role);
        user.setProject(project);
        userService.save(user);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", String.format("%s %s was added successfully", user.getLastName(),
                user.getFirstName()));
        return "redirect:/projects/project/" + projectId;
    }

    private void populateDefaultModel(Model model) {
        model.addAttribute("statuses", ReleaseStatus.values());
        model.addAttribute("roles", UserRole.values());
    }
}