package com.example.blooddonationapp;

public class BloodRequest {
    private String bloodGroup;
    private int quantity;

    public BloodRequest() {
        // Default constructor required for calls to DataSnapshot.getValue(BloodRequest.class)
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
