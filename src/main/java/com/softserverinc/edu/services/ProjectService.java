package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project findById(Long id) {
        return projectRepository.findOne(id);
    }

    public List<Project> findByTitle(String title) {
        return projectRepository.findByTitle(title);
    }

    public Project findByUsers(User user) {
        return projectRepository.findByUsers(user);
    }

    public Project findByProjectReleases(ProjectRelease projectRelease) {
        return projectRepository.findByProjectReleases(projectRelease);
    }

    public List<Project> findByGuestView(Boolean guestView) {
        return projectRepository.findByGuestView(guestView);
    }

    public List<Project> findByGuestCreateIssues(Boolean guestCreateIssues) {
        return projectRepository.findByGuestCreateIssues(guestCreateIssues);
    }

    public List<Project> findByGuestAddComment(Boolean guestAddComment) {
        return projectRepository.findByGuestAddComment(guestAddComment);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Transactional
    public Project save(Project project) {
        return projectRepository.saveAndFlush(project);
    }

    @Transactional
    public void delete(Long id) {
        projectRepository.delete(id);
    }

    @Transactional
    public Project update(Project project) {
        return projectRepository.saveAndFlush(project);
    }
}
