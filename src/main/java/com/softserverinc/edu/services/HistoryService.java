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

    /**
     * Returns converted pageable list of history for current user (user's activity).
     *
     * @param user      represents current user
     * @param pageable  represents total number of pages
     * @return          list of historyDto objects
     */
    public Page<HistoryDto> findAllHistoryForUser(User user, Pageable pageable) {
        return convertToHistoryDto(historyRepository.findByAssignedToUserIdOrChangedByUserIdOrderByCreateTimeDesc(
                user.getId(), user.getId(), pageable), pageable);
    }

    /**
     * Returns converted pageable list of comment history for current user.
     *
     * @param user      represents current user
     * @param pageable  represents total number of pages
     * @return          list of historyDto objects
     */
    public Page<HistoryDto> findCommentHistoryForUser(User user, Pageable pageable) {
        return convertToHistoryDto(historyRepository.findByChangedByUserIdAndIssueCommentIsNotNull(user.getId(), pageable),
                pageable);
    }

    /**
     * Returns converted pageable list of all issue history
     *
     * @param issue     represents current issue
     * @param pageable  represents total number of pages
     * @return          list of historyDto objects
     */
    public Page<HistoryDto> findAllHistoryForIssue(Issue issue, Pageable pageable) {
        return convertToHistoryDto(historyRepository.findByIssueOrderByCreateTimeDesc(issue, pageable), pageable);
    }

    /**
     * Writes issue changes, which are received from issue-form, into history.
     * Checks every issue field is it was changed, one by one. Then writes
     * these changes into history as separate record.
     *
     * @param updatedIssue  modified issue from issue-form
     * @param changeBy      user that modified issue
     */
    public void writeToHistory(Issue updatedIssue, User changeBy) {
        Long changeByUserId = (changeBy == null) ? null : changeBy.getId();
        Long assignedToUserId = updatedIssue.getAssignee().getId();
        // checks if issue is new and write this action into history
        if (issueService.isNewIssue(updatedIssue)) {
            save(History.newBuilder()
                    .setIssue(issueService.save(updatedIssue)).setChangedByUserId(changeByUserId)
                    .setAssignedToUserId(assignedToUserId).setAction(HistoryAction.CREATE_ISSUE).build()
            );
            return;
        }
        // not yet modified issue
        Issue previousIssue = issueService.findById(updatedIssue.getId());
        // checks if status was modified
        if (!updatedIssue.getStatus().equals(previousIssue.getStatus())) {
            save(History.newBuilder()
                    .setIssue(updatedIssue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                    .setAction(HistoryAction.CHANGE_ISSUE_STATUS).setStatus(updatedIssue.getStatus()).build()
            );
        }
        // checks if title was modified
        if (!updatedIssue.getTitle().equals(previousIssue.getTitle())) {
            save(History.newBuilder()
                    .setIssue(updatedIssue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                    .setAction(HistoryAction.CHANGE_ISSUE_TITLE).setTitle(updatedIssue.getTitle()).build()
            );
        }
        // checks if type was modified
        if (!updatedIssue.getType().equals(previousIssue.getType())) {
            save(History.newBuilder()
                    .setIssue(updatedIssue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                    .setAction(HistoryAction.CHANGE_ISSUE_TYPE).setType(updatedIssue.getType()).build()
            );
        }
        // checks if priority was modified
        if (!updatedIssue.getPriority().equals(previousIssue.getPriority())) {
            save(History.newBuilder()
                    .setIssue(updatedIssue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                    .setAction(HistoryAction.CHANGE_ISSUE_PRIORITY).setPriority(updatedIssue.getPriority()).build()
            );
        }
        // checks if assignee was modified
        if (!updatedIssue.getAssignee().equals(previousIssue.getAssignee())) {
            save(History.newBuilder()
                    .setIssue(updatedIssue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                    .setAction(HistoryAction.CHANGE_ISSUE_ASSIGNEE).build()
            );
        }
        // checks if description was modified
        if (!updatedIssue.getDescription().equals(previousIssue.getDescription())) {
            save(History.newBuilder()
                    .setIssue(updatedIssue).setChangedByUserId(changeByUserId).setAssignedToUserId(assignedToUserId)
                    .setAction(HistoryAction.CHANGE_ISSUE_DESCRIPTION).setDescription(updatedIssue.getDescription()).build()
            );
        }
    }

    /**
     * Writes users' comments, which are received from issue comment form, into history.
     * Before that checks if comment was written by authenticated user or anonym.
     *
     * @param issue     issue, where comment was published
     * @param changeBy  user who wrote comment
     * @param comment   current comment
     */
    public void writeToHistory(Issue issue, User changeBy, IssueComment comment) {
        Long changeByUserId = null;
        String anonymName = null;
        // check if user is anonymous
        if (changeBy.getId() == null) {
            anonymName = changeBy.getFirstName();
        } else {
            changeByUserId = changeBy.getId();
        }
        Long assignedToUserId = issue.getAssignee().getId();
        // checks if comment is new
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

    /**
     * Writes issue changes, which are received from release page (from ajax), into history.
     * Checks what action was performed and writes record with all required parameters to history.
     *
     * @param issue     modified issue from UI
     * @param changeBy  user that modified issue
     * @param inputData represents the input data (changed status, assignee etc.)
     * @param action    represents what action was performed
     */
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

    /**
     * This method converts history list to historyDto list. It's necessary because
     * "History" table in a database doesn't consist foreign keys to table "Users"
     * (it keeps only user id). So on the jsp page we won't be able to get user object
     * from history object. That's why this method converts history to historyDto, that
     * can hold "User" object.
     *
     * @param historyList   list that must be converted
     * @param pageable      represents total number of pages
     * @return              converted list of historyDto objects
     */
    private Page<HistoryDto> convertToHistoryDto(Page<History> historyList, Pageable pageable) {
        List<HistoryDto> result = new ArrayList<>();
        for (History history : historyList) {
            HistoryDto historyDto = new HistoryDto();
            historyDto.setAction(history.getAction());
            historyDto.setIssue(history.getIssue());
            historyDto.setStatus(history.getStatus());
            historyDto.setTitle(history.getTitle());
            // if history.getChangedByUserId() returns null we must save
            // into historyDto mock "User" object with replaced data
            historyDto.setChangedByUser(userService.getAvailableUser(
                    userService.findOne(history.getChangedByUserId())));
            historyDto.setAssignedToUser(userService.getAvailableUser(
                    userService.findOne(history.getAssignedToUserId())));
            historyDto.setCreateTime(history.getCreateTime());
            historyDto.setDescription(history.getDescription());
            historyDto.setIssueComment(history.getIssueComment());
            historyDto.setPriority(history.getPriority());
            historyDto.setType(history.getType());
            historyDto.setAnonymName(history.getAnonymName());
            result.add(historyDto);
        }
        // convert pageable list of histories to pageable list of historyDto objects
        Page<HistoryDto> historiesPage = new PageImpl<>(result, pageable, historyList.getTotalElements());
        return historiesPage;
    }

}
