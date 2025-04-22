package com.example.blooddonationapp;

public class Request {
    public String id;
    public String bloodGroup;
    public int quantity;

    public Request() {
        // Default constructor required for calls to DataSnapshot.getValue(Request.class)
    }

    public Request(String bloodGroup, int quantity) {
        this.bloodGroup = bloodGroup;
        this.quantity = quantity;
    }
}

