package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.services.ProjectReleaseService;
import com.softserverinc.edu.services.ProjectService;
import com.softserverinc.edu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

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

    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER') or (hasAnyRole('DEVELOPER', 'QA', 'GUEST') and " +
            "@projectService.findById(#projectId).guestView)")
    @GetMapping(value = "projects/project/{projectId}")
    public String projectPage(@PathVariable @P("projectId") Long projectId, Model model, Pageable pageable) {
        Page<ProjectRelease> pageableReleases = releaseService.findByProject(projectService.findById(projectId), pageable);
        model.addAttribute("usersList",
                userService.findByProjectAndIsDeletedAndEnabledIs(projectService.findById(projectId), false, 1));
        model.addAttribute("project", projectService.findById(projectId));
        model.addAttribute("releaseList", pageableReleases);
        return "project";
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/projects/{id}/remove")
    public String removeProject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        projectService.delete(id);
        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("msg", "Project was deleted!");
        return "redirect:/projects";
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('PROJECT_MANAGER') and " +
            "#id == @userService.findByEmail(#principal.getName()).get(0).getProject().getId())")
    @GetMapping(value = "/projects/{id}/edit")
    public String editProject(@PathVariable @P("id") Long id, Model model, @Param("principal") Principal principal) {
        model.addAttribute("project", projectService.findById(id));
        model.addAttribute("formaction", "edit");
        return "project_form";
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('PROJECT_MANAGER') and " +
            "#projectId == @userService.findByEmail(#principal.getName()).get(0).getProject().getId())")
    @GetMapping(value = "/projects/project/{projectId}/usersWithoutProject")
    public String usersWithoutProject(@PathVariable @P("projectId") Long projectId,
                                    Model model, @Param("principal") Principal principal, Pageable pageable){
        model.addAttribute("userList", userService.findNotDeletedUsersByRole(UserRole.ROLE_USER, false, 1, pageable));
        model.addAttribute("project", projectService.findById(projectId));
        usersRolesInProject(model);
        return "users_without_project";
    }

    @PostMapping(value = "/projects/project/{projectId}/usersWithoutProject/search")
    public String searchUsersWithoutProjects(@RequestParam(value = "searchedParam") String searchedParam,
                                            @RequestParam(value = "searchedString") String searchedString,
                                            @PathVariable("projectId") Long projectId, Model model, Pageable pageable){
        usersRolesInProject(model);
        model.addAttribute("project", projectService.findById(projectId));
        model.addAttribute("userList", userService.searchByUsersWithoutProject(searchedParam, searchedString,
                pageable));
        return "users_without_project";
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('PROJECT_MANAGER') and " +
            "#projectId == @userService.findByEmail(#principal.getName()).get(0).getProject().getId())")
    @GetMapping(value = "/projects/project/{projectId}/removeUser/{userId}")
    public String removeUserFromProject(@PathVariable Long userId, @PathVariable @P("projectId") Long projectId,
                                        @Param("principal") Principal principal,
                                        RedirectAttributes redirectAttributes) {
        User user = userService.findOne(userId);
        userService.deleteFromProject(userId);
        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("msg", String.format("%s %s was removed from project",
                user.getLastName(), user.getFirstName()));
        return "redirect: /projects/project/" + projectId;
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('PROJECT_MANAGER') and " +
            "#projectId == @userService.findByEmail(#principal.getName()).get(0).getProject().getId())")
    @GetMapping(value = "/projects/project/{projectId}/usersWithoutProject/{userId}/changeRole")
    public String changeUserRoleGet(@PathVariable @P("projectId") Long projectId, @PathVariable("userId") Long userId,
                                    @Param("principal") Principal principal, RedirectAttributes redirectAttributes) {
        User user = userService.findOne(userId);
        Project project = projectService.findById(projectId);
        redirectAttributes.addFlashAttribute("alert", "success");
        if(project.getUsers().isEmpty()) {
            redirectAttributes.addFlashAttribute("msg", String.format("%s %s was added as Project Manager",
                    user.getLastName(), user.getFirstName()));
            userService.changeUserRole(user, project, UserRole.ROLE_PROJECT_MANAGER);
            return "redirect:/projects/project/" + projectId;
        }
        userService.changeUserRole(user, project, null);
        redirectAttributes.addFlashAttribute("msg", String.format("%s %s's role was changed to %s",
                user.getLastName(), user.getFirstName(), user.getRole()));
        return "redirect:/projects/project/" + projectId;
    }

    @PostMapping(value = "/projects/project/{projectId}/usersWithoutProject/{userId}/selectRole")
    public String selectUserRole(@ModelAttribute("role") UserRole role,
                                     @PathVariable Long projectId, @PathVariable("userId") Long userId,
                                     RedirectAttributes redirectAttributes) {
        User user = userService.findOne(userId);
        Project project = projectService.findById(projectId);
        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("msg", String.format("%s %s %s was added to %s ", role,
                    user.getLastName(), user.getFirstName(), project.getTitle()));
        userService.changeUserRole(user, project, role);
        return "redirect:/projects/project/" + projectId;
    }

    private void usersRolesInProject(Model model) {
        model.addAttribute("DEV", UserRole.ROLE_DEVELOPER);
        model.addAttribute("QA", UserRole.ROLE_QA);
    }
}