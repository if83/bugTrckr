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

    public History findOne(Long id) {
        return historyRepository.findOne(id);
    }

    public List<History> findAll() {
        return historyRepository.findAll();
    }

    public Page<HistoryDto> findAllHistoryForUser(User user, Pageable pageable) {
        return convertToHistoryDto(historyRepository.findByAssignedToUserIdOrChangedByUserIdOrderByCreateTimeDesc(
                user.getId(), user.getId(), pageable), pageable);
    }

    public Page<HistoryDto> findCommentHistoryForUser(User user, Pageable pageable) {
        return convertToHistoryDto(historyRepository.findByChangedByUserIdAndIssueCommentIsNotNull(user.getId(), pageable),
                pageable);
    }

    public Page<HistoryDto> findAllHistoryForIssue(Issue issue, Pageable pageable) {
        return convertToHistoryDto(historyRepository.findByIssueOrderByCreateTimeDesc(issue, pageable), pageable);
    }

    public void writeToHistory(Issue updatedIssue, User changeBy) {
        Long changeByUserId = (changeBy == null) ? null : changeBy.getId();
        Long assignedToUserId = updatedIssue.getAssignee().getId();
        if (issueService.isNewIssue(updatedIssue)) {
            save(History.newBuilder()
                    .setIssue(issueService.save(updatedIssue)).setChangedByUserId(changeByUserId)
                    .setAssignedToUserId(assignedToUserId).setAction(HistoryAction.CREATE_ISSUE).build()
            );
            return;
        }
        Issue previousIssue = issueService.findById(updatedIssue.getId());
        if (!updatedIssue.getStatus().equals(previousIssue.getStatus())) {
            save(History.newBuilder()
                    .setIssue(updatedIssue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                    .setAction(HistoryAction.CHANGE_ISSUE_STATUS).setStatus(updatedIssue.getStatus()).build()
            );
        }
        if (!updatedIssue.getTitle().equals(previousIssue.getTitle())) {
            save(History.newBuilder()
                    .setIssue(updatedIssue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                    .setAction(HistoryAction.CHANGE_ISSUE_TITLE).setTitle(updatedIssue.getTitle()).build()
            );
        }
        if (!updatedIssue.getType().equals(previousIssue.getType())) {
            save(History.newBuilder()
                    .setIssue(updatedIssue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                    .setAction(HistoryAction.CHANGE_ISSUE_TYPE).setType(updatedIssue.getType()).build()
            );
        }
        if (!updatedIssue.getPriority().equals(previousIssue.getPriority())) {
            save(History.newBuilder()
                    .setIssue(updatedIssue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                    .setAction(HistoryAction.CHANGE_ISSUE_PRIORITY).setPriority(updatedIssue.getPriority()).build()
            );
        }
        if (!updatedIssue.getAssignee().equals(previousIssue.getAssignee())) {
            save(History.newBuilder()
                    .setIssue(updatedIssue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                    .setAction(HistoryAction.CHANGE_ISSUE_ASSIGNEE).build()
            );
        }
        if (!updatedIssue.getDescription().equals(previousIssue.getDescription())) {
            save(History.newBuilder()
                    .setIssue(updatedIssue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                    .setAction(HistoryAction.CHANGE_ISSUE_DESCRIPTION).setDescription(updatedIssue.getDescription()).build()
            );
        }
    }

    public void writeToHistory(Issue issue, User changeBy, IssueComment comment) {
        Long changeByUserId = null;
        String anonymName = null;
        if (changeBy.getId() == null) {
            anonymName = changeBy.getFirstName();
        } else {
            changeByUserId = changeBy.getId();
        }
        Long assignedToUserId = issue.getAssignee().getId();
        if (issueCommentService.isCommentNew(comment)) {
            save(History.newBuilder()
                    .setIssue(issue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                    .setAction(HistoryAction.ADD_ISSUE_COMMENT).setIssueComment(comment.getText())
                    .setAnonymName(anonymName).build()
            );
            return;
        }
        save(History.newBuilder()
                .setIssue(issue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                .setAction(HistoryAction.EDIT_ISSUE_COMMENT).setIssueComment(comment.getText())
                .setAnonymName(anonymName).build()
        );
    }

    public void writeToHistory(Issue issue, User changeBy, String inputData, HistoryAction action) {
        Long changeByUserId = (changeBy == null) ? null : changeBy.getId();
        switch (action) {
            case CHANGE_ISSUE_ASSIGNEE:
                Long assignedToUserId = Long.valueOf(inputData);
                issue.setAssignee(userService.findOne(assignedToUserId));
                save(History.newBuilder()
                        .setIssue(issue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                        .setAction(action).build()
                );
                return;
            case CHANGE_ISSUE_STATUS:
                IssueStatus updatedStatus = IssueStatus.valueOf(inputData);
                assignedToUserId = issue.getAssignee().getId();
                issue.setStatus(updatedStatus);
                save(History.newBuilder()
                        .setIssue(issue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                        .setStatus(updatedStatus).setAction(action).build()
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
            historyDto.setAnonymName(history.getAnonymName());
            result.add(historyDto);
        }
        Page<HistoryDto> historiesPage = new PageImpl<>(result, pageable, historyList.getTotalElements());
        return historiesPage;
    }

}
