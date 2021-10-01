package com.fruitvendorapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class VendorNotificationModel implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("is_sound")
    private Boolean isSound;
    @SerializedName("is_vibration")
    private Boolean isVibration;
    @SerializedName("is_sms")
    private Boolean isSms;
    @SerializedName("vendor")
    private String vendor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getSound() {
        return isSound;
    }

    public void setSound(Boolean sound) {
        isSound = sound;
    }

    public Boolean getVibration() {
        return isVibration;
    }

    public void setVibration(Boolean vibration) {
        isVibration = vibration;
    }

    public Boolean getSms() {
        return isSms;
    }

    public void setSms(Boolean sms) {
        isSms = sms;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
    protected VendorNotificationModel(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.isSound = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.isVibration = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.isSms = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.vendor = ((String) in.readValue((String.class.getClassLoader())));
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeValue(id);
        dest.writeValue(isSound);
        dest.writeValue(isVibration);
        dest.writeValue(isSms);
        dest.writeValue(vendor);

    }

    public final static Creator<VendorNotificationModel> CREATOR = new Creator<VendorNotificationModel>() {
        @SuppressWarnings({"unchecked"})
        public VendorNotificationModel createFromParcel(Parcel in) {
            return new VendorNotificationModel(in);
        }

        public VendorNotificationModel[] newArray(int size) {
            return (new VendorNotificationModel[size]);
        }

    };
}
