package com.example.blooddonationapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.database.DatabaseReference;
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
    private String bloodGroup;

    private Handler handler = new Handler();
    private Runnable fetchNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_user_details);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        // Get email from intent
        userEmail = getIntent().getStringExtra("email");

        setupViewPager();
        fetchUserBloodGroup(); // Start fetching blood group
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

    private void fetchUserBloodGroup() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

        usersRef.orderByChild("email").equalTo(userEmail)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot userSnap : snapshot.getChildren()) {
                            bloodGroup = userSnap.child("bloodGroup").getValue(String.class);
                            if (bloodGroup != null) {
                                startPollingNotifications();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Error getting blood group", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void startPollingNotifications() {
        fetchNotifications = new Runnable() {
            @Override
            public void run() {
                DatabaseReference notiRef = FirebaseDatabase.getInstance().getReference("notifications");

                notiRef.orderByChild("bloodGroup").equalTo(bloodGroup)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    String title = snap.child("title").getValue(String.class);
                                    String message = snap.child("message").getValue(String.class);

                                    if (title != null && message != null) {
                                        Toast.makeText(getApplicationContext(), title + ": " + message, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });

                handler.postDelayed(this, 5000); // Run again after 5 seconds
            }
        };

        handler.post(fetchNotifications); // Start polling
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(fetchNotifications); // Stop polling when screen is destroyed
    }
}