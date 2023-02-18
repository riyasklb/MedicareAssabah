package com.example.medicareassabah;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class DoctorLoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvForgot, tvRegister;
    String status, error;
    String url = Config.baseURL+"doctorlogin.php";
    String id,username,gender,department,qualification,experience,hospital,phone,email,working_time,available_days;
GifImageView gifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        etUsername = findViewById(R.id.etDusername);
        etPassword = findViewById(R.id.etDpassword);
        btnLogin = findViewById(R.id.btnDlogin);
//        tvForgot = findViewById(R.id.tvForgot);
        tvRegister = findViewById(R.id.tvDregister);
        gifImageView = findViewById(R.id.gii);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDoctor();

            }
        });
//        tvForgot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(DoctorLoginActivity.this,ForgotPassword2.class);
//                startActivity(i);
//                finish();
//
//            }
//        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorLoginActivity.this,DoctorRegisration.class);
                startActivity(i);
                finish();

            }
        });

    }

    //validation
    private void loginDoctor() {
        final String user = etUsername.getText().toString();
        final String pass = etPassword.getText().toString();

        if (TextUtils.isEmpty(user.trim())) {
            etUsername.setError("Please enter username");
            etUsername.requestFocus();
            return;
        } else if (TextUtils.isEmpty(pass.trim())) {
            etPassword.setError("Please enter password");
            etPassword.requestFocus();
            return;
        }

gifImageView.setVisibility(View.VISIBLE);
                    StringRequest request = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        gifImageView.setVisibility(View.GONE);
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
                                        Toast.makeText(DoctorLoginActivity.this, error, Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(DoctorLoginActivity.this, "login successfully", Toast.LENGTH_SHORT).show();
                                        new DoctorSession(DoctorLoginActivity.this).createLoginSession(id,username,gender,department,qualification,experience,hospital,phone,email,working_time,available_days);
                                        //Toast.makeText(DoctorLoginActivity.this, id+username+gender+department+qualification+experience+hospital+phone+email+working_time+available_days, Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(DoctorLoginActivity.this,DoctorHome.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DoctorLoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
}






