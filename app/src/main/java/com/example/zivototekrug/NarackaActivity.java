package com.example.zivototekrug;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class NarackaActivity extends AppCompatActivity {

    private static final String TAG = "NarackaActivity";
    private static final int PERMISSIONS_FINE_LOCATION = 99;
    private EditText tipUsluga;
    private EditText opisUsluga;
    private Spinner povtorlivostSpinner;
    private Spinner itnostSpinner;
    private Button datum;
    private Button vreme;
    private Button lokacija;
    private Button logout;
    private Button potvrdi;
    private Button listaNaracki;

    private FirebaseAuth auth;

    private int hour, minute, year, month, day;
    private double lat, lon;
    private String celoIme, rejting, telefon, datumVreme;

    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naracka);

        auth = FirebaseAuth.getInstance();

        tipUsluga = findViewById(R.id.tipUslugaText);
        opisUsluga = findViewById(R.id.opisUslugaText);
        povtorlivostSpinner = findViewById(R.id.povtorlivostSpinner);
        itnostSpinner = findViewById(R.id.itnostSpinner);
        lokacija = findViewById(R.id.lokacija);
        datum = findViewById(R.id.datum);
        vreme = findViewById(R.id.vreme);
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
        String emailKorsnik = auth.getCurrentUser().getEmail().toString();
        map.put("EmailKorisnik", emailKorsnik);

        if(povtorlivostSpinner.getSelectedItem().equals("Да")){
            map.put("Povtorlivost","Da");
        }else{
            map.put("Povtorlivost","Ne");
        }
        if(itnostSpinner.getSelectedItem().equals("Да")){
            map.put("Itnost","Da");
        }else{
            map.put("Itnost","Ne");
        }

        lokacija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NarackaActivity.this, LokacijaActivity.class));
            }
        });

        potvrdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Users").orderByChild("Email").equalTo(emailKorsnik).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot datas: snapshot.getChildren()){
                            celoIme = datas.child("FullName").getValue().toString();
                            rejting = datas.child("Rejting").getValue().toString();
                            telefon = datas.child("Phone").getValue().toString();
                        }
                        map.put("TipNaUsluga",tipUsluga.getText().toString());
                        map.put("OpisNaUsluga",opisUsluga.getText().toString());
                        map.put("Lat", lat);
                        map.put("Lon", lon);
                        if (datum.getText().equals("Избери датум") || vreme.getText().equals("Избери време")){
                            if(datum.getText().equals("Избери датум") && vreme.getText().equals("Избери време") == false){
                                datumVreme = vreme.getText().toString();
                            }else if(datum.getText().equals("Избери датум") == false && vreme.getText().equals("Избери време")){
                                datumVreme = datum.getText().toString();
                            }else{
                                datumVreme = "";
                            }
                        }else{
                            datumVreme = datum.getText() + " " + vreme.getText();
                        }
                        map.put("Datum", datumVreme);
                        map.put("ImeKorisnik",celoIme);
                        map.put("RejtingKorisnik",rejting);
                        map.put("EmailVolonter","");
                        map.put("ImeVolonter","");
                        map.put("RejtingVolonter","");
                        map.put("Status","Aktivna");
                        map.put("TelefonKorisnik",telefon);
                        map.put("TelefonVolonter","");
                        FirebaseDatabase.getInstance().getReference().child("KorisnickiNaracki").push().updateChildren(map);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }
        });

        listaNaracki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NarackaActivity.this,ListaNarackiActivity.class));
            }
        });

        datum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        datum.setText(String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year));
                    }
                };
                int style = AlertDialog.THEME_HOLO_DARK;
                DatePickerDialog dialog = new DatePickerDialog(NarackaActivity.this, style, dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setTitle("Set date");
                dialog.show();
            }
        });

        vreme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hour = selectedHour;
                        minute = selectedMinute;
                        vreme.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                    }
                };
                int style = AlertDialog.THEME_HOLO_DARK;
                TimePickerDialog timePickerDialog = new TimePickerDialog(NarackaActivity.this, style, onTimeSetListener, hour, minute, true);
                timePickerDialog.setTitle("Select time");
                timePickerDialog.show();
            }
        });
        updateGPS();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSIONS_FINE_LOCATION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    updateGPS();
                }else{
                    Toast.makeText(NarackaActivity.this, "This app requsts permission to be granted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void updateGPS(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(NarackaActivity.this);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(@NonNull Location location) {
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                }
            });
        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }
        }
    }

}