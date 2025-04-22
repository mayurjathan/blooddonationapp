package com.example.blooddonationapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BloodRequestAdapter extends RecyclerView.Adapter<BloodRequestAdapter.ViewHolder> {

    private List<BloodRequest> list;

    public BloodRequestAdapter(List<BloodRequest> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bloodType, hospital, urgency;

        public ViewHolder(View itemView) {
            super(itemView);
            bloodType = itemView.findViewById(R.id.blood_type);
            hospital = itemView.findViewById(R.id.hospital_name);
            urgency = itemView.findViewById(R.id.urgency_info);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blood_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BloodRequest item = list.get(position);
        holder.bloodType.setText("Blood Group: " + item.getBloodGroup());
        holder.hospital.setText("Quantity: " + item.getQuantity());
        holder.urgency.setText("Status: Requested");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

