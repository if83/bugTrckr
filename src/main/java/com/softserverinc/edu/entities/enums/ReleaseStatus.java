package com.softserverinc.edu.entities.enums;

public enum ReleaseStatus {
    OPEN("Open"), IN_PROGRESS("In progress"), CLOSED("Closed");

    private String status;

    ReleaseStatus(String role) {
        this.status = role;
    }

    @Override
    public String toString() {
        return status;
    }
}
