package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.History;
import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.User;

import java.util.List;

public interface HistoryService {

    History findOne(Long id);

    List<History> findByIssue(Issue issue);

    History findByParentId(Long parentId);

    List<History> findByAssigneeId(User assigneeId);

    History findByChangeById(Long changeById);

    History save(History history);

    void delete(Long id);

    History update(History history);

    List<History> findAll();

}
