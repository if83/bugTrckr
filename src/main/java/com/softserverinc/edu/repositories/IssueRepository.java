package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.*;
import com.softserverinc.edu.entities.enums.IssuePriority;
import com.softserverinc.edu.entities.enums.IssueStatus;
import com.softserverinc.edu.entities.enums.IssueType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findByTitle(String title);

    Page<Issue> findByTitleContaining(String title, Pageable pageable);

    List<Issue> findByType(IssueType type);

    List<Issue> findByStatus(IssueStatus status);

    List<Issue> findByPriority(IssuePriority priority);

    List<Issue> findByProjectRelease(ProjectRelease projectRelease);

    Page<Issue> findByProjectRelease(ProjectRelease projectRelease, Pageable pageable);

    Page<Issue> findByProjectReleaseAndTitleContaining(ProjectRelease projectRelease, String title, Pageable pageable);

    List<Issue> findByAssignee(User assignee);

    List<Issue> findByAssignee(User assignee, Pageable pageable);

    List<Issue> findByLabels(Label label);

    List<Issue> findByCreateTime(Date createTime);

    List<Issue> findByDueDate(Date dueDate);

    List<Issue> findByLastUpdateDate(Date lastUpdateDate);

    List<Issue> findByEstimateTime(Date estimateTime);

    Issue findByParentId(Long parentId);

    void delete(Long id);

    Page<Issue> findAll(Pageable pageable);

    Page<Issue> findByProjectId(Long projectId, Pageable pageable);
}
