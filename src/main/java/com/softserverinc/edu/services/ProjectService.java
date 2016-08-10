package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Project;

import java.util.List;

public interface ProjectService {

    Project getOne(Long id);
    Project save(Project project);
    void delete(Long id);
    Project update(Project project);
    List<Project> getAll();

}
