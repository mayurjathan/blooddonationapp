package com.example.blooddonationapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DBHelper {

    public void addDummyUsers() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        // User 1
        String user1Id = "user1Id";
        Map<String, Object> user1 = new HashMap<>();
        user1.put("id", user1Id);
        user1.put("name", "Siddharth Agrawal");
        user1.put("password", "pass1");
        user1.put("age", 21);
        user1.put("bloodGroup", "O+");
        user1.put("location", "Manipal");
        user1.put("phone", "1234567890");
        user1.put("email", "sid@example.com");

        usersRef.child(user1Id).setValue(user1);

        // User 2
        String user2Id = "user2Id";
        Map<String, Object> user2 = new HashMap<>();
        user2.put("id", user2Id);
        user2.put("name", "Mayur Jathan");
        user2.put("password", "pass2");
        user2.put("age", 30);
        user2.put("bloodGroup", "B-");
        user2.put("location", "Udupi");
        user2.put("phone", "0987654321");
        user2.put("email", "mayur@example.com");

        usersRef.child(user2Id).setValue(user2);
    }
}
