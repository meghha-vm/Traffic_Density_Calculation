package com.example.traffic_ai;

import androidx.appcompat.app.AppCompatActivity;

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

public class policeDetails extends AppCompatActivity {
    Button b1;
    TextView tv1,tv2,tv3,tv4,tv5,tv6;
    SharedPreferences sh;
    RequestQueue rq;
    String lid="",utype="",url="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_details);
        b1 = (Button) findViewById(R.id.button1);
        tv1 = (TextView) findViewById(R.id.textView2);
        tv2 = (TextView) findViewById(R.id.textView4);
        tv3 = (TextView) findViewById(R.id.textView6);
        tv4 = (TextView) findViewById(R.id.textView8);
        tv5 = (TextView) findViewById(R.id.textView10);
        tv6 = (TextView) findViewById(R.id.textView12);

        sh=getSharedPreferences("", Context.MODE_PRIVATE);
        url="http://"+sh.getString("ip","")+":5000/ApprovePolice";

        rq = Volley.newRequestQueue(this);

        String pagetype=getIntent().getStringExtra("pagetype");
        if(pagetype.equalsIgnoreCase("UserApproval"))
        {
            Toast.makeText(getApplicationContext(),(policeViewPage.name.get(policeViewPage.pos).toString()),Toast.LENGTH_LONG).show();

            tv1.setText(policeViewPage.name.get(policeViewPage.pos));
            tv2.setText(policeViewPage.designation.get(policeViewPage.pos));
            tv3.setText(policeViewPage.licen.get(policeViewPage.pos));
            tv4.setText(policeViewPage.uname.get(policeViewPage.pos));
            tv5.setText(policeViewPage.phone.get(policeViewPage.pos));
            tv6.setText(policeViewPage.email.get(policeViewPage.pos));
            //String ip=sh.getString("ip","");

            lid=policeViewPage.ulid.get(policeViewPage.pos);
        }else{
            Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_LONG).show();

            tv1.setText(viewApprovedPolice.name.get(viewApprovedPolice.pos));
            tv2.setText(viewApprovedPolice.designation.get(viewApprovedPolice.pos));
            tv3.setText(viewApprovedPolice.licen.get(viewApprovedPolice.pos));
            tv4.setText(viewApprovedPolice.uname.get(viewApprovedPolice.pos));
            tv5.setText(viewApprovedPolice.phone.get(viewApprovedPolice.pos));
            tv6.setText(viewApprovedPolice.email.get(viewApprovedPolice.pos));
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