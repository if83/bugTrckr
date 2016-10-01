package com.softserverinc.edu.entities.enums;

public enum IssuePriority {
    LOW("Low"), MEDIUM("Medium"), HIGH("High"), CRITICAL("Critical"), BLOCKER("Blocker");

    private String status;

    IssuePriority(String role) {
        this.status = role;
    }

    @Override
    public String toString() {
        return status;
    }
}
