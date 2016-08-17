package com.softserverinc.edu.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Project {

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
//    @JoinColumn(referencedColumnName = "id")
    @OneToMany
    @JoinColumn(name="projectId")
    private Set<User> users;

//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
    @OneToMany
    @JoinColumn(name="projectId")
    private Set<ProjectRelease> projectRelease;

    @Column(nullable = false)
    private boolean guestView;

    @Column(nullable = false)
    private boolean guestCreateIssues;

    @Column(nullable = false)
    private boolean guestAddComment;

    @Column(length = 10000, nullable = false)
    private String description;

    @Column
    private boolean isDeleted;

    public Project() {
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
        return projectRelease;
    }

    public void setProjectRelease(Set<ProjectRelease> projectRelease) {
        this.projectRelease = projectRelease;
    }

    public boolean isGuestView() {
        return guestView;
    }

    public void setGuestView(boolean guestView) {
        this.guestView = guestView;
    }

    public boolean isGuestCreateIssues() {
        return guestCreateIssues;
    }

    public void setGuestCreateIssues(boolean guestCreateIssues) {
        this.guestCreateIssues = guestCreateIssues;
    }

    public boolean isGuestAddComment() {
        return guestAddComment;
    }

    public void setGuestAddComment(boolean guestAddComment) {
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
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