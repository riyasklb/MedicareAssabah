package com.example.medicareassabah;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Viewpatient extends AppCompatActivity {
    TextView user,phone1,age,disease,bookingdate,bookingtime;
    String patuser,patphone,patage,patdis,patbookd,patbookt;

    Button btnUpload,call,btnDcancel,next;
    TextView textFiles;
    String duser,key;
    String status, message;
    private String upload_URL = Config.baseURL+"image1.php";

    private RequestQueue rQueue;
    private static ProgressDialog mProgressDialog;
    int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpatient);
        user=findViewById(R.id.username3);
        phone1=findViewById(R.id.Phone);
        age=findViewById(R.id.age);
        disease=findViewById(R.id.disease);
        bookingdate=findViewById(R.id.bookdate);
        bookingtime=findViewById(R.id.booktime);
        btnUpload=findViewById(R.id.upload);
        call=findViewById(R.id.call);
        next=findViewById(R.id.next);



        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+patphone));
                startActivity(intent);
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  upload();

            }
        });


        Intent i=getIntent();
        patuser=i.getStringExtra("pusername");
        patphone=i.getStringExtra("pphone");
        patage=i.getStringExtra("page");
        patdis=i.getStringExtra("pdisease");
        patbookd=i.getStringExtra("booking_date");
        patbookt=i.getStringExtra("booking_time");
        key=i.getStringExtra("key");

        user.setText(patuser);
        phone1.setText(patphone);
        age.setText(patage);
        disease.setText(patdis);
        bookingdate.setText(patbookd);
        bookingtime.setText(patbookt);
        btnUpload = findViewById(R.id.upload);
        textFiles = findViewById(R.id.textFiles);
        Toast.makeText(getApplicationContext(), patuser, Toast.LENGTH_SHORT).show();


        HashMap<String ,String>user= new DoctorSession(Viewpatient.this).getUserDetails();
        duser=user.get("username");
        Toast.makeText(getApplicationContext(), duser, Toast.LENGTH_SHORT).show();

        textFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FileList2.class);
                intent.putExtra("username",patuser);
                startActivity(intent);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = count +1;


                //Toast.makeText(Viewpatient.this,"Count :" + count, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), CreateKeyActivity.class);
                    intent.putExtra("phone", patphone);
                    startActivity(intent);


            }
        });
//        if (count==1)
//        {
//            prescription();
//        }

    }


    private void upload() {

        // Intent to pick an image or file to upload
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
//        intent.setType("video/mp4");
//        intent.setType("application/pdf");
        startActivityForResult(intent,1);
    }



    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("nameeeee>>>>  ",displayName);

                        uploadPDF(displayName,uri);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                Log.d("nameeeee>>>>  ",displayName);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);


    }

    private void uploadPDF(final String pdfname, Uri pdffile){

        InputStream iStream = null;
        try {

            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            showSimpleProgressDialog(Viewpatient.this, null, "Uploading image", false);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, upload_URL,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            removeSimpleProgressDialog();
                            Log.d("ressssssoo",new String(response.data));
                            rQueue.getCache().clear();
                            try {

                                JSONObject jsonObject = new JSONObject(new String(response.data));

                                jsonObject.toString().replace("\\\\","");

                                status = jsonObject.getString("status");
                                message = jsonObject.getString("message");

                                if (status.equals("1")) {
                                    Toast.makeText(Viewpatient.this, message, Toast.LENGTH_SHORT).show();
                                   Intent i=new Intent(getApplicationContext(),Emails.class);
                                   i.putExtra("username",patuser);
                                   startActivity(i);

                                    if (!patphone.equals("")) {
                                        checkPermission();

                                    }
                                }
                                else {
                                    Toast.makeText(Viewpatient.this, message, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            removeSimpleProgressDialog();
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {

                /*
                 * If you want to add more parameters with the image
                 * you can do it here
                 * here we have only one parameter with the image
                 * which is tags
                 * */
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //add string parameters
                   params.put("dusername", duser);
                   params.put("pusername", patuser);
                    params.put("ekey", key);
                    return params;
                }

                /*
                 *pass files using below method
                 * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    params.put("filename", new DataPart(pdfname ,inputData));
                    return params;
                }
            };


            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue = Volley.newRequestQueue(Viewpatient.this);
            rQueue.add(volleyMultipartRequest);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
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

    public static void showSimpleProgressDialog(Viewpatient context, String title,
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


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(Viewpatient.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(Viewpatient.this, new String[] { Manifest.permission.SEND_SMS }, 1);
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
            } else {
                Toast.makeText(Viewpatient.this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendOTP() {
        String msg = "Your booking Confirmed "+"\n"+"Doctor Name: "+duser;

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(patphone, null, msg, null, null);
    }
}