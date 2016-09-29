package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.repositories.ProjectRepository;
import com.softserverinc.edu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserRepository userRepository;


    public Project findById(Long id) {
        return projectRepository.findOne(id);
    }

    public Page<Project> findByTitleContaining(String title, Pageable pageable) {
        return projectRepository.findByTitleContaining(title, pageable);
    }

    public Project findByProjectReleases(ProjectRelease projectRelease) {
        return projectRepository.findByProjectReleases(projectRelease);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Page<Project> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Transactional
    public Project save(Project project, RedirectAttributes redirectAttributes) {
        if (project.getId() == null) {
            redirectAttributes.addFlashAttribute("msg", String.format("%s added successfully!", project.getTitle()));

        } else {
            redirectAttributes.addFlashAttribute("msg", String.format("%s updated successfully!", project.getTitle()));
        }
        return projectRepository.save(project);
    }

    @Transactional
    public void delete(Long projectId, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", "Project was deleted");
        for(User user: projectService.findById(projectId).getUsers()){
            user.setRole(UserRole.ROLE_USER);
            user.setProject(null);
            userRepository.save(user);
        }
        projectRepository.delete(projectId);
    }

}