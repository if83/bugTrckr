package com.softserverinc.edu.services.impl;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.Release;
import com.softserverinc.edu.entities.enums.ReleaseStatus;
import com.softserverinc.edu.repositories.ReleaseRepository;
import com.softserverinc.edu.services.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReleaseServiceImpl implements ReleaseService {

    @Autowired
    ReleaseRepository releaseRepository;

    @Override
    public Release findById(Long id) {
        return releaseRepository.findOne(id);
    }

    @Override
    public List<Release> findByProject(Project project) {
        return releaseRepository.findByProject(project);
    }

    @Override
    public Release findByIssue(Issue issue) {
        Release release = null;
        List<Release> listOfReleases = releaseRepository.findAll();
        for (Release releaseIterator : listOfReleases) {
            if (releaseIterator.getIssues().contains(issue)) {
                release = releaseIterator;
                break;
            }
        }
        return release;
    }

    @Override
    public List<Release> findByVersion(String version) {
        return releaseRepository.findByVersion(version);
    }

    @Override
    public List<Release> findByReleaseStatus(ReleaseStatus releaseStatus) {
        return releaseRepository.findByReleaseStatus(releaseStatus);
    }

    public List<Release> findByIsDeleted(Boolean isDeleted) {
        return releaseRepository.findByIsDeleted(isDeleted);
    }

    @Override
    public List<Release> findAll() {
        return releaseRepository.findAll();
    }

    @Transactional
    @Override
    public Release save(Release release) {
        return releaseRepository.saveAndFlush(release);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        releaseRepository.findOne(id).setIsDeleted(true);
    }

    @Transactional
    @Override
    public Release update(Release release) {
        return releaseRepository.saveAndFlush(release);
    }

}
