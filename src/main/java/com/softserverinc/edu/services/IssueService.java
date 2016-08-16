package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Label;
import com.softserverinc.edu.entities.Release;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.IssuePriority;
import com.softserverinc.edu.entities.enums.IssueStatus;
import com.softserverinc.edu.entities.enums.IssueType;

import java.util.Date;
import java.util.List;

public interface IssueService {

    Issue findById(Long id);

    List<Issue> findByTitle(String title);

    List<Issue> findByType(IssueType type);

    List<Issue> findByStatus(IssueStatus status);

    List<Issue> findByPriority(IssuePriority priority);

    Issue findByRelease(Release release);

    Issue findByAssignee(User assignee);

    List<Issue> findByLabel(Label label);

    List<Issue> findByCreateTime(Date createTime);

    List<Issue> findByDueDate(Date dueDate);

    List<Issue> findByLastUpdateDate(Date lastUpdateDate);

    List<Issue> findByEstimateTime(Date estimateTime);

    Issue findByParent(Issue parent);

    List<Issue> findByIsDeleted(Boolean isDeleted);

    List<Issue> findAll();

    Issue save(Issue issue);

    void delete(Long id);

    Issue update(Issue issue);

}
