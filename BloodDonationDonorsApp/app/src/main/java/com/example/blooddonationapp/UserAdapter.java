package com.example.blooddonationapp;

import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.blooddonationapp.R;
import com.example.blooddonationapp.UserDetailsActivity;
import com.example.blooddonationapp.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context context;
    private List<User> userList;

    public UserAdapter(Context ctx, List<User> list) {
        this.context = ctx;
        this.userList = list;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView name, bloodGroup, location;
        LinearLayout container;

        public UserViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            bloodGroup = itemView.findViewById(R.id.tvBloodGroup);
            location = itemView.findViewById(R.id.tvLocation);
            container = itemView.findViewById(R.id.userContainer);
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        final User u = userList.get(position);
        holder.name.setText(u.name);
        holder.bloodGroup.setText(u.bloodGroup);
        holder.location.setText(u.location);

        holder.container.setOnClickListener(v -> {
            Intent intent = new Intent(context, UserDetailsActivity.class);
            intent.putExtra("user_id", String.valueOf(u.id));
            context.startActivity(intent);
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, UserDetailsActivity.class);
            intent.putExtra("user_id", String.valueOf(u.id));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
