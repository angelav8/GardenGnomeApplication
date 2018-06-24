package com.example.ange.gardengnome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class weatherdetails extends AppCompatActivity {

    TextView temperature, humidity, pressure, visibility, wind, clouds;

    String min[];
    String max[];
    String dates[];
    Double temp=0.0;
    ArrayList<String> listdata = new ArrayList<>();
    ListView listView ;
    String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatherdetails);

        min = new String[5];
        temperature = (TextView) findViewById(R.id.temperature);
        max = new String[5];
        dates = new String[5];


        temperature.setText(getIntent().getStringExtra("temperature")+ (char) 0x00B0 +"C" );


        humidity = (TextView) findViewById(R.id.humidity);
        wind = (TextView) findViewById(R.id.wind);
        clouds = (TextView) findViewById(R.id.clouds);


        humidity.setText(getIntent().getStringExtra("humidity"));
        wind.setText(getIntent().getStringExtra("wind"));
        clouds.setText(getIntent().getStringExtra("clouds"));

        min = getIntent().getStringArrayExtra("min");
        max = getIntent().getStringArrayExtra("max");
        dates = getIntent().getStringArrayExtra("date");



        Calendar calendar = Calendar.getInstance();

        DateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        try {
            calendar.setTime(format.parse( dates[0]));
        } catch (ParseException e) {
            e.printStackTrace();
        }



        for (int i =0; i<5; i++){


            listdata.add("Min: "+min[i] +"\nMax: "+max[i]+"\nDate: "+dates[i]);


            Log.i("Data", "Min: "+min[i] +"\nMax: "+max[i]+"\nDate: "+dates[i]);
        }


        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list3);

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listdata);


        // Assign adapter to ListView
        listView.setAdapter(adapter);
    }
}
