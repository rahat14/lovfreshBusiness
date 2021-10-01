package com.fruitvendorapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UnitModel implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("value")
    private String value;
    @SerializedName("formula")
    private String formula;
    @SerializedName("parent")
    private String parent;

    public UnitModel(String id,String value,String name) {
        this.id = id;
        this.value = value;
        this.name = name;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    protected UnitModel(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.value = ((String) in.readValue((String.class.getClassLoader())));
        this.formula = ((String) in.readValue((String.class.getClassLoader())));
        this.parent = ((String) in.readValue((String.class.getClassLoader())));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(value);
        dest.writeValue(formula);
        dest.writeValue(parent);
    }

    public static final Creator<UnitModel> CREATOR = new Creator<UnitModel>() {
        @Override
        public UnitModel createFromParcel(Parcel source) {
            return new UnitModel(source);
        }

        @Override
        public UnitModel[] newArray(int size) {
            return new UnitModel[size];
        }
    };

}
