package com.example.blooddonationapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonationapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity2 extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        emailEditText = findViewById(R.id.email_input);
        passwordEditText = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_btn);

        loginButton.setOnClickListener(v -> {
            String inputEmail = emailEditText.getText().toString().trim();
            String inputPassword = passwordEditText.getText().toString().trim();

            if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Navigate through all user IDs
            FirebaseDatabase.getInstance().getReference("users")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean found = false;

                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                String email = userSnapshot.child("email").getValue(String.class);
                                String password = userSnapshot.child("password").getValue(String.class);

                                if (email != null && email.equals(inputEmail)) {
                                    if (password != null && password.equals(inputPassword)) {
                                        found = true;
                                        // Login success
                                        Intent intent = new Intent(MainActivity2.this, RegisteredUserDetails.class);
                                        intent.putExtra("email", inputEmail);
                                        intent.putExtra("password", inputPassword);
                                        startActivity(intent);
                                        finish();
                                        break;
                                    } else {
                                        Toast.makeText(MainActivity2.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            }

                            if (!found) {
                                Toast.makeText(MainActivity2.this, "Email not found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity2.this, "Database error", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}