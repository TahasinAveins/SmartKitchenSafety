package com.example.smartkitchensafty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Unsafe extends AppCompatActivity {

    Button back_BTN;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unsafe);

        back_BTN = (Button)findViewById(R.id.back);


        back_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Unsafe.this,Main2Activity.class));
            }
        });
    }
}
