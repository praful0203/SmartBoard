package com.example.prafulmishra.smartboard_alpha;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class NoticeActivity extends AppCompatActivity {

    private static final String URL = "http://smartboard.cf/app/disp_notice.php";
    ProgressDialog pgDialog;
    TextView lblId, lblNotice, lblTime;
    RecyclerView listNotice;
    List<ListData> listItems;
    int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        lblId = findViewById(R.id.lblId);
        lblNotice = findViewById(R.id.lblNotice);
        lblTime = findViewById(R.id.lblTime);
        listNotice = (RecyclerView)findViewById(R.id.noticelist);
        listNotice.setLayoutManager(new LinearLayoutManager(this));
        pgDialog = new ProgressDialog(NoticeActivity.this);
        pgDialog.setMessage("Loading...");
        pgDialog.setCancelable(false); //By default is true
        pgDialog.show(); //Never use this in doInBackground
        new RequestData().execute();


    }

    class RequestData extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listItems = new ArrayList<>();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            StringRequest request = new StringRequest(URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("RESPONSE_DATA:", response);
                //Toast.makeText(NoticeActivity.this, "Data Fetched!\n"+response, Toast.LENGTH_LONG).show();
                //lblData.setText(response);
                try {
                    JSONObject jo = new JSONObject(response);
                    for (int i = 0; i<jo.length();i++)
                    {
                        jo = new JSONArray().getJSONObject(i);
                        ListData listData = new ListData(jo.getString("notice_id"),jo.getString("notice"),jo.getString("time"));

                        listItems.add(listData);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listNotice.setAdapter(new NoticeAdapter(NoticeActivity.this,listItems));
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NoticeActivity.this, "No Response!", Toast.LENGTH_SHORT).show();

            }
        });

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(request);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pgDialog.dismiss();
        }
    }

}