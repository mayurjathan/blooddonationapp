package com.example.blooddonationapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestManagerActivity extends AppCompatActivity {

    ListView listView;
    Button btnAddRequest;
    ArrayList<Request> requestList = new ArrayList<>();
    RequestAdapter adapter;
    DatabaseReference requestRef;
    int selectedItemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_manager);

        listView = findViewById(R.id.requestListView);
        btnAddRequest = findViewById(R.id.btnAddRequest);

        requestRef = FirebaseDatabase.getInstance().getReference("requests");

        adapter = new RequestAdapter(this, requestList);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        loadRequests();

        btnAddRequest.setOnClickListener(v -> showRequestDialog(null));

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            selectedItemPosition = position;
            return false;
        });
    }

    private void loadRequests() {
        requestList.clear();
        requestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requestList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Request req = snap.getValue(Request.class);
                    if (req != null) {
                        req.id = snap.getKey(); // Store Firebase key
                        requestList.add(req);
                    }
                }
                adapter.notifyDataSetChanged();
                Log.d("TAG", "Requests pulled");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RequestManagerActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showRequestDialog(Request request) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_edit_request, null);
        EditText etGroup = view.findViewById(R.id.etBloodGroup);
        EditText etQuantity = view.findViewById(R.id.etQuantity);
        builder.setView(view);

        if (request != null) {
            etGroup.setText(request.bloodGroup);
            etQuantity.setText(String.valueOf(request.quantity));
            builder.setTitle("Edit Request");
        } else {
            builder.setTitle("Add Request");
        }

        builder.setPositiveButton("Save", (dialog, which) -> {
            String group = etGroup.getText().toString();
            int qty = Integer.parseInt(etQuantity.getText().toString());

            HashMap<String, Object> data = new HashMap<>();
            data.put("bloodGroup", group);
            data.put("quantity", qty);

            if (request == null) {
                requestRef.push().setValue(data);
            } else {
                requestRef.child(request.id).updateChildren(data);
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Manage Request");
        menu.add(0, 1, 0, "Edit");
        menu.add(0, 2, 1, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Request selectedRequest = requestList.get(selectedItemPosition);
        if (selectedRequest == null) return true;

        if (item.getItemId() == 1) {
            showRequestDialog(selectedRequest);
        } else if (item.getItemId() == 2) {
            requestRef.child(selectedRequest.id).removeValue();
        }
        return true;
    }
}
