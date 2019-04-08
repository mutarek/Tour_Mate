package com.kazishihan.tourmate.Fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kazishihan.tourmate.BottomSheet_AddTrip;
import com.kazishihan.tourmate.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TripFragment extends Fragment {


    public TripFragment() {
        // Required empty public constructor
    }

    private BottomSheet_AddTrip bottomSheet_addTrip;
    private FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip, container, false);

        ////// floating button addd trips action
         fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                bottomSheet_addTrip = new BottomSheet_AddTrip();
                bottomSheet_addTrip.show(getFragmentManager(),"BootmSheet_addtrip");

            }
        });


         return view;

    }

}
