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

public class Plants extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Intialized everthing here
    private Spinner spinner;
    private static final String[] category = {"Autumn", "Spring", "Summer", "Winter"};
    int j = 0;
    DatabaseReference ref;
    Button fruits, veg;
    ListView listView ;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants);

        // Loading text
        pd = new ProgressDialog(Plants.this);
        pd.setMessage("loading");


        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list3);

        // Adding spinner reference and assign values
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Plants.this, android.R.layout.simple_spinner_item, category);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        // Added buttons references
        fruits = (Button) findViewById(R.id.fruit);
        veg = (Button) findViewById(R.id.veg);

        //This will be called it fruits button clicked

        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();

                // Point to db to get values
                DatabaseReference temp = ref.child("Fruits");
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

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Plants.this, android.R.layout.simple_list_item_1, android.R.id.text1, data);


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


        //This button will be called if the vegetables button clicked
        veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();

                DatabaseReference temp = ref.child("Vegetables");
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


                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Plants.this, android.R.layout.simple_list_item_1, android.R.id.text1, data);


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



    }



    // This listener is called when a value in selected on the spinner
    //By default 0 is selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                //when category 0 is select point the db to autumn data. Same for all
                ref = FirebaseDatabase.getInstance().getReference().child("Plant").child(category[0]);
                break;
            case 1:
                ref = FirebaseDatabase.getInstance().getReference().child("Plant").child(category[1]);
                break;
            case 2:
                ref = FirebaseDatabase.getInstance().getReference().child("Plant").child(category[2]);
                break;
            case 3:
                ref = FirebaseDatabase.getInstance().getReference().child("Plant").child(category[3]);
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
