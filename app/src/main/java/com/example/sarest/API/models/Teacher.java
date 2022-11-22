package com.example.sarest.API.models;

public class Teacher {
    // Don't have an ID, cause of OneToOneField with "User" model
    public int user;

    public Teacher(int user)
    {
        this.user = user;
    }
}
