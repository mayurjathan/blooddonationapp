package com.example.blooddonationapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {

    EditText etTitle, etMessage, etBloodGroup;
    Button btnSendNotification;
    RecyclerView rvNotifications;
    NotificationAdapter adapter;
    ArrayList<NotificationModel> notificationList;

    DatabaseReference notificationsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        etTitle = findViewById(R.id.etTitle);
        etMessage = findViewById(R.id.etMessage);
        etBloodGroup = findViewById(R.id.etBloodGroup);
        btnSendNotification = findViewById(R.id.btnSendNotification);
        rvNotifications = findViewById(R.id.rvNotifications);

        notificationsRef = FirebaseDatabase.getInstance().getReference("notifications");

        btnSendNotification.setOnClickListener(v -> sendNotificationToFirebase());

        notificationList = new ArrayList<>();
        adapter = new NotificationAdapter(notificationList, this::deleteNotification);

        rvNotifications.setLayoutManager(new LinearLayoutManager(this));
        rvNotifications.setAdapter(adapter);

        loadNotifications();
    }

    private void sendNotificationToFirebase() {
        String title = etTitle.getText().toString().trim();
        String message = etMessage.getText().toString().trim();
        String bloodGroup = etBloodGroup.getText().toString().trim();

        if (title.isEmpty() || message.isEmpty() || bloodGroup.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        String notificationId = notificationsRef.push().getKey();

        Map<String, Object> notificationData = new HashMap<>();
        notificationData.put("title", title);
        notificationData.put("message", message);
        notificationData.put("bloodGroup", bloodGroup);
        notificationData.put("timestamp", System.currentTimeMillis());

        assert notificationId != null;
        notificationsRef.child(notificationId).setValue(notificationData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Notification sent", Toast.LENGTH_SHORT).show();
                    etTitle.setText(""); etMessage.setText(""); etBloodGroup.setText("");
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to send", Toast.LENGTH_SHORT).show());
    }

    private void loadNotifications() {
        notificationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notificationList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    NotificationModel model = snap.getValue(NotificationModel.class);
                    if (model != null) {
                        model.setId(snap.getKey());
                        notificationList.add(model);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void deleteNotification(String id) {
        notificationsRef.child(id).removeValue()
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Notification deleted", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show());
    }
}
