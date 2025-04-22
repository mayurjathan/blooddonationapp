package com.example.blooddonationapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonationapp.R;
import com.example.blooddonationapp.models.BloodRequest;

import java.util.List;

public class BloodRequestAdapter extends RecyclerView.Adapter<BloodRequestAdapter.ViewHolder> {

    private List<BloodRequest> bloodRequestList;

    public BloodRequestAdapter(List<BloodRequest> list) {
        this.bloodRequestList = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bloodType, quantity;

        public ViewHolder(View itemView) {
            super(itemView);
            bloodType = itemView.findViewById(R.id.blood_group_text);
            quantity = itemView.findViewById(R.id.quantity_text);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_blood_request1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BloodRequest item = bloodRequestList.get(position);
        holder.bloodType.setText("Blood Group: " + item.getBloodGroup());
        holder.quantity.setText("Quantity: " + item.getQuantity());
    }

    @Override
    public int getItemCount() {
        return bloodRequestList.size();
    }
}
