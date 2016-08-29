package com.softserverinc.edu.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class WorkLog {

    /*@NotNull*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    /*@NotNull*/
    @ManyToOne
    @JoinColumn(name = "issueId", referencedColumnName = "id", nullable = false)
    private Issue issue;

    /*@NotNull*/
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    private User user;

    /*@NotNull*/
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    //TODO: rename this field in workLog table (workday or smth else)
    //TODO: refactor repo and services code accordingly to new field name

    /*@NotNull
    @Min(0)
    @Max(8)*/ // here may be project or specified user workday duration
    @Column(nullable = false)
    private Long amountOfTime;

    //TODO: delete all usages of isDeleted field in worklog table from sql scripts

    public WorkLog() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Long getAmountOfTime() {
        return amountOfTime;
    }

    public void setAmountOfTime(Long amountOfTime) {
        this.amountOfTime = amountOfTime;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
