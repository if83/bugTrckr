package com.softserverinc.edu.services.impl;

import com.softserverinc.edu.entities.Release;
import com.softserverinc.edu.repositories.ReleaseRepository;
import com.softserverinc.edu.services.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReleaseServiceImpl implements ReleaseService {

    @Autowired
    ReleaseRepository releaseRepository;

    @Override
    public Release getOne(Long id) {
        return releaseRepository.getOne(id);
    }

    @Override
    public Release save(Release release) {
        return releaseRepository.saveAndFlush(release);
    }

    @Override
    public void delete(Long id) {
        releaseRepository.delete(id);
    }

    @Override
    public Release update(Release release) {
        return releaseRepository.saveAndFlush(release);
    }

    @Override
    public List<Release> getAll() {
        return releaseRepository.findAll();
    }
}
