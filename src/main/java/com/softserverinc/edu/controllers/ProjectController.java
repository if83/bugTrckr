package com.softserverinc.edu.controllers;

import com.softserverinc.edu.constants.PageConstant;
import com.softserverinc.edu.entities.Project;
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
    public String listOfProjects(@PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageable,
                                 ModelMap model) {
        model.addAttribute("listOfProjects", projectService.findAll(pageable));
        return "projects";
    }

    @PostMapping("projects/search")
    public String projectSearchByTitle(@RequestParam String title, Model model,
                                       @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageable) {
        model.addAttribute("listOfProjects", projectService.findByTitleContaining(title, pageable));
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
        if(result.hasErrors()) {
            return "project_form";
        }
        projectService.save(project, redirectAttributes);
        return "redirect:/projects/project/" + project.getId();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/projects/{projectId}/remove")
    public String removeProject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        projectService.delete(id, redirectAttributes);
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
        model.addAttribute("projectManager", userService.getProjectManagerOfProject(project));
        model.addAttribute("usersList", userService.findUsersInProjectPageable(project, false, 1, pageableUser));
        model.addAttribute("releaseList", releaseService.findByProject(project, pageableRelease));
        model.addAttribute("listOfIssues", issueService.findByProject(project, pageableIssue));
        return "project";
    }

    @PostMapping("/projects/project/{projectId}/users_search")
    public String searchUsersInProjects(@PathVariable("projectId") Long projectId, Model model,
            @RequestParam String searchedParam,
            @RequestParam UserRole role,
            @RequestParam String searchedString,
            @Qualifier("release") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageableRelease,
            @Qualifier("user") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageableUser,
            @Qualifier("issue") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageableIssue) {
        usersRolesInProject(model);
        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("projectManager", userService.getProjectManagerOfProject(project));
        model.addAttribute("usersList", userService.userSearch(project, searchedParam, role, searchedString,
                pageableUser));
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
        Page<User> usersList = userService.findUsersInProjectPageable(project, false, 1, pageableUser);
        model.addAttribute("usersList", usersList);
        model.addAttribute("projectManager", userService.getProjectManagerOfProject(project));
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
        model.addAttribute("usersList", userService.findUsersInProjectPageable(project, false, 1, pageableUser));
        model.addAttribute("projectManager", userService.getProjectManagerOfProject(project));
        model.addAttribute("releaseList", releaseService.findByProject(project, pageableRelease));
        model.addAttribute("listOfIssues", issueService.findIssuesByProject(project, searchedString ,pageableIssue));
        return "project";
    }

    @PreAuthorize("@projectSecurityService.hasPermissionToProjectManagement(#projectId)")
    @GetMapping("/projects/project/{projectId}/usersWithoutProject")
    public String usersWithoutProject(@PathVariable @P("projectId") Long projectId, Model model,
                                      @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS)Pageable pageable){
        model.addAttribute("userList", userService.findNotDeletedUsersByRole(UserRole.ROLE_USER, false, 1, pageable));
        model.addAttribute("project", projectService.findById(projectId));
        usersRolesInProject(model);
        return "users_without_project";
    }

    @PostMapping("/projects/project/{projectId}/usersWithoutProject/search")
    public String searchUsersWithoutProjects(@RequestParam String searchedParam, @RequestParam String searchedString,
                                             @PathVariable Long projectId, Model model,
                                             @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS) Pageable pageable) {
        usersRolesInProject(model);
        model.addAttribute("project", projectService.findById(projectId));
        model.addAttribute("userList", userService.userSearch(null, searchedParam, UserRole.ROLE_USER, searchedString,
                pageable));
        return "users_without_project";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/projects/project/{projectId}/addProjectManager/usersWithoutProject")
    public String usersForRoleProjectManager(@PathVariable @P("projectId") Long projectId, Model model,
           @Qualifier("users") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS)Pageable allUsers,
           @Qualifier("usersInProject") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS)Pageable usersInProject){
        usersRolesInProject(model);
        Project project = projectService.findById(projectId);
        model.addAttribute("action", "addPM");
        model.addAttribute("project", project);
        model.addAttribute("usersInProject", userService.findUsersInProjectPageable(project, false, 1, usersInProject));
        model.addAttribute("userList", userService.findNotDeletedUsersByRole(UserRole.ROLE_USER, false, 1, allUsers));
        return "users_without_project";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/projects/project/{projectId}/addProjectManager/usersWithoutProject/{userId}")
    public String choseProjectManager(@PathVariable Long projectId, @PathVariable Long userId,
                                      RedirectAttributes redirectAttributes){
        userService.projectManagerAppointment(userId, projectId, redirectAttributes);
        return "redirect:/projects/project/" + projectId;
    }

    @PostMapping("/projects/project/{projectId}/addProjectManager/usersWithoutProject/allUsers_search")
    public String searchUsersForAppointmentPM(@RequestParam String searchedParam, @RequestParam String searchedString,
           @PathVariable Long projectId, Model model,
           @Qualifier("users") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS)Pageable allUsers,
           @Qualifier("usersInProject") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS)Pageable usersInProject) {
        usersRolesInProject(model);
        model.addAttribute("action", "addPM");
        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("userList", userService.userSearch(null, searchedParam, UserRole.ROLE_USER, searchedString,
                allUsers));
        model.addAttribute("usersInProject", userService.findUsersInProjectPageable(project, false, 1, usersInProject));
        return "users_without_project";
    }

    @PostMapping("/projects/project/{projectId}/addProjectManager/usersWithoutProject/usersInProject_search")
    public String searchUsersInProjectForAppointmentPM(@RequestParam String searchedParam,
           @RequestParam String searchedString, @PathVariable Long projectId, Model model,
           @Qualifier("users") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS)Pageable allUsers,
           @Qualifier("usersInProject") @PageableDefault(PageConstant.AMOUNT_PROJECT_ELEMENTS)Pageable usersInProject) {
        usersRolesInProject(model);
        model.addAttribute("action", "addPM");
        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("userList", userService.findNotDeletedUsersByRole(UserRole.ROLE_USER, false, 1, allUsers));
        model.addAttribute("usersInProject", userService.userSearch(project, searchedParam, null,
                searchedString, usersInProject));
        return "users_without_project";
    }

    @PreAuthorize("@projectSecurityService.hasPermissionToProjectManagement(#projectId)")
    @GetMapping("/projects/project/{projectId}/removeUser/{userId}")
    public String removeUserFromProject(@PathVariable Long userId, @PathVariable @P("projectId") Long projectId,
                                        RedirectAttributes redirectAttributes) {
        userService.deleteUserFromProject(userId, redirectAttributes);
        return "redirect:/projects/project/" + projectId;
    }

    @PreAuthorize("@projectSecurityService.hasPermissionToProjectManagement(#projectId)")
    @GetMapping("/projects/project/{projectId}/usersWithoutProject/{userId}/changeRole")
    public String appointmentUserToProject(@PathVariable @P("projectId") Long projectId, @PathVariable Long userId,
                                           RedirectAttributes redirectAttributes) {
        userService.changeUserRole(userService.findOne(userId), projectService.findById(projectId), null,
                redirectAttributes);
        return "redirect:/projects/project/" + projectId;
    }

    @PostMapping("/projects/project/{projectId}/usersWithoutProject/{userId}/selectRole")
    public String selectUserRole(@ModelAttribute("role") UserRole role, @PathVariable Long projectId,
                                 @PathVariable Long userId, RedirectAttributes redirectAttributes) {
        userService.changeUserRole(userService.findOne(userId), projectService.findById(projectId), role,
                redirectAttributes);
        return "redirect:/projects/project/" + projectId;
    }

    private void usersRolesInProject(Model model) {
        model.addAttribute("DEV", UserRole.ROLE_DEVELOPER);
        model.addAttribute("QA", UserRole.ROLE_QA);
        model.addAttribute("PM", UserRole.ROLE_PROJECT_MANAGER);
    }
}