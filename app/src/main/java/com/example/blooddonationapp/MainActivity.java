package com.example.blooddonationapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonationapp.activities.MainActivity2;

public class MainActivity extends AppCompatActivity {

    Button guestButton, hospitalButton, userButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guestButton = findViewById(R.id.btn_guest);
        hospitalButton = findViewById(R.id.btn_hospital);
        userButton = findViewById(R.id.btn_user);

        guestButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MainActivity1.class)));
        hospitalButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MainActivity3.class)));
        userButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MainActivity2.class)));
    }
}
