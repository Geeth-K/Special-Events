package com.geethanjali.specialevents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SpecialEventsAdapter extends RecyclerView.Adapter<SpecialEventsAdapter.Viewholder> {
    private Context context;
    private ArrayList<SpecialEventModel> specialEventModelArrayList;

    // Constructor
    public SpecialEventsAdapter(Context context, ArrayList<SpecialEventModel> specialEventModelArrayList) {
        this.context = context;
        this.specialEventModelArrayList = specialEventModelArrayList;
    }

    @NonNull
    @Override
    public SpecialEventsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialEventsAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        SpecialEventModel model = specialEventModelArrayList.get(position);
        holder.eventNameTV.setText(model.getEvent_name());
        holder.eventVenueTV.setText(model.getEvent_venue());
        holder.eventDescTV.setText(model.getEvent_desc());
        holder.eventDateTV.setText("" + model.getEvent_date());
    }

    @Override
    public int getItemCount() {
        return specialEventModelArrayList.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView eventNameTV, eventDateTV, eventVenueTV, eventDescTV;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            eventNameTV = itemView.findViewById(R.id.idEventName);
            eventVenueTV = itemView.findViewById(R.id.idEventVenue);
            eventDateTV = itemView.findViewById(R.id.idEventDate);
            eventDescTV = itemView.findViewById(R.id.idEventDesc);
        }
    }
}
