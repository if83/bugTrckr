package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectReleaseRepository extends JpaRepository<ProjectRelease, Long>, PagingAndSortingRepository<ProjectRelease, Long> {

    List<ProjectRelease> findByProject(Project project);

    Page<ProjectRelease> findByProject(Project project, Pageable pageable);

    Page<ProjectRelease> findByProjectAndVersionContaining(Project project, String version, Pageable pageable);

    ProjectRelease findByIssues(Issue issue);

}
