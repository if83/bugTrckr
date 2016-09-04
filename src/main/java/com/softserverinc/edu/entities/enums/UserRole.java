package com.softserverinc.edu.entities.enums;

public enum UserRole {
    ROLE_ADMIN, ROLE_PROJECT_MANAGER, ROLE_DEVELOPER, ROLE_QA, ROLE_USER, ROLE_GUEST;

    public boolean isAdmin() {
        return this == ROLE_ADMIN;
    }

    public boolean isProjectManager() {
        return this == ROLE_PROJECT_MANAGER;
    }

    public boolean isDeveloper() {
        return this == ROLE_DEVELOPER;
    }

    public boolean isQA() {
        return this == ROLE_QA;
    }

    public boolean isUser() {
        return this == ROLE_USER;
    }

    public boolean isGuest() {
        return this == ROLE_GUEST;
    }
}
