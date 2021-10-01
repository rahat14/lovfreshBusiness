package com.fruitvendorapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class DateAndTimeSlotModel implements Parcelable {
    @SerializedName("date")
    private String date;
    @SerializedName("is_selected")
    private boolean is_selected=false;
    @SerializedName("time")
    private ArrayList<TimeSlotModel> time;

    public boolean isIs_selected() {
        return is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<TimeSlotModel> getTime() {
        return time;
    }

    public void setTime(ArrayList<TimeSlotModel> time) {
        this.time = time;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    protected DateAndTimeSlotModel(Parcel in) {
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.is_selected = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.time = in.createTypedArrayList(TimeSlotModel.CREATOR);

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(date);
        dest.writeValue(is_selected);
        dest.writeTypedList(this.time);
    }
    public final static Creator<TimeSlotModel> CREATOR = new Creator<TimeSlotModel>() {
        @SuppressWarnings({"unchecked"})
        public TimeSlotModel createFromParcel(Parcel in) {
            return new TimeSlotModel(in);
        }

        public TimeSlotModel[] newArray(int size) {
            return (new TimeSlotModel[size]);
        }};
}
