package com.example.jokefinder.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.jokefinder.data.model.Joke;
import com.example.jokefinder.data.model.JokeResponse;
import com.example.jokefinder.data.network.ApiClient;
import com.example.jokefinder.data.network.ApiService;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JokeRepository {
    private final ApiService apiService;

    public JokeRepository() {
        apiService = ApiClient.getApiService();
    }

    public interface JokeSearchCallback {
        void onSuccess(List<Joke> jokes, int total);
        void onError(String message);
    }

    public void searchJokes(String query, JokeSearchCallback callback) {
        apiService.searchJokes(query).enqueue(new Callback<JokeResponse>() {
            @Override
            public void onResponse(@NonNull Call<JokeResponse> call,
                                   @NonNull Response<JokeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JokeResponse body = response.body();
                    List<Joke> result = body.getResult();
                    int total = body.getTotal();
                    callback.onSuccess(result, total);
                } else {
                    try {
                        String errMsg;
                        if (response.errorBody() != null) {
                            String errorJson = response.errorBody().string();
                            JSONObject errorObj = new JSONObject(errorJson);
                            errMsg = errorObj.has("message")
                                    ? errorObj.getString("message")
                                    : "There's an error (" + response.code() + ")";
                        } else {
                            errMsg = "There's an error(" + response.code() + ")";
                        }
                        callback.onError(errMsg);
                    } catch (Exception e) {
                        callback.onError("Failed to process error response");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JokeResponse> call, @NonNull Throwable t) {
                String msg;
                if (t instanceof IOException) {
                    msg = "Network error, check internet connection.";
                } else {
                    msg = "There's an error: " + t.getMessage();
                }
                callback.onError(msg);
            }
        });
    }
}
