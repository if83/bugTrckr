package com.softserverinc.edu.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotEmpty(message = "Please enter Project Title")
    @Size(max = 100, message = "Project title must be no longer than 100 characters")
    @Column(nullable = false, length = 100)
    private String title;

    @OneToMany(mappedBy = "project")
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private Set<ProjectRelease> projectReleases = new HashSet<>();

    @Column(nullable = false)
    private Boolean guestView;

    @Column(nullable = false)
    private Boolean guestCreateIssues;

    @Column(nullable = false)
    private Boolean guestAddComment;

    @NotEmpty(message = "Please enter project description")
    @Size(max = 10000, message = "Project title must be no longer than 10000 characters")
    @Column(length = 10000, nullable = false)
    private String description;

    public Project() {
        guestAddComment = false;
        guestCreateIssues = false;
        guestView = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<ProjectRelease> getProjectRelease() {
        return projectReleases;
    }

    public void setProjectRelease(Set<ProjectRelease> projectReleases) {
        this.projectReleases = projectReleases;
    }

    public Boolean getGuestView() {
        return guestView;
    }

    public void setGuestView(Boolean guestView) {
        this.guestView = guestView;
    }

    public Boolean getGuestCreateIssues() {
        return guestCreateIssues;
    }

    public void setGuestCreateIssues(Boolean guestCreateIssues) {
        this.guestCreateIssues = guestCreateIssues;
    }

    public Boolean getGuestAddComment() {
        return guestAddComment;
    }

    public void setGuestAddComment(Boolean guestAddComment) {
        this.guestAddComment = guestAddComment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}