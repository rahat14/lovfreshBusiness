package com.fruitvendorapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PromoCodeModel implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("code")
    private String code;
    @SerializedName("value")
    private String value;
    @SerializedName("is_percentage")
    private Boolean isPercentage;
    @SerializedName("times_used")
    private String timesUsed;
    @SerializedName("valid_from")
    private String validFrom;
    @SerializedName("valid_to")
    private String validTo;
    @SerializedName("created")
    private String created;
    @SerializedName("is_active")
    private Boolean isActive;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getPercentage() {
        return isPercentage;
    }

    public void setPercentage(Boolean percentage) {
        isPercentage = percentage;
    }

    public String getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(String timesUsed) {
        this.timesUsed = timesUsed;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(code);
        dest.writeValue(value);
        dest.writeValue(isPercentage);
        dest.writeValue(timesUsed);
        dest.writeValue(validFrom);
        dest.writeValue(validTo);
        dest.writeValue(created);
        dest.writeValue(isActive);
    }
    protected PromoCodeModel(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.value = ((String) in.readValue((String.class.getClassLoader())));
        this.isPercentage = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.timesUsed = ((String) in.readValue((String.class.getClassLoader())));
        this.validFrom = ((String) in.readValue((String.class.getClassLoader())));
        this.validTo = ((String) in.readValue((String.class.getClassLoader())));
        this.created = ((String) in.readValue((String.class.getClassLoader())));
        this.isActive = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public final static Creator<PromoCodeModel> CREATOR = new Creator<PromoCodeModel>() {
        @SuppressWarnings({"unchecked"})
        public PromoCodeModel createFromParcel(Parcel in) {
            return new PromoCodeModel(in);
        }

        public PromoCodeModel[] newArray(int size) {
            return (new PromoCodeModel[size]);
        }

    };
}
