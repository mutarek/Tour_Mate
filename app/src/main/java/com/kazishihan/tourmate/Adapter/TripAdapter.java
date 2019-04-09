package com.kazishihan.tourmate.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kazishihan.tourmate.Classes.IndividualTrip;
import com.kazishihan.tourmate.R;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {
    private List<IndividualTrip> individualTrips;
    Context context;

    public TripAdapter(List<IndividualTrip> individualTrips, Context context) {
        this.individualTrips = individualTrips;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_trip_sample_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        IndividualTrip mylist = individualTrips.get(position);
        viewHolder.trip_title.setText(""+mylist.getEvent_Name());
        viewHolder.trip_description.setText(""+mylist.getEvent_Description());
        viewHolder.fromdate.setText(""+mylist.getFrom_Date());
        viewHolder.todate.setText(""+mylist.getTo_Date());
    }

    @Override
    public int getItemCount() {
        return individualTrips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView trip_title,trip_description,fromdate,todate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            trip_title = itemView.findViewById(R.id.trip_name_id);
            trip_description =itemView.findViewById(R.id.trip_description_id);
            fromdate = itemView.findViewById(R.id.trip_From_date);
            todate = itemView.findViewById(R.id.trip_TO_Date);
        }
    }
}
