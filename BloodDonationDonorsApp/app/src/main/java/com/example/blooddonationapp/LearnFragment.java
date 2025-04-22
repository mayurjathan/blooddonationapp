package com.example.blooddonationapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class LearnFragment extends Fragment {

    public LearnFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learn, container, false);

        // Example: Toggle logic for showing/hiding content (you can repeat for all)
        TextView donationContent = view.findViewById(R.id.donation_content);
        CardView donationCard = view.findViewById(R.id.card_donation);

        donationCard.setOnClickListener(v -> {
            if (donationContent.getVisibility() == View.GONE) {
                donationContent.setVisibility(View.VISIBLE);
            } else {
                donationContent.setVisibility(View.GONE);
            }
        });

        TextView bloodLossContent = view.findViewById(R.id.blood_loss_content);
        CardView bloodLossCard = view.findViewById(R.id.card_tests);

        bloodLossCard.setOnClickListener(v -> {
            if (bloodLossContent.getVisibility() == View.GONE) {
                bloodLossContent.setVisibility(View.VISIBLE);
            } else {
                bloodLossContent.setVisibility(View.GONE);
            }
        });

        TextView bloodTestContent = view.findViewById(R.id.blood_test_content);
        CardView bloodTestCard = view.findViewById(R.id.card_firstaid);

        bloodTestCard.setOnClickListener(v -> {
            if (bloodTestContent.getVisibility() == View.GONE) {
                bloodTestContent.setVisibility(View.VISIBLE);
            } else {
                bloodTestContent.setVisibility(View.GONE);
            }
        });

        return view;
    }
}

