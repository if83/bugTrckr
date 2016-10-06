package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.*;
import com.softserverinc.edu.repositories.IssueRepository;
import com.softserverinc.edu.repositories.ProjectReleaseRepository;
import com.softserverinc.edu.repositories.ProjectRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
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
        projectRelease = createTestRelease(id);
        Mockito.when(issueRepository.findByProjectRelease(projectRelease, pageable)).thenReturn(issuePage);

        Page<Issue> retrievedIssue = issueService.findIssuesForRelease(projectRelease, pageable);
        Assert.assertEquals(issuePage, retrievedIssue);
    }

    @Test
    public void testFindByReleaseAndIssueTitle() throws Exception {
        id = 4L;
        searchedTitle = "First";
        issuePage = createTestIssuePage(id);
        projectRelease = createTestRelease(id);
        Mockito.when(issueRepository.findByProjectReleaseAndTitleContaining(projectRelease, searchedTitle, pageable))
                .thenReturn(issuePage);

        Page<Issue> retrievedIssue = issueService.findByReleaseAndIssueTitle(projectRelease, searchedTitle, pageable);
        Assert.assertEquals(issuePage, retrievedIssue);
    }

    @Test
    public void testFindIssuesByProject() throws Exception {
        id = 5L;
        searchedTitle = "First";
        issuePage = createTestIssuePage(id);
        project = createTestProject(id);
        Mockito.when(issueRepository.findByProjectAndTitleContaining(project, searchedTitle, pageable))
                .thenReturn(issuePage);

        Page<Issue> retrievedIssue = issueService.findIssuesByProject(project, searchedTitle, pageable);
        Assert.assertEquals(issuePage, retrievedIssue);
    }

    @Test
    public void testFindAll() throws Exception {
        id = 5L;
        issuePage = createTestIssuePage(id);
        Mockito.when(issueRepository.findAll(pageable)).thenReturn(issuePage);

        Page<Issue> retrievedIssue = issueService.findAll(pageable);
        Assert.assertEquals(issuePage, retrievedIssue);
    }

    @Test
    public void testSave() throws Exception {
        id = 1L;
        issue = createTestIssue(id);
        Mockito.when(issueRepository.findOne(0L)).thenReturn(issue);
        issueService.save(issue);
        Mockito.verify(issueRepository).saveAndFlush(issue);
    }

    @Test
    public void testDelete() throws Exception {
        id = 1L;
        issue = createTestIssue(id);
        Mockito.when(issueRepository.findOne(0L)).thenReturn(issue);
        issueService.delete(id);
        Mockito.verify(issueRepository).delete(id);
    }


    @Test
    public void testFindByTitleContaining() throws Exception {
        id = 5L;
        searchedTitle = "First";
        issuePage = createTestIssuePage(id);
        Mockito.when(issueRepository.findByTitleContaining(searchedTitle, pageable)).thenReturn(issuePage);

        Page<Issue> retrievedIssue = issueService.findByTitleContaining(searchedTitle, pageable);
        Assert.assertEquals(issuePage, retrievedIssue);
    }

    @Test
    public void testFindAllList() throws Exception {
        id = 1L;
        issue = createTestIssue(id);
        List<Issue> issues = new ArrayList<>();
        issues.add(issue);
        Mockito.when(issueRepository.findAll()).thenReturn(issues);

        List<Issue> retrievedIssues = issueService.findAll();
        Assert.assertEquals(issues, retrievedIssues);
    }


    @Test
    public void testFindByProject() throws Exception {
        id = 5L;
        issuePage = createTestIssuePage(id);
        project = createTestProject(id);
        Mockito.when(issueRepository.findByProject(project, pageable)).thenReturn(issuePage);

        Page<Issue> retrievedIssue = issueService.findByProject(project, pageable);
        Assert.assertEquals(issuePage, retrievedIssue);
    }

    private Issue createTestIssue(Long id) {
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
        issue.setProject(createTestProject(id));
        issue.setProjectRelease(createTestRelease(id));
        issue.setLastUpdateDate(new Date());
        return issue;
    }

    private Page<Issue> createTestIssuePage(Long id) {
        Issue issue = createTestIssue(id);
        List<Issue> issues = new ArrayList<>();
        issues.add(issue);
        return new PageImpl<>(issues);
    }

    private Project createTestProject(Long id) {
        Project project = new Project();
        project.setId(id);
        project.setTitle("Project");
        project.setGuestAddComment(true);
        project.setGuestCreateIssues(true);
        project.setGuestView(true);
        project.setDescription("Some text");
        return project;
    }

    private ProjectRelease createTestRelease(Long id) {
        ProjectRelease projectRelease = new ProjectRelease();
        projectRelease.setId(id);
        projectRelease.setVersion("New version");
        projectRelease.setReleaseStatus(ReleaseStatus.OPEN);
        return projectRelease;
    }

}