package com.fruitvendorapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ImageModel implements Parcelable {
    @SerializedName("image")
    private String image="";
    @SerializedName("id")
    private String id="";
    @SerializedName("date")
    private String date;
    @SerializedName("object_id")
    private String objectId;
    @SerializedName("is_banner")
    private Boolean isBanner;
    @SerializedName("content_type")
    private String contentType;
    private int indexType;

    public ImageModel(String image) {
        this.image = image;
    }
    public ImageModel(int indexType) {
        this.indexType = indexType;
    }

    public int getIndexType() {
        return indexType;
    }

    public void setIndexType(int indexType) {
        this.indexType = indexType;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Boolean getBanner() {
        return isBanner;
    }
    public void setBanner(Boolean banner) {
        isBanner = banner;
    }
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    protected ImageModel(Parcel in) {
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.indexType = in.readInt();
    }
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(image);
        dest.writeInt(this.indexType);
    }
    public int describeContents() {
        return 0;
    }
    public final static Creator<ImageModel> CREATOR = new Creator<ImageModel>() {
        @Override
        public ImageModel createFromParcel(Parcel source) {
            return new ImageModel(source);
        }
        @Override
        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };
}
