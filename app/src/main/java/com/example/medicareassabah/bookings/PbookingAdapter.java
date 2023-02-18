package com.example.medicareassabah.bookings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.medicareassabah.Config;
import com.example.medicareassabah.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PbookingAdapter extends RecyclerView.Adapter<PbookingAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<PbookingDataModel> dataModelArrayList;
    Context c;

    public PbookingAdapter(Context ctx, ArrayList<PbookingDataModel> dataModelArrayList){
        c = ctx;
        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.pbooking_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.textDname.setText(dataModelArrayList.get(position).getDusername());
        holder.textHname5.setText(dataModelArrayList.get(position).getDhospital());
        holder.textDdate.setText(dataModelArrayList.get(position).getBooking_date());
        holder.textTime.setText(dataModelArrayList.get(position).getBooking_time());
        holder.textDisease.setText(dataModelArrayList.get(position).getPdisease());
        holder.textAmount.setText(dataModelArrayList.get(position).getPayment());
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel(dataModelArrayList.get(position).getId(),dataModelArrayList.get(position).getPusername());
            }
        });

    }

    private void cancel(final String id, final String pusername) {
        String url = Config.baseURL + "cancled.php";
        final String[] status = new String[1];
        final String[] error = new String[1];
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject c = new JSONObject(response);
                    status[0] =c.getString("StatusID");
                    error[0] =c.getString("Error");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status[0].equals("1"))
                {
                    Toast.makeText(c, error[0], Toast.LENGTH_SHORT).show();
                    c.startActivity(new Intent(c,PatientBookingListActivity.class));
                    ((Activity)c).finish();
                }
                else
                {
                    Toast.makeText(c, error[0], Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(c, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id",id);
                map.put("pusername",pusername);
                return map;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 20000;
            }
            @Override
            public int getCurrentRetryCount() {
                return 20000;
            }
            @Override
            public void retry(VolleyError error) {
                Toast.makeText(c, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue q = Volley.newRequestQueue(c);
        q.add(stringRequest);

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textDname,textHname5,textDdate,textTime,textDisease,textAmount;
        CardView cardBooking;
        Button btnCancel,btnReciept;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textDname =itemView.findViewById(R.id.textDname);
            textHname5 =itemView.findViewById(R.id.textHname5);
            textDdate =itemView.findViewById(R.id.textDdate);
            cardBooking =itemView.findViewById(R.id.cardBooking);
            btnCancel =itemView.findViewById(R.id.btnCancel);
//            btnReciept =itemView.findViewById(R.id.btnReciept);
            textTime =itemView.findViewById(R.id.textTime);
            textDisease =itemView.findViewById(R.id.textDisease);
            textAmount =itemView.findViewById(R.id.textAmount);
        }
    }
}
