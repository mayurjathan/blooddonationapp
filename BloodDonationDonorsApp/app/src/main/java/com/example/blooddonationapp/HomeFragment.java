package com.example.blooddonationapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView bloodRequestRecycler, hospitalRecycler;
    private List<BloodRequest> bloodRequests;
    private BloodRequestAdapter bloodRequestAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        bloodRequestRecycler = view.findViewById(R.id.blood_request_recycler);
        hospitalRecycler = view.findViewById(R.id.hospital_recycler);

        bloodRequestRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        hospitalRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize list and adapter for blood requests
        bloodRequests = new ArrayList<>();
        bloodRequestAdapter = new BloodRequestAdapter(bloodRequests);
        bloodRequestRecycler.setAdapter(bloodRequestAdapter);

        // Fetch real-time data from Firebase
        fetchBloodRequests();

        // Hardcoded hospital data
        List<Hospital> hospitals = new ArrayList<>();
        hospitals.add(new Hospital("KMC", "+91 91405-05210"));
//        hospitals.add(new Hospital("Adarsha Hospital", "+91 97418-61754"));

        hospitalRecycler.setAdapter(new HospitalAdapter(hospitals));

        return view;
    }

    private void fetchBloodRequests() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance()
                .getReference("requests");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bloodRequests.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    BloodRequest request = childSnapshot.getValue(BloodRequest.class);
                    if (request != null) {
                        bloodRequests.add(request);
                    }
                }
                bloodRequestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to read requests", error.toException());
            }
        });
    }
}
