package com.springboot.university.domain.lecture;

public enum Classroom {
    ENGINEERING_HALL("공학관"),
    GLOBAL_BUSINESS_HALL("경영관"),
    NATURAL_SCIENCE_HALL("과학관"),
    MAIN_ACADEMIC_HALL("종합강의동");

    private final String label;

    Classroom(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
