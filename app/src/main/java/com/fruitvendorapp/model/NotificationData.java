package com.fruitvendorapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationData {

    @SerializedName("results")
    private List<OrderModel> results;

    public List<OrderModel> getResults() {
        return results;
    }

    public void setResults(List<OrderModel> results) {
        this.results = results;
    }

}
