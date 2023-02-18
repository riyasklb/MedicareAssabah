package com.example.medicareassabah;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateKeyActivity extends AppCompatActivity {
    EditText EdtcodeBox,EdtcodeTime,etdate;
    Button nextBtn;
    String code,time,phone,dated,key,status,error,duser,codes;
    TextView click;
    Calendar mycalendar,mcurrenttime;
    String url=Config.baseURL+"updatescode.php";
    String url2=Config.baseURL+"fetchcode.php";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_key);



        EdtcodeBox = findViewById(R.id.EdtcodeBox);
        EdtcodeTime = findViewById(R.id.EdtcodeTime);
        etdate = findViewById(R.id.Edtcodedate);
        nextBtn = findViewById(R.id.nextBtn);
        click = findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              fetchcode();
            }
        });

        Intent intent=getIntent();
        phone=intent.getStringExtra("phone");
        key=intent.getStringExtra("key");
        duser=intent.getStringExtra("duser");

        mycalendar= Calendar.getInstance();
        mcurrenttime=Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mycalendar.set(Calendar.YEAR, year);
                mycalendar.set(Calendar.MONTH, monthOfYear);
                mycalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel1();

            }
        };
        etdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateKeyActivity.this, date1, mycalendar
                        .get(Calendar.YEAR), mycalendar.get(Calendar.MONTH),
                        mycalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        EdtcodeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = mcurrenttime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrenttime.get(Calendar.MINUTE);
                TimePickerDialog mTimepicker;
                mTimepicker = new TimePickerDialog(CreateKeyActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        EdtcodeTime.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);
                mTimepicker.setTitle("select time");
                mTimepicker.show();
            }
        });

        
        
        
        
        
        
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                setsms();
                

            }
        });





    }

    private void fetchcode() {
        StringRequest request = new StringRequest(Request.Method.POST, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject c = new JSONObject(response);
                            status = c.getString("Status");
                            error = c.getString("Error");
                            codes=c.getString("code");


                            if (status.equals("0")) {
                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                i.putExtra("fcode",codes);
                                startActivity(i);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> m = new HashMap<>();
                m.put("ekey",key);
                m.put("duser",duser);
                return m;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);



    }

    private void updateLabel1() {
        String myFormat = "dd/MM/yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        etdate.setText(sdf.format(mycalendar.getTime()));
    }

    private void setsms() {


        code = EdtcodeBox.getText().toString();
        time = EdtcodeTime.getText().toString();
        dated = etdate.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject c = new JSONObject(response);
                            status = c.getString("Status");
                            error = c.getString("Error");


                            if (status.equals("0")) {
                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Sending..", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), BookingList.class);
                                checkPermission();
                                startActivity(i);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> m = new HashMap<>();
                m.put("ekey",key);
                m.put("code",code);
                m.put("duser",duser);

                return m;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);

        
    }

    public void checkPermission()
    {
        if (ContextCompat.checkSelfPermission(CreateKeyActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(CreateKeyActivity.this, new String[] { Manifest.permission.SEND_SMS }, 1);
        }
        else {
            sendOTP();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendOTP();
            }
            else {
                Toast.makeText(CreateKeyActivity.this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void sendOTP() {



        String msg = "Dear user  " + "\n" + "Video consulting is scheduled at  " + dated   + " and time " +  time + "\n" + " Security code will be : " + code + "  for videocall " ;

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phone, null, msg, null, null);


    }

}