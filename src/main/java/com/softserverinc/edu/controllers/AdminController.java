package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * For administrator controller
 */

@RestController
@RequestMapping("/admin/rest")
public class AdminController {

    public static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    UserService userService;

    @GetMapping("/{role}")
    public ResponseEntity<List<LocalUsers>> adminIndex(@PathVariable String role) {
        List<User> users = userService.findAll();
        List<LocalUsers> localUserses = new ArrayList<>();
        for (User user: users) {
            switch (role){
                case "admin":
                    if((user.getRole() == UserRole.ROLE_ADMIN.ROLE_ADMIN) || (user.getRole() == UserRole.ROLE_PROJECT_MANAGER))
                    localUserses.add(addOneUser(user));
                    break;
                case "all":
                    localUserses.add(addOneUser(user));
                    break;
                case "other":
                    if(!((user.getRole() == UserRole.ROLE_ADMIN.ROLE_ADMIN) || (user.getRole() == UserRole.ROLE_PROJECT_MANAGER)))
                        localUserses.add(addOneUser(user));
                    break;
            }
        }
        return new ResponseEntity<List<LocalUsers>>(localUserses, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String adminDeleteUser(@PathVariable long id) {
        userService.delete(id);
        LOGGER.debug("delete user " + id);
        return "redirect:/admin/rest";
    }


    private LocalUsers addOneUser(User user) {
        LocalUsers oneLocalUser = new LocalUsers();
        oneLocalUser.setId(user.getId());
        oneLocalUser.setFirstName(user.getFirstName());
        oneLocalUser.setLastName(user.getLastName());
        oneLocalUser.setEmail(user.getEmail());
        oneLocalUser.setRole(user.getRole());
        if(user.getProject() != null)
            oneLocalUser.setProjectTitle(user.getProject().getTitle());
        oneLocalUser.setDescription(user.getDescription());
        return oneLocalUser;
    }

    private class LocalUsers {
        private long id;
        private String firstName;
        private String lastName;
        private String email;
        private UserRole role;
        private String description;
        private String projectTitle;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public UserRole getRole() {
            return role;
        }

        public void setRole(UserRole role) {
            this.role = role;
        }

        public String getDescription() {
            if (description == null)
                description = "";
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getProjectTitle() {
            if (projectTitle == null)
                projectTitle = "";
            return projectTitle;
        }

        public void setProjectTitle(String projectTitle) {
            this.projectTitle = projectTitle;
        }

        @Override
        public String toString() {
            return "LocalUsers{" +
                    "id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", email='" + email + '\'' +
                    ", role=" + role +
                    ", description='" + description + '\'' +
                    ", projectTitle='" + projectTitle + '\'' +
                    '}';
        }
    }

}
