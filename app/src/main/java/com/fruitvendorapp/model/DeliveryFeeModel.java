package com.fruitvendorapp.model;

import android.os.Parcelable;

import com.fruitvendorapp.utilities.Constant;
import com.google.gson.annotations.SerializedName;

public class DeliveryFeeModel implements Parcelable {
    @SerializedName(Constant.DELIVERY_FEE)
    private String deliveryFee;
    @SerializedName(Constant.DISTANCE)
    private String distance;

    public DeliveryFeeModel(String deliveryFee, String distance) {
        this.deliveryFee = deliveryFee;
        this.distance = distance;
    }

    public DeliveryFeeModel() {
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
    protected DeliveryFeeModel(android.os.Parcel in) {
        this.deliveryFee = ((String) in.readValue((String.class.getClassLoader())));
        this.distance = ((String) in.readValue((String.class.getClassLoader())));
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(deliveryFee);
        dest.writeValue(distance);
    }

    public int describeContents() {
        return 0;
    }
    public final static Creator<DeliveryFeeModel> CREATOR = new Creator<DeliveryFeeModel>() {
        @SuppressWarnings({"unchecked"})
        public DeliveryFeeModel createFromParcel(android.os.Parcel in) {
            return new DeliveryFeeModel(in);
        }

        public DeliveryFeeModel[] newArray(int size) {
            return (new DeliveryFeeModel[size]);
        }

    };
}
