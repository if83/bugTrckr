package com.softserverinc.edu.entities;

import com.softserverinc.edu.entities.enums.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Issue {

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false, length = 25)
    private String title;

    @Column(nullable = false, length = 25)
    @Enumerated(EnumType.STRING)
    private IssueType type;

    @Column(nullable = false, length = 25)
    @Enumerated(EnumType.STRING)
    private IssueStatus status;

    @Column(nullable = false, length = 25)
    @Enumerated(EnumType.STRING)
    private IssuePriority priority;

    @ManyToOne
    @JoinColumn(name = "releaseId", referencedColumnName = "id", nullable = false)
    private Release release;

    @OneToOne
    @JoinColumn(name = "assigneeId", referencedColumnName = "id", nullable = false)
    private User assignee;

    @ManyToMany
    @JoinColumn(referencedColumnName = "id")
    private Set<Label> labels;

    @Column(nullable = false)
    private Date createTime;

    @Column(nullable = false)
    private Date dueTime;

    @Column(nullable = false)
    private Date lastUpdateTime;

    @Column(nullable = false)
    private Long estimateTime;

    @OneToOne
    @JoinColumn(name = "parentId", referencedColumnName = "id")
    private Issue parent;

    public Issue() {
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

    public IssueType getType() {
        return type;
    }

    public void setType(IssueType type) {
        this.type = type;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    public Release getRelease() {
        return release;
    }

    public void setRelease(Release release) {
        this.release = release;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public Set<Label> getLabels() {
        return labels;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(Long estimateTime) {
        this.estimateTime = estimateTime;
    }

    public Issue getParent() {
        return parent;
    }

    public void setParent(Issue parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}



