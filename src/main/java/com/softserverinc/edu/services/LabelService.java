package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Label;

import java.util.List;

public interface LabelService {

    Label findOne(Long id);

    Label findByTitle(String title);

    List<Label> findByIssues(Issue issue);

    List<Label> findByIsDeleted(Boolean isDeleted);

    List<Label> findAll();

    Label save(Label label);

    void delete(Long id);

    Label update(Label label);

}
