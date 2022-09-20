package com.example.strzelnica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ActivitySettings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public String[] language = {"Polski", "Angielski"};
    String languageSetter="Polski";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.setTitle("STRZELNICA");

        //---polaczenie z nowa klasa
        Intent myIntent = new Intent(ActivitySettings.this, MainActivity.class);

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, language);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        Button button = findViewById(R.id.buttonSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //---wyslanie wartosci do innych klas
                myIntent.putExtra("languageSetter", languageSetter.toString());
                startActivity(myIntent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        languageSetter = language[i];

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}