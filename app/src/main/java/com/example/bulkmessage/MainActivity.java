package com.example.bulkmessage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText nEdtpass, nEdtemail,nEdtLogincompanyid;
    Button nbtlogin;
    TextView ncreatebtn;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nEdtpass = (EditText) findViewById(R.id.EdtPass);
        nEdtemail = (EditText) findViewById(R.id.EdtEmail);
        nbtlogin = (Button) findViewById(R.id.Btlogin);
        ncreatebtn = findViewById(R.id.createbtn);
        fAuth = FirebaseAuth.getInstance();

        nbtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = nEdtemail.getText().toString().trim();
                String password = nEdtpass.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    nEdtemail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    nEdtpass.setError("Enter password");
                    return;
                }
                if (password.length()<6){
                    nEdtpass.setError("Password must have more 6 characters");
                    return;
                }


                fAuth = FirebaseAuth.getInstance();
                fAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(MainActivity.this, "Welcome, Successfully Logged in", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), sendMessage.class));
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}