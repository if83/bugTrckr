package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.repositories.ProjectReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectReleaseService {

    @Autowired
    private ProjectReleaseRepository projectReleaseRepository;

    public ProjectRelease findById(Long id) {
        return projectReleaseRepository.findOne(id);
    }

    public List<ProjectRelease> findByProject(Project project) {
        return projectReleaseRepository.findByProject(project);
    }

    public ProjectRelease setProjectAndSaveRelease(Project project, ProjectRelease release) {
        release.setProject(project);
        return save(release);
    }

    public Page<ProjectRelease> findByProject(Project project, Pageable pageable) {
        return projectReleaseRepository.findByProject(project, pageable);
    }

    public Page<ProjectRelease> searchByTitle(Project project, String searchedString, Pageable pageable) {
        return projectReleaseRepository.findByProjectAndVersionContaining(project, searchedString, pageable);
    }

    public ProjectRelease findByIssues(Issue issue) {
        return projectReleaseRepository.findByIssues(issue);
    }

    public List<ProjectRelease> findAll() {
        return projectReleaseRepository.findAll();
    }

    public Page<ProjectRelease> findAll(Pageable  pageable) {
        return projectReleaseRepository.findAll(pageable);
    }

    @Transactional
    public ProjectRelease save(ProjectRelease projectRelease) {
        return projectReleaseRepository.saveAndFlush(projectRelease);
    }

    @Transactional
    public void delete(Long id) {
        projectReleaseRepository.delete(id);
    }

    @Transactional
    public ProjectRelease update(ProjectRelease projectRelease) {
        return projectReleaseRepository.saveAndFlush(projectRelease);
    }

}
