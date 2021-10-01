package com.fruitvendorapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductAttributeModel implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("total_sell_product")
    private String totalSellProduct;
    @SerializedName("uom")
    private UnitModel uom;
    @SerializedName("stock")
    private String stock;
    @SerializedName("price")
    private String price;
    @SerializedName("product")
    private String product;

    public String getId() {
        return id;
    }

    public String getTotalSellProduct() {
        return totalSellProduct;
    }

    public void setTotalSellProduct(String totalSellProduct) {
        this.totalSellProduct = totalSellProduct;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UnitModel getUom() {
        return uom;
    }

    public void setUom(UnitModel uom) {
        this.uom = uom;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    protected ProductAttributeModel(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.totalSellProduct = ((String) in.readValue((String.class.getClassLoader())));
        this.uom = ((UnitModel) in.readValue((UnitModel.class.getClassLoader())));
        this.stock = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.product = ((String) in.readValue((String.class.getClassLoader())));
    }
        @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(totalSellProduct);
        dest.writeValue(uom);
        dest.writeValue(stock);
        dest.writeValue(price);
        dest.writeValue(product);

    }

    public static final Creator<ProductAttributeModel> CREATOR = new Creator<ProductAttributeModel>() {
        @Override
        public ProductAttributeModel createFromParcel(Parcel source) {
            return new ProductAttributeModel(source);
        }

        @Override
        public ProductAttributeModel[] newArray(int size) {
            return new ProductAttributeModel[size];
        }
    };

}
