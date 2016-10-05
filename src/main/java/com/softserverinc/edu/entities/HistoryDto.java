package com.softserverinc.edu.entities;

import com.softserverinc.edu.entities.enums.HistoryAction;
import com.softserverinc.edu.entities.enums.IssuePriority;
import com.softserverinc.edu.entities.enums.IssueStatus;
import com.softserverinc.edu.entities.enums.IssueType;

import java.util.Date;

/**
 * This class was created because simple {@see History.java} class can
 * keep only id of users on {@see History#assignedToUser} and
 * {@see History#changedByUser} fields (but not "User" objects).
 * To avoid this limitation "History" object can be converted to "HistoryDto" object
 */
public class HistoryDto {

    private Issue issue;

    private User assignedToUser;

    private User changedByUser;

    private String createTime;

    private IssueStatus status;

    private HistoryAction action;

    private String title;

    private IssueType type;

    private IssuePriority priority;

    private String description;

    private String issueComment;

    private String anonymName;

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public HistoryAction getAction() {
        return action;
    }

    public void setAction(HistoryAction action) {
        this.action = action;
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

    public IssuePriority getPriority() {
        return priority;
    }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIssueComment() {
        return issueComment;
    }

    public void setIssueComment(String issueComment) {
        this.issueComment = issueComment;
    }

    public String getAnonymName() {
        return anonymName;
    }

    public void setAnonymName(String anonymName) {
        this.anonymName = anonymName;
    }
}
