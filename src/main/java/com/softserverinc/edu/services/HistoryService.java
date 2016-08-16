package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.History;
import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.User;

import java.util.List;

public interface HistoryService {

    History findOne(Long id);

    List<History> findByIssue(Issue issue);

    History findByParent(History parent);

    List<History> findByAssignedToUser(User assignedToUser);

    History findByChangedByUser(User changedByUser);

    List<History> findByIsDeleted(Boolean isDeleted);

    History save(History history);

    void delete(Long id);

    History update(History history);

    List<History> findAll();

}
