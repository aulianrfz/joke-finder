package com.example.jokefinder.data.model;

import com.google.gson.annotations.SerializedName;

public class Joke {
    @SerializedName("id")
    private String id;
    @SerializedName("value")
    private String value;
    public String getId() { return id; }
    public String getValue() { return value; }
}
