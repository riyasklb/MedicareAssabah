package com.example.medicareassabah;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPI {
    @GET
    Call<Model> getALlNews(@Url String url);

    @GET
    Call<Model> getNewsByCategory(@Url String url);
}
