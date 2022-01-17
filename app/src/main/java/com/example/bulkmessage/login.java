package com.example.bulkmessage;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends AppCompatActivity {
    EditText nEdtpass, nEdtemail,nEdtLogincompanyid;
    Button nbtlogin;
    private FirebaseAuth fAuth;

    TextView ncreatebtn;
    FirebaseFirestore fstore;

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 5000* 1000;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel("my notification2","My notification2", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }



        nEdtpass = (EditText) findViewById(R.id.EdtPass);
        nEdtemail = (EditText) findViewById(R.id.EdtEmail);
        nbtlogin = (Button) findViewById(R.id.Btlogin);
        ncreatebtn = findViewById(R.id.createbtn);


        fAuth = FirebaseAuth.getInstance();




        nbtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
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
                fAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(login.this, "successfully logged in", Toast.LENGTH_SHORT).show();


//                        checkUserAccessLevel(authResult.getUser().getUid());

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login.this, "Unable to login", Toast.LENGTH_SHORT).show();


                    }
                });


            }

        });

        ncreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });


    }
    private void checkExistence(){
        final String UserEnteredcompanyid = nEdtLogincompanyid.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkCompany = reference.orderByChild("companyId").equalTo(UserEnteredcompanyid);
        checkCompany.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                if (snapshot.exists()){
//                    sendSMSMessage();


                }else{
//                    Toast.makeText(com.example.bargraph.login.this, "kwani hujui jokes buda", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }


//
//    protected void sendSMSMessage() {
//
//
//
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.SEND_SMS)
//                != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.SEND_SMS)) {
//            } else {
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.SEND_SMS},
//                        MY_PERMISSIONS_REQUEST_SEND_SMS);
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    final String UserEnteredcompanyid = nEdtLogincompanyid.getText().toString();
//                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
//                    Query checkCompany = reference.orderByChild("companyId").equalTo(UserEnteredcompanyid);
//                    checkCompany.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
//                            String companyNumber = snapshot.child(UserEnteredcompanyid).child("companyName1").getValue(String.class);
//                            String Message ="Congratulations your company has been picked for funding." +
//                                    " We will contact you through mathewmbugua2015@gmail.com ";
//
//                            SmsManager smsManager = SmsManager.getDefault();
//                            smsManager.sendTextMessage(companyNumber, null, Message, null, null);
//                            Toast.makeText(getApplicationContext(), "SMS sent.",
//                                    Toast.LENGTH_LONG).show();
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//
//
//
//                } else {
//                    Toast.makeText(getApplicationContext(),
//                            "SMS failed, please try again.", Toast.LENGTH_LONG).show();
//                    return;
//                }
//            }
//        }
//
//    }

//    private void checkUserAccessLevel(String uid) {
//        fstore= FirebaseFirestore.getInstance();
//        DocumentReference df = fstore.collection("users").document(uid);
//        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Log.d("TAG","onSuccess:"+documentSnapshot.getData());
//
//                if (documentSnapshot.getString("isAdmin")!=null){
//                    String loginCompanyId = nEdtLogincompanyid.getText().toString();
//                    Intent intent2 = new Intent(com.example.bargraph.login.this,adminDashboard.class);
//                    intent2.putExtra("LogincompanyId", loginCompanyId );
//                    startActivity(intent2);
//                }else {
//                    String loginCompanyId = nEdtLogincompanyid.getText().toString();
//                    Intent intent1 = new Intent(com.example.bargraph.login.this,businessdashboard.class);
//                    intent1.putExtra("LogincompanyId", loginCompanyId );
//                    startActivity(intent1);
//                }
//
//            }
//        });
//    }
//
//    @Override
//    protected void onResume() {
//        handler.postDelayed(runnable = new Runnable() {
//            public void run() {
//
//
//                final String UserEnteredcompanyid1 = nEdtLogincompanyid.getText().toString();
//                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
//                Query checkCompany = reference.orderByChild("companyId").equalTo(UserEnteredcompanyid1);
//                checkCompany.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
//                        String YESSS="YES";
//                        String Funding = snapshot.child(UserEnteredcompanyid1).child("Funding").getValue(String.class);
//                        if (Funding.equals(YESSS)){
//                            NotificationCompat.Builder builder = new NotificationCompat.Builder(com.example.bargraph.login.this, "my notification2");
//                            builder.setContentTitle("Application confirmation");
//                            builder.setContentText("Congratulations!Your Business has been selected for funding.");
//                            builder.setSmallIcon(R.drawable.addperson);
//                            builder.setAutoCancel(true);
//
//                            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(com.example.bargraph.login.this);
//                            managerCompat.notify(1,builder.build());
//                            checkExistence();
//
//                        }
//
//
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//
//
//
//                handler.postDelayed(runnable, delay);
//            }
//        }, delay);
//        super.onResume();
//    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}