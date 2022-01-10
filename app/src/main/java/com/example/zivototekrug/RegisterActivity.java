package com.example.zivototekrug;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText ime;
    private EditText prezime;
    private EditText telefon;
    private RadioGroup radioGrupa;
    private RadioButton radioButton;
    private Button register;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        ime = findViewById(R.id.ime);
        prezime = findViewById(R.id.prezime);
        telefon = findViewById(R.id.telefon);
        radioGrupa = findViewById(R.id.radioGrupa);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_ime = ime.getText().toString();
                String txt_prezime = prezime.getText().toString();
                String txt_telefon = telefon.getText().toString();

                if(TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_password)||TextUtils.isEmpty(txt_ime)||
                        TextUtils.isEmpty(txt_prezime)||TextUtils.isEmpty(txt_telefon)){
                    Toast.makeText(RegisterActivity.this,"Пополнете ги сите полиња",Toast.LENGTH_SHORT).show();
                }else if(txt_password.length()<6){
                    Toast.makeText(RegisterActivity.this,"Кратка лозинка",Toast.LENGTH_SHORT).show();
                }else{
                    auth.createUserWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                HashMap<String,Object> map = new HashMap<>();
                                map.put("Name",txt_ime);
                                map.put("Surname",txt_prezime);
                                map.put("Phone",txt_telefon);
                                map.put("Email",txt_email);
                                map.put("BrojNaRejting",0);
                                map.put("Rejting",0);
                                map.put("SumaRejting",0);
                                String fullName = txt_ime + " " + txt_prezime;
                                map.put("FullName",fullName);
                                int selectedId = radioGrupa.getCheckedRadioButtonId();
                                radioButton = (RadioButton)findViewById(selectedId);
                                if(radioButton.getId() == R.id.postaroLice) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(uid).updateChildren(map);
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName("PostaroLice").build();
                                    user.updateProfile(profileUpdates);
                                    Toast.makeText(RegisterActivity.this, "Успешно се креираше постаро лице", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, NarackaActivity.class));
                                }else if(radioButton.getId() == R.id.volonter){
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(uid).updateChildren(map);
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName("Volonter").build();
                                    user.updateProfile(profileUpdates);
                                    Toast.makeText(RegisterActivity.this,"Успешно се креираше волонтер",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, VolonterActivity.class));
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Неуспешно креирање на корисник", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}