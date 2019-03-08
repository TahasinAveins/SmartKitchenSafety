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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    private EditText userName, inputEmail, inputPassword;
    private Button btnSignIn, btnReg;

    private FirebaseAuth auth;

    String uname;
    String uemail ;
    String upassWord ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();

        setupViewsId();

        registrationBtnSetup();
        signInBtnSetup();
    }

    public void setupViewsId(){

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnReg = (Button) findViewById(R.id.reg_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        userName = (EditText) findViewById(R.id.userName);

    }

    public Boolean validation(){
        Boolean checkResult = false;

        uname = userName.getText().toString();
        uemail = inputEmail.getText().toString();
        upassWord = inputPassword.getText().toString();

        if (uname.isEmpty() || uemail.isEmpty() || upassWord.isEmpty())
        {
            Toast.makeText(this, "Please fill up all fields", Toast.LENGTH_SHORT).show();
        }
        else {
            checkResult = true;
        }
        return checkResult;
    }


    public void registrationBtnSetup(){

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()){
                    //upload data to database
                    String user_email = inputEmail.getText().toString().trim();
                    String user_password = inputPassword.getText().toString().trim();

                    auth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                sendUserData();
                                Toast.makeText(Registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(Registration.this,MainActivity.class));
                            }
                            else {
                                Toast.makeText(Registration.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                }

            }
        });
    }

    public void signInBtnSetup(){

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this,Login.class));
            }
        });
    }


    private void sendUserData(){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myref = firebaseDatabase.getReference(auth.getUid());

        UserProfile userProfile =  new UserProfile(uname,uemail);
        myref.setValue(userProfile);
    }

}
