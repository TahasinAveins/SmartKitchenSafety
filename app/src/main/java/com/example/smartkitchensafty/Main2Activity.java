package com.example.smartkitchensafty;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private TextView userNameView;

    RequestQueue rq;
    Button state;

    String url_address = "http://iot.uysys.net/app/fg1.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        rq = Volley.newRequestQueue(this);



        state = (Button)findViewById(R.id.button);
        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status();
            }
        });


        auth = FirebaseAuth.getInstance();
        userNameView = (TextView)findViewById(R.id.userNameView);

        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference(auth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);

                userNameView.setText("Welcome " + userProfile.getNameUser());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Main2Activity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void status()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_address, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("status");

                    if (success.equals("1"))
                    {
                        startActivity(new Intent(Main2Activity.this,Safe.class));
                    }

                    else {
                        startActivity(new Intent(Main2Activity.this,Unsafe.class));
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        rq.add(stringRequest);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){


            case(R.id.history):
            {
                Intent intent = new  Intent(Main2Activity.this,MainActivity.class);
                startActivity(intent);
            }


            case(R.id.settingID):
            {
                auth.signOut();
                Intent intent = new  Intent(Main2Activity.this,Login.class);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
