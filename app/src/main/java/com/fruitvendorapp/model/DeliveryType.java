package com.fruitvendorapp.model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DeliveryType implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("is_active")
    private Boolean isActive;
    @SerializedName("minimum_order_value")
    private String minimumOrderValue;

    @SerializedName("delivery_type")
    private String delivery_type;

    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public DeliveryType(String id, Boolean isActive) {
        this.id = id;
        this.isActive = isActive;
    }

    public DeliveryType(String id, String name, Boolean isActive, String minimumOrderValue, String delivery_type, boolean isSelected) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.minimumOrderValue = minimumOrderValue;
        this.delivery_type = delivery_type;
        this.isSelected = isSelected;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(String delivery_type) {
        this.delivery_type = delivery_type;
    }

    public static Creator<DeliveryType> getCREATOR() {
        return CREATOR;
    }

    public void setOn_off_flag(Boolean isActive) {
        this.isActive = isActive;
    }

    public DeliveryType() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public String getMinimumOrderValue() {
        return minimumOrderValue;
    }

    public void setMinimumOrderValue(String minimumOrderValue) {
        this.minimumOrderValue = minimumOrderValue;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    protected DeliveryType(android.os.Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.isActive = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.minimumOrderValue = ((String) in.readValue((String.class.getClassLoader())));
        this.isSelected = in.readByte() != 0;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(minimumOrderValue);
        dest.writeValue(isActive);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    public int describeContents() {
        return 0;
    }

    public final static Creator<DeliveryType> CREATOR = new Creator<DeliveryType>() {
        @SuppressWarnings({"unchecked"})
        public DeliveryType createFromParcel(android.os.Parcel in) {
            return new DeliveryType(in);
        }

        public DeliveryType[] newArray(int size) {
            return (new DeliveryType[size]);
        }

    };

}
