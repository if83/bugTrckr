package com.softserverinc.edu.entities;

import com.softserverinc.edu.entities.enums.IssuePriority;
import com.softserverinc.edu.entities.enums.IssueStatus;
import com.softserverinc.edu.entities.enums.IssueType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false, length = 32)
    private String title;

    @Column(nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private IssueType type;

    @Column(nullable = false, length = 32, columnDefinition = "varchar(32) default OPEN")
    @Enumerated(EnumType.STRING)
    private IssueStatus status = IssueStatus.OPEN;

    @Column(nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private IssuePriority priority;

    @ManyToOne
    @JoinColumn(name = "projectReleaseId", referencedColumnName = "id", nullable = false)
    private ProjectRelease projectRelease;

    @OneToOne
    @JoinColumn(name = "assigneeId", referencedColumnName = "id", nullable = false)
    private User assignee;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "issues")
    private Set<Label> labels;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createTime = new Date();

    @Column
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dueDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date lastUpdateDate;

    @Column
    private Long estimateTime;

    @Column
    private Long parentId;

    @Column(nullable = false, length = 10000)
    private String description;

    @Column
    private Boolean isDeleted;

    @Column
    private Boolean editAbility;

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

    public ProjectRelease getProjectRelease() {
        return projectRelease;
    }

    public void setProjectRelease(ProjectRelease projectRelease) {
        this.projectRelease = projectRelease;
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Long getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(Long estimateTime) {
        this.estimateTime = estimateTime;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParent(Long parentId) {
        this.parentId = parentId;
    }

    public boolean isNewIssue() {
        return (this.id == null || this.id == 0L);
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getEditAbility() {
        return editAbility;
    }

    public void setEditAbility(Boolean editAbility) {
        this.editAbility = editAbility;
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