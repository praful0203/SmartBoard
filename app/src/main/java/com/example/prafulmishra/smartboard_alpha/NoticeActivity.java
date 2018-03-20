package com.example.prafulmishra.smartboard_alpha;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NoticeActivity extends AppCompatActivity {

    ArrayList<GetNotice> getData = new ArrayList<>();
    ArrayList<String> data = new ArrayList<>();
    TextView lblId, lblNotice, lblTime;
    String idnotice,notice,time;
    ProgressDialog pgDialog;
    RecyclerView lstData;
    LinearLayoutManager layoutManager;
    DataAdapter adapter;
    int success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        lblId = (TextView) findViewById(R.id.lblId);
        lblNotice = (TextView)findViewById(R.id.lblNotice);
        lblTime = (TextView)findViewById(R.id.lblTime);
        new RequestData().execute();
        lstData = (RecyclerView)findViewById(R.id.lstData);
        GestureDetector mGestureDetector;
        AdapterView.OnItemClickListener mListener;


    }

    class RequestData extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pgDialog = new ProgressDialog(NoticeActivity.this);
            pgDialog.setMessage("Loading...");
            pgDialog.setCancelable(false); //By default is true
            pgDialog.show(); //Never use this in doInBackground
            OkHttpClient client = new OkHttpClient();
            String url_db = "http://smartboard.cf/app/disp_notice.php";
            final Request request = new Request.Builder()
                    .url(url_db)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String myResponse = response.body().string();
                        try {
                            while (myResponse!=null)
                            {
                                JSONArray JA = new JSONArray(myResponse);
                                for (int i=0; i < JA.length(); i++)
                                {
                                    JSONObject jo = (JSONObject) JA.get(i);

                                    idnotice = jo.getString("notice_id");
                                    notice = jo.getString("notice");
                                    time = jo.getString("time");
                                    GetNotice gt = new GetNotice(idnotice,notice,time);
                                    getData.add(gt);
                                    Toast.makeText(NoticeActivity.this, ""+gt, Toast.LENGTH_SHORT).show();
                                    //adapter = new DataAdapter();
                                    layoutManager = new LinearLayoutManager(getApplicationContext());
                                    lstData.setLayoutManager(layoutManager);
                                    lstData.setAdapter(adapter);
                                    lstData.setHasFixedSize(true);
                                    adapter.notifyDataSetChanged();

                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(NoticeActivity.this, "Please check your internet connection!", Toast.LENGTH_LONG).show();
                            success = 0;
                        }
                    }
                }
            });

        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pgDialog.dismiss();
        }
    }


}
