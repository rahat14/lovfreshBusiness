package com.fruitvendorapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class meta {
    @Expose
    @SerializedName("path")
    private String path;
    @Expose
    @SerializedName("total")
    private int total;
    @Expose
    @SerializedName("last_page")
    private int last_page;
    @Expose
    @SerializedName("current_page")
    private int current_page;
    @Expose
    @SerializedName("per_page")
    private int per_page;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }
}
