package com.example.blooddonationapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private final ArrayList<NotificationModel> list;
    private final OnDeleteClickListener deleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(String id);
    }

    public NotificationAdapter(ArrayList<NotificationModel> list, OnDeleteClickListener listener) {
        this.list = list;
        this.deleteClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, message, bloodGroup;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            message = itemView.findViewById(R.id.tvMessage);
            bloodGroup = itemView.findViewById(R.id.tvBloodGroup);
        }
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationModel model = list.get(position);
        holder.title.setText("Title: " + model.getTitle());
        holder.message.setText("Message: " + model.getMessage());
        holder.bloodGroup.setText("Blood Group: " + model.getBloodGroup());

        holder.itemView.setOnLongClickListener(v -> {
            deleteClickListener.onDeleteClick(model.getId());
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
