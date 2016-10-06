package com.softserverinc.edu.entities;

import com.softserverinc.edu.entities.enums.UserRole;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User {

    /**
     * responsible for password confirmation in user form
     */
    @Transient
    private String confirmPassword;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotEmpty(message = "Enter first name")
    @Size(max = 20, message = "First name must be no longer than 20 characters")
    @Column(nullable = false, length = 20)
    private String firstName;

    @NotEmpty(message = "Enter last name")
    @Size(max = 20, message = "Last name must be no longer than 20 characters")
    @Column(nullable = false, length = 20)
    private String lastName;

    @NotEmpty(message = "Enter user's email")
    @Size(max = 32, message = "Email must be no longer than 32 characters")
    @Email
    @Column(unique = true, nullable = false, length = 64)
    private String email;

    @NotEmpty(message = "Enter user's password")
    @Size(max = 60, message = "Password must be no longer than 60 characters")
    @Column(length = 60)
    private String password;

    @NotNull(message = "Select user's role")
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Size(max = 10000, message = "Description must be no longer than 10000 characters")
    @Column(length = 10000)
    private String description;

    /**
     * for spring security, 1 is enables, 0 is disabled
     */
    @Column(columnDefinition = "TINYINT")
    private int enabled;

    @ManyToOne
    @JoinColumn(name = "projectId", referencedColumnName = "id")
    private Project project;

    /**
     * When true a user is deleted, default false
     */
    @Column
    private boolean isDeleted;

    public User() {
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(id, user.id)
                .append(email, user.email)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(email)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("email", email)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .toString();
    }

}