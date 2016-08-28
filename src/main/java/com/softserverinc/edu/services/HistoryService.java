package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.History;
import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.HistoryAction;
import com.softserverinc.edu.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    public History findOne(Long id) {
        return historyRepository.getOne(id);
    }

    public List<History> findByIssue(Issue issue) {
        return historyRepository.findByIssue(issue);
    }

    public List<History> findByAssignedToUser(User assignedToUser) {
        return historyRepository.findByAssignedToUser(assignedToUser);
    }

    public List<History> findByChangedByUser(User changedByUser) {
        return historyRepository.findByChangedByUser(changedByUser);
    }

    public List<History> findAll() {
        return historyRepository.findAll();
    }

    public List<History> findAllHistoryForUser(User user) {
        return historyRepository.findByAssignedToUserOrChangedByUserOrderByCreateDateDesc(user, user);
    }

    @Transactional
    public History save(History history) {
        return historyRepository.saveAndFlush(history);
    }

    @Transactional
    public void delete(Long id) {
        historyRepository.delete(id);
    }

    @Transactional
    public History update(History history) {
        return historyRepository.saveAndFlush(history);
    }

    public void writeToTheHistory(HistoryAction action, Issue issue, User changedByUser, String createTime) {
        History history = new History();
        history.setAction(action);
        history.setCreateTime(createTime);
        history.setIssue(issue);
        history.setAssignedToUser(issue.getAssignee());
        history.setChangedByUser(changedByUser);
        history.setIssueStatus(issue.getStatus());
        save(history);
    }

}
