package com.softserverinc.edu.services.securityServices;


import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * This class represents basic methods needed for receiving information
 * from Spring Security context (information about logged user).
 */
@Service
public class BasicSecurityService {

    @Autowired
    private UserService userService;

    /**
     * Returns current logged user.
     *
     * @return active user
     */
    public User getActiveUser() {
        return userService.findByEmailIs(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    /**
     * Checks if current user is authenticated in system.
     *
     * @return true if user is authenticated, false otherwise
     */
    public boolean isAuthenticated() {
        boolean isAuthenticationNotNull = SecurityContextHolder.getContext().getAuthentication() != null;
        boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        boolean isNotAnonymousAuthentication = !(SecurityContextHolder.getContext().getAuthentication()
                instanceof AnonymousAuthenticationToken);
        return isAuthenticationNotNull && isAuthenticated && isNotAnonymousAuthentication;
    }

    /**
     * Returns role of current user that will specify access rights to resources
     *
     * @return role of user
     */
    public UserRole getActiveUserRole() {
        return UserRole.valueOf(SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().toArray()[0].toString());
    }


}
