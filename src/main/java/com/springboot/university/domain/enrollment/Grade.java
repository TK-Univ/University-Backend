package com.springboot.university.domain.enrollment;

public enum Grade {
    F("F"),
    C_MINUS("C-"),
    C("C"),
    C_PLUS("C+"),
    B_MINUS("B-"),
    B("B"),
    B_PLUS("B+"),
    A_MINUS("A-"),
    A("A"),
    A_PLUS("A+");

    private final String label;

    Grade(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
