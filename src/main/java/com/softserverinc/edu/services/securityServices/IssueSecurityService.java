package com.softserverinc.edu.services.securityServices;

import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class IssueSecurityService extends BasicSecurityService {

    public boolean hasPermissionToEditIssue(Long currentIssueId) {
        if (isAuthenticated()) {
            return true;
        }
        return false;
    }

}
