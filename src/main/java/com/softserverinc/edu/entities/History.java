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

    @Column
    private Long parentId;

    @Column(nullable = false)
    private Long assigneeId;

    @Column
    private Long changeById;

    // Fixme: use status field from Issue
    @OneToOne
    @JoinColumn(referencedColumnName = "status", nullable = false)
    private Issue issueStatus;

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    public Long getChangeById() {
        return changeById;
    }

    public void setChangeById(Long changeById) {
        this.changeById = changeById;
    }

    public Issue getStatus() {
        return issueStatus;
    }

    public void setStatus(Issue issueStatus) {
        this.issueStatus = issueStatus;
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
