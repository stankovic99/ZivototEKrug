package com.example.zivototekrug;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Scanner;

public class ListaNarackiActivity extends AppCompatActivity {

    private ArrayList<String> lines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_naracki);
        //FirebaseDatabase.getInstance().getReference().child("KorisnickiNaracki").orderByChild("Email").equalTo("TipNaUsluga");
    }
}