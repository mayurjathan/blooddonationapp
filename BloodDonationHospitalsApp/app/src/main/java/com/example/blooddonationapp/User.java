package com.example.blooddonationapp;

public class User {
    public String id;
    public String name;
    public int age;
    public String bloodGroup;
    public String location;
    public String phone;
    public String email;
    public String userType;

    // Default constructor for Firebase
    public User() {}

    // Optional: constructor if you want to create User manually
    public User(String id, String name, int age, String bloodGroup, String location,
                String phone, String email, String userType) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.userType = userType;
    }
}
