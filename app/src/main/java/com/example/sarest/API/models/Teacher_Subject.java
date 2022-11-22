package com.example.sarest.API.models;

public class Teacher_Subject {
    public int id;
    public int teacherId;
    public int subjectId;

    public Teacher_Subject(int id, int teacherId, int subjectId)
    {
        this.id = id;
        this.teacherId = teacherId;
        this.subjectId = subjectId;
    }
}
