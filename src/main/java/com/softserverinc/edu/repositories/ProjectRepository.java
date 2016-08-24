package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByTitle(String title);

    Project findByUsers(User user);

    Project findByProjectReleases(ProjectRelease projectRelease);

    List<Project> findByGuestView(Boolean guestView);

    List<Project> findByGuestCreateIssues(Boolean guestCreateIssues);

    List<Project> findByGuestAddComment(Boolean guestAddComment);

    Page<Project> findAll(Pageable pageable);
}
