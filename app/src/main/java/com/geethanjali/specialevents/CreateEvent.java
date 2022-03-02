package com.geethanjali.specialevents;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        Button cancelButton = findViewById(R.id.idCancelBtn);
        Button createButton = findViewById(R.id.idCreateBtn);
        EditText eventNameET = findViewById(R.id.idEventNameValue);
        EditText eventVenueET = findViewById(R.id.idEventVenueValue);
        EditText eventDescET = findViewById(R.id.idEventDescValue);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("event_name",eventNameET.getText().toString());
                returnIntent.putExtra("event_venue",eventVenueET.getText().toString());
                returnIntent.putExtra("event_desc",eventDescET.getText().toString());
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}