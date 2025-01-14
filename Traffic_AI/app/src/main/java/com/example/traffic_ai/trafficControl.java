package com.example.traffic_ai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class trafficControl extends AppCompatActivity implements View.OnClickListener {
    Button b1,b2,b3,b4;
    SharedPreferences sh;
    String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_control);
        b1=(Button) findViewById(R.id.button4);
        b2=(Button) findViewById(R.id.button5);
        b3=(Button) findViewById(R.id.button6);
        b4=(Button) findViewById(R.id.button7);
        sh=getSharedPreferences("", Context.MODE_PRIVATE);
        url="http://"+sh.getString("ip","")+":5000/tControl";

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==b1){
            sendData("A");
        }
        else if(v==b2){
            sendData("B");
        }
        else if(v==b3){
            sendData("C");
        }
        else if(v==b4){
            sendData("D");
        }

    }
    public void sendData(final String data){
        RequestQueue requestQueue= Volley.newRequestQueue(trafficControl.this);
        //url="http://"+sharedPreferences.getString("ip", "")+":5000/control";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equalsIgnoreCase("success"))
                        Toast.makeText(trafficControl.this, "Success", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(trafficControl.this, "Not success", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(trafficControl.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String>  params = new HashMap<String, String>();
                params.put("data", data);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}