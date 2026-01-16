package com.springboot.university.domain.staff;

public enum StaffAuthority {
    ADMIN("관리자"),
    GENERAL("일반");

    private final String label;

    StaffAuthority(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
