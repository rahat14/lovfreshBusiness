package com.fruitvendorapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TransactionModel implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("transactionsid")
    private String transactionsid;
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("text")
    private String text;
    @SerializedName("invoice")
    private String invoice;
    @SerializedName("user")
    private String user;
    @SerializedName("order")
    private String order;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionsid() {
        return transactionsid;
    }

    public void setTransactionsid(String transactionsid) {
        this.transactionsid = transactionsid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    protected TransactionModel(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.transactionsid = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.text = ((String) in.readValue((String.class.getClassLoader())));
        this.invoice = ((String) in.readValue((String.class.getClassLoader())));
        this.user = ((String) in.readValue((String.class.getClassLoader())));
        this.order = ((String) in.readValue((String.class.getClassLoader())));
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeValue(id);
        dest.writeValue(transactionsid);
        dest.writeValue(status);
        dest.writeValue(message);
        dest.writeValue(text);
        dest.writeValue(invoice);
        dest.writeValue(user);
        dest.writeValue(order);
    }

    public final static Creator<TransactionModel> CREATOR = new Creator<TransactionModel>() {
        @SuppressWarnings({"unchecked"})
        public TransactionModel createFromParcel(Parcel in) {
            return new TransactionModel(in);
        }

        public TransactionModel[] newArray(int size) {
            return (new TransactionModel[size]);
        }

    };
}