package com.example.traffic_ai;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class sampleActivity extends AppCompatActivity {

    EditText name,email,phone;
    SharedPreferences sh;
    Button b1;


    String nname = "", emmail = "", mob = "";
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        name=(EditText) findViewById(R.id.editTextText6);
        phone=(EditText) findViewById(R.id.editTextText7);
        email=(EditText) findViewById(R.id.editTextText8);
        b1=(Button) findViewById(R.id.button11);
        sh=getSharedPreferences("", Context.MODE_PRIVATE);

        url="http://"+sh.getString("ip","")+":5000/reg";


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nname=name.getText().toString();
                emmail=email.getText().toString();
                mob=phone.getText().toString();

                RequestQueue rq= Volley.newRequestQueue(sampleActivity.this);

                StringRequest sq= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.getString("result").equalsIgnoreCase("success")) {
                                Toast.makeText(sampleActivity.this, "Success", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), trafficControl.class));
                            }
                            else
                                Toast.makeText(sampleActivity.this, "Not success", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams()  {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("name", nname);
                        params.put("mail",emmail);
                        params.put("mob",mob);

                        return params;
                    }
                };
                rq.add(sq);


            }
        });
    }
}