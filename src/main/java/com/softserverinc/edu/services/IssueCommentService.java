package com.softserverinc.edu.services;

import com.softserverinc.edu.constants.PageConstant;
import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.IssueComment;
import com.softserverinc.edu.repositories.IssueCommentRepository;
import com.softserverinc.edu.services.securityServices.BasicSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class IssueCommentService {

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @Autowired
    private BasicSecurityService basicSecurityService;

    @Autowired
    private IssueCommentRepository issueCommentRepository;

    public IssueComment findOne(Long id) {
        return issueCommentRepository.findOne(id);
    }

    public List<IssueComment> findByIssue(Issue issue) {
        return issueCommentRepository.findByIssue(issue);
    }

    public List<IssueComment> findAll() {
        return issueCommentRepository.findAll();
    }

    @Transactional
    public IssueComment save(IssueComment issueComment) {
        return issueCommentRepository.saveAndFlush(issueComment);
    }

    @Transactional
    public void delete(Long id) {
        issueCommentRepository.delete(id);
    }

    @Transactional
    public IssueComment update(IssueComment issueComment) {
        return issueCommentRepository.saveAndFlush(issueComment);
    }

    public List<IssueComment> findByIssueId(Long issueId) {
        return issueCommentRepository.findByIssue(issueService.findById(issueId));
    }

    public boolean isCommentNew(IssueComment comment) {
        return (comment.getId() == null || comment.getId() == 0L);
    }

    public IssueComment getEditedCommentById(Long issueCommentId) {
        return findOne(issueCommentId);
    }

    public IssueComment getNewIssueComment(Long issueId) {
        IssueComment issueComment = new IssueComment();
        issueComment.setIssue(issueService.findById(issueId));
        issueComment.setIsEdited(false);
        if (basicSecurityService.isAuthenticated()){
            issueComment.setUser(userService.findOne(basicSecurityService.getActiveUser().getId()));
        }
        return issueComment;
    }

    public Boolean validateIssueCommentUI(IssueComment issueComment) {
        if (!issueComment.getAnonymousName().equals(PageConstant.EMPTY_STRING) ||
                !basicSecurityService.isAuthenticated()) {
            int anonymousNameLength = issueComment.getAnonymousName().length();
            return anonymousNameLength >= PageConstant.MIN_ANONYM_NAME_LENGTH &&
                    anonymousNameLength <= PageConstant.MAX_ANONYM_NAME_LENGTH;
        }
        return true;
    }

    public void preSaveIssueComment (IssueComment issueComment) {
        if (issueComment.getId() == null) {
            issueComment.setTimeStamp(new Date());
        } else {
            String text = findOne(issueComment.getId()).getText();
            Date timeStamp = findOne(issueComment.getId()).getTimeStamp();
            issueComment.setTimeStamp(timeStamp);
            if (!issueComment.getText().equals(text)) {
                issueComment.setIsEdited(true);
            }
        }
    }
}