package com.fruitvendorapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationResp {
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
     NotificationData data;

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

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        this.data = data;
    }
}
