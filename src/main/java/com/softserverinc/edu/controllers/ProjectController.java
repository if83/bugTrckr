package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.services.IssueService;
import com.softserverinc.edu.services.ProjectReleaseService;
import com.softserverinc.edu.services.ProjectService;
import com.softserverinc.edu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
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

    @Autowired
    private IssueService issueService;

    @GetMapping("/projects")
    public String listOfProjects(ModelMap model, @PageableDefault(value = 12) Pageable pageable, Principal principal) {
        model.addAttribute("listOfProjects", projectService.findAll(pageable));
        if (principal != null) {
            model.addAttribute("loggedUser", userService.findByEmailIs(principal.getName()));
        }
        return "projects";
    }

    @PostMapping(value = "projects/search")
    public String projectSearchByTitle(@RequestParam String title, Model model,
                                       @PageableDefault(value = 12) Pageable pageable) {
        model.addAttribute("listOfProjects", projectService.findByTitleContaining(title, pageable));
        return "projects";
    }

    @PreAuthorize("isAuthenticated() or (isAnonymous() and @projectService.findById(#projectId).guestView)")
    @GetMapping("projects/project/{projectId}")
    public String projectPage(@PathVariable @P("projectId") Long projectId, Model model,
                              @Qualifier("release") @PageableDefault(value = 12) Pageable pageableRelease,
                              @Qualifier("project") @PageableDefault(value = 12) Pageable pageableProject,
                              @Qualifier("issue") @PageableDefault(value = 12) Pageable pageableIssue) {
        usersRolesInProject(model);
        Project project = projectService.findById(projectId);
        model.addAttribute("usersList", userService.findUsersInProjectPageable(project, false, 1, pageableProject));
        model.addAttribute("project", project);
        model.addAttribute("releaseList", releaseService.findByProject(project, pageableRelease));
        model.addAttribute("listOfIssues", issueService.findByProjectId(projectId, pageableIssue));
        return "project";
    }

    @PostMapping("/projects/project/{projectId}/users_search")
    public String searchUsersWithoutProjects(@RequestParam String searchedParam, @RequestParam UserRole role,
                                             @RequestParam String searchedString, @PathVariable Long projectId,
                                             Model model, @PageableDefault(value = 12) Pageable pageable){
        usersRolesInProject(model);
        Project project = projectService.findById(projectId);
        model.addAttribute("releaseList", releaseService.findByProject(project, pageable));
        model.addAttribute("project", project);
        model.addAttribute("usersList", userService.searchByUsersInProject(project, searchedParam, role, searchedString,
                pageable));
        return "project";
    }

    @PostMapping("/projects/project/{projectId}/releases/search")
    public String searchByReleaseTitle(@RequestParam String searchedString, @PathVariable Long projectId, Model model,
                                       @Qualifier("release") @PageableDefault(value = 12) Pageable pageableRelease,
                                       @Qualifier("project") @PageableDefault(value = 12) Pageable pageableProject) {
        Project project = projectService.findById(projectId);
        model.addAttribute("releaseList", releaseService.searchByVersionNameContaining(project, searchedString,
                pageableRelease));
        model.addAttribute("usersList", userService.findUsersInProjectPageable(projectService.findById(projectId),
                false, 1, pageableProject));
        model.addAttribute("project", project);
        return "project";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/projects/add")
    public String addProject(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("formaction", "new");
        return "project_form";
    }

    @PostMapping("/projects/add")
    public String addProjectPost(@ModelAttribute @Valid Project project, BindingResult result,
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
    @GetMapping("/projects/{id}/remove")
    public String removeProject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        projectService.delete(id);
        return "redirect: /projects";
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('PROJECT_MANAGER') and " +
            "#id == @userService.findByEmailIs(#principal.getName()).getProject().getId())")
    @GetMapping("/projects/{id}/edit")
    public String editProject(@PathVariable @P("id") Long id, Model model, @Param("principal") Principal principal) {
        model.addAttribute("project", projectService.findById(id));
        model.addAttribute("formaction", "edit");
        return "project_form";
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('PROJECT_MANAGER') and " +
            "#projectId == @userService.findByEmailIs(#principal.getName()).getProject().getId())")
    @GetMapping("/projects/project/{projectId}/usersWithoutProject")
    public String usersWithoutProject(@PathVariable @P("projectId") Long projectId, Model model,
                                      @Param("principal") Principal principal,
                                      @PageableDefault(value = 12) Pageable pageable){
        model.addAttribute("userList", userService.findNotDeletedUsersByRole(UserRole.ROLE_USER, false, 1, pageable));
        model.addAttribute("project", projectService.findById(projectId));
        usersRolesInProject(model);
        return "users_without_project";
    }

    @PostMapping("/projects/project/{projectId}/usersWithoutProject/search")
    public String searchUsersWithoutProjects(@RequestParam String searchedParam, @RequestParam String searchedString,
                                             @PathVariable Long projectId, Model model,
                                             @PageableDefault(value = 12) Pageable pageable){
        usersRolesInProject(model);
        model.addAttribute("project", projectService.findById(projectId));
        model.addAttribute("userList", userService.searchByUsersWithoutProject(searchedParam, searchedString,
                pageable));
        return "users_without_project";
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('PROJECT_MANAGER') and " +
            "#projectId == @userService.findByEmailIs(#principal.getName()).getProject().getId())")
    @GetMapping("/projects/project/{projectId}/removeUser/{userId}")
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
            "#projectId == @userService.findByEmailIs(#principal.getName()).getProject().getId())")
    @GetMapping("/projects/project/{projectId}/usersWithoutProject/{userId}/changeRole")
    public String changeUserRoleGet(@PathVariable @P("projectId") Long projectId, @PathVariable Long userId,
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

    @PostMapping("/projects/project/{projectId}/usersWithoutProject/{userId}/selectRole")
    public String selectUserRole(@ModelAttribute("role") UserRole role,
                                 @PathVariable Long projectId, @PathVariable Long userId,
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
        model.addAttribute("PM", UserRole.ROLE_PROJECT_MANAGER);
    }
}