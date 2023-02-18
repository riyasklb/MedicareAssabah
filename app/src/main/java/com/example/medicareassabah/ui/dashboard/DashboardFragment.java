package com.example.medicareassabah.ui.dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.medicareassabah.Adapter;
import com.example.medicareassabah.Articles;
import com.example.medicareassabah.Config;
import com.example.medicareassabah.Model;
import com.example.medicareassabah.R;
import com.example.medicareassabah.RetrofitAPI;
import com.example.medicareassabah.bookings.PbookingAdapter;
import com.example.medicareassabah.bookings.PbookingDataModel;
import com.example.medicareassabah.databinding.FragmentDashboardBinding;
import com.example.medicareassabah.databinding.FragmentNotificationsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardFragment extends Fragment {
    private RecyclerView newsRV, categoriesRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articles;
//    private ArrayList<CategoryRVModel> categoryRVModals;
//    private CategoryRVAdapter categoryRVAdapter;
    private Adapter newsRVAapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_dashboard, container, false);

            newsRV = root.findViewById(R.id.idRVNews);
//            categoriesRV = root.findViewById(R.id.idRVCategory);
            loadingPB =root. findViewById(R.id.idPBLoading);
            loadingPB.setVisibility(View.VISIBLE);
            articles = new ArrayList<>();
//            categoryRVModals = new ArrayList<>();
            newsRVAapter = new Adapter(articles, getActivity());
//            categoryRVAdapter = new CategoryRVAdapter(categoryRVModals, this, this::onCategoryClick);
            newsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
            newsRV.setAdapter(newsRVAapter);
//            categoriesRV.setAdapter(categoryRVAdapter);
//            getCategories();
            getNewsByRetrofit("All");
            newsRVAapter.notifyDataSetChanged();
            return root;


        }

            private void getNewsByRetrofit(String category) {
            loadingPB.setVisibility(View.VISIBLE);
            articles.clear();
            String categoryURL="https://newsapi.org/v2/top-headlines?q=health&apiKey=88288f3c30f14c66bfcf248f7f341b5f";
//        String categoryURL = "http://newsapi.org/v2/top-headlines?country=in&category=" + category + "&apiKey=88288f3c30f14c66bfcf248f7f341b5f";
//        String url = "http://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=88288f3c30f14c66bfcf248f7f341b5f";
            String BASE_URL = "http://newsapi.org/";
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
            Call<Model> call;
            if (category.equals("All")) {
                call = retrofitAPI.getALlNews(categoryURL);
            } else {
                call = retrofitAPI.getALlNews(categoryURL);
            }

            call.enqueue(new Callback<Model>()  {
                @Override
                public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {

                    Model newsModel = response.body();
                    loadingPB.setVisibility(View.GONE);
                    ArrayList<Articles> articlesArrayList = newsModel.getArticles();
                    for (int i = 0; i < articlesArrayList.size(); i++) {
                        articles.add(new Articles(articlesArrayList.get(i).getTitle(), articlesArrayList.get(i).getDescription(), articlesArrayList.get(i).getUrlToImage(), articlesArrayList.get(i).getUrl(), articlesArrayList.get(i).getContent()));
                    }
                    newsRVAapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<Model> call, Throwable t) {
                    Toast.makeText(getActivity(), "Fail to get response..", Toast.LENGTH_SHORT).show();
                }
            });

        }

//            @Override
//            public void onCategoryClick(int position) {
//            String category = categoryRVModals.get(position).getCategory();
//            getNewsByRetrofit(category);
//        }
        }
