package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Release;

import java.util.List;

public interface ReleaseService {

    Release getOne(Long id);
    Release save(Release release);
    void delete(Long id);
    Release update(Release release);
    List<Release> getAll();

}
