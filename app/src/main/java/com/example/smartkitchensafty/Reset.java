package com.example.smartkitchensafty;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Reset extends AppCompatActivity {

    private EditText reg_mail;
    private Button resetPassword;
    private Button backBTN;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        auth = FirebaseAuth.getInstance();

        reg_mail = (EditText)findViewById(R.id.email);
        resetPassword = (Button)findViewById(R.id.btn_reset_password);
        backBTN = (Button)findViewById(R.id.btn_back);


        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Reset.this,Login.class));
            }
        });


        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = reg_mail.getText().toString().trim();

                if (userEmail.isEmpty())
                {
                    Toast.makeText(Reset.this, "Please Enter Registered Email ID", Toast.LENGTH_SHORT).show();
                }
                else {
                    auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Reset.this, "Password Reset Send", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Reset.this,Login.class));
                            }
                            else {
                                Toast.makeText(Reset.this, "Error send", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });
    }

}
