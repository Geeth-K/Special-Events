package com.geethanjali.specialevents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;

public class CreateEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        Button cancelButton = findViewById(R.id.idCancelBtn);
        Button createButton = findViewById(R.id.idCreateBtn);
        ImageButton datePickerButton = findViewById(R.id.idSelectDateBtn);
        EditText eventNameET = findViewById(R.id.idEventNameValue);
        EditText eventVenueET = findViewById(R.id.idEventVenueValue);
        EditText eventDateET = findViewById(R.id.idEventDateValue);
        EditText eventDescET = findViewById(R.id.idEventDescValue);

        //Launch the date picker dialog
        datePickerButton.setOnClickListener(this::showDatePickerDialog);

        cancelButton.setOnClickListener(view -> {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        });

        createButton.setOnClickListener(view -> {
            String eventNameFromET = eventNameET.getText().toString();
            if(eventNameFromET.isEmpty()){
                Toast toast = Toast.makeText(CreateEvent.this, "Please enter an event name.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                //If at least event name is entered, create the event and return the result
                Intent returnIntent = new Intent();
                returnIntent.putExtra("event_name", eventNameFromET);
                returnIntent.putExtra("event_venue", eventVenueET.getText().toString());
                returnIntent.putExtra("event_date", eventDateET.getText().toString());
                returnIntent.putExtra("event_desc", eventDescET.getText().toString());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
            String date = day + "-" + months[month] + "-" + year;

            //Update event date field with the selected date
            if(getActivity() != null) {
                EditText eventDateET = getActivity().findViewById(R.id.idEventDateValue);
                eventDateET.setText(date);
            }
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}