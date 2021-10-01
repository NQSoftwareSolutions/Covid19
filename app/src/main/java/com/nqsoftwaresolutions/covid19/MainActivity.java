package com.nqsoftwaresolutions.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    ImageView flags;
    Spinner spinner;
    Button stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniView();

        setCountrySpinner();

    }

    private void iniView() {
        spinner = (Spinner) findViewById(R.id.spinner);
        flags = (ImageView) findViewById(R.id.flag);
        stat = (Button)  findViewById(R.id.stat);
    }

    private void setCountrySpinner() {
        spinner.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                CountryData.countryNames));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                flags.setImageResource(CountryData.countryFlag[spinner.getSelectedItemPosition()]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void sendToStatics(View view) {
        Intent statIntent = new Intent(MainActivity.this, StatisticsActivity.class);
        startActivity(statIntent);
    }
}