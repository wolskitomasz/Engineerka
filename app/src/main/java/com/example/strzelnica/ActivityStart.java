package com.example.strzelnica;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ActivityStart extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
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
            editTextName.setText("IMIĘ");
            editTextSurname.setText("NAZWISKO");
            editTextAge.setText("WIEK");
        }
        else
        {
            txtName.setText("Name");
            txtSur.setText("Surname");
            txtAge.setText("Age");
            buttonNext.setText("SAVE");
            txtLabel.setText("PREPARE");
            editTextName.setText("NAME");
            editTextSurname.setText("SURNAME");
            editTextAge.setText("AGE");
        }

        //Sprawdzenie czy istnieje plik "player"
        String[] variables = new String[3];
        File file = new File("/data/data/com.example.strzelnica/files/player");
        if(file.exists())
        {
            StringBuffer datax2 = new StringBuffer("");
            try {
                FileInputStream fIn = openFileInput ( "player" ) ;
                InputStreamReader isr = new InputStreamReader ( fIn ) ;
                BufferedReader buffreader = new BufferedReader ( isr ) ;

                int i = 1;
                String readString = buffreader.readLine ( ) ;
                variables[0] = readString;
                while ( readString != null && i <= 2) {
                    datax2.append(readString);
                    readString = buffreader.readLine ( );
                    variables[i] = readString;
                    i++;
                }

                isr.close ( ) ;
            } catch ( IOException ioe ) {
                ioe.printStackTrace ( ) ;
            }
            editTextName.setText(variables[0]);
            editTextSurname.setText(variables[1]);
            editTextAge.setText(variables[2]);
        }

        //Utworzenie pliku tekstowego z danymi osoby rozpoczynającej grę
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] variables = {editTextName.getText().toString(), editTextSurname.getText().toString(), editTextAge.getText().toString()};

                String file = "player";

                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = openFileOutput(file, MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    for(int i = 0; i<3; i++){
                        if(i == 2)
                        {
                            fileOutputStream.write(variables[i].getBytes());
                        }
                        else
                        {
                            fileOutputStream.write(variables[i].getBytes());
                            fileOutputStream.write("\r\n".getBytes());
                        }
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