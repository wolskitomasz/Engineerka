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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ActivitySettings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public String[] language = {"Polski", "Angielski"};
    String languageSetter="Polski";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.setTitle("STRZELNICA");

        Button button = findViewById(R.id.buttonSave);

        StringBuffer datax = new StringBuffer("");
        try {
            FileInputStream fIn = openFileInput ( "jezyk" ) ;
            InputStreamReader isr = new InputStreamReader ( fIn ) ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;

            String readString = buffreader.readLine ( ) ;
            while ( readString != null ) {
                datax.append(readString);
                readString = buffreader.readLine ( ) ;
            }

            isr.close ( ) ;
        } catch ( IOException ioe ) {
            ioe.printStackTrace ( ) ;
        }

        if(datax.toString().equals("Polski"))
        {
            button.setText("ZAPISZ");
        }
        else
        {
            button.setText("SAVE");
        }

        //---polaczenie z nowa klasa
        Intent myIntent = new Intent(ActivitySettings.this, MainActivity.class);

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, language);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //---wyslanie wartosci do innych klas
                //myIntent.putExtra("languageSetter", languageSetter.toString());
                startActivity(myIntent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        languageSetter = language[i];

        //Toast.makeText(getApplicationContext(), test, Toast.LENGTH_LONG).show();
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput("jezyk", MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fileOutputStream.write(languageSetter.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}