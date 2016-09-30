package com.softserverinc.edu.entities;

import com.softserverinc.edu.entities.enums.HistoryAction;
import com.softserverinc.edu.entities.enums.IssuePriority;
import com.softserverinc.edu.entities.enums.IssueStatus;
import com.softserverinc.edu.entities.enums.IssueType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "issueId", referencedColumnName = "id", nullable = false)
    private Issue issue;

    private Long changedByUserId;

    private String createTime;

    private HistoryAction action;

    private String title;

    private IssueType type;

    private IssuePriority priority;

    private IssueStatus status;

    private Long assignedToUserId;

    private String description;

    private String issueComment;

    private String anonymName;

    private History() {
    }

    public Long getId() {
        return id;
    }

    public Issue getIssue() {
        return issue;
    }

    public Long getChangedByUserId() {
        return changedByUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public HistoryAction getAction() {
        return action;
    }

    public String getTitle() {
        return title;
    }

    public IssueType getType() {
        return type;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public Long getAssignedToUserId() {
        return assignedToUserId;
    }

    public String getDescription() {
        return description;
    }

    public String getIssueComment() {
        return issueComment;
    }

    public String getAnonymName() {
        return anonymName;
    }

    public static Builder newBuilder() {
        return new History().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setId(Long id) {
            History.this.id = id;
            return this;
        }

        public Builder setIssue(Issue issue) {
            History.this.issue = issue;
            return this;
        }

        public Builder setChangedByUserId(Long changedByUserId) {
            History.this.changedByUserId = changedByUserId;
            return this;
        }

        public Builder setCreateTime(String createTime) {
            History.this.createTime = createTime;
            return this;
        }

        public Builder setAction(HistoryAction action) {
            History.this.action = action;
            return this;
        }

        public Builder setTitle(String title) {
            History.this.title = title;
            return this;
        }

        public Builder setType(IssueType type) {
            History.this.type = type;
            return this;
        }

        public Builder setPriority(IssuePriority priority) {
            History.this.priority = priority;
            return this;
        }

        public Builder setStatus(IssueStatus status) {
            History.this.status = status;
            return this;
        }

        public Builder setAssignedToUserId(Long assignedToUserId) {
            History.this.assignedToUserId = assignedToUserId;
            return this;
        }

        public Builder setDescription(String description) {
            History.this.description = description;
            return this;
        }

        public Builder setIssueComment(String issueComment) {
            History.this.issueComment = issueComment;
            return this;
        }

        public Builder setAnonymName(String anonymName) {
            History.this.anonymName = anonymName;
            return this;
        }

        public History build() {
            return History.this;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        History history = (History) o;

        return new EqualsBuilder()
                .append(id, history.id)
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
                .toString();
    }

}
