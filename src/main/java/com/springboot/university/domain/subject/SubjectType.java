package com.springboot.university.domain.subject;

public enum SubjectType {
    MAJOR("전공"),
    ELECTIVE("교양");

    private final String label;

    SubjectType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
