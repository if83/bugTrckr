package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {

    Project findById(Long id);

    Project findByUsers(User user);

    Project findByProjectReleases(ProjectRelease projectRelease);

    List<Project> findByGuestView(Boolean guestView);

    List<Project> findByGuestCreateIssues(Boolean guestCreateIssues);

    List<Project> findByGuestAddComment(Boolean guestAddComment);

    List<Project> findByTitle(String title);

    List<Project> findByIsDeleted(Boolean isDeleted);

    List<Project> findAll();

    Page<Project> findAll(Pageable pageable);

    Project save(Project project);

    void delete(Long id);

    Project update(Project project);

}
