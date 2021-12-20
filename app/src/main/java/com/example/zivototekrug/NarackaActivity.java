package com.example.zivototekrug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class NarackaActivity extends AppCompatActivity {

    private EditText tipUsluga;
    private EditText opisUsluga;
    private Spinner vremetraenjeSpinner;
    private Spinner itnostSpinner;
    private Button lokacija;
    private Button logout;
    private Button potvrdi;
    private Button listaNaracki;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naracka);

        auth = FirebaseAuth.getInstance();

        tipUsluga = findViewById(R.id.tipUslugaText);
        opisUsluga = findViewById(R.id.opisUslugaText);
        vremetraenjeSpinner = findViewById(R.id.vremetraenjeSpinner);
        itnostSpinner = findViewById(R.id.itnostSpinner);
        lokacija = findViewById(R.id.lokacija);
        logout = findViewById(R.id.logout);
        potvrdi = findViewById(R.id.potvrdi);
        listaNaracki = findViewById(R.id.listaNaracki);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(NarackaActivity.this,MainActivity.class));
            }
        });
        HashMap<String, Object> map = new HashMap<>();
        map.put("Email", auth.getCurrentUser().getEmail().toString());
        if(vremetraenjeSpinner.getSelectedItem().equals("Еднократно")){
            map.put("Vremetraenje","Ednokratno");
        }else{
            map.put("Vremetraenje","Povekekratno");
        }
        if(itnostSpinner.getSelectedItem().equals("Да")){
            map.put("Itnost","Da");
        }else{
            map.put("Itnost","Ne");
        }

        potvrdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.put("TipNaUsluga",tipUsluga.getText().toString());
                map.put("OpisNaUsluga",opisUsluga.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("KorisnickiNaracki").push().updateChildren(map);
            }
        });

        listaNaracki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NarackaActivity.this,ListaNarackiActivity.class));
            }
        });
    }
}