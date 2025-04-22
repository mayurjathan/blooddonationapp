package com.example.blooddonationapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonationapp.R;
import com.example.blooddonationapp.adapters.BloodRequestAdapter;
import com.example.blooddonationapp.models.BloodRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CurrentRequestsFragment extends Fragment {

    private RecyclerView recyclerView;
    private BloodRequestAdapter adapter;
    private List<BloodRequest> requestList;

    public CurrentRequestsFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_requests, container, false);

        recyclerView = view.findViewById(R.id.current_requests_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        requestList = new ArrayList<>();
        adapter = new BloodRequestAdapter(requestList);
        recyclerView.setAdapter(adapter);

        fetchRequests();

        return view;
    }

    private void fetchRequests() {
        FirebaseDatabase.getInstance().getReference("requests")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        requestList.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            BloodRequest request = child.getValue(BloodRequest.class);
                            if (request != null) {
                                requestList.add(request);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Firebase", "Error fetching requests", error.toException());
                    }
                });
    }
}
