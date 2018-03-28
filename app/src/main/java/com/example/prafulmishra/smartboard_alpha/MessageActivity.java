package com.example.prafulmishra.smartboard_alpha;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MessageActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 0;
    ProgressBar pbar;
    TextView lblResponse;
    String voice;
    ImageButton imgBtnVoice,imgBtnNotice;
    Button btnSend;
    EditText edtxtMessage;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        imgBtnVoice = findViewById(R.id.imgBtnVoice);
        imgBtnNotice = findViewById(R.id.imgBtnNotice);
        btnSend = findViewById(R.id.btnSend);
        edtxtMessage = findViewById(R.id.edtxtMessage);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
                Snackbar snackbar = Snackbar
                        .make(view, "Your notice is Being Displayed", Snackbar.LENGTH_LONG);
                snackbar.show();
                edtxtMessage.setText("");

            }
        });

        imgBtnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForPermission();
                Snackbar snackbar = Snackbar
                        .make(view, "Your notice is Being Displayed", Snackbar.LENGTH_LONG);

                snackbar.show();



            }
        });

        imgBtnNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),NoticeActivity.class);
                startActivity(intent);
            }
        });

    }

    private void sendMessage() {
        String msg = edtxtMessage.getText().toString();
        if (msg.equals(""))
        {
            edtxtMessage.setError("Notice cannot be empty!");
        }
        else
        {
            OkHttpClient client = new OkHttpClient(); //for display
            OkHttpClient client_db = new OkHttpClient(); //fordatabase
            String url_db = "http://smartboard.cf/add_notice.php?notice=+"+msg+"";
            String url = "http://192.168.43.143/&MSG=" + msg + "/&";

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        final String myResponse = response.body().string();

                       /* MessageActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            lblResponse.setText(myResponse);
                            }
                        });*/
                    }
                }
            });

            Request request_db = new Request.Builder()
                    .url(url_db)
                    .build();

            client_db.newCall(request_db).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String myResponse = response.body().string();
                       /* MessageActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            lblResponse.setText(myResponse);
                            }
                        });*/
                    }
                }
            });
        }

    }

    private void sendVoiceMessage() {

        OkHttpClient client1 = new OkHttpClient();
        OkHttpClient client_db = new OkHttpClient();
        String url = "http://192.168.43.143/&MSG=" + voice + "/&";
        String url_db = "http://smartboard.cf/add_notice.php?notice=+"+voice+"";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client1.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                }
            }
        });

        Request request_db = new Request.Builder()
                .url(url_db)
                .build();

        client_db.newCall(request_db).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                }
            }
        });

    }
    private void checkForPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MessageActivity.this,
                    Manifest.permission.RECORD_AUDIO)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MessageActivity.this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_REQUEST_RECORD_AUDIO);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            startRecognition();

        }
    }

    private void startRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Read the Notice to be Displayed...");
        try
        {
            startActivityForResult(intent, 100);
        }
        catch (ActivityNotFoundException a) {
            Toast.makeText(this, "Device not supports speech to text!", Toast.LENGTH_LONG).show();
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.msgView
                    ), "Your notice is Displayed", Snackbar.LENGTH_LONG);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 100:
                if (resultCode == RESULT_OK && data!=null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    voice = result.get(0);
                    if (voice.equals("")) {
                        Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show();
                    } else {
                        sendVoiceMessage();
                        Toast.makeText(this, "Notice sent:" + voice, Toast.LENGTH_LONG).show();

                    }
                }
                break;

        }
    }
}