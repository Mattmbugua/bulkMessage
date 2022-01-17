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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    EditText nEdtPass,nEdtEmail,nEdtUserName,nnationalId;
    TextView nloginTxt;


    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    boolean valid = true;
    Button nbtregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//
        nnationalId=findViewById(R.id.EdtnationalId);
        nEdtUserName = (EditText) findViewById(R.id.EdtUserName);
        nEdtEmail = (EditText) findViewById(R.id.EdtEmail);
        nEdtPass = (EditText) findViewById(R.id.EdtPass);
        nloginTxt=findViewById(R.id.loginText);
        nloginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            }
        });
        nbtregister= findViewById(R.id.Btlogin);
        nbtregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadDatabase();


                fAuth=FirebaseAuth.getInstance();
                fstore=FirebaseFirestore.getInstance();
                String email= nEdtEmail.getText().toString().trim();
                String password = nEdtPass.getText().toString().trim();
                String sname = nEdtUserName.getText().toString().trim();
                String nationalId = nnationalId.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    nEdtEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    nEdtPass.setError("Enter password");
                    return;
                }
                if (password.length()<6){
                    nEdtPass.setError("Password must have more 6 characters");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(register.this, "User successfully created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();


                        rootNode = FirebaseDatabase.getInstance();
                        reference = rootNode.getReference("Users");
                        FirebaseUser user = fAuth.getCurrentUser();
                        fstore=FirebaseFirestore.getInstance();
                        fAuth=FirebaseAuth.getInstance();
                        DocumentReference df = fstore.collection("users").document(user.getUid());
                        Map<String,Object> userinfo = new HashMap<>();
                        userinfo.put("Sirname", nEdtUserName.getText().toString() );
                        userinfo.put("email", nEdtEmail.getText().toString() );
                        userinfo.put("password", nEdtPass.getText().toString() );
                        userinfo.put("nationalId", nnationalId.getText().toString() );

                        userinfo.put("isUser", "1" );

                        df.set(userinfo);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(register.this, "Couldn't register said user", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    public void uploadDatabase(){
        String [] numbers ={"0714558223","0702865690","0708818702"};

        for (int i=0;i<numbers.length;i++){
            reference = FirebaseDatabase.getInstance().getReference();
            String email= nEdtEmail.getText().toString().trim();
            String password = nEdtPass.getText().toString().trim();
            String sname = nEdtUserName.getText().toString().trim();
            String nationalId = numbers[i];


            Userhelperclass helperclass = new Userhelperclass(sname,email,password,nationalId);
            reference.child("Users").child(String.valueOf(i)).setValue(helperclass);

        };





    }
}