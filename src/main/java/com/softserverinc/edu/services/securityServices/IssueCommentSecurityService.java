package com.softserverinc.edu.services.securityServices;

import com.softserverinc.edu.services.IssueCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueCommentSecurityService extends BasicSecurityService {

    @Autowired
    private IssueCommentService issueCommentService;
}
