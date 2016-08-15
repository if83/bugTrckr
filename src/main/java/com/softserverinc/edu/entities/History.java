package com.softserverinc.edu.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
public class History {

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "issueId", referencedColumnName = "id", nullable = false)
    private Issue issue;

    @OneToOne
    @JoinColumn(name = "parentId", referencedColumnName = "id")
    private History parent;

    @OneToOne
    @JoinColumn(name = "assignedToUserId", referencedColumnName = "id", nullable = false)
    private User assignedToUser;

    @OneToOne
    @JoinColumn(name = "changedByUserId", referencedColumnName = "id", nullable = false)
    private User changedByUser;

    @Column
    private boolean isDeleted;

    public History() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public History getParent() {
        return parent;
    }

    public void setParent(History parent) {
        this.parent = parent;
    }

    public User getAssignedToUser() {
        return assignedToUser;
    }

    public void setAssignedToUser(User assignedToUser) {
        this.assignedToUser = assignedToUser;
    }

    public User getChangedByUser() {
        return changedByUser;
    }

    public void setChangedByUser(User changedByUser) {
        this.changedByUser = changedByUser;
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
