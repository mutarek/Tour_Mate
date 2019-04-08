package com.kazishihan.tourmate.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kazishihan.tourmate.BottomSheet_AddTrip;
import com.kazishihan.tourmate.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemoryFragment extends Fragment {


    public MemoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_memory, container, false);


    }

}
