package com.softserverinc.edu.controllers;

import com.softserverinc.edu.constants.PageConstant;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
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

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectReleaseService releaseService;

    @Autowired
    private IssueService issueService;

    @GetMapping("/projects")
    public String listOfProjects(@PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageable,
                                 ModelMap model) {
        model.addAttribute("listOfProjects", projectService.findAll(pageable));
        return "projects";
    }

    @PostMapping("projects/search")
    public String projectSearchByTitle(@RequestParam String title, Model model,
                                       @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageable) {
        model.addAttribute("listOfProjects", projectService.findProjectByTitle(title, pageable));
        return "projects";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/projects/add")
    public String addProject(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("action", "new");
        return "project_form";
    }

    @PostMapping("/projects/add")
    public String addProjectPost(@ModelAttribute @Valid Project project, BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "project_form";
        }
        confirmationOfProjectChanges(project, redirectAttributes);
        projectService.save(project);
        return "redirect:/projects/project/" + project.getId();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/projects/{projectId}/remove")
    public String deleteProject(@PathVariable Long projectId, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", "Project was deleted");
        projectService.delete(projectId);
        return "redirect:/projects";
    }

    @PreAuthorize("@projectSecurityService.hasPermissionToProjectManagement(#projectId)")
    @GetMapping("/projects/{projectId}/edit")
    public String editProject(@PathVariable @P("projectId") Long projectId, Model model) {
        model.addAttribute("project", projectService.findById(projectId));
        model.addAttribute("action", "edit");
        return "project_form";
    }

    @PreAuthorize("@projectSecurityService.hasPermissionToViewProject(#projectId)")
    @GetMapping("projects/project/{projectId}")
    public String projectPage(@PathVariable @P("projectId") Long projectId, Model model,
            @Qualifier("release") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageableRelease,
            @Qualifier("user") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageableUser,
            @Qualifier("issue") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageableIssue) {
        usersRolesInProject(model);
        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("projectManager", userService.getProjectManagerOfProject(projectId));
        model.addAttribute("usersList", userService.findUsersByProjectPageable(project, pageableUser));
        model.addAttribute("releaseList", releaseService.findByProject(project, pageableRelease));
        model.addAttribute("listOfIssues", issueService.findByProject(project, pageableIssue));
        return "project";
    }

    @PostMapping("/projects/project/{projectId}/users_search")
    public String searchUsersInProjects(@PathVariable("projectId") Long projectId, Model model,
            @RequestParam String searchedParam, @RequestParam UserRole role, @RequestParam String searchedString,
            @Qualifier("release") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageableRelease,
            @Qualifier("user") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageableUser,
            @Qualifier("issue") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageableIssue) {
        usersRolesInProject(model);
        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("projectManager", userService.getProjectManagerOfProject(projectId));
        model.addAttribute("usersList",
                userService.searchByUsers(project, searchedParam, role, searchedString, pageableUser));
        model.addAttribute("releaseList", releaseService.findByProject(project, pageableRelease));
        model.addAttribute("listOfIssues", issueService.findByProject(project, pageableIssue));
        return "project";
    }

    @PostMapping("/projects/project/{projectId}/releases/search")
    public String searchByReleaseTitle(@PathVariable Long projectId, @RequestParam String searchedString, Model model,
            @Qualifier("release") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageableRelease,
            @Qualifier("user") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageableUser,
            @Qualifier("issue") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageableIssue) {
        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("projectManager", userService.getProjectManagerOfProject(projectId));
        model.addAttribute("usersList", userService.findUsersByProjectPageable(project, pageableUser));
        model.addAttribute("releaseList", releaseService.searchByTitle(project, searchedString, pageableRelease));
        model.addAttribute("listOfIssues", issueService.findByProject(project, pageableIssue));
        return "project";
    }

    @PostMapping("/projects/project/{projectId}/search_issues")
    public String searchByIssueTitle(@PathVariable Long projectId, @RequestParam String searchedString, Model model,
                  @Qualifier("release") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageableRelease,
                  @Qualifier("user") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageableUser,
                  @Qualifier("issue") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageableIssue) {
        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("projectManager", userService.getProjectManagerOfProject(projectId));
        model.addAttribute("usersList", userService.findUsersByProjectPageable(project, pageableUser));
        model.addAttribute("releaseList", releaseService.findByProject(project, pageableRelease));
        model.addAttribute("listOfIssues", issueService.findIssuesByProject(project, searchedString, pageableIssue));
        return "project";
    }

    @PreAuthorize("@projectSecurityService.hasPermissionToProjectManagement(#projectId)")
    @GetMapping("/projects/project/{projectId}/usersWithoutProject")
    public String usersWithoutProject(@PathVariable @P("projectId") Long projectId, Model model,
                                      @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageable) {
        usersRolesInProject(model);
        model.addAttribute("project", projectService.findById(projectId));
        model.addAttribute("userList", userService.findUsersByRole(UserRole.ROLE_USER, pageable));
        return "users_without_project";
    }

    @PostMapping("/projects/project/{projectId}/usersWithoutProject/search")
    public String searchUsersWithoutProjects(@RequestParam String searchedParam, @RequestParam String searchedString,
                                             @PathVariable Long projectId, Model model,
                                             @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageable) {
        usersRolesInProject(model);
        model.addAttribute("project", projectService.findById(projectId));
        model.addAttribute("userList", userService.searchByUsers(null, searchedParam, UserRole.ROLE_USER,
                searchedString, pageable));
        return "users_without_project";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/projects/project/{projectId}/addProjectManager/usersWithoutProject")
    public String usersForRoleProjectManager(@PathVariable @P("projectId") Long projectId, Model model,
           @Qualifier("availableUsers") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS)Pageable availableUsers,
           @Qualifier("usersInProject") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS)Pageable usersInProject){
        usersRolesInProject(model);
        Project project = projectService.findById(projectId);
        model.addAttribute("action", "addPM");
        model.addAttribute("project", project);
        model.addAttribute("usersInProject", userService.findUsersByProjectPageable(project, usersInProject));
        model.addAttribute("userList", userService.findUsersByRole(UserRole.ROLE_USER, availableUsers));
        return "users_without_project";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/projects/project/{projectId}/addProjectManager/usersWithoutProject/{userId}")
    public String appointmentOfProjectManager(@PathVariable Long projectId, @PathVariable Long userId,
                                              RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", String.format("%s is Project Manager",
                userService.findOne(userId).getFullName()));
        userService.saveProjectManager(userService.findOne(userId), projectId);
        return "redirect:/projects/project/" + projectId;
    }

    @PostMapping("/projects/project/{projectId}/addProjectManager/usersWithoutProject/allUsers_search")
    public String searchUsersForAppointmentPM(@RequestParam String searchedParam, @RequestParam String searchedString,
           @PathVariable Long projectId, Model model,
           @Qualifier("users") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS)Pageable allUsers,
           @Qualifier("usersInProject") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS)Pageable usersInProject) {
        usersRolesInProject(model);
        Project project = projectService.findById(projectId);
        model.addAttribute("action", "addPM");
        model.addAttribute("project", project);
        model.addAttribute("userList", userService.searchByUsers(null, searchedParam, UserRole.ROLE_USER,
                searchedString, allUsers));
        model.addAttribute("usersInProject", userService.findUsersByProjectPageable(project, usersInProject));
        return "users_without_project";
    }

    @PostMapping("/projects/project/{projectId}/addProjectManager/usersWithoutProject/usersInProject_search")
    public String searchUsersInProjectForAppointmentPM(@RequestParam String searchedParam,
           @RequestParam String searchedString, @PathVariable Long projectId, Model model,
           @Qualifier("users") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS)Pageable allUsers,
           @Qualifier("usersInProject") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS)Pageable usersInProject) {
        usersRolesInProject(model);
        Project project = projectService.findById(projectId);
        model.addAttribute("action", "addPM");
        model.addAttribute("project", project);
        model.addAttribute("userList", userService.findUsersByRole(UserRole.ROLE_USER, allUsers));
        model.addAttribute("usersInProject", userService.searchByUsers(project, searchedParam, null, searchedString,
                usersInProject));
        return "users_without_project";
    }

    @PreAuthorize("@projectSecurityService.hasPermissionToProjectManagement(#projectId)")
    @GetMapping("/projects/project/{projectId}/removeUser/{userId}")
    public String deleteUserFromProject(@PathVariable Long userId, @PathVariable @P("projectId") Long projectId,
                                        RedirectAttributes redirectAttributes) {
        User user = userService.findOne(userId);
        redirectAttributes.addFlashAttribute("msg", String.format("%s was removed from project", user.getFullName()));
        userService.userManagementInProject(user, null, UserRole.ROLE_USER);
        return "redirect:/projects/project/" + projectId;
    }

    @PreAuthorize("@projectSecurityService.hasPermissionToProjectManagement(#projectId)")
    @GetMapping("/projects/project/{projectId}/usersWithoutProject/{userId}/changeRole")
    public String appointmentUserToProject(@PathVariable @P("projectId") Long projectId, @PathVariable Long userId,
                                           RedirectAttributes redirectAttributes) {
        confirmationOfChangeOfUserRole(userId, redirectAttributes);
        userService.changeUserRoleInProject(userId, projectId, null);
        return "redirect:/projects/project/" + projectId;
    }

    @PostMapping("/projects/project/{projectId}/usersWithoutProject/{userId}/selectRole")
    public String selectUserRole(@ModelAttribute("role") UserRole role, @PathVariable Long projectId,
                                 @PathVariable Long userId, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", String.format("%s %s was added to %s ", role,
                userService.findOne(userId).getFullName(), projectService.findById(projectId).getTitle()));
        userService.changeUserRoleInProject(userId, projectId, role);
        return "redirect:/projects/project/" + projectId;
    }

    private void usersRolesInProject(Model model) {
        model.addAttribute("DEV", UserRole.ROLE_DEVELOPER);
        model.addAttribute("QA", UserRole.ROLE_QA);
    }

    private void confirmationOfProjectChanges(Project project, RedirectAttributes redirectAttributes) {
        if (project.getId() == null) {
            redirectAttributes.addFlashAttribute("msg", String.format("%s added successfully!", project.getTitle()));

        } else {
            redirectAttributes.addFlashAttribute("msg", String.format("%s updated successfully!", project.getTitle()));
        }
    }

    private void confirmationOfChangeOfUserRole(Long userId, RedirectAttributes redirectAttributes) {
        User user = userService.findOne(userId);
        UserRole role = user.getRole();
        if (role.isDeveloper()) {
            redirectAttributes.addFlashAttribute("msg", String.format("role of %s was changed to QA",
                    user.getFullName()));
            return;
        }
        if (role.isQA()) {
            redirectAttributes.addFlashAttribute("msg", String.format("role of %s was changed to Developer",
                    user.getFullName()));
        }
    }

}