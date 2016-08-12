package com.softserverinc.edu.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Proxy;

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

    // FIXME: We should have Set of users or only one projectManager??
    @OneToOne
    @JoinColumn(name = "projectManagerId", referencedColumnName = "id", nullable = false)
    private User projectManager;

    @OneToMany
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Set<User> users;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Release> releases;

    @Column(nullable = false)
    private boolean guestView;

    @Column(nullable = false)
    private boolean guestCreateIssues;

    @Column(nullable = false)
    private boolean guestAddComment;

    @Column(length = 10000)
    private String description;

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

    public User getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(User projectManager) {
        this.projectManager = projectManager;
    }

    public Set<Release> getReleases() {
        return releases;
    }

    public void setReleases(Set<Release> releases) {
        this.releases = releases;
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