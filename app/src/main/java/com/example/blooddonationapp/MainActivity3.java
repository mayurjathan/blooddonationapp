package com.example.blooddonationapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonationapp.DBHelper;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity3 extends AppCompatActivity {

    Button btnUsers, btnRequests, btnNotifications;
    DBHelper dbHelper;
    String[] userIds = {"user1Id", "user2Id"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main3);

        dbHelper = new DBHelper();
        //subscribeUsersToTopics();

        // Call this only once to avoid re-adding users on every launch
        //dbHelper.addDummyUsers();

        btnUsers = findViewById(R.id.btnUsers);
        btnRequests = findViewById(R.id.btnRequests);
        btnNotifications = findViewById(R.id.btnNotifications);

        btnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // For now, direct to StaffManagerActivity
                Intent intent = new Intent(MainActivity3.this, RegisteredUsersActivity.class);
                startActivity(intent);
            }
        });

        btnRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Placeholder – redirect to registered user screen in future
                // For testing, you could even use RegisteredUsersActivity here
                Intent intent = new Intent(MainActivity3.this, RequestManagerActivity.class);
                startActivity(intent);
            }
        });
//
//        btnNotifications.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Placeholder – redirect to registered user screen in future
//                // For testing, you could even use RegisteredUsersActivity here
//                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void subscribeUsersToTopics() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

        for (String id : userIds) {
            usersRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String bloodGroup = snapshot.child("bloodGroup").getValue(String.class);
//                    if (bloodGroup != null) {
//                        String topic = "bloodgroup_" + bloodGroup.replace("+", "plus").replace("-", "minus");
//
//                        FirebaseMessaging.getInstance().subscribeToTopic(topic)
//                                .addOnCompleteListener(task -> {
//                                    if (task.isSuccessful()) {
//                                        Log.d("FCM", id + " subscribed to: " + topic);
//                                    } else {
//                                        Log.e("FCM", "Subscription failed for " + id);
//                                    }
//                                });
//                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Firebase", "Error loading user data for " + id);
                }
            });
        }
    }
}
