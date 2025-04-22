package com.example.blooddonationapp;

import android.content.Context;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.blooddonationapp.R;
import com.example.blooddonationapp.Request;

import java.util.List;

public class RequestAdapter extends BaseAdapter {
    Context context;
    List<Request> requestList;

    public RequestAdapter(Context context, List<Request> list) {
        this.context = context;
        this.requestList = list;
    }

    @Override
    public int getCount() {
        return requestList.size();
    }

    @Override
    public Object getItem(int i) {
        return requestList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i; // Firebase keys are strings, so using index is safer here
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.request_item, parent, false);
        TextView tvBloodGroup = view.findViewById(R.id.tvBloodGroup);
        TextView tvQuantity = view.findViewById(R.id.tvQuantity);

        Request r = requestList.get(i);
        tvBloodGroup.setText("Group: " + r.bloodGroup);
        tvQuantity.setText("Quantity: " + r.quantity + " units");
        return view;
    }
}
