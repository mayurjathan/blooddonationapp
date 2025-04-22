package com.example.blooddonationapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserDetailsActivity extends AppCompatActivity {

    TextView tvName, tvAge, tvBloodGroup, tvLocation, tvPhone, tvEmail, tvLastDonation, tvDonationHistory;
    Button btnDonate;
    DatabaseReference usersRef, fulfilledRef;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        // Initialize views
        tvName = findViewById(R.id.tvName);
        tvAge = findViewById(R.id.tvAge);
        tvBloodGroup = findViewById(R.id.tvBloodGroup);
        tvLocation = findViewById(R.id.tvLocation);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        //tvDonationHistory = findViewById(R.id.tvDonationHistory); // for future use

        btnDonate = findViewById(R.id.btnDonate); // Add this button in layout

        // Get user ID from intent
        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");

        // Firebase refs
        usersRef = FirebaseDatabase.getInstance().getReference("users");
        fulfilledRef = FirebaseDatabase.getInstance().getReference("fulfilled_requests");

        if (userId != null) {
            loadUserDetails(userId);
        }

        btnDonate.setOnClickListener(v -> showDonationDialog());
    }

    private void loadUserDetails(String userId) {
        usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue(String.class);
                    Long age = snapshot.child("age").getValue(Long.class);
                    String bloodGroup = snapshot.child("bloodGroup").getValue(String.class);
                    String location = snapshot.child("location").getValue(String.class);
                    String phone = snapshot.child("phone").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String lastDonation = snapshot.child("lastDonationDate").getValue(String.class);

                    tvName.setText("Name: " + name);
                    tvAge.setText("Age: " + (age != null ? age : ""));
                    tvBloodGroup.setText("Blood Group: " + bloodGroup);
                    tvLocation.setText("Location: " + location);
                    tvPhone.setText("Phone: " + phone);
                    tvEmail.setText("Email: " + email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserDetailsActivity.this, "Failed to load user details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDonationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserDetailsActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_donation, null);
        EditText etName = dialogView.findViewById(R.id.etDonorName);
        EditText etQuantity = dialogView.findViewById(R.id.etDonationQty);
        builder.setView(dialogView);
        builder.setTitle("Record Donation");

        builder.setPositiveButton("Submit", (dialog, which) -> {
            String name = etName.getText().toString().trim();
            String qtyStr = etQuantity.getText().toString().trim();

            if (!name.isEmpty() && !qtyStr.isEmpty()) {
                int qty = Integer.parseInt(qtyStr);

                // Push to Firebase under userId
                DatabaseReference newEntry = fulfilledRef.push();
                newEntry.child("userId").setValue(userId);
                newEntry.child("email").setValue(name);
                newEntry.child("quantity").setValue(qty);
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                newEntry.child("date").setValue(currentDate);


                Toast.makeText(this, "Donation recorded", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
