package com.fruitvendorapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderResp {
    @Expose
    @SerializedName("code")
    private int code;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("meta")
    private meta meta;

    @SerializedName("data")
     data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public com.fruitvendorapp.model.meta getMeta() {
        return meta;
    }

    public void setMeta(com.fruitvendorapp.model.meta meta) {
        this.meta = meta;
    }

    public com.fruitvendorapp.model.data getData() {
        return data;
    }

    public void setData(com.fruitvendorapp.model.data data) {
        this.data = data;
    }
}
