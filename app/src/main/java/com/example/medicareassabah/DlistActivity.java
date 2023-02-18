package com.example.medicareassabah;


import static com.google.gson.internal.bind.util.ISO8601Utils.format;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DlistActivity extends AppCompatActivity {
    TextView textView1,textView2,txtqualification,txtExperience,textView5,textView6,stime,etime,
    textView8,textView9;
    EditText edittext1, editText2;
    Calendar mycalendar, mcurrenttime;
    Button dbutton;
    String dayOfTheWeek;
    String url = Config.baseURL+"book.php";
    String patientName,doctorusername, department, qualification, experience, hospital,bookingtime,
    bookingdate, status, error,age,disease,phone,working_time,avday,DoctorPhone,key;
    Spinner spin;
    String spinner,paycharge;
    String mode[]={"Offline payment","Online Payment"};
    String starttime,endtime;
    public SimpleDateFormat dateFormatter;
    public SimpleDateFormat sdf;
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.remainder_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        //handle menu item clicks
//        int id = item.getItemId();
//
//        if (id == R.id.remainder) {
//            Intent intent = new Intent(DlistActivity.this, NotificationActivity.class);
//            startActivity(intent);
//            //do your function here
//
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dlist);

        textView1=findViewById(R.id.duser);
        textView2=findViewById(R.id.ddpt);
        txtqualification=findViewById(R.id.dqu);
        txtExperience=findViewById(R.id.dex);
        textView5=findViewById(R.id.dhos);
        textView6=findViewById(R.id.dphn);
        stime=findViewById(R.id.dstime);
        etime=findViewById(R.id.detime);
        textView8=findViewById(R.id.davd);
        edittext1 = findViewById(R.id.dedittext);
        editText2 = findViewById(R.id.tedittext);
        mycalendar = Calendar.getInstance();
        mcurrenttime = Calendar.getInstance();
        dbutton = findViewById(R.id.dpay);
        spin = findViewById(R.id.modeofpayment);
        textView9 = findViewById(R.id.paycharge);

        ArrayAdapter<String>adapter=new ArrayAdapter<>(DlistActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,mode);
        spin.setAdapter(adapter);

        HashMap<String, String> user = new SessionManager(DlistActivity.this).getUserDetails();
        patientName=user.get("username");
        age=user.get("age");
        phone=user.get("phone");
        disease=user.get("disease");
        key=user.get("ekey");

        Toast.makeText(DlistActivity.this, ""+disease, Toast.LENGTH_SHORT).show();

        Intent in=getIntent();

         doctorusername = in.getStringExtra("username");
         department = in.getStringExtra("department");
         qualification = in.getStringExtra("qualification");
         experience = in.getStringExtra("experience");
         hospital = in.getStringExtra("hospital");
         DoctorPhone=in.getStringExtra("phone");
         starttime = in.getStringExtra("stime");
         endtime = in.getStringExtra("etime");
         avday = in.getStringExtra("available_day");
         paycharge = in.getStringExtra("payment");
//        Toast.makeText(getApplicationContext(), qualification, Toast.LENGTH_SHORT).show();
        textView1.setText("Doctor Name: "+doctorusername);
        textView2.setText("Department: "+department);
        txtqualification.setText("Qualification: "+qualification);
        txtExperience.setText("Experience: "+experience);
        textView5.setText("Hospital Name: "+hospital);
        textView6.setText("DoctorPhone: "+DoctorPhone);
        stime.setText(starttime);
        etime.setText(endtime);
        textView8.setText(avday);
        textView9.setText(paycharge);




        dbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Booking();
            }
        });

        edittext1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.US);
                sdf = new SimpleDateFormat("EEE, d MMM yyyy", Locale.US);
                Calendar calendar = Calendar.getInstance(Locale.getDefault());
                DatePickerDialog datePickerDialog = new DatePickerDialog(DlistActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int dayOfMonth, int month, int year) {
                                //todo
                                Calendar newDate = Calendar.getInstance();
                                newDate.set(dayOfMonth, month, year);
                                edittext1.setText(sdf.format(newDate.getTime()));

                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                calendar.add(Calendar.DATE,0);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());//set minDate
//                calendar.add (Calendar.DATE, 7);
//                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());//set maxdate

                datePickerDialog.show();
            }
        });
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = mcurrenttime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrenttime.get(Calendar.MINUTE);
                TimePickerDialog mTimepicker;
                mTimepicker = new TimePickerDialog(DlistActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        editText2.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);
                mTimepicker.setTitle("select time");
                mTimepicker.show();
            }
        });
    }

    private void Booking() {
//        Toast.makeText(this, textView8.getText().toString(), Toast.LENGTH_SHORT).show();
        bookingdate = edittext1.getText().toString();
        bookingtime = editText2.getText().toString();
        spinner = spin.getSelectedItem().toString();
        starttime = stime.getText().toString();
        endtime = etime.getText().toString();

        try {
            String string1 =starttime;
            Date time1 = new SimpleDateFormat("HH:mm").parse(string1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            calendar1.add(Calendar.DATE, 1);


            String string2 = endtime;
            Date time2 = new SimpleDateFormat("HH:mm").parse(string2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);

            String someRandomTime = bookingtime;
            Date d = new SimpleDateFormat("HH:mm").parse(someRandomTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();
            if (x.before(calendar1.getTime()) && x.after(calendar2.getTime())) {
                //checkes whether the current time is between 14:49:00 and 20:11:13.
                Toast.makeText(getApplicationContext(), "no ,time is not valid", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(),"yes,You can book now" , Toast.LENGTH_SHORT).show();
                books();
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(bookingdate.trim())) {
            edittext1.setError("Please enter username");
            edittext1.requestFocus();
            return;
        } else if (TextUtils.isEmpty(bookingtime.trim())) {
            editText2.setError("Please enter password");
            editText2.requestFocus();
            return;
        }
//        if (textView8.getText().toString().contains(dayOfTheWeek)) {
//
//            Toast.makeText(this, "Date : " + bookingdate, Toast.LENGTH_SHORT).show();
//        }
//        else {
//            edittext1.setError("Invalid day");
//            edittext1.requestFocus();
//            return;
//        }


    }

    private void books() {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(DlistActivity.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject c = new JSONObject(response);
                            status = c.getString("Status");
                            error = c.getString("Error");
                            if (status.equals("0")) {
                                Toast.makeText(DlistActivity.this, error, Toast.LENGTH_SHORT).show();
                            } else {
                                if (spinner.equals("Online Payment")) {
                                    Toast.makeText(DlistActivity.this, "Registration successfull", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(DlistActivity.this, PayActivity.class);
                                    i.putExtra("pay", paycharge);
                                    startActivity(i);
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Booked Succesfully", Toast.LENGTH_SHORT).show();
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DlistActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("dusername", doctorusername);
                map.put("dphone", DoctorPhone);
                map.put("ddepartment", department);
                map.put("dhospital", hospital);
                map.put("pusername",patientName);
                map.put("page",age);
                map.put("pdisease",disease);
                map.put("pphone",phone);
                map.put("booking_date",bookingdate);
                map.put("booking_time",bookingtime);
                map.put("ekey",key);
                map.put("mode",spinner);
                map.put("payment",paycharge);
                return map;

            }

        };
        Volley.newRequestQueue(DlistActivity.this).add(request);
    }




    private void updateLabel1() {
        String myFormat = "dd/MM/yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        sdf.applyPattern("EEE, d MMM yyyy");

        edittext1.setText(sdf.format(mycalendar.getTime()));

         sdf = new SimpleDateFormat("EEE");
        Date d_name = new Date(format(mycalendar.getTime()));
         dayOfTheWeek = sdf.format(d_name);

      // Toast.makeText(this, dayOfTheWeek, Toast.LENGTH_SHORT).show();

    }

}
