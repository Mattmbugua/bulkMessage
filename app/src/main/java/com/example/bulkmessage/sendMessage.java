package com.example.bulkmessage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.StringValue;

public class sendMessage extends AppCompatActivity {
    Button nsend;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        nsend=findViewById(R.id.sendMesso);

        nsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMSMessage();
            }
        });
        

    }
    protected void sendSMSMessage() {



        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);

        } else {
            sendTextMsg();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String companyNumber = "0702865690";
                    String Message = "Congratulations your company has been picked for funding." +
                            " We will contact you through mathewmbugua2015@gmail.com ";

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(companyNumber, null, Message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }
    private void sendTextMsg(){
        for ( int i =0;i<3;i++) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            Query checkCompany = reference.orderByChild(String.valueOf(i));
            int finalI = i;
            checkCompany.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String companyNumber = snapshot.child(String.valueOf(finalI)).child("nationalId").getValue(String.class);
                    String name = snapshot.child(String.valueOf(finalI)).child("sname").getValue(String.class);
                    String Message = "Happy Holidays " + name +" from Pecker agro supplies family"+
                            " have a prosperous new year ";
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(companyNumber, null, Message, null, null);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(sendMessage.this, "SMS not sent.Try again.Sir/madam/they", Toast.LENGTH_SHORT).show();

                }
            });


        }

        Toast.makeText(getApplicationContext(), "SMS sent.",
                Toast.LENGTH_LONG).show();
    }
}