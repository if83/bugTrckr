package com.softserverinc.edu.entities;

import com.softserverinc.edu.constants.PageConstant;
import com.softserverinc.edu.entities.enums.IssuePriority;
import com.softserverinc.edu.entities.enums.IssueStatus;
import com.softserverinc.edu.entities.enums.IssueType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Indexed
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotEmpty(message = "Please enter the issue title")
    @Size(max = 32, message = "Issue title must be no longer than 32 characters")
    @Column(nullable = false, length = 32)
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String title;

    @NotNull(message = "Please select issue type")
    @Column(nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private IssueType type;

    @Column(nullable = false, length = 32, columnDefinition = "varchar(32) default OPEN")
    @Enumerated(EnumType.STRING)
    private IssueStatus status = IssueStatus.OPEN;

    @NotNull(message = "Please select issue priority")
    @Column(nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private IssuePriority priority;

    @NotNull(message = "Please select the release")
    @ManyToOne
    @JoinColumn(name = "projectReleaseId", referencedColumnName = "id", nullable = false)
    private ProjectRelease projectRelease;

    @NotNull(message = "Please select the project")
    @ManyToOne
    @JoinColumn(name = "projectId", referencedColumnName = "id", nullable = false)
    private Project project;

    @NotNull(message = "Please select an assignee")
    @OneToOne
    @JoinColumn(name = "assigneeId", referencedColumnName = "id", nullable = false)
    private User assignee;

    @OneToOne
    @JoinColumn(name = "createdById", referencedColumnName = "id")
    private User createdBy;

    @ManyToMany()
    @JoinTable(name = "Label_Issue",
            joinColumns = @JoinColumn(name = "issueId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "labelId", referencedColumnName = "id"))
    private Set<Label> labels;

    @NotNull
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = PageConstant.ISSUE_DATE_FORMAT)
    private Date createTime = new Date();

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = PageConstant.ISSUE_DATE_FORMAT)
    private Date dueDate;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = PageConstant.ISSUE_DATE_FORMAT)
    private Date lastUpdateDate;

    @NotNull(message = "Please enter estimated time")
    @Column(nullable = false)
    private Long estimateTime;

    @Column
    private Long parentId;

    @NotEmpty(message = "Please enter description")
    @Size(max = 10000, message = "Issue description must be no longer than 10000 characters")
    @Column(nullable = false, length = 10000)
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String description;

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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Issue issue = (Issue) o;

        return new EqualsBuilder()
                .append(id, issue.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("title", title)
                .append("type", type)
                .append("status", status)
                .toString();
    }

}