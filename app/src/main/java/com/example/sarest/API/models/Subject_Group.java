package com.example.sarest.API.models;

public class Subject_Group {
    public int id;
    public int subjectId;
    public int groupId;

    public Subject_Group(int id, int subjectId, int groupId)
    {
        this.id = id;
        this.subjectId = subjectId;
        this.groupId = groupId;
    }
}
