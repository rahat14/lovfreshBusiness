package com.fruitvendorapp.model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TimeSlotModel implements Parcelable {
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("strt_time_end_time")
    private String strtTimeEndTime;
    @SerializedName("is_active")
    private Boolean isActive;


    public TimeSlotModel() {
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStrtTimeEndTime() {
        return strtTimeEndTime;
    }

    public void setStrtTimeEndTime(String strtTimeEndTime) {
        this.strtTimeEndTime = strtTimeEndTime;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(startTime);
        dest.writeValue(endTime);
        dest.writeValue(strtTimeEndTime);
        dest.writeValue(isActive);
    }

    protected TimeSlotModel(android.os.Parcel in) {
        this.startTime = ((String) in.readValue((String.class.getClassLoader())));
        this.endTime = ((String) in.readValue((String.class.getClassLoader())));
        this.strtTimeEndTime = ((String) in.readValue((String.class.getClassLoader())));
        this.isActive = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public int describeContents() {
        return 0;
    }
    public final static Creator<TimeSlotModel> CREATOR = new Creator<TimeSlotModel>() {
        @SuppressWarnings({"unchecked"})
        public TimeSlotModel createFromParcel(android.os.Parcel in) {
            return new TimeSlotModel(in);
        }

        public TimeSlotModel[] newArray(int size) {
            return (new TimeSlotModel[size]);
        }

    };
}
