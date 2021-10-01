package com.fruitvendorapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DistanceFeeModel implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("delivery_fee")
    private String deliveryFee;
    @SerializedName("distance")
    private String distance;
    @SerializedName("vendor_order_type")
    private String vendorOrderType;
    @SerializedName("is_active")
    private boolean is_active;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getVendorOrderType() {
        return vendorOrderType;
    }

    public void setVendorOrderType(String vendorOrderType) {
        this.vendorOrderType = vendorOrderType;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(deliveryFee);
        dest.writeValue(distance);
        dest.writeValue(vendorOrderType);
        dest.writeByte(is_active ? (byte) 1 : (byte) 0);

    }

    protected DistanceFeeModel(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.deliveryFee = ((String) in.readValue((String.class.getClassLoader())));
        this.distance = ((String) in.readValue((String.class.getClassLoader())));
        this.is_active = in.readByte() != 0;
    }

    public static final Creator<DistanceFeeModel> CREATOR = new Creator<DistanceFeeModel>() {
        @Override
        public DistanceFeeModel createFromParcel(Parcel source) {
            return new DistanceFeeModel(source);
        }

        @Override
        public DistanceFeeModel[] newArray(int size) {
            return new DistanceFeeModel[size];
        }
    };


}
