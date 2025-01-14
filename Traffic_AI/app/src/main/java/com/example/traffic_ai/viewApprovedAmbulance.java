package com.example.traffic_ai;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewApprovedAmbulance extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv;
    SharedPreferences sh;
    String lid=" ", utype=" " ,url=" ";

    public static int pos;

    public static ArrayList<String> name,email,photo,designation,uname,phone,licen,ulid;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_approved_ambulance);
        lv=(ListView) findViewById(R.id.lv);

        sh=getSharedPreferences("", Context.MODE_PRIVATE);
        url="http://"+sh.getString("ip","")+":5000/viewApproveAmb";



        name=new ArrayList<>();
        email=new ArrayList<>();
        phone=new ArrayList<>();
        //photo=new ArrayList<>();
        uname=new ArrayList<>();
        //designation=new ArrayList<>();
        licen=new ArrayList<>();
        ulid=new ArrayList<>();


        ViewApprovedUsers();

        lv.setOnItemClickListener(this);

    }

    private void ViewApprovedUsers(){
        RequestQueue rq = Volley.newRequestQueue(viewApprovedAmbulance.this);
        StringRequest ja=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job=new JSONObject(response);
                    String status=job.getString("status");
                    Toast.makeText(getApplicationContext(), job.toString(), Toast.LENGTH_SHORT).show();
                    if (status.equalsIgnoreCase("success")){

                        JSONArray jar=job.getJSONArray("results");
                        for(int i=0;i<jar.length();i++)
                        {
                            JSONObject jo=jar.getJSONObject(i);
                            name.add(jo.getString("name"));
                            uname.add(jo.getString("username"));
                            email.add(jo.getString("email"));
                            //designation.add(jo.getString("designation"));
                            licen.add(jo.getString("registerNum"));
                            phone.add(jo.getString("phone"));
                            // photo.add(jo.getString("photo"));
                            ulid.add(jo.getString("login_id"));
                        }

                        ArrayAdapter<String> ad=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,name);
                        lv.setAdapter(ad);

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

                return params;


            }
        };
        rq.add(ja);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pos=position;
        Intent ii=new Intent(getApplicationContext(),ambulanceVieww.class);
        ii.putExtra("pagetype","ApprovedUsers");
        startActivity(ii);
    }
}