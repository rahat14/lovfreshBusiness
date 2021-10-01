package com.fruitvendorapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fruitvendorapp.utilities.Constant;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class OrderModel implements Parcelable {
    @SerializedName(Constant.ORDER_ID)
    private String id;
    @SerializedName("get_status")
    private String getStatus;
    @SerializedName("order_products")
    private ArrayList<OrderProductModel> orderProducts;
    @SerializedName("order_number")
    private String orderNumber;
    @SerializedName("status")
    private String status;
    @SerializedName("total")
    private String total;
    @SerializedName("delivery_cost")
    private String deliveryCost;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("deliver_date")
    private String deliverDate;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("rejection_reason")
    private String rejectionReason;
    @SerializedName("order_type")
    private String orderType;
    @SerializedName("payment_type")
    private String paymentType;
    @SerializedName("user")
    private UserModel user;
    @SerializedName("vendor")
    private VendorModel vendor;
    @SerializedName("transaction")
    private ArrayList<TransactionModel> transaction;


    public static final Creator<OrderModel> CREATOR = new Creator<OrderModel>() {
        @Override
        public OrderModel createFromParcel(Parcel in) {
            return new OrderModel(in);
        }

        @Override
        public OrderModel[] newArray(int size) {
            return new OrderModel[size];
        }
    };

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public ArrayList<TransactionModel> getTransaction() {
        return transaction;
    }

    public void setTransaction(ArrayList<TransactionModel> transaction) {
        this.transaction = transaction;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGetStatus() {
        return getStatus;
    }

    public void setGetStatus(String getStatus) {
        this.getStatus = getStatus;
    }

    public ArrayList<OrderProductModel> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(ArrayList<OrderProductModel> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(String deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(String deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public VendorModel getVendor() {
        return vendor;
    }

    public void setVendor(VendorModel vendor) {
        this.vendor = vendor;
    }

    /*   public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }*/
    protected OrderModel(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.rejectionReason = ((String) in.readValue((String.class.getClassLoader())));
        this.getStatus = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.orderProducts, (OrderProductModel.class.getClassLoader()));
        this.orderNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.total = ((String) in.readValue((String.class.getClassLoader())));
        this.deliveryCost = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.deliverDate = ((String) in.readValue((String.class.getClassLoader())));
        this.startTime = ((String) in.readValue((String.class.getClassLoader())));
        this.endTime = ((String) in.readValue((String.class.getClassLoader())));
        this.orderType = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentType = ((String) in.readValue((String.class.getClassLoader())));
        this.user = ((UserModel) in.readValue((UserModel.class.getClassLoader())));
        this.vendor = ((VendorModel) in.readValue((VendorModel.class.getClassLoader())));
        in.readList(this.transaction, (TransactionModel.class.getClassLoader()));
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(getStatus);
        dest.writeValue(rejectionReason);
        dest.writeTypedList(orderProducts);
        dest.writeValue(orderNumber);
        dest.writeValue(status);
        dest.writeValue(total);
        dest.writeValue(deliveryCost);
        dest.writeValue(createdAt);
        dest.writeValue(deliverDate);
        dest.writeValue(startTime);
        dest.writeValue(endTime);
        dest.writeValue(orderType);
        dest.writeValue(paymentType);
        dest.writeValue(user);
        dest.writeValue(vendor);
        dest.writeTypedList(transaction);
    }
}