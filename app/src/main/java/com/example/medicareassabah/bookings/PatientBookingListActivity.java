package com.example.medicareassabah.bookings;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.medicareassabah.Config;
import com.example.medicareassabah.R;
import com.example.medicareassabah.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PatientBookingListActivity extends AppCompatActivity {
    private String URLstring = Config.baseURL +  "patient_blist.php";
    private static ProgressDialog mProgressDialog;
    ArrayList<PbookingDataModel> dataModelArrayList;
    private PbookingAdapter rvAdapter;
    private RecyclerView recyclerView;
    Context c;
    String user,dusername,dhospital,dphone,disease,datess,time,amount;
    Button btnRec;
    File myFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_booking_list);

        HashMap<String,String> m = new SessionManager(this).getUserDetails();
        user = m.get("username");
        recyclerView = findViewById(R.id.recyclerBookingP);
//        btnRec = findViewById(R.id.btnReciept);

//        btnRec.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    getValues();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (DocumentException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        fetchingJSON();
    }

//    private void getValues() throws FileNotFoundException, DocumentException {
//        for(int i=0; i<dataModelArrayList.size(); i++){
//            dusername=dataModelArrayList.get(i).getDusername();
//            dhospital=dataModelArrayList.get(i).getDhospital();
//            dphone=dataModelArrayList.get(i).getDphone();
//            disease=dataModelArrayList.get(i).getPdisease();
//            datess=dataModelArrayList.get(i).getBooking_date();
//            time=dataModelArrayList.get(i).getBooking_time();
//            amount=dataModelArrayList.get(i).getPayment();
////            Toast.makeText(getApplicationContext(), dates+mode+comments, Toast.LENGTH_SHORT).show();
////            StringBuilder str = new StringBuilder();
////            str.append(dates);
//
//        }
//        createandDisplayPdf(dusername,dhospital,dphone,disease,datess,time,amount);
//    }
//    private void createandDisplayPdf(String dusername,String dhospital,String dphone, String disease,String datess,String time,String amount) throws FileNotFoundException, DocumentException {
//
//        File pdfFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"pdfdemo");
//        if(!pdfFolder.exists()){
//            pdfFolder.mkdir();
//        }
//        Date date=new Date();
//        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
//        File myFile=new File(pdfFolder+timeStamp+".pdf");
//        OutputStream output=new FileOutputStream(myFile);
//        Document document=new Document();
//        PdfWriter.getInstance(document,output);
//        document.open();
//        document.add(new Paragraph("Booking Details"));
//        document.add(new Paragraph("Doctor Name: " +  dusername));
//        document.add(new Paragraph("Hospital Name: " +  dhospital));
//        document.add(new Paragraph("Doctor Name: " +  dphone));
//        document.add(new Paragraph("Disease: "+ disease));
//        document.add(new Paragraph("Booking Date: " +datess));
//        document.add(new Paragraph("Booking Time: " +time));
//        document.add(new Paragraph("Amount: " +amount));
//        document.close();
//        viewPdf(myFile);
//    }
//
//    private void viewPdf(File pdfFile) {
//        Uri path = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", pdfFile);
//
//        Intent intent=new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(path,"application/pdf");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        startActivity(intent);
//
//    }

    private void fetchingJSON() {

        showSimpleProgressDialog(this, "Loading...","Fetching Json",false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            removeSimpleProgressDialog();

                            dataModelArrayList = new ArrayList<>();

                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject dataobj = array.getJSONObject(i);

                                dataModelArrayList.add(new PbookingDataModel(
                                        dataobj.getString("id"),
                                        dataobj.getString("dusername"),
                                        dataobj.getString("dhospital"),
                                        dataobj.getString("dphone"),
                                        dataobj.getString("pusername"),
                                        dataobj.getString("booking_date"),
                                        dataobj.getString("booking_time"),
                                        dataobj.getString("pdisease"),
                                        dataobj.getString("mode"),
                                        dataobj.getString("payment")
                                ));

                            }
                            setupRecycler();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Log", "inside onErrorResponse");
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map= new HashMap<>();
                map.put("pusername",user);
                return map;
            }
        };

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setupRecycler(){

        rvAdapter = new PbookingAdapter(this, dataModelArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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