package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Issue;

import java.util.List;

public interface IssueService {

    Issue getOne(Long id);
    Issue save(Issue issue);
    void delete(Long id);
    Issue update(Issue issue);
    List<Issue> getAll();

}
