package com.example.zivototekrug;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListaNarackiActivity extends AppCompatActivity {

    private ArrayList<String> lines = new ArrayList<>();
    private ListView lista;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private String tipNaUsluga, opisNaUsluga, datum, povtorlivost, itnost, status, volonter, rejting, telefon, emailVolonter, rejtingZaVolonter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_naracki);

        lista = findViewById(R.id.lista);

        String emailKorsnik = auth.getCurrentUser().getEmail().toString();
        FirebaseDatabase.getInstance().getReference("KorisnickiNaracki").orderByChild("EmailKorisnik").equalTo(emailKorsnik).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    //Tuka treba da se presmeta rastojanie na korisnici
                    lines.add(postSnapshot.child("TipNaUsluga").getValue().toString());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        ListaNarackiActivity.this, android.R.layout.simple_list_item_1, lines);
                lista.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick (AdapterView < ? > parent, View view, int index, long id){
                String tipUsluga = lines.get(index);
                Intent intent = new Intent(ListaNarackiActivity.this,DetaliKorisnikActivity.class);
                FirebaseDatabase.getInstance().getReference("KorisnickiNaracki").orderByChild("TipNaUsluga").equalTo(tipUsluga).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            tipNaUsluga = postSnapshot.child("TipNaUsluga").getValue().toString();
                            opisNaUsluga = postSnapshot.child("OpisNaUsluga").getValue().toString();
                            datum = postSnapshot.child("Datum").getValue().toString();
                            povtorlivost = postSnapshot.child("Povtorlivost").getValue().toString();
                            itnost = postSnapshot.child("Itnost").getValue().toString();
                            status = postSnapshot.child("Status").getValue().toString();
                            volonter = postSnapshot.child("ImeVolonter").getValue().toString();
                            rejting = postSnapshot.child("RejtingNaVolonter").getValue().toString();
                            telefon = postSnapshot.child("TelefonVolonter").getValue().toString();
                            emailVolonter = postSnapshot.child("EmailVolonter").getValue().toString();
                            rejtingZaVolonter = postSnapshot.child("RejtingZaVolonter").getValue().toString();
                        }
                        intent.putExtra("TipNaUsluga",tipNaUsluga);
                        intent.putExtra("OpisNaUsluga",opisNaUsluga);
                        intent.putExtra("Datum",datum);
                        intent.putExtra("Povtorlivost",povtorlivost);
                        intent.putExtra("Itnost",itnost);
                        intent.putExtra("Status",status);
                        intent.putExtra("ImeVolonter",volonter);
                        intent.putExtra("RejtingNaVolonter",rejting);
                        intent.putExtra("TelefonVolonter",telefon);
                        intent.putExtra("EmailVolonter",emailVolonter);
                        intent.putExtra("RejtingZaVolonter",rejtingZaVolonter);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });
    }
}