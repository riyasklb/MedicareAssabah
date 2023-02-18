package com.example.medicareassabah;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Emails extends AppCompatActivity {
EditText to,sub,nxtdate,nxttime,users;
String t,s,d,ti,username;
Button btnsend;
Calendar mycalendar,mcurrenttime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emails);
        to=findViewById(R.id.to);
        users=findViewById(R.id.users);
        sub=findViewById(R.id.sub);
        nxtdate=findViewById(R.id.nextdate);
        nxttime=findViewById(R.id.nexttime);
        btnsend=findViewById(R.id.emails);
        mycalendar=Calendar.getInstance();
        mcurrenttime = Calendar.getInstance();
        Intent i=getIntent();
        username=i.getStringExtra("username");

        users.setText(username);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mycalendar.set(Calendar.YEAR, year);
                mycalendar.set(Calendar.MONTH, monthOfYear);
                mycalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel1();

            }
        };
        nxtdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(Emails.this, date, mycalendar
                        .get(Calendar.YEAR), mycalendar.get(Calendar.MONTH),
                        mycalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        nxttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = mcurrenttime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrenttime.get(Calendar.MINUTE);
                TimePickerDialog mTimepicker;
                mTimepicker = new TimePickerDialog(Emails.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        nxttime.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);
                mTimepicker.setTitle("select time");
                mTimepicker.show();
            }
        });

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });

    }

    private void updateLabel1() {
        String myFormat = "dd/MM/yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        nxtdate.setText(sdf.format(mycalendar.getTime()));
    }

    private void send() {
       t=to.getText().toString();
       s=sub.getText().toString();
       d=nxtdate.getText().toString();
       ti=nxttime.getText().toString();

       Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{t});
        email.putExtra(Intent.EXTRA_SUBJECT, s);
        email.putExtra(Intent.EXTRA_TEXT, "Hi " + username + " Next Consulting date scheduled on " + d + "and time will be " + ti);

        //need this to prompts email client only
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));




    }
}