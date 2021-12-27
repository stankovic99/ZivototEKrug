package com.example.zivototekrug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetaliKorisnikActivity extends AppCompatActivity {
    private Button prifati;
    private TextView tipUslugaDesno, opisUslugaDesno, datumDesno, povtorlivostDesno, itnostDesno, statusDesno, volonterDesno, rejtingDesno, telefonDesno, emailVolonterDesno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detali_korisnik);
        Intent intent = getIntent();
        String tipNaUsluga = intent.getStringExtra("TipNaUsluga");
        String datum = intent.getStringExtra("Datum");
        String opisNaUsluga = intent.getStringExtra("OpisNaUsluga");
        String povtorlivost = intent.getStringExtra("Povtorlivost");
        String itnost = intent.getStringExtra("Itnost");
        String status = intent.getStringExtra("Status");
        String volonter = intent.getStringExtra("ImeVolonter");
        String rejting = intent.getStringExtra("RejtingVolonter");
        String telefon = intent.getStringExtra("TelefonVolonter");
        String emailVolonter = intent.getStringExtra("EmailVolonter");

        prifati = findViewById(R.id.prifati);
        tipUslugaDesno = findViewById(R.id.tipUslugaDesno);
        opisUslugaDesno = findViewById(R.id.opisUslugaDesno);
        datumDesno = findViewById(R.id.datumDesno);
        povtorlivostDesno = findViewById(R.id.povtorlivostDesno);
        itnostDesno = findViewById(R.id.itnostDesno);
        statusDesno = findViewById(R.id.statusDesno);
        volonterDesno = findViewById(R.id.volonterDesno);
        rejtingDesno = findViewById(R.id.rejtingDesno);
        telefonDesno = findViewById(R.id.telefonDesno);
        emailVolonterDesno = findViewById(R.id.emailVolonterDesno);

        tipUslugaDesno.setText(tipNaUsluga);
        opisUslugaDesno.setText(opisNaUsluga);
        datumDesno.setText(datum);
        povtorlivostDesno.setText(povtorlivost);
        itnostDesno.setText(itnost);
        statusDesno.setText(status);
        volonterDesno.setText(volonter);
        rejtingDesno.setText(rejting);
        telefonDesno.setText(telefon);
        emailVolonterDesno.setText(emailVolonter);

        prifati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}