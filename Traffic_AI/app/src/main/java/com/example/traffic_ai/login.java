package com.example.traffic_ai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class login extends AppCompatActivity {

    Button b1,b2;
    SharedPreferences sh;
    String url="";
    boolean passvisi;
    EditText ed1,ed2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b1=(Button) findViewById(R.id.button2);
        b2=(Button) findViewById(R.id.button3);
        ed1=(EditText) findViewById(R.id.editTextText2);
        ed2=(EditText) findViewById(R.id.editTextTextPassword2);
        sh=getSharedPreferences("", Context.MODE_PRIVATE);
        url="http://"+sh.getString("ip","")+":5000/login";
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SELECTION.class));

            }
        });

        ed2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX()>=ed2.getRight()-ed2.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = ed2.getSelectionEnd();
                        if(passvisi){
                            // set drawable image here
                            ed2.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_off_24,0);

                            // for hide password
                            ed2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passvisi=false;
                        }
                        else{
                            ed2.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibility_black_24dp,0);

                            // for show password
                            ed2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passvisi=true;
                        }
                        ed2.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }


        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(ed1.getText().toString())) {
                    ed1.setError("username is mandatory");
                }
                else if (TextUtils.isEmpty(ed2.getText().toString())) {
                    Toast.makeText(login.this,"password not entered", Toast.LENGTH_SHORT).show();
                }
                else{
                final String uname = ed1.getText().toString();
                final String pass = ed2.getText().toString();
                RequestQueue requestQueue = Volley.newRequestQueue(login.this);
                // url="http://"+sh.getString("ip", "")+":5000/control";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(login.this, jsonObject.getString("result"), Toast.LENGTH_LONG).show();

                            if (jsonObject.getString("result").equalsIgnoreCase("success")) {
                                // Toast.makeText(login.this, "Success", Toast.LENGTH_LONG).show();
                                if (jsonObject.getString("type").equalsIgnoreCase("admin")) {
                                    String type = jsonObject.getString("type");
                                    // Toast.makeText(login.this,type , Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), adminHome.class));
                                } else if (jsonObject.getString("type").equalsIgnoreCase("police")) {
                                    String type = jsonObject.getString("type");
                                    Toast.makeText(login.this, type, Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), trafficControl.class));
                                } else if (jsonObject.getString("type").equalsIgnoreCase("ambulance")) {
                                    String type = jsonObject.getString("type");
                                    Toast.makeText(login.this, type, Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), trafficControl.class));
                                }
                                //
                            } else
                                Toast.makeText(login.this, "Not success", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(login.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", uname);
                        params.put("passw", pass);

                        return params;
                    }
                };
                requestQueue.add(stringRequest);

            }
            }
        });
    }

}