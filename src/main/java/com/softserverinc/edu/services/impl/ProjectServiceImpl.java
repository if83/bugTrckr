package com.softserverinc.edu.services.impl;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.Release;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.repositories.ProjectRepository;
import com.softserverinc.edu.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public Project findById(Long id) {
        return projectRepository.findOne(id);
    }

    @Override
    public Project findByTitle(String title) {
        return projectRepository.findByTitle(title);
    }

    /*@Override
    public Project findByProjectManager(User projectManager) {
        return projectRepository.findByProjectManager(projectManager);
    }*/

    @Override
    public Project findByUser(User user) {
        Project project = null;
        List<Project> listOfProjects = projectRepository.findAll();
        for (Project releaseIterator : listOfProjects) {
            if (releaseIterator.getUsers().contains(user)) {
                project = releaseIterator;
                break;
            }
        }
        return project;
    }

    @Override
    public Project findByRelease(Release release) {
        Project project = null;
        List<Project> listOfProjects = projectRepository.findAll();
        for (Project releaseIterator : listOfProjects) {
            if (releaseIterator.getReleases().contains(release)) {
                project = releaseIterator;
                break;
            }
        }
        return project;
    }

    @Override
    public List<Project> findByGuestView(Boolean guestView) {
        return projectRepository.findByGuestView(guestView);
    }

    @Override
    public List<Project> findByGuestCreateIssues(Boolean guestCreateIssues) {
        return projectRepository.findByGuestCreateIssues(guestCreateIssues);
    }

    @Override
    public List<Project> findByGuestAddComment(Boolean guestAddComment) {
        return projectRepository.findByGuestAddComment(guestAddComment);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    @Transactional
    public Project save(Project project) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {

    }

    @Override
    @Transactional
    public Project update(Project project) {
        return null;
    }
}
