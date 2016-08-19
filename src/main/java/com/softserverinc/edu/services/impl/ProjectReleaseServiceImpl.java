package com.softserverinc.edu.services.impl;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.enums.ReleaseStatus;
import com.softserverinc.edu.repositories.ProjectReleaseRepository;
import com.softserverinc.edu.services.ProjectReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectReleaseServiceImpl implements ProjectReleaseService {

    @Autowired
    private ProjectReleaseRepository projectReleaseRepository;

    @Override
    public ProjectRelease findById(Long id) {
        return projectReleaseRepository.findOne(id);
    }

    @Override
    public List<ProjectRelease> findByProject(Project project) {
        return projectReleaseRepository.findByProject(project);
    }

   /* @Override
    public ProjectRelease findByIssues(Issue issue) {
        return projectReleaseRepository.findByIssues(issue);
        *//*ProjectRelease projectRelease = null;
        List<ProjectRelease> listOfProjectReleases = releaseRepository.findAll();
        for (ProjectRelease projectReleaseIterator : listOfProjectReleases) {
            if (projectReleaseIterator.getIssues().contains(issue)) {
                projectRelease = projectReleaseIterator;
                break;
            }
        }
        return projectRelease;*//*
    }*/

    @Override
    public List<ProjectRelease> findByVersion(String version) {
        return projectReleaseRepository.findByVersion(version);
    }

    @Override
    public List<ProjectRelease> findByReleaseStatus(ReleaseStatus releaseStatus) {
        return projectReleaseRepository.findByReleaseStatus(releaseStatus);
    }

    public List<ProjectRelease> findByIsDeleted(Boolean isDeleted) {
        return projectReleaseRepository.findByIsDeleted(isDeleted);
    }

    @Override
    public List<ProjectRelease> findAll() {
        return projectReleaseRepository.findAll();
    }

    @Transactional
    @Override
    public ProjectRelease save(ProjectRelease projectRelease) {
        return projectReleaseRepository.saveAndFlush(projectRelease);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        projectReleaseRepository.findOne(id).setIsDeleted(true);
    }

    @Transactional
    @Override
    public ProjectRelease update(ProjectRelease projectRelease) {
        return projectReleaseRepository.saveAndFlush(projectRelease);
    }

}
