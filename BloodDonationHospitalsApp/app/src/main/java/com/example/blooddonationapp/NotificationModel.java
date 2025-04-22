package com.example.blooddonationapp;
public class NotificationModel {
    private String id;
    private String title;
    private String message;
    private String bloodGroup;
    private long timestamp;

    public NotificationModel() {} // Firebase needs empty constructor

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getBloodGroup() { return bloodGroup; }
    public long getTimestamp() { return timestamp; }
}
