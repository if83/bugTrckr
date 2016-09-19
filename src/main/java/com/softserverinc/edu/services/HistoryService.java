package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.History;
import com.softserverinc.edu.entities.HistoryDto;
import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.HistoryAction;
import com.softserverinc.edu.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryService {

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryRepository historyRepository;

    public History findOne(Long id) {
        return historyRepository.findOne(id);
    }

    @Transactional
    public List<History> findAll() {
        return historyRepository.findAll();
    }

    @Transactional
    public Page<History> findAllHistoryForUser(User user, Pageable pageable) {
        return historyRepository.findByAssignedToUserIdOrChangedByUserIdOrderByCreateTimeDesc(user.getId(),
                user.getId(), pageable);
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
        history.setAssignedToUserId(issue.getAssignee().getId());
        history.setChangedByUserId(changedByUser.getId());
        history.setIssueStatus(issue.getStatus());
        save(history);
    }

    @Transactional
    public Page<HistoryDto> convertToHistoryDto(Page<History> historyList, Pageable pageable) {
        List<HistoryDto> result = new ArrayList<>();
        for (History history : historyList) {
            HistoryDto historyDto = new HistoryDto();
            historyDto.setAction(history.getAction());
            historyDto.setIssue(history.getIssue());
            historyDto.setChangedByUser(userService.findOne(history.getChangedByUserId()).isDeleted()
                                        ? null
                                        : userService.findOne(history.getChangedByUserId()));
            historyDto.setAssignedToUser(userService.findOne(history.getAssignedToUserId()).isDeleted()
                                         ? null
                                         : userService.findOne(history.getAssignedToUserId()));
            historyDto.setIssueStatus(history.getIssueStatus());
            historyDto.setCreateTime(history.getCreateTime());
            result.add(historyDto);
        }
        Page<HistoryDto> historiesPage = new PageImpl<>(result, pageable, historyList.getTotalElements());
        return historiesPage;
    }

}
