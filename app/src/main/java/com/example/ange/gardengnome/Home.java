package com.example.ange.gardengnome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class Home extends AppCompatActivity {
    Button moistBtn;
    Button tempBtn;
    Button weatherBtn;
    Button humidBtn;
    Button planBtn;
    Button settingsBtn;
    public FirebaseAuth auth;
    public FirebaseDatabase mFirebaseDatabase;
    public FirebaseAuth.AuthStateListener mAuthListener;
    public DatabaseReference myRef;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        auth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = auth.getCurrentUser();

        sendToDB();

        Common.currentToken = FirebaseInstanceId.getInstance().getToken();
        sendtoRaspberry();
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

        weatherBtn=(Button)findViewById(R.id.weatherBtn);
        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(Home.this,weather.class);
                startActivity(a);
            }
        });

        humidBtn=(Button)findViewById(R.id.humidBtn);
        humidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent z=new Intent(Home.this,MapsActivity.class);
                startActivity(z);
            }
        });

        planBtn=(Button)findViewById(R.id.planBtn);
        planBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent(Home.this,Plants.class);
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

    private void sendToDB() {
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = mFirebaseDatabase.getReference("user/userID");

        myRef.setValue(userID);
    }

    private void sendtoRaspberry() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String tokenstring = "users/"+userID+"/token";
        DatabaseReference myRef = database.getReference(tokenstring);

        myRef.setValue(Common.currentToken);
    }


}

