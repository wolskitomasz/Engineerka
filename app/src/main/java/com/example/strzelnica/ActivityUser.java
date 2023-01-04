package com.example.strzelnica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ActivityUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //Sprawdzenie języka
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

        Button buttonGuest = findViewById(R.id.buttonGuest);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        //zmiana jezyka
        if(datax2.toString().equals("Polski"))
        {
            buttonGuest.setText("GOŚĆ");
            buttonLogin.setText("ZALOGUJ");
        }
        else
        {
            buttonGuest.setText("GUEST");
            buttonLogin.setText("LOG IN");
        }

        buttonGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityUser.this, ActivityStart.class));
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(datax2.toString().equals("Polski"))
                {
                    Toast.makeText(getApplicationContext(), "DO ZOBACZENIA WKRÓTCE", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "SEE YOU SOON", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}