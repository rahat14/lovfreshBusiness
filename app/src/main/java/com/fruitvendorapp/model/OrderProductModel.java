package com.fruitvendorapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class OrderProductModel implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("price")
    private String price;
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("order")
    private String order;
    @SerializedName("product")
    private ProductModel product;
    @SerializedName("uom")
    private String uom;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    protected OrderProductModel(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.quantity = ((String) in.readValue((String.class.getClassLoader())));
        this.order = ((String) in.readValue((String.class.getClassLoader())));
        this.product = ((ProductModel) in.readValue((ProductModel.class.getClassLoader())));
        this.uom = ((String) in.readValue((String.class.getClassLoader())));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(price);
        dest.writeValue(quantity);
        dest.writeValue(order);
        dest.writeValue(product);
        dest.writeValue(uom);
    }
}
