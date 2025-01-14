package com.example.traffic_ai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class Ambulance extends AppCompatActivity {
    EditText nm,li,ph,em,us,p1,p2;
    Button b1;
    SharedPreferences sh;
    String url="";
    boolean passvisi;
    String name="",us_name="",licen="",phone="",email="",passw="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);
        nm=(EditText) findViewById(R.id.editTextText3);

        li=(EditText) findViewById(R.id.editTextText5);
        ph=(EditText) findViewById(R.id.editTextPhone);
        em=(EditText) findViewById(R.id.editTextTextEmailAddress);
        b1=(Button) findViewById(R.id.button10);

        us=(EditText) findViewById(R.id.ambuser);
        p1=(EditText) findViewById(R.id.password1);
        p2=(EditText) findViewById(R.id.conpass);

        sh=getSharedPreferences("", Context.MODE_PRIVATE);
        url="http://"+sh.getString("ip","")+":5000/aregister";


        p1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX()>=p1.getRight()-p1.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = p1.getSelectionEnd();
                        if(passvisi){
                            // set drawable image here
                            p1.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_off_24,0);

                            // for hide password
                            p1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passvisi=false;
                        }
                        else{
                            p1.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibility_black_24dp,0);

                            // for show password
                            p1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passvisi=true;
                        }
                        p1.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });


        p2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String ppassword=p1.getText().toString();
                String cpassword=s.toString();
                if (ppassword.equals(cpassword)) {
                    // Passwords match, no need to show an error message
                    p2.setError(null);
                } else {
                    // Passwords don't match, show error message
                    p2.setError("Passwords don't match");
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=nm.getText().toString();
                licen=li.getText().toString();
                phone=ph.getText().toString();
                email=em.getText().toString();
                us_name=us.getText().toString();
                passw=p2.getText().toString();

                RequestQueue requestQueue= Volley.newRequestQueue(Ambulance.this);
                //url="http://"+sh.getString("ip", "")+":5000/aregister";
                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.getString("result").equalsIgnoreCase("success")) {
                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), login.class));
                            }
                            else
                                Toast.makeText(getApplicationContext(), "Not success", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("name", name);

                        params.put("licen",licen);
                        params.put("mob",phone);
                        params.put("email",email);
                        params.put("uname",us_name);
                        params.put("pass",passw);

                        return params;
                    }
                };
                requestQueue.add(stringRequest);


            }
        });
    }
}