package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.repositories.IssueRepository;
import com.softserverinc.edu.repositories.ProjectRepository;
import com.softserverinc.edu.repositories.SearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
public class SearchController {

    private final static Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping(value = "/search_text")
    public String search(@RequestParam(value = "searchText") String searchText, Model model){

        LOGGER.info(searchText);

        searchRepository.indexEntity();

        List<Object> searchResult = searchRepository.search(searchText);
        List<Project> projects = new ArrayList<>();
        List<Issue> issues = new ArrayList<>();
        LOGGER.info("length", searchText.length());
        for (Object object : searchResult) {
            if (object instanceof Project) {
                projects.add((Project) object);
                LOGGER.info(((Project) object).getTitle());
            } else {
                issues.add((Issue) object);

                LOGGER.info(((Issue) object).getTitle());
            }
        }
        System.out.println(issues.size());
        model.addAttribute("issues", issues);
        model.addAttribute("projects", projects);
        return "search_result";
    }
}
