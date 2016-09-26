package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.*;
import com.softserverinc.edu.entities.enums.HistoryAction;
import com.softserverinc.edu.entities.enums.IssueStatus;
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
    private IssueService issueService;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private IssueCommentService issueCommentService;

    public History findOne(Long id) {
        return historyRepository.findOne(id);
    }

    @Transactional
    public List<History> findAll() {
        return historyRepository.findAll();
    }

    @Transactional
    public Page<HistoryDto> findAllHistoryForUser(User user, Pageable pageable) {
        return convertToHistoryDto(historyRepository.findByAssignedToUserIdOrChangedByUserIdOrderByCreateTimeDesc(
                user.getId(), user.getId(), pageable), pageable);
    }

    @Transactional
    public Page<HistoryDto> findAllHistoryForIssue(Issue issue, Pageable pageable) {
        return convertToHistoryDto(historyRepository.findByIssueOrderByCreateTimeDesc(issue, pageable), pageable);
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

    public void writeToHistory(Issue issue, User changeBy) {
        History history;
        Long changeByUserId = changeBy.getId();
        if (issue.isNewIssue()) {
            history = new History(issueService.save(issue), changeByUserId, issue.getAssignee().getId(), HistoryAction.CREATE_ISSUE);
            history.setAssignedToUserId(issue.getAssignee().getId());
            save(history);
        } else {
            Issue notChangedIssue = issueService.findById(issue.getId());
            if (!issue.getStatus().equals(notChangedIssue.getStatus())) {
                history = new History(issue, changeByUserId, issue.getAssignee().getId(), HistoryAction.CHANGE_ISSUE_STATUS);
                history.setStatus(issue.getStatus());
                save(history);
            }
            if (!issue.getTitle().equals(notChangedIssue.getTitle())) {
                history = new History(issue, changeByUserId, issue.getAssignee().getId(), HistoryAction.CHANGE_ISSUE_TITLE);
                history.setTitle(issue.getTitle());
                save(history);
            }
            if (!issue.getType().equals(notChangedIssue.getType())) {
                history = new History(issue, changeByUserId, issue.getAssignee().getId(), HistoryAction.CHANGE_ISSUE_TYPE);
                history.setType(issue.getType());
                save(history);
            }
            if (!issue.getPriority().equals(notChangedIssue.getPriority())) {
                history = new History(issue, changeByUserId, issue.getAssignee().getId(), HistoryAction.CHANGE_ISSUE_PRIORITY);
                history.setPriority(issue.getPriority());
                save(history);
            }
            if (!issue.getAssignee().equals(notChangedIssue.getAssignee())) {
                history = new History(issue, changeByUserId, issue.getAssignee().getId(), HistoryAction.CHANGE_ISSUE_ASSIGNEE);
                history.setAssignedToUserId(issue.getAssignee().getId());
                save(history);
            }
            if (!issue.getDescription().equals(notChangedIssue.getDescription())) {
                history = new History(issue, changeByUserId, issue.getAssignee().getId(), HistoryAction.CHANGE_ISSUE_DESCRIPTION);
                history.setDescription(issue.getDescription());
                save(history);
            }
        }
    }

    public void writeToHistory(Issue issue, User changeBy, String inputData, String action) {
        HistoryAction historyAction = HistoryAction.valueOf(action);
        Long changeByUserId = changeBy.getId();
        History history;
        switch (historyAction) {
            case CHANGE_ISSUE_ASSIGNEE:
                Long assignedToUserId = Long.valueOf(inputData);
                issue.setAssignee(userService.findOne(assignedToUserId));
                history = new History(issue, changeByUserId, issue.getAssignee().getId(), historyAction);
                history.setAssignedToUserId(assignedToUserId);
                save(history);
                break;
            case CHANGE_ISSUE_STATUS:
                IssueStatus updatedStatus = IssueStatus.IN_PROGRESS.valueOf(inputData);
                issue.setStatus(updatedStatus);
                history = new History(issue, changeByUserId, issue.getAssignee().getId(), historyAction);
                history.setStatus(updatedStatus);
                save(history);
                break;
        }
    }

    public Page<HistoryDto> convertToHistoryDto(Page<History> historyList, Pageable pageable) {
        List<HistoryDto> result = new ArrayList<>();
        for (History history : historyList) {
            HistoryDto historyDto = new HistoryDto();
            historyDto.setAction(history.getAction());
            historyDto.setIssue(history.getIssue());
            historyDto.setStatus(history.getStatus());
            historyDto.setTitle(history.getTitle());
            historyDto.setChangedByUser(userService.getAvaliableUser(
                    userService.findOne(history.getChangedByUserId())));
            historyDto.setAssignedToUser(userService.getAvaliableUser(
                    userService.findOne(history.getAssignedToUserId())));
            historyDto.setCreateTime(history.getCreateTime());
            historyDto.setDescription(history.getDescription());
            historyDto.setIssueComment(history.getIssueComment());
            historyDto.setPriority(history.getPriority());
            historyDto.setType(history.getType());
            result.add(historyDto);
        }
        Page<HistoryDto> historiesPage = new PageImpl<>(result, pageable, historyList.getTotalElements());
        return historiesPage;
    }

}
