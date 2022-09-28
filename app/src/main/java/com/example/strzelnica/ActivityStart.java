package com.example.strzelnica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityStart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        //Utworzenie instancji dla przycisku i pól tekstowych
        Button buttonNext = findViewById(R.id.buttonNext);

        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextSurname = findViewById(R.id.editTextSurname);
        EditText editTextAge = findViewById(R.id.editTextAge);

        TextView txtName = findViewById(R.id.textView3);
        TextView txtSur = findViewById(R.id.textView7);
        TextView txtAge =  findViewById(R.id.textView8);
        TextView txtLabel =  findViewById(R.id.textView2);

        //ustawienia jezyka
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
            txtName.setText("Imię");
            txtSur.setText("Nazwisko");
            txtAge.setText("Wiek");
            buttonNext.setText("ZAPISZ");
            txtLabel.setText("PRZYGOTOWANIE");
        }
        else
        {
            txtName.setText("Name");
            txtSur.setText("Surname");
            txtAge.setText("Age");
            buttonNext.setText("SAVE");
            txtLabel.setText("PREPARE");
        }

        //Utworzenie pliku tekstowego z danymi osoby rozpoczynającej grę
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] variables = {editTextName.getText().toString(), editTextSurname.getText().toString(), editTextAge.getText().toString()};

                Date nowDate = new Date();
                SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy_HH:mm");
                String file = variables[0] + "_" + variables[1] + "_" + date.format(nowDate);


                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = openFileOutput(file, MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    for(int i = 0; i<3; i++){
                        fileOutputStream.write(variables[i].getBytes());
                        fileOutputStream.write("\r\n".getBytes());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                startActivity(new Intent(ActivityStart.this, ActivityPrepare.class));
            }
        });



    }
}