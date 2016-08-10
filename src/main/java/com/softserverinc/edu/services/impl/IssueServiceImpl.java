package com.softserverinc.edu.services.impl;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.repositories.IssueRepository;
import com.softserverinc.edu.services.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueServiceImpl implements IssueService {

    @Autowired
    IssueRepository issueRepository;

    @Override
    public Issue getOne(Long id) {
        return issueRepository.getOne(id);
    }

    @Override
    public Issue save(Issue issue) {
        return issueRepository.saveAndFlush(issue);
    }

    @Override
    public void delete(Long id) {
        issueRepository.delete(id);
    }

    @Override
    public Issue update(Issue issue) {
        return issueRepository.saveAndFlush(issue);
    }

    @Override
    public List<Issue> getAll() {
        return issueRepository.findAll();
    }

}
