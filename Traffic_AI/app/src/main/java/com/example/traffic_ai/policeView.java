package com.example.traffic_ai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListAdapter;
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

public class policeView extends AppCompatActivity {
    ListView lv;
    private Handler handler;
    String url="";
    SharedPreferences sharedPreferences;
    ArrayList<String> name,department,email,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_view);
        lv=(ListView) findViewById(R.id.listview);
        sharedPreferences=getSharedPreferences("", Context.MODE_PRIVATE);
        url="http://"+sharedPreferences.getString("ip","")+":5000/pview";
    }

    void p_View(){
        RequestQueue rq= Volley.newRequestQueue(policeView.this);

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    Toast.makeText(getApplicationContext(),jsonArray.toString(), Toast.LENGTH_LONG).show();
                    if (jsonArray.length() > 0)
                    {
                        name = new ArrayList<String>();
                        department = new ArrayList<String>();
                        phone= new ArrayList<String>();
                        email = new ArrayList<String>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jo = jsonArray.getJSONObject(i);

                            name.add(jo.getString("name"));
                            department.add(jo.getString("dep"));
                            phone.add(jo.getString("mobnum"));
                            email.add(jo.getString("email"));
                        }

                        lv.setAdapter((ListAdapter) new pArray(getApplicationContext(),name,department,email,phone));
                        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();

//                        lv.setOnItemClickListener((AdapterView.OnItemClickListener) view_student.this);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(policeView.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        rq.add(stringRequest);

    }
}