package com.example.arinamoldovean.sunshine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private List<String> villes;
    Spinner spinner;
    String URL = "http://api.worldweatheronline.com/premium/v1/weather.ashx?key=fd9d0ae8699e4e99914132828183004&format=json&q=";
    GetData gd;
    TextView view_temperature;
    TextView view_description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view_temperature = findViewById(R.id.textView2);
        view_description = findViewById(R.id.textView);
        villes = new ArrayList<>();
        villes.add("Bordeaux");
        villes.add("Madrid");
        villes.add("Paris");



        for (String s : villes) { System.out.println(s);}
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("position" + position);
                String ville_selectionnee =villes.get(position);
                meteo(ville_selectionnee);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, villes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }


    void meteo(String ville){
        gd = new GetData();
        String json_file = null;
        try {
            json_file = gd.execute(URL+ville).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("json file" + json_file);
        JSONObject obj = null;
        JSONObject objet_meteo= null;
        try {
            obj = new JSONObject(json_file);
            objet_meteo = obj.getJSONObject("data").getJSONArray("current_condition").getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {

            view_temperature.setText("Temperature (°C) : " + objet_meteo.getString("temp_C"));
            view_description.setText(("Desc: " + objet_meteo.getJSONArray("weatherDesc").getJSONObject(0).getString("value")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}



