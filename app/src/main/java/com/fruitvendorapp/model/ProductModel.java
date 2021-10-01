package com.fruitvendorapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fruitvendorapp.utilities.Constant;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductModel implements Parcelable {
    @SerializedName(Constant.PRODUCT_ID)
    private String product_id;
    @SerializedName("product_attributes")
    private ArrayList<ProductAttributeModel> productAttributes;
    @SerializedName("images")
    private ArrayList<ImageModel> product_images;
    @SerializedName("title")
    private String title;
    @SerializedName("short_description")
    private String shortDescription;
    @SerializedName("long_description")
    private String longDescription;
    @SerializedName("price")
    private String price;
    @SerializedName("type")
    private String type;
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("start_date_time")
    private String startDateTime;
    @SerializedName("end_date_time")
    private String endDateTime;
    @SerializedName("is_active")
    private Boolean isActive;
    @SerializedName(Constant.IS_HIDE)
    private Boolean isHide;
    @SerializedName("promotional")
    private Boolean isPromotional;
    @SerializedName("promotional_price")
    private String promotional_price;
    @SerializedName("categories")
    private ArrayList<Integer> categories;
    private boolean isSelected = false;

    public String getPromotional_price() {
        return promotional_price;
    }

    public void setPromotional_price(String promotional_price) {
        this.promotional_price = promotional_price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Boolean getPromotional() {
        return isPromotional;
    }

    public void setPromotional(Boolean promotional) {
        isPromotional = promotional;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public Boolean getHide() {
        return isHide;
    }

    public void setHide(Boolean hide) {
        isHide = hide;
    }

    public ArrayList<ImageModel> getProduct_images() {
        return product_images;
    }

    public void setProduct_images(ArrayList<ImageModel> product_images) {
        this.product_images = product_images;
    }

    public ArrayList<ProductAttributeModel> getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(ArrayList<ProductAttributeModel> productAttributes) {
        this.productAttributes = productAttributes;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public ArrayList<Integer> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Integer> categories) {
        this.categories = categories;
    }

    protected ProductModel(Parcel in) {
        this.product_id = ((String) in.readValue((String.class.getClassLoader())));
        this.productAttributes = in.createTypedArrayList(ProductAttributeModel.CREATOR);
        this.product_images = in.createTypedArrayList(ImageModel.CREATOR);
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.shortDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.longDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.quantity = ((String) in.readValue((String.class.getClassLoader())));
        this.startDateTime = ((String) in.readValue((String.class.getClassLoader())));
        this.endDateTime = ((String) in.readValue((String.class.getClassLoader())));
        this.isActive = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.isHide = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.isPromotional = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.promotional_price = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.categories, (Integer.class.getClassLoader()));
        this.isSelected = in.readByte() != 0;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(product_id);
        dest.writeTypedList(this.productAttributes);
        dest.writeTypedList(this.product_images);
        dest.writeValue(title);
        dest.writeValue(shortDescription);
        dest.writeValue(longDescription);
        dest.writeValue(price);
        dest.writeValue(type);
        dest.writeValue(quantity);
        dest.writeValue(startDateTime);
        dest.writeValue(endDateTime);
        dest.writeValue(isActive);
        dest.writeValue(isHide);
        dest.writeValue(isPromotional);
        dest.writeValue(promotional_price);
        dest.writeList(categories);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel source) {
            return new ProductModel(source);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };
}

