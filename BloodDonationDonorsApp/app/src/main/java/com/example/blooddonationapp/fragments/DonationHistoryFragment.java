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
import com.example.blooddonationapp.adapters.DonationHistoryAdapter;
import com.example.blooddonationapp.models.DonationRecord;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DonationHistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private DonationHistoryAdapter adapter;
    private List<DonationRecord> donationList;

    private String userEmail;

    public DonationHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donation_history, container, false);

        recyclerView = view.findViewById(R.id.donation_history_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        donationList = new ArrayList<>();
        adapter = new DonationHistoryAdapter(donationList);
        recyclerView.setAdapter(adapter);

        if (getActivity() != null && getActivity().getIntent() != null) {
            userEmail = getActivity().getIntent().getStringExtra("email");
            fetchDonationHistory(userEmail);
        }

        return view;
    }

    private void fetchDonationHistory(String email) {
        FirebaseDatabase.getInstance().getReference("fulfilled_requests")
                .orderByChild("email").equalTo(email)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        donationList.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            DonationRecord record = child.getValue(DonationRecord.class);
                            if (record != null) {
                                donationList.add(record);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Firebase", "Error fetching donation history", error.toException());
                    }
                });
    }
}
