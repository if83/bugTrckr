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

/**
 * Serve for working with search requests
 */
@Controller
public class SearchController {

    private final static Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchRepository searchRepository;

    /**
     * @param searchText request text to searching information in entities
     * @param model      holder for model attributes
     * @return search_result page with list of find information
     */
    @PostMapping("/search_text")
    public String search(@RequestParam("searchText") String searchText, Model model) {

        searchRepository.indexEntity();

        List searchResult = searchRepository.search(searchText);

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
