package com.softserverinc.edu.services.impl;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.repositories.ProjectRepository;
import com.softserverinc.edu.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public Project getOne(Long id) {
        return projectRepository.getOne(id);
    }

    @Override
    public Project save(Project project) {
        return projectRepository.saveAndFlush(project);
    }

    @Override
    public void delete(Long id) {
        projectRepository.delete(id);
    }

    @Override
    public Project update(Project project) {
        return projectRepository.saveAndFlush(project);
    }

    @Override
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

}
