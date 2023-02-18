package com.example.medicareassabah;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FileAdapter2 extends RecyclerView.Adapter<FileAdapter2.MyViewHolder>implements Filterable {
    private LayoutInflater inflater;
    private ArrayList<FileData> file;
    private ArrayList<FileData> fileDataArrayList;
    Context c;

    public FileAdapter2(Context ctx, ArrayList<FileData> file) {
        c = ctx;
        inflater = LayoutInflater.from(ctx);
        this.file = file;
        this.fileDataArrayList = file;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.orginals, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.filename.setVisibility(View.VISIBLE);
        final FileData product = fileDataArrayList.get(position);
        holder.username.setText("UserName:"+product.getUser());
        holder.filename.setText("FileName:"+product.getFilename());
        holder.record.setText("Records:"+product.getRecord());

//holder.remove.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        delete(product.getUser());
//    }
//});
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileData p = fileDataArrayList.get(position);
                String record = p.getRecord();
                Intent i = new Intent();
                i.setPackage("com.android.chrome");
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Config.imageURL+ record));
                c.startActivity(i);
            }
        });

    }

//    private void delete(String user) {
//        final String[] status = {""};
//        final String url = Config.baseURL +"deletefile.php";
//
//        StringRequest s = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
////                            Toast.makeText(c, response, Toast.LENGTH_SHORT).show();
//                            JSONObject c = new JSONObject(response);
//                            status[0] = c.getString("StatusID");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        if (status[0].equals("1")) {
//                            Toast.makeText(c, "Removed Successfully", Toast.LENGTH_SHORT).show();
//                            c.startActivity(new Intent(c,FileList.class));
//
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(c, error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> m = new HashMap<>();
//                m.put("username", user);
//                return m;
//            }
//        };
//        RequestQueue q = Volley.newRequestQueue(c);
//        q.add(s);
//
//
//    }

    @Override
    public int getItemCount() {
        return fileDataArrayList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    fileDataArrayList = file;
                } else {
                    ArrayList<FileData> filteredList = new ArrayList<>();
                    for (FileData row : fileDataArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getFilename().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getFilename().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    fileDataArrayList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = fileDataArrayList;
                return filterResults;
            }

        @Override
        protected void publishResults (CharSequence constraint, FilterResults results){
                notifyDataSetChanged();
        }
    };


}


    class MyViewHolder extends RecyclerView.ViewHolder {
    TextView username, filename,record;
    CardView cardView;
    Button remove;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username= itemView.findViewById(R.id.username);
            filename= itemView.findViewById(R.id.filename);
            record= itemView.findViewById(R.id.record);
            cardView=itemView.findViewById(R.id.cardview);
//            remove=itemView.findViewById(R.id.btnRemove);
        }
    }
}

