package com.example.sarest.API.models;

public class Grade {
    public int id;
    public int teacher_subjectId;
    public int criterionId;
    public int grade;

    public Grade(int id, int teacher_subjectId, int criterionId, int grade)
    {
        this.id = id;
        this.teacher_subjectId = teacher_subjectId;
        this.criterionId = criterionId;
        this.grade = grade;
    }
}
