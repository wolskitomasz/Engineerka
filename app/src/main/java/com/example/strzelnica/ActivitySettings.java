package com.example.strzelnica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ActivitySettings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Deklaracja zmiennych
    public String[] language = {"Polski", "English"};
    String languageSetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        this.setTitle("STRZELNICA");
        //Utworzenie instancji dla przycisku
        Button button = findViewById(R.id.buttonSave);
        TextView textView = findViewById(R.id.textViewTitle);
        TextView textView2 = findViewById(R.id.textView4);

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
            button.setText("ZAPISZ");
            textView.setText("USTAWIENIA");
            textView2.setText("Język");
            language[0] = "Polski";
            language[1] = "English";
        }
        else
        {
            button.setText("SAVE");
            textView.setText("SETTINGS");
            textView2.setText("Language");
            language[0] = "English";
            language[1] = "Polski";
        }

        //Utworzenie i wypełnienie spinera
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, language);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivitySettings.this, MainActivity.class));
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //Zapis wybranego języka do pliku tekstowego
        languageSetter = language[i];

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