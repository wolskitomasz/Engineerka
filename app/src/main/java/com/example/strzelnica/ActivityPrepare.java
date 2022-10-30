package com.example.strzelnica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ActivityPrepare extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare);

        String[] variables = new String[3];

        //Utworzenie instancji dla przycisku i pól tekstowych
        Button buttonCorrect = findViewById(R.id.buttonCorrect);
        Button buttonStartGame = findViewById(R.id.buttonStartGame);

        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewSurname = findViewById(R.id.textViewSurname);
        TextView textViewAge = findViewById(R.id.textViewAge);
        TextView textView1 = findViewById(R.id.textView6);
        TextView textView2 = findViewById(R.id.textView10);
        TextView textView3 = findViewById(R.id.textView12);
        TextView textView4 = findViewById(R.id.textView5);


        //Sprawdzenie języka i jego ewentualna zmiana
        StringBuffer datax2 = new StringBuffer("");
        try {
            FileInputStream fIn = openFileInput ( "jezyk" ) ;
            InputStreamReader isr = new InputStreamReader ( fIn ) ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;

            String readString = buffreader.readLine ( ) ;
            while ( readString != null ) {
                datax2.append(readString);
                readString = buffreader.readLine ( ) ;
            }

            isr.close ( ) ;
        } catch ( IOException ioe ) {
            ioe.printStackTrace ( ) ;
        }

        if(datax2.toString().equals("Polski"))
        {
            textView1.setText("Imię");
            textView2.setText("Nazwisko");
            textView3.setText("Wiek");
            textView4.setText("PRZYGOTOWANIE");
            buttonCorrect.setText("POPRAW");
            buttonStartGame.setText("START");
        }
        else
        {
            textView1.setText("Name");
            textView2.setText("Surname");
            textView3.setText("Age");
            textView4.setText("PREPARE");
            buttonCorrect.setText("CORRECT");
            buttonStartGame.setText("START");
        }

        //Wczytanie danych gracza
        StringBuffer datax = new StringBuffer("");
        try {
            FileInputStream fIn = openFileInput ( "player" ) ;
            InputStreamReader isr = new InputStreamReader ( fIn ) ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;

            int i = 1;
            String readString = buffreader.readLine ( ) ;
            variables[0] = readString;
            while ( readString != null && i <= 2) {
                datax.append(readString);
                readString = buffreader.readLine ( );
                variables[i] = readString;
                i++;
            }

            isr.close ( ) ;
        } catch ( IOException ioe ) {
            ioe.printStackTrace ( ) ;
        }

        textViewName.setText(variables[0]);
        textViewSurname.setText(variables[1]);
        textViewAge.setText(variables[2]);

        buttonCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityPrepare.this, ActivityStart.class));
            }
        });

        buttonStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityPrepare.this, ActivityCamera.class));
            }
        });


    }
}