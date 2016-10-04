package com.softserverinc.edu.services.securityServices;

import org.springframework.stereotype.Service;

@Service
public class UserSecurityService extends BasicSecurityService {

    public boolean hasPermissionToUserManagement() {
        return getActiveUserRole().isAdmin();
    }

    public boolean hasPermissionToViewUserProfile() {
        return isAuthenticated();
    }

}
