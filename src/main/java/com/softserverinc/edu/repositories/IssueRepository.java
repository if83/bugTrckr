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


    Page<Issue> findByTitleContaining(String title, Pageable pageable);

    Page<Issue> findByProjectRelease(ProjectRelease projectRelease, Pageable pageable);

    Page<Issue> findByProjectReleaseAndTitleContaining(ProjectRelease projectRelease, String title, Pageable pageable);

    List<Issue> findByAssignee(User assignee, Pageable pageable);

    void delete(Long id);

    Page<Issue> findAll(Pageable pageable);

    Page<Issue> findByProject(Project project, Pageable pageable);
}
