package com.example.medicareassabah;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;


public class PatientLoginActivity extends AppCompatActivity {
    EditText etUserna,etPas;
    Button btnLog;
    TextView forg,tvRegis;
    String status,user,pass,error,email,gender,age,username,disease,id,phone,blood,key,password;
    String[] iam={"Click here to choose","Patient","Doctor"};
    String purl=Config.baseURL+"login.php";
    String durl=Config.baseURL+"doctorlogin.php";
    Spinner spinner;
    String location,department,qualification,experience,hospital,working_time,available_days;
    private static ProgressDialog mProgressDialog;

    GifImageView gifImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientlogin);

        etUserna=findViewById(R.id.etPausername);
        etPas=findViewById(R.id.etPapassword);
        btnLog=findViewById(R.id.btnPlogin);
        forg=findViewById(R.id.tvForgot);
        tvRegis=findViewById(R.id.tvParegister);
        gifImageView=findViewById(R.id.gii);
//        spinner=findViewById(R.id.spins);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>( this,R.layout.support_simple_spinner_dropdown_item,iam);
//        spinner.setAdapter(adapter);


        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });
//        forg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            Intent i = new Intent(PatientLoginActivity.this,ForgotPassword.class);
//            startActivity(i);
//            finish();
//            }
//        });
        tvRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PatientLoginActivity.this,PatientRegistration.class);
                startActivity(i);
                finish();

            }
        });


    }

    //validation
    private void login() {
         user = etUserna.getText().toString();
         pass = etPas.getText().toString();
//        showSimpleProgressDialog(this, "Loading...", "data", false);
        gifImageView.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, purl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            removeSimpleProgressDialog();
                            gifImageView.setVisibility(View.GONE);
                            JSONObject c = new JSONObject(response);
                            status = c.getString("Status");
                            error = c.getString("Error");
                            id= c.getString("id");
                            username= c.getString("username");
                            password= c.getString("password");
                            age= c.getString("age");
                            gender= c.getString("gender");
                            phone= c.getString("phone");
                            disease= c.getString("disease");
                            blood=c.getString("blood_grp");
                            email= c.getString("mail");
                            key=c.getString("ekey");
                            location=c.getString("location");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (status.equals("0")){
                            Toast.makeText(PatientLoginActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                        else {


                            Toast.makeText(PatientLoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            new SessionManager(PatientLoginActivity.this).createLoginSession(id,username,age,gender,phone,disease,blood,email,key,location);
                            //Toast.makeText(PatientLoginActivity.this, id+username+age+gender+phone+disease+blood+email+key, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(PatientLoginActivity.this,UserHomeActivity.class);
//                        i.putExtra("mail", email);
//                        i.putExtra("username", username);
//                        i.putExtra("age", age);
//                        i.putExtra("gender", gender);
//                        i.putExtra("disease", disease);
//                        i.putExtra("phone", phone);
//                        i.putExtra("blood_grp", blood);
//                        i.putExtra("id", id);
                            Toast.makeText(PatientLoginActivity.this,username,Toast.LENGTH_SHORT).show();
                            startActivity(i);
                            finish();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        removeSimpleProgressDialog();
                        Toast.makeText(PatientLoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("username", user);
                map.put("password",pass);
                return map;

            }
        };
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(request);
    }

    private void loginDoctor() {
        StringRequest request = new StringRequest(Request.Method.POST, durl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            removeSimpleProgressDialog();
                            JSONObject c = new JSONObject(response);
                            status = c.getString("Status");
                            error = c.getString("error");
                            id= c.getString("id");
                            username= c.getString("username");
                            gender= c.getString("gender");
                            department= c.getString("department");
                            qualification= c.getString("qualification");
                            experience= c.getString("experience");
                            hospital= c.getString("hospital");
                            phone= c.getString("phone");
                            email= c.getString("mail");
                            working_time=c.getString("working_time");
                            available_days= c.getString("available_day");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if("0".equals(status))
                        {
                            Toast.makeText(PatientLoginActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(PatientLoginActivity.this, "login successfully", Toast.LENGTH_SHORT).show();
                            new DoctorSession(PatientLoginActivity.this).createLoginSession(id,username,gender,department,qualification,experience,hospital,phone,email,working_time,available_days);
                            //Toast.makeText(DoctorLoginActivity.this, id+username+gender+department+qualification+experience+hospital+phone+email+working_time+available_days, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(PatientLoginActivity.this,DoctorHome.class);
                            startActivity(i);
                            finish();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        removeSimpleProgressDialog();
                        Toast.makeText(PatientLoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("username", user);
                map.put("password", pass);
                return map;

            }
        };
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(request);
    }
    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            Log.e("Log", "inside catch IllegalArgumentException");
            ie.printStackTrace();

        } catch (RuntimeException re) {
            Log.e("Log", "inside catch RuntimeException");
            re.printStackTrace();
        } catch (Exception e) {
            Log.e("Log", "Inside catch Exception");
            e.printStackTrace();
        }

    }
    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


