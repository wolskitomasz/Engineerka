package com.example.strzelnica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ActivitySummary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        TextView textView = findViewById(R.id.textView);
        String[] variables = new String[3];

        //wczytanie ilości punktów
        StringBuffer datax = new StringBuffer("");
        try {
            FileInputStream fIn = openFileInput ( "punkty" ) ;
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
        textView.setText(datax.toString());

        //Wczytanie danych gracza
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

//        Toast.makeText(getApplicationContext(), variables[0] + " " + variables[1], Toast.LENGTH_LONG).show();
        //Zapis do historii

        String[] var = {variables[0] + " " + variables[1], datax.toString(), new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date())};

        String file = "history";

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(file, MODE_APPEND);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            for(int i = 0; i<3; i++){
                fileOutputStream.write(var[i].getBytes());
                fileOutputStream.write("\r\n".getBytes());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}