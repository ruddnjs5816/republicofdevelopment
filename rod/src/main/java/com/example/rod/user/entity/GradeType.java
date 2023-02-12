package com.example.rod.user.entity;

public enum GradeType {

    BRONZE(Grade.BRONZE),
    SILVER(Grade.SILVER),
    GOLD(Grade.GOLD),
    PLATINUM(Grade.PLATINUM),
    DIAMOND(Grade.DIAMOND),
    MASTER(Grade.MASTER),
    GRANDMASTER(Grade.GRANDMASTER);

    private final String grade;

    GradeType(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }

    public static class Grade{
        public static final String BRONZE = "GRADE_BRONZE";
        public static final String SILVER = "GRADE_SILVER";
        public static final String GOLD = "GRADE_GOLD";
        public static final String PLATINUM = "GRADE_PLATINUM";
        public static final String DIAMOND = "GRADE_DIAMOND";
        public static final String MASTER = "GRADE_MASTER";
        public static final String GRANDMASTER = "GRADE_GRANDMASTER";
    }
}
