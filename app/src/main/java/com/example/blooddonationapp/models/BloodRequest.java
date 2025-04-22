package com.example.blooddonationapp.models;

public class BloodRequest {
    private String bloodGroup;
    private int quantity;

    public BloodRequest() {
        // Required for Firebase
    }

    public BloodRequest(String bloodGroup, int quantity) {
        this.bloodGroup = bloodGroup;
        this.quantity = quantity;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public int getQuantity() {
        return quantity;
    }
}
