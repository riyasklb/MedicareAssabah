package com.example.medicareassabah;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder>implements Filterable {
    private LayoutInflater inflater;
    private ArrayList<BookData> book;
    private ArrayList<BookData> bookDataArrayList;
    Context c;

    public BookAdapter(Context ctx, ArrayList<BookData> book) {
        c = ctx;
        inflater = LayoutInflater.from(ctx);
        this.book = book;
        this.bookDataArrayList = book;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_book, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.currentdates.setVisibility(View.VISIBLE);
        final BookData product = bookDataArrayList.get(position);
        holder.usernames.setText("PatientName: "  +product.getPuser());
        //holder.contacts.setText("Patient Number: "  +product.getPphone());
        holder.currentdates.setText("Booking Date: " +product.getBookdate());
        holder.currentimes.setText("Booking Time: "  +product.getBooktime());
        holder.btnDcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canceled(bookDataArrayList.get(position).getId(),bookDataArrayList.get(position).getDuser(),bookDataArrayList.get(position).getPphone());
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookData p = bookDataArrayList.get(position);
                String patuser = p.getPuser();
                String patphone = p.getPphone();
                String patage = p.getPage();
                String patdis = p.getPdisease();
                String patbookd=p.getBookdate();
                String patbookt=p.getBooktime();
                String key = p.getKey();
                Intent i = new Intent(c,Viewpatient.class);

                i.putExtra("pusername",patuser);
                i.putExtra("pphone",patphone);
                i.putExtra("page",patage);
                i.putExtra("pdisease",patdis);
                i.putExtra("booking_date",patbookd);
                i.putExtra("booking_time",patbookt);
                i.putExtra("key",key);
                c.startActivity(i);
                ((Activity)c).finish();
            }
        });

    }

    private void canceled(final String id, final String duser, final String pphone) {
        String url=Config.baseURL+"doctorreject.php";
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
                if ("1".equals(status[0]))
                {
                    Toast.makeText(c, error[0], Toast.LENGTH_SHORT).show();
                    c.startActivity(new Intent(c, BookingList.class));
                    ((Activity)c).finish();
                    sendSms(pphone,duser);
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
                map.put("dusername",duser);
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

    private void sendSms(String pphone, String duser) {
        String msg = "Your Bookig  canceled by  Doctor : "+ duser;

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(pphone, null, msg, null, null);
    }


    @Override
    public int getItemCount() {
        return bookDataArrayList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    bookDataArrayList = book;
                } else {
                    ArrayList<BookData> filteredList = new ArrayList<>();
                    for (BookData row : bookDataArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getPuser().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getPuser().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    bookDataArrayList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = bookDataArrayList;
                return filterResults;
            }

        @Override
        protected void publishResults (CharSequence constraint, FilterResults results){
                bookDataArrayList.clear();
           bookDataArrayList.addAll((ArrayList) results.values);

            notifyDataSetChanged();
        }
    };


}


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView usernames,  currentdates, currentimes;
CardView cardView;
Button btnDcancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            usernames= itemView.findViewById(R.id.pusern);
//            contacts= itemView.findViewById(R.id.pphone);
            currentdates= itemView.findViewById(R.id.bookingd);
            currentimes = itemView.findViewById(R.id.bookingt);
            cardView=itemView.findViewById(R.id.cardview);
            btnDcancel=itemView.findViewById(R.id.btnDcancel);

        }
    }
}

