package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.enums.ReleaseStatus;

import java.util.List;

public interface ReleaseService {

    ProjectRelease findById(Long id);

    List<ProjectRelease> findByProject(Project project);

    ProjectRelease findByIssue(Issue issue);

    List<ProjectRelease> findByVersion(String version);

    List<ProjectRelease> findByReleaseStatus(ReleaseStatus releaseStatus);

    List<ProjectRelease> findByIsDeleted(Boolean isDeleted);

    List<ProjectRelease> findAll();

    ProjectRelease save(ProjectRelease user);

    void delete(Long id);

    ProjectRelease update(ProjectRelease user);

}
