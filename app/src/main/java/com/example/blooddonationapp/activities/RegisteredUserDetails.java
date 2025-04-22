package com.example.blooddonationapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.blooddonationapp.R;
import com.example.blooddonationapp.fragments.CurrentRequestsFragment;
import com.example.blooddonationapp.fragments.DonationHistoryFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class RegisteredUserDetails extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;

    private String userEmail;
    private String userId;
    private String bloodGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_user_details);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        setupViewPager();

        // Get email from Intent
//        Intent intent = getIntent();
//        userEmail = intent.getStringExtra("email");
//
//        if (userEmail != null) {
//            fetchUserDataAndSubscribe(userEmail);
//        } else {
//            Toast.makeText(this, "User email not found", Toast.LENGTH_SHORT).show();
//        }
    }

    private void setupViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new DonationHistoryFragment());
        fragments.add(new CurrentRequestsFragment());

        adapter = new ViewPagerAdapter(this, fragments);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) tab.setText("Donation History");
                    else tab.setText("Current Requests");
                }).attach();
    }

    private void fetchUserDataAndSubscribe(String email) {
        FirebaseDatabase.getInstance().getReference("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            String dbEmail = userSnapshot.child("email").getValue(String.class);
                            if (dbEmail != null && dbEmail.equals(email)) {
                                userId = userSnapshot.getKey();  // Get user ID
                                bloodGroup = userSnapshot.child("bloodGroup").getValue(String.class);

                                if (userId != null && bloodGroup != null) {
                                    subscribeToBloodGroupTopic(userId, bloodGroup);
                                } else {
                                    Toast.makeText(RegisteredUserDetails.this, "User data incomplete", Toast.LENGTH_SHORT).show();
                                }
                                return;
                            }
                        }
                        Toast.makeText(RegisteredUserDetails.this, "User not found", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(RegisteredUserDetails.this, "Database error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void subscribeToBloodGroupTopic(String userId, String bloodGroup) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String token = task.getResult();
                        Log.d("FCM", "Token: " + token);

                        // Save token to Firebase DB
                        FirebaseDatabase.getInstance().getReference("users")
                                .child(userId).child("fcmToken").setValue(token);
                    } else {
                        Log.w("FCM", "Fetching FCM token failed", task.getException());
                    }
                });

        String topic = "bloodgroup_" + bloodGroup.replace("+", "plus").replace("-", "minus");

        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("FCM", "Subscribed to topic: " + topic);
                    } else {
                        Log.w("FCM", "Subscription to topic failed", task.getException());
                    }
                });
    }
}
