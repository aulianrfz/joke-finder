package com.example.jokefinder.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class JokeResponse {
    @SerializedName("total")
    private int total;
    @SerializedName("result")
    private List<Joke> result;
    public int getTotal() { return total; }
    public List<Joke> getResult() { return result; }
}
