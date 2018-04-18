package com.example.ange.gardengnome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
    Button moistBtn;
    Button tempBtn;
    Button luxBtn;
    Button humidBtn;
    Button planBtn;
    Button settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        moistBtn=(Button)findViewById(R.id.moistBtn);

        moistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this,Moisture.class);
                startActivity(i);
            }
        });

        tempBtn=(Button)findViewById(R.id.tempBtn);
        tempBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                Intent o=new Intent(Home.this,Temperature.class);
                startActivity(o);
             }
                                   }
        );

        luxBtn=(Button)findViewById(R.id.luxBtn);
        luxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(Home.this,Sunlight.class);
                startActivity(a);
            }
        });

        humidBtn=(Button)findViewById(R.id.humidBtn);
        humidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent z=new Intent(Home.this,Humidity.class);
                startActivity(z);
            }
        });

        planBtn=(Button)findViewById(R.id.planBtn);
        planBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent(Home.this,Settings.class);
                startActivity(x);
            }
        });

        settingsBtn =(Button)findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m=new Intent(Home.this,Settings.class);
                startActivity(m);
            }
        });
    }
}
