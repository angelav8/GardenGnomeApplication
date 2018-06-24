package com.example.ange.gardengnome;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Moisture extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Intialized everthing here
    private Spinner spinner;
    private static final String[] category = {"Moisture"};
    int j = 0;
    DatabaseReference ref;
    Button currentstatus, lastWatered, checkTemp, datetempchecked;
    ListView listView, listView2, listView3, listView4;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moisture);

        // Loading text
        pd = new ProgressDialog(Moisture.this);
        pd.setMessage("loading");


        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list3);
        listView2 = (ListView) findViewById(R.id.list2);
        listView3 = (ListView) findViewById(R.id.list3);
        listView4 = (ListView) findViewById(R.id.list4);

        // Adding spinner reference and assign values
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Moisture.this,
                android.R.layout.simple_spinner_item, category);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        // Added buttons references
        currentstatus = (Button) findViewById(R.id.currentstatus);
        lastWatered = (Button) findViewById(R.id.lastWatered);



        currentstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();

                // Point to db to get values
                DatabaseReference temp = ref.child("CurrentStatus");
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<String> data  = new ArrayList<>();

                        //datasnapshot is a object that holds all the values that we got from db. We need to
                        // loop through to read all of them
                        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                            String d = ""+singleSnapshot.getValue();
                            data.add(d);
                        }


                        // Define a new Adapter
                        // First parameter - Context
                        // Second parameter - Layout for the row
                        // Third parameter - ID of the TextView to which the data is written
                        // Forth - the Array of data

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Moisture.this,
                                android.R.layout.simple_list_item_1, android.R.id.text1, data);


                        // Assign adapter to ListView
                        listView.setAdapter(adapter);

                        pd.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        pd.dismiss();
                    }
                };
                temp.addListenerForSingleValueEvent(eventListener);
            }
        });


        lastWatered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();

                DatabaseReference temp = ref.child("LastWatered");
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<String> data  = new ArrayList<>();

                        //datasnapshot is a object that holds all the values that we got from db. We need to
                        // loop through to read all of them
                        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                            String d = ""+singleSnapshot.getValue();
                            data.add(d);
                        }


                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Moisture.this,
                                android.R.layout.simple_list_item_1, android.R.id.text1, data);


                        // Assign adapter to ListView
                        listView2.setAdapter(adapter);
                        pd.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        pd.dismiss();
                    }
                };
                temp.addListenerForSingleValueEvent(eventListener);


            }
        });


    }



    // This listener is called when a value in selected on the spinner
    //By default 0 is selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                //when category 0 is select point the db to autumn data. Same for all
                ref = FirebaseDatabase.getInstance().getReference().child("Status").child(category[0]);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
