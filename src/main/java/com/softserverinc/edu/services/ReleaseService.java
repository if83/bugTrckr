package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.Release;
import com.softserverinc.edu.entities.enums.ReleaseStatus;

import java.util.List;

public interface ReleaseService {

    Release findById(Long id);

    List<Release> findByProject(Project project);

    Release findByIssue(Issue issue);

    List<Release> findByVersion(String version);

    List<Release> findByReleaseStatus(ReleaseStatus releaseStatus);

    List<Release> findByIsDeleted(Boolean isDeleted);

    List<Release> findAll();

    Release save(Release user);

    void delete(Long id);

    Release update(Release user);

}
