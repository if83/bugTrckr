package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.*;
import com.softserverinc.edu.entities.enums.IssuePriority;
import com.softserverinc.edu.entities.enums.IssueStatus;
import com.softserverinc.edu.entities.enums.IssueType;
import com.softserverinc.edu.repositories.IssueCommentRepository;
import com.softserverinc.edu.repositories.IssueRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class IssueCommentServiceTest {
    @Mock
    private IssueRepository issueRepository;

    @Mock
    private IssueCommentRepository issueCommentRepository;

    @InjectMocks
    private IssueCommentService issueCommentService;

    private Long id;
    private IssueComment issueComment;
    private Issue issue;
    private List<IssueComment> issueCommentList;

    @Test
    public void testFindOne() throws Exception {
        id = 1L;
        issueComment = getTestIssueComment(id);
        Mockito.when(issueCommentRepository.findOne(id)).thenReturn(issueComment);

        IssueComment currentIssueComment = issueCommentService.findOne(id);
        Assert.assertEquals(issueComment, currentIssueComment);
    }

    @Test
    public void testFindByIssue() throws Exception {
        id = 1L;
        issue = getTestIssue(id);
        issueCommentList = getTestIssueCommentList(id);
        Mockito.when(issueCommentRepository.findByIssue(issue)).thenReturn(issueCommentList);

        List<IssueComment> currentIssueCommentList = issueCommentService.findByIssue(issue);
        Assert.assertEquals(issueCommentList, currentIssueCommentList);
    }

    @Test
    public void testSave() throws Exception {
        id = 1L;
        issueComment = getTestIssueComment(id);
        Mockito.when(issueCommentRepository.findOne(0L)).thenReturn(issueComment);
        issueCommentService.save(issueComment);
        Mockito.verify(issueCommentRepository).saveAndFlush(issueComment);
    }

    @Test
    public void testDelete() throws Exception {
        id = 1L;
        issueComment = getTestIssueComment(id);
        Mockito.when(issueCommentRepository.findOne(0L)).thenReturn(issueComment);
        issueCommentService.delete(id);
        Mockito.verify(issueCommentRepository).delete(id);
    }

    public IssueComment getTestIssueComment(Long id) {
        IssueComment issueComment = new IssueComment();
        issueComment.setId(id);
        issueComment.setTimeStamp(new Date());
        issueComment.setText("text");
        issueComment.setIsEdited(false);
        issueComment.setAnonymousName("anonymous");
        return issueComment;
    }

    private Issue getTestIssue(Long id) {
        Issue issue = new Issue();
        issue.setId(id);
        issue.setTitle("Issue");
        issue.setType(IssueType.BUG);
        issue.setDescription("Test");
        issue.setPriority(IssuePriority.BLOCKER);
        issue.setStatus(IssueStatus.OPEN);
        issue.setCreatedBy(new User());
        issue.setAssignee(new User());
        issue.setCreateTime(new Date());
        issue.setDueDate(new Date());
        issue.setProject(new Project());
        issue.setProjectRelease(new ProjectRelease());
        issue.setLastUpdateDate(new Date());
        return issue;
    }

    private List<IssueComment> getTestIssueCommentList(Long id) {
        IssueComment issueComment = getTestIssueComment(id);
        List<IssueComment> issueComments = new ArrayList<>();
        issueComments.add(issueComment);
        return issueComments;
    }
}
