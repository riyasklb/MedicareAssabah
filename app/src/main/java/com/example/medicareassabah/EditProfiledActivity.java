package com.example.medicareassabah;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class EditProfiledActivity extends AppCompatActivity {
    EditText user1, pho,dept, hos, qualific, experi, emails, wrkt, avd,gen;
    Button profile, delete,upd;
    String id,username, gender, department, qualification, experience,
    hospital, phone, email, working_time, avdays;
    String username1, gender1, department1, qualification1, experience1,
    hospital1, phone1, email1, working_time1, avdays1;
    String status,error;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profiled);

        HashMap<String, String> user = new DoctorSession(EditProfiledActivity.this).getUserDetails();
        id=user.get("id");
        email = user.get("mail");
        username = user.get("username");
        phone = user.get("phone");
        department = user.get("department");
        gender = user.get("gender");
        qualification = user.get("qualification");
        experience = user.get("experience");
        hospital = user.get("hospital");
        working_time = user.get("working_time");
        avdays = user.get("available_day");


        //Toast.makeText(this, avdays, Toast.LENGTH_SHORT).show();
        user1 = findViewById(R.id.usr);
        pho = findViewById(R.id.phones);
        dept = findViewById(R.id.departs);
        hos = findViewById(R.id.hospital);
        gen=findViewById(R.id.genders);
        qualific = findViewById(R.id.quali);
        experi = findViewById(R.id.expe);
        emails = findViewById(R.id.mails);
        wrkt = findViewById(R.id.workingTime1);
        avd = findViewById(R.id.availableDays1);
        profile = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        upd = findViewById(R.id.doc);
//upd.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        Intent i = new Intent(EditProfiledActivity.this,ImageActivity.class);
//        startActivity(i);
//    }
//});


        user1.setText(username);
        pho.setText(phone);
        dept.setText(department);
        hos.setText(hospital);
        gen.setText(gender);
        qualific.setText(qualification);
        experi.setText(experience);
        emails.setText(email);
        wrkt.setText(working_time);
        avd.setText(avdays);



delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        delete();
    }
});
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });


    }

    private void delete() {
        String url=Config.baseURL+"delete_product1.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject c = new JSONObject(response);
                            status = c.getString("Status");
                            error = c.getString("Error");


                            if (status.equals("0")) {
                                Toast.makeText(EditProfiledActivity.this, error, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EditProfiledActivity.this, "Deletion successfull", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(EditProfiledActivity.this,DoctorHome.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditProfiledActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> m = new HashMap<>();
                m.put("id",id);
                m.put("username", username);
                m.put("mail", email);
                m.put("gender", gender);
                m.put("phone", phone);
                m.put("department", department);
                m.put("qualification", qualification);
                m.put("experience", experience);
                m.put("hospital", hospital);
                m.put("working_time", working_time);
                m.put("available_day", avdays);

                return m;
            }
        };
        Volley.newRequestQueue(EditProfiledActivity.this).add(request);

    }

    private void update() {
        String url = Config.baseURL + "updateProfile2.php";
        username1=user1.getText().toString();
        email1=emails.getText().toString();
        gender1 =gen.getText().toString();
        phone1 = pho.getText().toString();
        department1 =dept.getText().toString();
        qualification1=qualific.getText().toString();
        experience1 =experi.getText().toString();
        hospital1 =hos.getText().toString();
        working_time1 = wrkt.getText().toString();
        avdays1 =avd.getText().toString();
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            try {
                                JSONObject c = new JSONObject(response);
                                status = c.getString("Status");
                                error = c.getString("Error");


                                if (status.equals("0")) {
                                    Toast.makeText(EditProfiledActivity.this, error, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(EditProfiledActivity.this, "Updation successfull", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(EditProfiledActivity.this,DoctorHome.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(EditProfiledActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> m = new HashMap<>();
                    m.put("id",id);
                    m.put("username", username1);
                    m.put("mail", email1);
                    m.put("gender", gender1);
                    m.put("phone", phone1);
                    m.put("department", department1);
                    m.put("qualification", qualification1);
                    m.put("experience", experience1);
                    m.put("hospital", hospital1);
                    m.put("working_time", working_time1);
                    m.put("available_day", avdays1);

                    return m;
                }
            };
            Volley.newRequestQueue(EditProfiledActivity.this).add(request);

        }

        }




