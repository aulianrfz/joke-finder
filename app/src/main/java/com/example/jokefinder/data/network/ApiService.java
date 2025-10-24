package com.example.jokefinder.data.network;

import com.example.jokefinder.data.model.JokeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("jokes/search")
    Call<JokeResponse> searchJokes(@Query("query") String query);
}
