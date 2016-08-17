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
    private ProjectReleaseRepository releaseRepository;

    @Override
    public ProjectRelease findById(Long id) {
        return releaseRepository.findOne(id);
    }

    @Override
    public List<ProjectRelease> findByProject(Project project) {
        return releaseRepository.findByProject(project);
    }

    @Override
    public ProjectRelease findByIssue(Issue issue) {
        ProjectRelease projectRelease = null;
        List<ProjectRelease> listOfProjectReleases = releaseRepository.findAll();
        for (ProjectRelease projectReleaseIterator : listOfProjectReleases) {
            if (projectReleaseIterator.getIssues().contains(issue)) {
                projectRelease = projectReleaseIterator;
                break;
            }
        }
        return projectRelease;
    }

    @Override
    public List<ProjectRelease> findByVersion(String version) {
        return releaseRepository.findByVersion(version);
    }

    @Override
    public List<ProjectRelease> findByReleaseStatus(ReleaseStatus releaseStatus) {
        return releaseRepository.findByReleaseStatus(releaseStatus);
    }

    public List<ProjectRelease> findByIsDeleted(Boolean isDeleted) {
        return releaseRepository.findByIsDeleted(isDeleted);
    }

    @Override
    public List<ProjectRelease> findAll() {
        return releaseRepository.findAll();
    }

    @Transactional
    @Override
    public ProjectRelease save(ProjectRelease projectRelease) {
        return releaseRepository.saveAndFlush(projectRelease);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        releaseRepository.findOne(id).setIsDeleted(true);
    }

    @Transactional
    @Override
    public ProjectRelease update(ProjectRelease projectRelease) {
        return releaseRepository.saveAndFlush(projectRelease);
    }

}
