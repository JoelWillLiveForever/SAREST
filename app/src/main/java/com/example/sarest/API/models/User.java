package com.example.sarest.API.models;

public class User {
    public int id;
    public String email;
    public String surname;
    public String name;
    public String lastname;

    public User(int id, String email, String surname, String name, String lastname)
    {
        this.id = id;
        this.email = email;
        this.surname = surname;
        this.name = name;
        this.lastname = lastname;
    }
}
