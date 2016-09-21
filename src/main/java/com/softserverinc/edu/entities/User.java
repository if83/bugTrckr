package com.softserverinc.edu.entities;

import com.softserverinc.edu.entities.enums.UserRole;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
public class User {

    /**
     * For saveAndUpdate method
     */
    @Transient
    private boolean newuser;
    /**
     * form:input - confirmPassword
     */
    @Transient
    private String confirmPassword;

    /**
     * for encoding images from byte[] arrays. Have only a Get method
     */
    @Transient
    private String encodedImage;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false, length = 25)
    private String firstName;

    @Column(nullable = false, length = 32)
    private String lastName;

    @Column(unique = true, nullable = false, length = 64)
    private String email;

    @Column(length = 60)
    private String password;

    @Column(nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Lob
    @Column(columnDefinition = "mediumblob")
    private byte[] imageData;

    @Column(length = 64)
    private String imageFilename;

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
        return firstName + " " +lastName;
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

    public boolean isNewuser() {
        return (this.id == null || this.id == 0L);
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getEncodedImage() {
        if (getImageData() != null)
            encodedImage = new String(new Base64().encode(getImageData()));
        else
            encodedImage = "";
        return encodedImage;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
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
