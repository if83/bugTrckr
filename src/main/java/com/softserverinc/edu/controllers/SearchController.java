package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
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

    @PostMapping("/search_text")
    public String search(@RequestParam(value = "searchText") String searchText, Model model) {

        searchRepository.indexEntity();

        List<Object> searchResult = searchRepository.search(searchText);
        List<Project> projects = new ArrayList<>();
        List<Issue> issues = new ArrayList<>();
        List<ProjectRelease> releases = new ArrayList<>();

        for (Object object : searchResult) {
            if (object instanceof Project) {
                projects.add((Project) object);
            } else if (object instanceof Issue) {
                issues.add((Issue) object);
            } else {
                releases.add((ProjectRelease) object);
            }
        }
        model.addAttribute("issues", issues);
        model.addAttribute("projects", projects);
        model.addAttribute("releases", releases);
        return "search_result";
    }

}
