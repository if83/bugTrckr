package com.softserverinc.edu.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.softserverinc.edu.controllers.json.view.UsersJson;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @JsonView(LocalUserList.class)
    public ResponseEntity<List<UsersJson>> adminIndex() {
        List<User> users = userService.findAll();
        List<UsersJson> localUserses = new ArrayList<>();
        for (User user : users) {
            localUserses.add(addOneUser(user, false));
        }
        LOGGER.debug("admin controller User rest list ");
        return new ResponseEntity<>(localUserses, HttpStatus.OK);
    }

    ;

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String adminDeleteUser(@PathVariable long id) {
        userService.delete(id);
        LOGGER.debug("admin controller delete user" + id);
        return "redirect:/admin/rest";
    }

    ;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/deleted/{id}")
    public String adminSetDeletedUser(@PathVariable long id) {
        User user = userService.findOne(id);
        user.setIsDeleted(!user.isDeleted());
        userService.update(user);
        LOGGER.debug("admin controller set deleted attr user " + id);
        return "redirect:/admin/rest";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/enabled/{id}")
    public String adminSetEnabledUser(@PathVariable long id) {
        User user = userService.findOne(id);
        user.setEnabled(user.getEnabled() == 1 ? 0 : 1);
        userService.update(user);
        LOGGER.debug("admin controller set enabled attr user " + id);
        return "redirect:/admin/rest";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @JsonView(LocalUserDetails.class)
    public ResponseEntity<UsersJson> adminUserDetails(@PathVariable long id) {
        User user = userService.findOne(id);
        LOGGER.debug("admin controller User rest details " + id);
        return new ResponseEntity<UsersJson>(addOneUser(user, true), HttpStatus.OK);
    }

    private UsersJson addOneUser(User user, boolean details) {
        UsersJson oneLocalUser = new UsersJson();
        oneLocalUser.setId(user.getId());
        oneLocalUser.setFirstName(user.getFirstName());
        oneLocalUser.setLastName(user.getLastName());
        oneLocalUser.setEmail(user.getEmail());
        oneLocalUser.setRole(user.getRole());
        oneLocalUser.setDeleted(user.isDeleted());
        oneLocalUser.setEnabled(user.getEnabled());
        if (user.getProject() != null)
            oneLocalUser.setProjectTitle(user.getProject().getTitle());
        oneLocalUser.setDescription(user.getDescription());
        if (details) {
            oneLocalUser.setDescription(user.getDescription());
        }
        return oneLocalUser;
    }

    public interface LocalUserList {
    }


    public interface LocalUserDetails extends LocalUserList {
    }

}
