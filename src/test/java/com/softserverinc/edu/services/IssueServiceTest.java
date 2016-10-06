package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.IssuePriority;
import com.softserverinc.edu.entities.enums.IssueStatus;
import com.softserverinc.edu.entities.enums.IssueType;
import com.softserverinc.edu.repositories.IssueRepository;
import com.softserverinc.edu.repositories.ProjectReleaseRepository;
import com.softserverinc.edu.repositories.ProjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.junit.Assert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class IssueServiceTest {

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectReleaseRepository projectReleaseRepository;


    @InjectMocks
    private IssueService issueService;

    private Long id;
    private Issue issue;
    private Project project;
    private ProjectRelease projectRelease;
    private Pageable pageable;
    private Page<Issue> issuePage;
    private String searchedTitle;

    @Test
    public void testFindById() throws Exception {
        id = 1L;
        issue = createTestIssue(id);
        Mockito.when(issueRepository.findOne(id)).thenReturn(issue);

        Issue retrievedIssue = issueService.findById(id);
        Assert.assertEquals(issue, retrievedIssue);
    }

    @Test
    public void testIsNewIssue() throws Exception {
        id = 2L;
        issue = createTestIssue(id);
        Assert.assertFalse(issueService.isNewIssue(issue));
    }

    @Test
    public void testFindIssuesByRelease() throws Exception {
        id = 3L;
        issuePage = createTestIssuePage(id);
        projectRelease = projectReleaseRepository.findOne(id);
        Mockito.when(issueRepository.findByProjectRelease(projectRelease, pageable)).thenReturn(issuePage);

        Page<Issue> retrievedIssue = issueService.findIssuesByRelease(projectRelease, pageable);
        Assert.assertEquals(issuePage, retrievedIssue);
    }

    @Test
    public void testFindByReleaseAndIssueTitle() throws Exception {
        id = 4L;
        searchedTitle = "First";
        issuePage = createTestIssuePage(id);
        projectRelease = projectReleaseRepository.findOne(id);
        Mockito.when(issueRepository.findByProjectReleaseAndTitleContaining(projectRelease, searchedTitle, pageable))
                .thenReturn(issuePage);

        Page<Issue> retrievedIssue = issueService.findByReleaseAndIssueTitle(projectRelease, searchedTitle,pageable);
        Assert.assertEquals(issuePage, retrievedIssue);
    }

    @Test
    public void testFindIssuesByProject() throws Exception {
        id = 5L;
        searchedTitle = "First";
        issuePage = createTestIssuePage(id);
        project = projectRepository.findOne(id);
        Mockito.when(issueRepository.findByProjectAndTitleContaining(project, searchedTitle, pageable))
                .thenReturn(issuePage);

        Page<Issue> retrievedIssue = issueService.findIssuesByProject(project, searchedTitle,pageable);
        Assert.assertEquals(issuePage, retrievedIssue);
    }

    private Issue createTestIssue(Long issueId){
        Issue issue = new Issue();
        issue.setId(issueId);
        issue.setTitle("First issue");
        issue.setType(IssueType.BUG);
        issue.setDescription("Test");
        issue.setPriority(IssuePriority.BLOCKER);
        issue.setStatus(IssueStatus.OPEN);
        issue.setCreatedBy(new User());
        issue.setAssignee(new User());
        issue.setCreateTime(new Date());
        issue.setDueDate(new Date());
        issue.setProject(projectRepository.getOne(1L));
        issue.setProjectRelease(projectReleaseRepository.getOne(1L));
        issue.setLastUpdateDate(new Date());
        return issue;
    }

    private Page<Issue> createTestIssuePage(Long id){
        Issue issue = new Issue();
        issue.setId(id);
        issue.setTitle("First issue");
        issue.setType(IssueType.BUG);
        issue.setDescription("Test");
        issue.setPriority(IssuePriority.BLOCKER);
        issue.setStatus(IssueStatus.OPEN);
        issue.setCreatedBy(new User());
        issue.setAssignee(new User());
        issue.setCreateTime(new Date());
        issue.setDueDate(new Date());
        issue.setProject(projectRepository.getOne(id));
        issue.setProjectRelease(projectReleaseRepository.getOne(id));
        issue.setLastUpdateDate(new Date());
        List<Issue> issues = new ArrayList<>();
        issues.add(issue);
        return new PageImpl<>(issues);
    }

}