package com.example.medicareassabah;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.droidsonroids.gif.GifImageView;

public class DoctorRegisration extends AppCompatActivity {

    EditText etUserna,etPasswo,etEx,etQual,etHospi, etPho, etMa,wt,WorkigDay,charge,stime,etime,nn;
    Spinner avday;
    TextView upload;
    Button btnRegist;
    RadioGroup radioGend;
    Spinner spinner;
    Calendar mycalendar, mcurrenttime;
    String gender,charg;
    String start,end;
    String dept,status,error,users,workt,availabled;
    String[] days={"Select days","Mon-Sat","Sunday","Monday-wednesday"};
    String[] department = {"-SELECT DEPARTMENT-","Cardiology", "Allergists", "Anesthesiologists", "Dermatologists", "Endocrinologists", "Gastroenterologists",
            "Infectious Disease Specialists", "Gynecologists", "ENT"};
    String url = Config.baseURL+"doctor.php";
    Button mon,tue,wed,thu,fri,sat,sun;
    String a="Mon";
    String b="Tue";
    String c="Wed";
    String d="Thu";
    String e="Fri";
    String f="Sat";
    String g="Sun";
    String abc;
GifImageView gifImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_regisration);
        mon=findViewById(R.id.mon);
        tue=findViewById(R.id.tue);
        wed=findViewById(R.id.wed);
        thu=findViewById(R.id.thu);
        fri=findViewById(R.id.fri);
        sat=findViewById(R.id.sat);
        sun=findViewById(R.id.sun);
        gifImageView=findViewById(R.id.gii);
//        nn=findViewById(R.id.timed);

        etUserna = findViewById(R.id.etUsername);
        etPasswo = findViewById(R.id.etPassword);
        radioGend = findViewById(R.id.radioGender);
        etPho = findViewById(R.id.etPhone);
        etMa = findViewById(R.id.etMail);
        btnRegist = findViewById(R.id.btnRegister);
        WorkigDay = findViewById(R.id.WorkigDay);
        spinner = findViewById(R.id.spinner);
        etQual = findViewById(R.id.etQuali);
        stime=findViewById(R.id.starttime);
        etime=findViewById(R.id.endtime);
        avday=findViewById(R.id.availableDays);
        etHospi = findViewById(R.id.etHospital);
        etEx = findViewById(R.id.etExp);
        charge = findViewById(R.id.charge);
        stime = findViewById(R.id.starttime);
        etime = findViewById(R.id.endtime);

        stime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(DoctorRegisration.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        stime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        etime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(DoctorRegisration.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
//        nn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(DoctorRegisration.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        nn.setText( selectedHour + ":" + selectedMinute);
//                    }
//                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Select Time");
//                mTimePicker.show();
//            }
//        });





        abc=WorkigDay.getText().toString();
        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             WorkigDay.setText(a);

            }
        });
        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abc=WorkigDay.getText().toString();
                WorkigDay.setText(abc+" "+b);

            }
        });
        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abc=WorkigDay.getText().toString();
                WorkigDay.setText(abc+" "+c);
            }
        });
        thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abc=WorkigDay.getText().toString();
                WorkigDay.setText(abc+" "+d);
            }
        });
        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abc=WorkigDay.getText().toString();
                WorkigDay.setText(abc+" "+e);
            }
        });
        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abc=WorkigDay.getText().toString();
                WorkigDay.setText(abc+" "+f);
            }
        });
        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abc=WorkigDay.getText().toString();
                WorkigDay.setText(abc+" "+g);
            }
        });




        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(DoctorRegisration.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, days);
        avday.setAdapter(adapter1);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(DoctorRegisration.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, department);
        spinner.setAdapter(adapter);
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { registerDoctor();
            }
        });

    }

    private void registerDoctor() {
        final String username = etUserna.getText().toString();
        final String day = WorkigDay.getText().toString();
        final String password = etPasswo.getText().toString();
        final String phone = etPho.getText().toString();
        final String mail = etMa.getText().toString();
        final String exp = etEx.getText().toString();
        final String hos = etHospi.getText().toString();
        final String qual = etQual.getText().toString();
//        final String aa = nn.getText().toString();
        start=stime.getText().toString();
        end=etime.getText().toString();
        charg = charge.getText().toString();
        dept = spinner.getSelectedItem().toString();

//        try {
//            String string1 =start;
//            Date time1 = new SimpleDateFormat("HH:mm").parse(string1);
//            Calendar calendar1 = Calendar.getInstance();
//            calendar1.setTime(time1);
//            calendar1.add(Calendar.DATE, 1);
//
//
//            String string2 = end;
//            Date time2 = new SimpleDateFormat("HH:mm").parse(string2);
//            Calendar calendar2 = Calendar.getInstance();
//            calendar2.setTime(time2);
//            calendar2.add(Calendar.DATE, 1);
//
//            String someRandomTime =aa ;
//            Date d = new SimpleDateFormat("HH:mm").parse(someRandomTime);
//            Calendar calendar3 = Calendar.getInstance();
//            calendar3.setTime(d);
//            calendar3.add(Calendar.DATE, 1);
//
//            Date x = calendar3.getTime();
//            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
//                //checkes whether the current time is between 14:49:00 and 20:11:13.
//                Toast.makeText(getApplicationContext(), "yes", Toast.LENGTH_SHORT).show();
//            }
//            else{
//                Toast.makeText(getApplicationContext(), "no", Toast.LENGTH_SHORT).show();
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//

        int id = radioGend.getCheckedRadioButtonId();
        if (id == -1) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
        } else {
            RadioButton rb = radioGend.findViewById(id);
            gender = rb.getText().toString();
        }
        if (dept.equals("-SELECT DEPARTMENT-")) {
            Toast.makeText(this, "Please select department", Toast.LENGTH_SHORT).show();
        }


        if (TextUtils.isEmpty(username.trim())) {
            etUserna.setError("Please enter username");
            etUserna.requestFocus();
            return;
        } else if (TextUtils.isEmpty(password.trim())) {
            etPasswo.setError("Please enter password");
            etPasswo.requestFocus();
            return;
        } else if (TextUtils.isEmpty(phone.trim())) {
            etPho.setError("Please enter phone no");
            etPho.requestFocus();
            return;
        }else if (TextUtils.isEmpty(qual.trim())) {
            etQual.setError("Please enter Qualification");
            etQual.requestFocus();
            return;
        }else if (TextUtils.isEmpty(exp.trim())) {
            etEx.setError("Please enter Experience");
            etEx.requestFocus();
            return;
        }

        else if (TextUtils.isEmpty(hos.trim())) {
            etHospi.setError("Please enter Hospital Name");
            etHospi.requestFocus();
            return;
        } else if (!isPhoneValid(phone)) {
            etPho.setError("Invalid phone no");
            etPho.requestFocus();
            return;
        } else if (TextUtils.isEmpty(mail.trim())) {
            etMa.setError("Please enter mail id");
            etMa.requestFocus();
            return;
        } else if (!isEmailValid(mail)) {
            etMa.setError("Invalid mail id");
            etMa.requestFocus();
            return;
        }




        //connecting to php
gifImageView.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        gifImageView.setVisibility(View.GONE);

                       // Toast.makeText(DoctorRegisration.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject c = new JSONObject(response);
                            status = c.getString("Status");
                            error = c.getString("Error");


                            if (status.equals("0")) {
                                Toast.makeText(DoctorRegisration.this, error, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(DoctorRegisration.this, "Registration successfull", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(DoctorRegisration.this, DoctorLoginActivity.class);
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
                Toast.makeText(DoctorRegisration.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> m = new HashMap<>();
                m.put("username",username);
                m.put("password",password);
                m.put("gender",gender);
                m.put("department",dept);
                m.put("qualification",qual);
                m.put("experience",exp);
                m.put("hospital",hos);
                m.put("phone",phone);
                m.put("mail",mail);
                m.put("stime",start);
                m.put("etime",end);
                m.put("available_day",day);
                m.put("payment",charg);
                return m;
            }
        };
        Volley.newRequestQueue(DoctorRegisration.this).add(request);



    }

    public static boolean isPhoneValid(String s) {
        Pattern p = Pattern.compile("(0/91)?[6-9][0-9]{9}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }
}