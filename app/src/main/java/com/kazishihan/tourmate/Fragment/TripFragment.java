package com.kazishihan.tourmate.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kazishihan.tourmate.Adapter.TripAdapter;
import com.kazishihan.tourmate.BottomSheet_AddTrip;
import com.kazishihan.tourmate.Classes.IndividualTrip;
import com.kazishihan.tourmate.R;

import java.util.ArrayList;
import java.util.List;

public class TripFragment extends Fragment {


    public TripFragment() {
    }

    private BottomSheet_AddTrip bottomSheet_addTrip;
    private FloatingActionButton fab;
    private RecyclerView triprecyclerView;
    DatabaseReference database;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private List<IndividualTrip> list;
    private TripAdapter tripAdapter;
    private Context context;
    private FirebaseAuth firebaseAuth;
    String currentuser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        currentuser = firebaseAuth.getCurrentUser().getUid();
        list = new ArrayList<>();


        triprecyclerView = view.findViewById(R.id.trip_recycler_view);
        triprecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheet_addTrip = new BottomSheet_AddTrip();
                bottomSheet_addTrip.show(getFragmentManager(), "BootmSheet_addtrip");

            }
        });




        database = FirebaseDatabase.getInstance().getReference().child("UserList").child(currentuser);
        database.child("Events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    list.clear();
                    for (DataSnapshot data: dataSnapshot.getChildren()) {
                        IndividualTrip trip = data.getValue(IndividualTrip.class);
                        list.add(trip);

                    }
                    tripAdapter = new TripAdapter(list,context);
                    triprecyclerView.setAdapter(tripAdapter);
                    tripAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getActivity(), "Empty database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        return view;

    }

}
