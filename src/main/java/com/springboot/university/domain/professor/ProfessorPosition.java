package com.springboot.university.domain.professor;

public enum ProfessorPosition {
    PROFESSOR("정교수"),
    ASSOCIATE_PROFESSOR("부교수"),
    ASSISTANT_PROFESSOR("조교수"),
    LECTURER("강사");

    private final String label;

    ProfessorPosition(String label) {
        this.label = label;
    }
}
