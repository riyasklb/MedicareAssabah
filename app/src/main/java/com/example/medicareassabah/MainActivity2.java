package com.example.medicareassabah;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity2 extends AppCompatActivity {
    TextView secretCodeBox;
    Button joinBtn;
    String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        secretCodeBox = findViewById(R.id.codeBox1);
        joinBtn = findViewById(R.id.joinBtn1);
        URL serverURL;


//        try {
//            serverURL = new URL("https://meet.jit.si");
//            JitsiMeetConferenceOptions defaultOptions =
//                    new JitsiMeetConferenceOptions.Builder()
//                            .setServerURL(serverURL)
//                            .setWelcomePageEnabled(false)
//                            .build();
//            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        joinBtn.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
//                        .setRoom(secretCodeBox.getText().toString())
//                        .setWelcomePageEnabled(false)
//                        .build();
//
//                JitsiMeetActivity.launch(MainActivity2.this, options);
//            }
//        } );

    }
}