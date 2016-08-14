package com.softserverinc.edu.services.impl;

import com.softserverinc.edu.entities.History;
import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.repositories.HistoryRepository;
import com.softserverinc.edu.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    HistoryRepository historyRepository;

    @Override
    public History findOne(Long id) {
        return historyRepository.getOne(id);
    }

    @Override
    public List<History> findByIssue(Issue issue) {
        return historyRepository.findByIssue(issue);
    }

    @Override
    public History findByParentId(Long parentId) {
        return historyRepository.findByParentId(parentId);
    }

    @Override
    public List<History> findByAssigneeId(User assigneeId) {
        return historyRepository.findByAssigneeId(assigneeId);
    }

    @Override
    public History findByChangeById(Long changeById) {
        return historyRepository.findByChangeById(changeById);
    }

    @Override
    public List<History> findAll() {
        return historyRepository.findAll();
    }

    @Override
    @Transactional
    public History save(History history) {
        return historyRepository.saveAndFlush(history);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        historyRepository.delete(id);
    }

    @Override
    @Transactional
    public History update(History history) {
        return historyRepository.saveAndFlush(history);
    }

}
