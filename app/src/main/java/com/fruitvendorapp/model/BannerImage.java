package com.fruitvendorapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class BannerImage implements Parcelable {
    @SerializedName("image")
    private String image;

    public BannerImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(image);
    }

    public int describeContents() {
        return 0;
    }

    protected BannerImage(Parcel in) {
        this.image = ((String) in.readValue((String.class.getClassLoader())));
    }

    public final static Creator<BannerImage> CREATOR = new Creator<BannerImage>() {
        @Override
        public BannerImage createFromParcel(Parcel source) {
            return new BannerImage(source);
        }

        @Override
        public BannerImage[] newArray(int size) {
            return new BannerImage[size];
        }
    };
}
