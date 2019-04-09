package com.kazishihan.tourmate;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kazishihan.tourmate.Classes.IndividualTrip;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BottomSheet_AddTrip extends BottomSheetDialogFragment {

    private EditText addTriptitle, addTripDiscription;
    private Button addtrip;
    private ImageView fromDateIv, toDateIv;
    private TextView DateTv, toDateTv;
    private long selectedFromDateinMS;
    private long selectedToDateinMS;
    private String spinnerdata;
    private RelativeLayout datePicker, timePicker;
    String addTripFromDate, addTripToDate, postrandomname;
    private FirebaseAuth firebaseAuth;
    String currentuser;
    private DatabaseReference databaseReference, postRef;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.bottom_add_trip, container, false);

        datePicker = view.findViewById(R.id.datepicklayoutid);
        timePicker = view.findViewById(R.id.todatepicklayoutid);
        DateTv = view.findViewById(R.id.opendatepickerTvID);
        toDateTv = view.findViewById(R.id.toopendatepickerTvID);
        addTriptitle = view.findViewById(R.id.tripNameId);
        addTripDiscription = view.findViewById(R.id.tripDescriptionId);
        addtrip = view.findViewById(R.id.addTrip);

        firebaseAuth = FirebaseAuth.getInstance();
        currentuser = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserList");
        postRef = FirebaseDatabase.getInstance().getReference().child("UserList").child(currentuser).child("Events");

        addtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String triptitle = addTriptitle.getText().toString();
                String tripDescription = addTripDiscription.getText().toString();
                addTripFromDate = String.valueOf(selectedFromDateinMS);
                addTripToDate = String.valueOf(selectedToDateinMS);

                if (triptitle.equals("")) {
                    Toast.makeText(getContext(), "Please Enter your Event Title", Toast.LENGTH_SHORT).show();
                } else if (tripDescription.equals("")) {
                    Toast.makeText(getContext(), "Please Enter a description for your event", Toast.LENGTH_SHORT).show();
                } else {
                    InsertDatatoDatabase(triptitle, tripDescription, addTripToDate, addTripFromDate);
                }

            }
        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDatePicker();
            }
        });

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openToDatePicker();

            }
        });


        return view;
    }

    private void InsertDatatoDatabase(final String triptitle, final String tripDescription, final String addTripToDate, final String addTripFromDate) {
        final IndividualTrip individualTrip = new IndividualTrip();
        individualTrip.setEvent_Description(tripDescription);
        individualTrip.setEvent_Name(triptitle);
        individualTrip.setFrom_Date(addTripFromDate);
        individualTrip.setTo_Date(addTripToDate);

                postRef.child(currentuser + postrandomname).setValue(individualTrip)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Event Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        
    });
    }


    //////////fromdate picker method
    private void openDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;
                String selectedDate = year + "/" + month + "/" + dayOfMonth + " 00:00:00";

                SimpleDateFormat dateandTimeSDF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                SimpleDateFormat dateSDF = new SimpleDateFormat("dd MMM yyyy");
                Date date = null;
                try {
                    date = dateandTimeSDF.parse(selectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                selectedFromDateinMS = date.getTime();
                DateTv.setText(dateSDF.format(date));


            }
        };
        /////getcurntdate
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH);
        int day = calendar.get(calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
        datePickerDialog.show();


    }

    ///to date///
    private void openToDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;
                String selectedDate = year + "/" + month + "/" + dayOfMonth + " 00:00:00";

                SimpleDateFormat dateandTimeSDF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                SimpleDateFormat dateSDF = new SimpleDateFormat("dd MMM yyyy");
                Date date = null;
                try {
                    date = dateandTimeSDF.parse(selectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                selectedToDateinMS = date.getTime();
                toDateTv.setText(dateSDF.format(date));


            }
        };
        /////getcurntdate
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH);
        int day = calendar.get(calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
        datePickerDialog.show();


    }


}
