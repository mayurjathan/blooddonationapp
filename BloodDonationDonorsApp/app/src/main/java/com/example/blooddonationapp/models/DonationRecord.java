package com.example.blooddonationapp.models;

public class DonationRecord {
    private int quantity;
    private String date;

    public DonationRecord() {
        // Required for Firebase
    }

    public DonationRecord(int quantity, String date) {
        this.quantity = quantity;
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }
}
