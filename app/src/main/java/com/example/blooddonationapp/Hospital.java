package com.example.blooddonationapp;

public class Hospital {
    private String name;
    private String contact;

    public Hospital(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }
}

