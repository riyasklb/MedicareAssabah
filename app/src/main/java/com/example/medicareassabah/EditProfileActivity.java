package com.example.medicareassabah;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class EditProfileActivity extends AppCompatActivity {
    TextView userna,mai,ag,dise,gend,pho;
    Button edit,delete;
    String id,username,email,age,gender,disease,phone,status,error;
    String username1,email1,age1,gender1,disease1,phone1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        HashMap<String, String> user = new SessionManager(EditProfileActivity.this).getUserDetails();
        id = user.get("id");
        username = user.get("username");
        email = user.get("mail");
        phone = user.get("phone");
        age = user.get("age");
        gender = user.get("gender");
        disease = user.get("disease");


        userna = findViewById(R.id.username3);
        pho = findViewById(R.id.phone3);
        mai = findViewById(R.id.mail3);
        edit = findViewById(R.id.up);
        delete = findViewById(R.id.delete);
        gend = findViewById(R.id.gender3);
        ag = findViewById(R.id.age4);
        dise = findViewById(R.id.disease3);

        userna.setText( username);
        mai.setText( email);
        ag.setText( age);
        gend.setText( gender);
        pho.setText( phone);
        dise.setText( disease);



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

    }

    private void delete() {
        String url=Config.baseURL+"delete_product.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            Toast.makeText(EditProfileActivity.this, response, Toast.LENGTH_SHORT).show();
                            JSONObject c = new JSONObject(response);
                            status = c.getString("Status");
                            error = c.getString("Error");
                            if (status.equals("0")) {
                                Toast.makeText(EditProfileActivity.this, error, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EditProfileActivity.this, "Deletion successfull", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(EditProfileActivity.this,HomeActivity.class);
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
                Toast.makeText(EditProfileActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> m = new HashMap<>();
                m.put("id",id);
                m.put("username", username);
                m.put("mail", email);
                m.put("age", age);
                m.put("gender", gender);
                m.put("phone", phone);
                m.put("disease", disease);

                return m;
            }
        };
        Volley.newRequestQueue(EditProfileActivity .this).add(request);

    }

    private void update() {
        String url=Config.baseURL+"updateProfile.php";

        username1=userna.getText().toString();
        email1=mai.getText().toString();
        age1=ag.getText().toString();
        gender1=gend.getText().toString();
        phone1=pho.getText().toString();
        disease1=dise.getText().toString();
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Toast.makeText(EditProfileActivity.this, response, Toast.LENGTH_SHORT).show();
                                JSONObject c = new JSONObject(response);
                                status = c.getString("Status");
                                error = c.getString("Error");


                                if (status.equals("0")) {
                                    Toast.makeText(EditProfileActivity.this, error, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(EditProfileActivity.this, "Updation successfull", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(EditProfileActivity.this,HomeActivity.class);
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
                    Toast.makeText(EditProfileActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> m = new HashMap<>();
                    m.put("id",id);
                    m.put("username", username1);
                    m.put("mail", email1);
                    m.put("age", age1);
                    m.put("gender", gender1);
                    m.put("phone", phone1);
                    m.put("disease", disease1);
                    return m;
                }
            };
            Volley.newRequestQueue(EditProfileActivity .this).add(request);

        }

    }



