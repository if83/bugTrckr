package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.repositories.IssueRep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    private Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    IssueRep issueRep;


    public String index() {
        return "search";
    }

    @PostMapping(value = "/contentSearch")
    public String search(@RequestParam(value = "title") String s,
                         Model model) throws Exception {
        logger.info(s);
        System.out.println(s);

        issueRep.indexIssues();

        List<Issue> issues = issueRep.searchInIssue(s);
//        List<Issue> issues = issueRepository.findByTitle(s);

        model.addAllAttributes(issues);
        model.addAttribute(issues.get(0).getTitle());
        return "content_search";

    }

}
