package com.example.traffic_ai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class policeRegister extends AppCompatActivity {
    EditText nm,li,ph,em,u1,p1,p2;
    Spinner sp;
    boolean passvisi;
    Button b1;
    SharedPreferences sh;
    String url="";
    String name="",pst="",licen="",phone="",email="",item="",usrn="",cpass="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_register);
        nm=(EditText) findViewById(R.id.editTextText3);
        sp=(Spinner) findViewById(R.id.spinnner_name);
        li=(EditText) findViewById(R.id.editTextText5);
        u1=(EditText)findViewById(R.id.ed_username);
        p1=(EditText)findViewById(R.id.ed_password);
        p2=(EditText)findViewById(R.id.ed1_password);
        ph=(EditText) findViewById(R.id.editTextPhone);
        em=(EditText) findViewById(R.id.editTextTextEmailAddress);
        b1=(Button) findViewById(R.id.button10);
        sh=getSharedPreferences("", Context.MODE_PRIVATE);
        url="http://"+sh.getString("ip","")+":5000/pregister";
        //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();

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

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                item=adapterView.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), item+" selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Police Constable");
        arrayList.add("Police Sub-Inspector");
        arrayList.add("Police Circle Inspector");
        arrayList.add("Additional Superintendent of Police ");
        ArrayAdapter<String> adapter=   new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        sp.setAdapter(adapter);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(em.getText().toString())) {
                    em.setError("Email is mandatory");

                }
                else if (TextUtils.isEmpty(nm.getText().toString())) {
                    nm.setError("Password is mandatory");

                }
                else if (TextUtils.isEmpty(li.getText().toString())) {
                    li.setError("Password is mandatory");

                }
                else if (TextUtils.isEmpty(p2.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "password not entered", Toast.LENGTH_LONG).show();

                }
                else if (TextUtils.isEmpty(u1.getText().toString())) {
                    u1.setError("Password is mandatory");

                } else if (TextUtils.isEmpty(ph.getText().toString())) {
                    ph.setError("Password is mandatory");
                }

                else {


                    name = nm.getText().toString();
                    pst=item;
                    usrn=u1.getText().toString();
                    cpass=p2.getText().toString();
                    licen = li.getText().toString();
                    phone = ph.getText().toString();
                    email = em.getText().toString();
                    //Toast.makeText(getApplicationContext(), pst.toString()+"  ::itemm", Toast.LENGTH_LONG).show();
                    RequestQueue requestQueue = Volley.newRequestQueue(policeRegister.this);
                    //url="http://"+sh.getString("ip", "")+":5000/pregister";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("result").equalsIgnoreCase("success")) {
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
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("name", name);
                            params.put("pst",pst);
                            params.put("licen", licen);
                            params.put("mob", phone);
                            params.put("email", email);
                            params.put("uname",usrn);
                            params.put("pass",cpass);

                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);

                }

            }
        });
    }
}