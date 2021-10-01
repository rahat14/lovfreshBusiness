package com.fruitvendorapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductImageModel implements Parcelable {
    private String product_img="";
    private int indexType;

    public ProductImageModel(String product_img) {
        this.product_img = product_img;

    }

    public ProductImageModel(int indexType) {
        this.indexType = indexType;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public int getIndexType() {
        return indexType;
    }

    public void setIndexType(int indexType) {
        this.indexType = indexType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.product_img);
        dest.writeInt(this.indexType);
    }

    protected ProductImageModel(Parcel in) {
        this.product_img = in.readString();
        this.indexType = in.readInt();
    }

    public static final Creator<ProductImageModel> CREATOR = new Creator<ProductImageModel>() {
        @Override
        public ProductImageModel createFromParcel(Parcel source) {
            return new ProductImageModel(source);
        }

        @Override
        public ProductImageModel[] newArray(int size) {
            return new ProductImageModel[size];
        }
    };

}
