package com.example.strzelnica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ActivityHistory extends AppCompatActivity {
    ArrayList <String> list = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TextView textView = findViewById(R.id.textView9);

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
            textView.setText("HISTORIA");
        }
        else
        {
            textView.setText("HISTORY");
        }

        Button button = findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityHistory.this, MainActivity.class));
            }
        });
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        //Utworzenie wiersza nagłówkowego
        TableRow tb = new TableRow(this);
        tb.setBackgroundColor(Color.BLACK);

        TextView tv0 = new TextView(this);
        if(datax.toString().equals("Polski"))
        {
            tv0.setText("Imię i nazwisko");
        }
        else
        {
            tv0.setText("Full name");
        }
        tv0.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
        tv0.setGravity(Gravity.CENTER);
        tv0.setTextColor(Color.WHITE);
        tv0.setTextSize(18);
        tb.addView(tv0);
        TextView tv1 = new TextView(this);
        if(datax.toString().equals("Polski"))
        {
            tv1.setText("Punkty");
        }
        else
        {
            tv1.setText("Points");
        }
        tv1.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(Color.WHITE);
        tv1.setTextSize(18);
        tb.addView(tv1);
        TextView tv2 = new TextView(this);
        if(datax.toString().equals("Polski"))
        {
            tv2.setText("Data");
        }
        else
        {
            tv2.setText("Date");
        }
        tv2.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(Color.WHITE);
        tv2.setTextSize(18);
        tb.addView(tv2);
        tableLayout.addView(tb);

        StringBuffer datax2 = new StringBuffer("");
        try {
            FileInputStream fIn = openFileInput ( "history" ) ;
            InputStreamReader isr = new InputStreamReader ( fIn ) ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;
            String readString = buffreader.readLine();
            list.add(readString);
            while (readString != null) {
                datax2.append(readString);
                readString = buffreader.readLine ( );
                list.add(readString);
            }
            isr.close ( ) ;
        } catch ( IOException ioe ) {
            ioe.printStackTrace ( ) ;
        }
        int game=0;
        while(list.get(game) != null)
        {
            game++;
        }

        for(int i=0; i<game; i=i+3)
        {
            TableRow tableRow = new TableRow(this);
            TextView tv11 = new TextView(this);
            tv11.setText(list.get(i));
            tv11.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
            tv11.setGravity(Gravity.CENTER);
            tv11.setTextColor(Color.WHITE);
            tv11.setTextSize(16);
            tv11.setPadding(0,25,0,0);
            tableRow.addView(tv11);
            TextView tv12 = new TextView(this);
            tv12.setText(list.get(i+1));
            tv12.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            tv12.setGravity(Gravity.CENTER);
            tv12.setTextColor(Color.WHITE);
            tv12.setTextSize(16);
            tv12.setPadding(0,25,0,0);
            tableRow.addView(tv12);
            TextView tv13 = new TextView(this);
            tv13.setText(list.get(i+2));
            tv13.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            tv13.setGravity(Gravity.CENTER);
            tv13.setTextColor(Color.WHITE);
            tv13.setTextSize(16);
            tv13.setPadding(0,25,0,0);
            tableRow.addView(tv13);
            tableLayout.addView(tableRow);
        }

    }
}
