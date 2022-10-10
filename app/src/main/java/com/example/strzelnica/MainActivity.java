package com.example.strzelnica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("STRZELNICA");

        //Utworzenie instancji dla przycisków
        Button buttonStart = findViewById(R.id.buttonStart);
        Button buttonHistory = findViewById(R.id.buttonHistory);
        Button buttonSettings = findViewById(R.id.buttonSettings);
        Button buttonClose = findViewById(R.id.buttonClose);

        //Sprawdzenie języka i jego ewentualna zmiana
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
            buttonHistory.setText("Historia");
            buttonSettings.setText("Ustawienia");
            buttonClose.setText("Wyjdź");
            buttonStart.setText("Start");
        }
        else
        {
            buttonHistory.setText("History");
            buttonSettings.setText("Settings");
            buttonClose.setText("Close");
            buttonStart.setText("Start");
        }

        //Poszczególne akcje dla przycisków
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ActivityUser.class));
            }
        });
        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ActivityHistory.class));
            }
        });
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ActivitySettings.class));
            }
        });
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}