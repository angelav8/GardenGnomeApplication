package com.example.ange.gardengnome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class weather extends AppCompatActivity {


        Button btn;
        EditText txt;
        String[] min;
        String[] max;
        String[] date;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_weather);

            min = new String[5];
            max = new String[5];
            date = new String[5];

            btn = (Button)findViewById(R.id.btnReverse);
            txt = (EditText)findViewById(R.id.mytxt);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FourDayForecast();
                }
            });
        }

        void FourDayForecast()
        {
            Thread myThread  = new Thread(new Runnable(){
                public void run()
                {
                    try {


                        String Url = "http://api.openweathermap.org/data/2.5/forecast/daily?q="+txt.getText().toString()+"&mode=xml&appid=f95589db6ab132cb3c21fd7ff16babd5&units=metric";

                        URL getWeatherData = new URL(Url);
                        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                        DocumentBuilder db = dbf.newDocumentBuilder();
                        Document doc = db.parse(new InputSource(getWeatherData.openStream()));
                        doc.getDocumentElement().normalize();

                        // looping through all item nodes <item>
                        for (int i = 1; i < 6; i++) {
                            String cityName = doc.getElementsByTagName("temperature").item(i)
                                    .getAttributes().getNamedItem("min").getNodeValue();

                            String maxi = doc.getElementsByTagName("temperature").item(i)
                                    .getAttributes().getNamedItem("max").getNodeValue();

                            min[i-1] = cityName;
                            max[i-1] = maxi;
                            Log.i("From", min[i-1]);
                            Log.i("From", max[i-1]);
                        }


                        // looping through all item nodes <item>
                        for (int i = 1; i < 6; i++) {
                            String cityName = doc.getElementsByTagName("time").item(i)
                                    .getAttributes().getNamedItem("day").getNodeValue();



                            date[i-1] = cityName;
                            Log.i("dat", date[i-1]);
                        }

                        OneDayForecast();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(MainActivity.this, "value"+min[0], Toast.LENGTH_LONG).show();
                                txt.setText("");
                                Log.d("********","******DONE");

                            }
                        });
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            });

            myThread.start();

        }

        void OneDayForecast()
        {
            Thread myThread  = new Thread(new Runnable(){
                public void run()
                {
                    try {

                        String City= txt.getText().toString();
                        String Url = "http://api.openweathermap.org/data/2.5/weather?q="+City+"&mode=xml&appid=f95589db6ab132cb3c21fd7ff16babd5&units=metric";

                        URL yahoo = new URL(Url);
                        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                        DocumentBuilder db = dbf.newDocumentBuilder();
                        Document doc = db.parse(new InputSource(yahoo.openStream()));
                        doc.getDocumentElement().normalize();


                        final String Temperature = doc.getElementsByTagName("temperature").item(0)
                                .getAttributes().getNamedItem("value").getNodeValue();

                        final String humidity = doc.getElementsByTagName("humidity").item(0)
                                .getAttributes().getNamedItem("value").getNodeValue()+"%";


                        final String clouds = doc.getElementsByTagName("clouds").item(0)
                                .getAttributes().getNamedItem("name").getNodeValue();



                        final String windName = doc.getElementsByTagName("speed").item(0)
                                .getAttributes().getNamedItem("name").getNodeValue();

                        final String windValue = doc.getElementsByTagName("speed").item(0)
                                .getAttributes().getNamedItem("value").getNodeValue();

                        final String wind = windName+"\nSpeed "+windValue+" kmph";


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Toast.makeText(MainActivity.this, "Dta"+MinTemp[0], Toast.LENGTH_LONG).show();
                                Log.d("********","******DONE");
                                Intent i = new Intent(weather.this, weatherdetails.class);
                                i.putExtra("temperature", Temperature);
                                i.putExtra("humidity",humidity);
                                i.putExtra("clouds",clouds);
                                i.putExtra("wind",wind);
                                i.putExtra("min",min);
                                i.putExtra("max",max);
                                i.putExtra("date", date);
                                startActivity(i);



                            }
                        });
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            });

            myThread.start();

        }



    }
