package com.softserverinc.edu.controllers.json.view;

import com.fasterxml.jackson.annotation.JsonView;
import com.softserverinc.edu.controllers.AdminController;
import com.softserverinc.edu.entities.enums.UserRole;

/**
 * For view in admin controller. It forms JSON data.
 */
public class UsersJson {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
    private String description;
    private String projectTitle;
    private int deleted;
    private int enabled;
    private byte[] imageData;
    private String imageFilename;

    @JsonView(AdminController.LocalUserList.class)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonView(AdminController.LocalUserList.class)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonView(AdminController.LocalUserList.class)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonView(AdminController.LocalUserList.class)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonView(AdminController.LocalUserList.class)
    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @JsonView(AdminController.LocalUserDetails.class)
    public String getDescription() {
        if (description == null)
            description = "";
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonView(AdminController.LocalUserList.class)
    public String getProjectTitle() {
        if (projectTitle == null)
            projectTitle = "";
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    @JsonView(AdminController.LocalUserList.class)
    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted ? 1 : 0;
    }

    @JsonView(AdminController.LocalUserList.class)
    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @JsonView(AdminController.LocalUserDetails.class)
    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    @JsonView(AdminController.LocalUserDetails.class)
    public String getImageFilename() {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }

    @Override
    public String toString() {
        return "UsersJson{" +
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
