package com.example.blooddonationapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonationapp.R;
import com.example.blooddonationapp.UserAdapter;
import com.example.blooddonationapp.DBHelper;
import com.example.blooddonationapp.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegisteredUsersActivity extends AppCompatActivity {

    EditText searchInput;
    Spinner bloodGroupSpinner, locationSpinner;
    RecyclerView recyclerView;
    UserAdapter adapter;
    List<User> fullUserList = new ArrayList<>();
    List<User> filteredList = new ArrayList<>();
    DBHelper dbHelper;
    SQLiteDatabase db;

    String[] bloodGroups = {"All", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
    String[] locations = {"All", "Udupi", "Manipal", "Parkala", "Kundapur"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_users);

        searchInput = findViewById(R.id.etSearch);
        bloodGroupSpinner = findViewById(R.id.spinnerBloodGroup);
        locationSpinner = findViewById(R.id.spinnerLocation);
        recyclerView = findViewById(R.id.recyclerViewUsers);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Users");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(this, filteredList);
        recyclerView.setAdapter(adapter);

        loadUsers();
        setupFilters();
    }

    private void loadUsers() {
        fullUserList.clear();

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            User user = userSnapshot.getValue(User.class);
                            if (user != null) {
                                fullUserList.add(user);
                            }
                        }

                        // Refresh the RecyclerView
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Error loading users", Toast.LENGTH_SHORT).show();
                    }
                });
        applyFilters();
    }



    private void setupFilters() {
        ArrayAdapter<String> bloodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bloodGroups);
        bloodGroupSpinner.setAdapter(bloodAdapter);

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        locationSpinner.setAdapter(locationAdapter);

        bloodGroupSpinner.setOnItemSelectedListener(new FilterChangeListener());
        locationSpinner.setOnItemSelectedListener(new FilterChangeListener());

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilters();
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void applyFilters() {
        String search = searchInput.getText().toString().toLowerCase();
        String selectedBlood = bloodGroupSpinner.getSelectedItem() != null ? bloodGroupSpinner.getSelectedItem().toString() : "All";  // Default to "All" if null
        String selectedLocation = locationSpinner.getSelectedItem() != null ? locationSpinner.getSelectedItem().toString() : "All";  // Default to "All" if null

        filteredList.clear();
        for (User user : fullUserList) {
            boolean matchesSearch = user.name.toLowerCase().contains(search);
            boolean matchesBlood = selectedBlood.equals("All") || user.bloodGroup.equals(selectedBlood);
            boolean matchesLocation = selectedLocation.equals("All") || user.location.equals(selectedLocation);

            if (matchesSearch && matchesBlood && matchesLocation) {
                filteredList.add(user);
            }
        }
        adapter.notifyDataSetChanged();
    }


    private class FilterChangeListener implements AdapterView.OnItemSelectedListener {
        @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            applyFilters();
        }
        @Override public void onNothingSelected(AdapterView<?> parent) {}
    }
}
