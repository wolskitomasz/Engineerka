package com.example.strzelnica;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

public class ActivityHistory extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        //Utworzenie wiersza nagłówkowego
        TableRow tb = new TableRow(this);
        tb.setBackgroundColor(Color.BLACK);

        TextView tv0 = new TextView(this);
        tv0.setText("Imię i nazwisko");
        tv0.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
        tv0.setGravity(Gravity.CENTER);
        tv0.setTextColor(Color.WHITE);
        tb.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText("Punkty");
        tv1.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(Color.WHITE);
        tb.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText("Data");
        tv2.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(Color.WHITE);
        tb.addView(tv2);
        tableLayout.addView(tb);

    }
}