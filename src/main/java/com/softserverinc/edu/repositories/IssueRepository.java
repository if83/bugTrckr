package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Contain custom Spring Data JPA methods wor working with DB
 */
@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    Page<Issue> findByTitleContaining(String title, Pageable pageable);

    Page<Issue> findByProjectRelease(ProjectRelease projectRelease, Pageable pageable);

    Page<Issue> findByProjectReleaseAndTitleContaining(ProjectRelease projectRelease, String title, Pageable pageable);

    Page<Issue> findByAssignee(User assignee, Pageable pageable);

    Page<Issue> findAll(Pageable pageable);

    Page<Issue> findByProject(Project project, Pageable pageable);

    Page<Issue> findByProjectAndTitleContaining(Project project, String searchedString, Pageable pageable);
}
