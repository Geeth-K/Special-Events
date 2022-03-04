package com.geethanjali.specialevents;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView specialEventsRV;
    // Arraylist for storing data
    private ArrayList<SpecialEventModel> specialEventModelArrayList;
    ActivityResultLauncher<Intent> createEventActivityResultLauncher = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        specialEventsRV = findViewById(R.id.idSpecialEvents);

        specialEventModelArrayList = new ArrayList<>();

        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Success", document.getId() + " => " + document.getData());

                            Map<String, Object> user = document.getData();
                            String name = user.get("event_name").toString();
                            String date = user.get("event_date").toString();
                            String venue = user.get("event_venue").toString();
                            String desc = user.get("event_desc").toString();

                            if (specialEventModelArrayList != null) {
                                specialEventModelArrayList.add(new SpecialEventModel(name, date, venue, desc));
                            } else {
                                //TODO: Display error message
                            }
                        }

                        if (specialEventsRV != null) {
                            specialEventsRV.getAdapter().notifyDataSetChanged();
                        }
                    } else {
                        Log.w("Failure", "Error getting documents.", task.getException());
                    }
                });

        SpecialEventsAdapter specialEventsAdapter = new SpecialEventsAdapter(this, specialEventModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        specialEventsRV.setLayoutManager(linearLayoutManager);
        specialEventsRV.setAdapter(specialEventsAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            //Launch activity to create a new event
            launchCreateEventActivity();
        });

        // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
        createEventActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        //TODO: Store the date in the database and also add a card with the new event
                        Intent data = result.getData();
                        if(data != null) {
                            String name = data.getStringExtra("event_name");
                            String date = data.getStringExtra("event_date");
                            String venue = data.getStringExtra("event_venue");
                            String desc = data.getStringExtra("event_desc");

                            // Create a new user with a first and last name
                            Map<String, Object> user = new HashMap<>();
                            user.put("event_name", name);
                            user.put("event_date", date);
                            user.put("event_venue", venue);
                            user.put("event_desc", desc);

                            // Add a new document with a generated ID
                            db.collection("users")
                                    .add(user)
                                    .addOnSuccessListener(documentReference -> {
                                        Log.d("Success", "DocumentSnapshot added with ID: " + documentReference.getId());
                                        if (specialEventsRV != null) {
                                            specialEventModelArrayList.add(new SpecialEventModel(name, date, venue, desc));
                                            specialEventsRV.getAdapter().notifyDataSetChanged();
                                        } else {
                                            //TODO: Display error message
                                        }
                                    })
                                    .addOnFailureListener(e -> Log.w("Failure", "Error adding document", e));
                        }
                    }
                });
    }

    private void launchCreateEventActivity() {
        Intent intent = new Intent(this, CreateEvent.class);
        if(createEventActivityResultLauncher != null) {
            createEventActivityResultLauncher.launch(intent);
        } else {
            Toast toast = Toast.makeText(this, R.string.error_create_event, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}