package com.fruitvendorapp.model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ShopOnlineModel implements Parcelable {
    @SerializedName("id")
    private String cate_id;
    @SerializedName("products")
    private ArrayList<ProductModel> products;
    @SerializedName("slug")
    private String slug;
    @SerializedName("name")
    private String name;
    @SerializedName("vendor")
    private String vendor;

    public ShopOnlineModel(String name,ArrayList<ProductModel> products) {
        this.name = name;
        this.products = products;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public ArrayList<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductModel> products) {
        this.products = products;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
    public ShopOnlineModel() {
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(cate_id);
        dest.writeList(products);
        dest.writeValue(slug);
        dest.writeValue(name);
        dest.writeValue(vendor);
    }

    public int describeContents() {
        return 0;
    }

    protected ShopOnlineModel(android.os.Parcel in) {
        this.cate_id = ((String) in.readValue((Integer.class.getClassLoader())));
        this.products= in.createTypedArrayList(ProductModel.CREATOR);
        this.slug = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.vendor = ((String) in.readValue((Integer.class.getClassLoader())));
    }


    public final static Creator<ShopOnlineModel> CREATOR = new Creator<ShopOnlineModel>() {
        @SuppressWarnings({"unchecked"})
        public ShopOnlineModel createFromParcel(android.os.Parcel in) {
            return new ShopOnlineModel(in);
        }

        public ShopOnlineModel[] newArray(int size) {
            return (new ShopOnlineModel[size]);
        }

    };


}
