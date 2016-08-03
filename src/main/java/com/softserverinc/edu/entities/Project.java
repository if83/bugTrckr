package com.softserverinc.edu.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by volodymyr on 8/3/16.
 */
@Entity
@Table
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "projectManagerId", referencedColumnName = "id", nullable = false)
    private User projectManager;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Release> releases;

    @Column(nullable = false)
    private boolean guestView;

    @Column(nullable = false)
    private boolean guestCreateIssues;

    @Column(nullable = false)
    private boolean guestAddComment;

    @Column(nullable = true, length = 10000)
    private String description;

    public Project() {
    }

    public Project(String title, User projectManager, boolean guestView, boolean guestCreateIssues, boolean guestAddComment, String description) {
        this.title = title;
        this.projectManager = projectManager;
        this.guestView = guestView;
        this.guestCreateIssues = guestCreateIssues;
        this.guestAddComment = guestAddComment;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getProjectManager() {
        return projectManager;
    }

    public boolean isGuestView() {
        return guestView;
    }

    public boolean isGuestCreateIssues() {
        return guestCreateIssues;
    }

    public boolean isGuestAddComment() {
        return guestAddComment;
    }

    public String getDescription() {
        return description;
    }

    public Set<Release> getReleases() {
        return releases;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProjectManager(User projectManager) {
        this.projectManager = projectManager;
    }

    public void setReleases(Set<Release> releases) {
        this.releases = releases;
    }

    public void setGuestView(boolean guestView) {
        this.guestView = guestView;
    }

    public void setGuestCreateIssues(boolean guestCreateIssues) {
        this.guestCreateIssues = guestCreateIssues;
    }

    public void setGuestAddComment(boolean guestAddComment) {
        this.guestAddComment = guestAddComment;
    }

    public void setDescription(String description) {
        this.description = description;
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