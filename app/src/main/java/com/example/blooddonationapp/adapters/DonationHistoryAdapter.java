package com.example.blooddonationapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonationapp.R;
import com.example.blooddonationapp.models.DonationRecord;

import java.util.List;

public class DonationHistoryAdapter extends RecyclerView.Adapter<DonationHistoryAdapter.ViewHolder> {

    private List<DonationRecord> donationList;

    public DonationHistoryAdapter(List<DonationRecord> list) {
        this.donationList = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView quantityText, dateText;

        public ViewHolder(View itemView) {
            super(itemView);
            quantityText = itemView.findViewById(R.id.donation_quantity_text);
            dateText = itemView.findViewById(R.id.donation_date_text);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_donation_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonationRecord record = donationList.get(position);
        holder.quantityText.setText("Quantity: " + record.getQuantity());
        holder.dateText.setText("Date: " + record.getDate());
    }

    @Override
    public int getItemCount() {
        return donationList.size();
    }
}
