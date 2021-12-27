package com.example.zivototekrug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetaliVolonterActivity extends AppCompatActivity {
    private TextView imeKorisnikDesno, datumDesno, tipNaUslugaDesno, rastojanieDesno, rejtingDesno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detali_volonter);
        imeKorisnikDesno = findViewById(R.id.imeKorisnikDesno);
        datumDesno = findViewById(R.id.datumVolonterDesno);
        tipNaUslugaDesno = findViewById(R.id.tipUslugaVolonterDesno);
        rastojanieDesno = findViewById(R.id.rastojanieDesno);
        rejtingDesno = findViewById(R.id.rejtingVolonterDesno);

        Intent intent = getIntent();
        String celoIme = intent.getStringExtra("ImeKorisnik");
        String datum = intent.getStringExtra("Datum");
        String tipNaUsluga = intent.getStringExtra("TipNaUsluga");
        String rastojanie = intent.getStringExtra("Rastojanie");
        String rejting = intent.getStringExtra("RejtingVolonter");

        imeKorisnikDesno.setText(celoIme);
        datumDesno.setText(datum);
        tipNaUslugaDesno.setText(tipNaUsluga);
        rastojanieDesno.setText(rastojanie + " km");
        rejtingDesno.setText(rejting);
    }
}