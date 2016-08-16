package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.Release;
import com.softserverinc.edu.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByTitle(String title);

    Project findByUser(User user);

    Project findByRelease(Release release);

    List<Project> findByGuestView(Boolean guestView);

    List<Project> findByGuestCreateIssues(Boolean guestCreateIssues);

    List<Project> findByGuestAddComment(Boolean guestAddComment);

    List<Project> findByIsDeleted(Boolean isDeleted);

}
