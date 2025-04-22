package com.example.blooddonationapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FAQsFragment extends Fragment {

    public FAQsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faqs, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.faq_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<FAQ> faqList = new ArrayList<>();
        faqList.add(new FAQ("Can I donate if I have a tattoo?", "Yes, if it's been more than 6 months since you got it."));
        faqList.add(new FAQ("Is blood donation painful?", "Only a quick pinch is felt. It’s mostly painless."));
        faqList.add(new FAQ("Can vegetarians donate blood?", "Yes. A vegetarian diet doesn't affect eligibility."));
        faqList.add(new FAQ("Will donating blood make me weak?", "No. Your body replenishes blood quickly."));

        FAQsAdapter adapter = new FAQsAdapter(faqList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
