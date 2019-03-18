package com.example.smartkitchensafty;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String url_Address = "http://iot.uysys.net/app/fg.php";
    String[] gas_ms;
    String[] time_ms;


    ListView listView;
    Button back_BTN;

    BufferedInputStream is;
    String line= null;
    String result = null;
    int i;

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = (ListView) findViewById(R.id.list_item);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        collectData();

        CustomListView customListView = new CustomListView(this,gas_ms,time_ms);
        listView.setAdapter(customListView);

        back_BTN = (Button)findViewById(R.id.back);


        back_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });

    }


    private void collectData(){
        try
        {
            URL url = new URL(url_Address);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            is = new BufferedInputStream(connection.getInputStream());

        }catch (Exception ex){
            ex.printStackTrace();
        }


        try{

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            while ((line=br.readLine())!= null){

                sb.append(line+"\n");
            }

            is.close();
            result = sb.toString();


        }catch (Exception ex)
        {
            ex.printStackTrace();
        }


        try{

            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;
            gas_ms = new String[ja.length()];
            time_ms = new String[ja.length()];

            for (i=0;i<=ja.length();i++){
                jo = ja.getJSONObject(i);
                gas_ms[i] = jo.getString("gas");
                time_ms[i] = jo.getString("date_time");
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


}
