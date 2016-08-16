package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Label;
import com.softserverinc.edu.entities.Release;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.IssuePriority;
import com.softserverinc.edu.entities.enums.IssueStatus;
import com.softserverinc.edu.entities.enums.IssueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

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

}
