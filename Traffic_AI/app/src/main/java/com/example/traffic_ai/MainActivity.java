package com.example.traffic_ai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText ed1;
    Button b1;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1=(EditText) findViewById(R.id.editTextText);
        b1=(Button) findViewById(R.id.button);
        sh = getSharedPreferences("", Context.MODE_PRIVATE);

        ed1.setText(sh.getString("ip",""));
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip=ed1.getText().toString();
                if(!Patterns.IP_ADDRESS.matcher(ip).matches())
                {
                    ed1.setError("Enter Valid Ip Address");
                }
                else {
                    SharedPreferences.Editor ed=sh.edit();
                    ed.putString("ip",ip);
                    ed.commit();
                    startActivity(new Intent(getApplicationContext(), login.class));
                }

            }
        });
    }
}