package com.example.traffic_ai;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ambulanceVieww extends AppCompatActivity {
    Button b1;
    TextView tv1,tv2,tv3,tv4,tv5,tv6;
    SharedPreferences sh;
    RequestQueue rq;
    String lid="",utype="",url="";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_vieww);
        b1 = (Button) findViewById(R.id.button1);
        tv1 = (TextView) findViewById(R.id.textView2);
        tv2 = (TextView) findViewById(R.id.textView4);
        tv3 = (TextView) findViewById(R.id.textView6);
        tv4 = (TextView) findViewById(R.id.textView8);
        tv5 = (TextView) findViewById(R.id.textView10);
        tv6 = (TextView) findViewById(R.id.textView12);

        sh=getSharedPreferences("", Context.MODE_PRIVATE);
        url="http://"+sh.getString("ip","")+":5000/ApprovePolice";
        Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();

        rq = Volley.newRequestQueue(this);
        String pagetype=getIntent().getStringExtra("pagetype");
        if(pagetype.equalsIgnoreCase("UserApproval"))
        {
            Toast.makeText(getApplicationContext(),(ambulanceView.name.get(ambulanceView.pos).toString()),Toast.LENGTH_LONG).show();

            tv1.setText(ambulanceView.name.get(ambulanceView.pos));
            tv2.setText(ambulanceView.licen.get(ambulanceView.pos));
            tv3.setText(ambulanceView.uname.get(ambulanceView.pos));
            tv4.setText(ambulanceView.email.get(ambulanceView.pos));
            tv5.setText(ambulanceView.phone.get(ambulanceView.pos));
            //tv6.setText(ambulanceView.email.get(ambulanceView.pos));
            //String ip=sh.getString("ip","");

            lid=ambulanceView.ulid.get(ambulanceView.pos);
        }else{
            Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_LONG).show();

            tv1.setText(viewApprovedAmbulance.name.get(viewApprovedAmbulance.pos));
            tv2.setText(viewApprovedAmbulance.licen.get(viewApprovedAmbulance.pos));
            tv3.setText(viewApprovedAmbulance.uname.get(viewApprovedAmbulance.pos));
            tv4.setText(viewApprovedAmbulance.email.get(viewApprovedAmbulance.pos));
            tv5.setText(viewApprovedAmbulance.phone.get(viewApprovedAmbulance.pos));
            //tv6.setText(viewApprovedPolice.email.get(viewApprovedPolice.pos));
            String ip=sh.getString("ip","");


            b1.setVisibility(View.GONE);
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApproveUser();

            }
        });
    }
    private void ApproveUser(){
        RequestQueue rq = Volley.newRequestQueue(ambulanceVieww.this);
        StringRequest ja=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job=new JSONObject(response);
                    String status=job.getString("status");
                    //   Toast.makeText(getApplicationContext(), job.toString(), Toast.LENGTH_SHORT).show();
                    if (status.equalsIgnoreCase("success")){
                        Toast.makeText(getApplicationContext(),"Successfully Approved...",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),adminHome.class);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error..1..."+e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error..1..."+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String>params= new HashMap<String,String>();
                params.put("lid",lid);
                return params;


            }
        };
        rq.add(ja);
    }
}