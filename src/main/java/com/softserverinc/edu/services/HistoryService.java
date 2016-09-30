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
        Long changeByUserId = (changeBy == null) ? null : changeBy.getId();
        Long assignedToUserId = issue.getAssignee().getId();
        if (issueService.isNewIssue(issue)) {
            save(History.newBuilder()
                    .setIssue(issueService.save(issue)).setChangedByUserId(changeByUserId)
                    .setAssignedToUserId(assignedToUserId).setAction(HistoryAction.CREATE_ISSUE).build()
            );
            return;
        }
        Issue notChangedIssue = issueService.findById(issue.getId());
        if (!issue.getStatus().equals(notChangedIssue.getStatus())) {
            save(History.newBuilder()
                    .setIssue(issue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                    .setAction(HistoryAction.CHANGE_ISSUE_STATUS).setStatus(issue.getStatus()).build()
            );
        }
        if (!issue.getTitle().equals(notChangedIssue.getTitle())) {
            save(History.newBuilder()
                    .setIssue(issue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                    .setAction(HistoryAction.CHANGE_ISSUE_TITLE).setTitle(issue.getTitle()).build()
            );
        }
        if (!issue.getType().equals(notChangedIssue.getType())) {
            save(History.newBuilder()
                    .setIssue(issue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                    .setAction(HistoryAction.CHANGE_ISSUE_TYPE).setType(issue.getType()).build()
            );
        }
        if (!issue.getPriority().equals(notChangedIssue.getPriority())) {
            save(History.newBuilder()
                    .setIssue(issue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                    .setAction(HistoryAction.CHANGE_ISSUE_PRIORITY).setPriority(issue.getPriority()).build()
            );
        }
        if (!issue.getAssignee().equals(notChangedIssue.getAssignee())) {
            save(History.newBuilder()
                    .setIssue(issue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                    .setAction(HistoryAction.CHANGE_ISSUE_ASSIGNEE).build()
            );
        }
        if (!issue.getDescription().equals(notChangedIssue.getDescription())) {
            save(History.newBuilder()
                    .setIssue(issue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                    .setAction(HistoryAction.CHANGE_ISSUE_DESCRIPTION).setDescription(issue.getDescription()).build()
            );
        }
    }

    public void writeToHistory(Issue issue, User changeBy, IssueComment comment) {
        Long changeByUserId = (changeBy == null) ? null : changeBy.getId();
        Long assignedToUserId = issue.getAssignee().getId();
        if (issueCommentService.isCommentNew(comment)) {
            save(History.newBuilder()
                    .setIssue(issue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                    .setAction(HistoryAction.ADD_ISSUE_COMMENT).setIssueComment(comment.getText()).build()
            );
            return;
        }
        save(History.newBuilder()
                .setIssue(issue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                .setAction(HistoryAction.EDIT_ISSUE_COMMENT).setIssueComment(comment.getText()).build()
        );
    }

    public void writeToHistory(Issue issue, User changeBy, String inputData, String action) {
        HistoryAction historyAction = HistoryAction.valueOf(action);
        Long changeByUserId = (changeBy == null) ? null : changeBy.getId();
        switch (historyAction) {
           case CHANGE_ISSUE_ASSIGNEE:
                Long assignedToUserId = Long.valueOf(inputData);
                issue.setAssignee(userService.findOne(assignedToUserId));
                save(History.newBuilder()
                       .setIssue(issue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                       .setAction(historyAction).build()
                );
                return;
            case CHANGE_ISSUE_STATUS:
                IssueStatus updatedStatus = IssueStatus.IN_PROGRESS.valueOf(inputData);
                assignedToUserId = issue.getAssignee().getId();
                issue.setStatus(updatedStatus);
                save(History.newBuilder()
                        .setIssue(issue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                        .setStatus(updatedStatus).setAction(historyAction).build()
                );
        }
    }

    private Page<HistoryDto> convertToHistoryDto(Page<History> historyList, Pageable pageable) {
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
