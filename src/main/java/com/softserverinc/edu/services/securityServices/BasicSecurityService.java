package com.softserverinc.edu.services.securityServices;


import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BasicSecurityService {

    @Autowired
    private UserService userService;

    public UserRole getActiveUserRole() {
        return UserRole.valueOf(SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().toArray()[0].toString());
    }

    public User getActiveUser() {
        return userService.findByEmailIs(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null
               && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
               && !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken);
    }

}
