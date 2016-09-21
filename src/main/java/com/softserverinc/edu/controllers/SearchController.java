package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.repositories.SearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@Controller
public class SearchController {
    private Logger logger = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    SearchRepository searchRepository;

    @PostMapping(value = "/contentSearch")
    public ModelAndView search(@RequestParam(value = "title") String searchText) throws Exception {
        logger.info(searchText);
        searchRepository.indexIssues();
        List<Object> searchResult = searchRepository.searchInIssue(searchText);
        List<Project> projects = new ArrayList<>();
        List<Issue> issues = new ArrayList<>();
        for (Object object : searchResult) {
            if (object instanceof Issue) {
                issues.add((Issue) object);
                logger.info(((Issue) object).getTitle());
            } else {
                projects.add((Project) object);
                logger.info(((Project) object).getTitle());
            }
        }
        ModelAndView modelAndView = new ModelAndView("content_search");
        modelAndView.addObject("issues", issues);
        modelAndView.addObject("projects", projects);
        return modelAndView;
    }

    @GetMapping(value = "/contentSearch")
    public String search() throws Exception {
        return "content_search";
    }
}
