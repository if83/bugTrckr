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

/**
 * Contain methods for working with issueComment database table and auxiliary methods
 */
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

    /**
     * Find issueComment entry by id
     *
     * @param id issueComment entry's id
     * @return issueComment entry with specified id or null if there is no such entry
     */
    public IssueComment findOne(Long id) {
        return issueCommentRepository.findOne(id);
    }

    /**
     * Find issueComment entry by issue
     *
     * @param issue issue instance
     * @return List of issueComment entries with specified issue or empty list if there is no such entries
     */
    public List<IssueComment> findByIssue(Issue issue) {
        return issueCommentRepository.findByIssue(issue);
    }

    /**
     * Save issueComment entry
     * @param issueComment IssueComment instance
     * @return saved IssueComment instance
     */
    @Transactional
    public IssueComment save(IssueComment issueComment) {
        return issueCommentRepository.saveAndFlush(issueComment);
    }

    /**
     * Remove issueComment entry by specified id
     *
     * @param id issueComment entry's id
     */
    @Transactional
    public void delete(Long id) {
        issueCommentRepository.delete(id);
    }

    /**
     * Find list of issueComment entries by issueId
     *
     * @param issueId issue's id
     * @return List of issueComment entries with specified by id issue or empty list if there is no such entries
     */
    public List<IssueComment> findByIssueId(Long issueId) {
        return issueCommentRepository.findByIssue(issueService.findById(issueId));
    }

    /**
     * Check if issueComment is new
     * @param comment IssueComment instance
     * @return boolean representation of checking
     */
    public boolean isCommentNew(IssueComment comment) {
        return (comment.getId() == null || comment.getId() == 0L);
    }

    /**
     * Find issueComment entry which is to be edited by id
     * @param issueCommentId issueComment's id
     * @return issueComment entry with specified id
     */
    public IssueComment getEditedCommentById(Long issueCommentId) {
        return findOne(issueCommentId);
    }
/**
 * Prepare WorkLog instance ready to be used in form
 *
 * <p>invoke {@link #getCurrentIssue(Long)}</p>
 * @param issueId issue's id
 * @return WorkLog instance with specified issue and user fields
 */
    /**
     * Prepare IssueComment instance ready to be used in form
     *
     * @param issueId issue's id
     * @return IssueComment instance with specified issue, user and isEdited fields
     */
    public IssueComment getNewIssueComment(Long issueId) {
        IssueComment issueComment = new IssueComment();
        issueComment.setIssue(issueService.findById(issueId));
        issueComment.setIsEdited(false);
        if (basicSecurityService.isAuthenticated()){
            issueComment.setUser(userService.findOne(basicSecurityService.getActiveUser().getId()));
        }
        return issueComment;
    }

    /**
     * Validate IssueComment instance accepted from UI
     *
     * @param issueComment issueComment's id
     * @return Boolean representation of validation result
     */
    public Boolean validateIssueCommentUI(IssueComment issueComment) {
        if (!issueComment.getAnonymousName().equals(PageConstant.EMPTY_STRING) ||
                !basicSecurityService.isAuthenticated()) {
            int anonymousNameLength = issueComment.getAnonymousName().length();
            return anonymousNameLength >= PageConstant.MIN_ANONYM_NAME_LENGTH &&
                    anonymousNameLength <= PageConstant.MAX_ANONYM_NAME_LENGTH;
        }
        return true;
    }

    /**
     * Check if issueComment is new, set timeStamp for new issueComment and isEdited fields if text was changed
     * @param issueComment issueComment instance
     */
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