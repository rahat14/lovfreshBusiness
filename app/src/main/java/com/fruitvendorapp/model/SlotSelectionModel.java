package com.fruitvendorapp.model;

public class SlotSelectionModel {
    String date;
    String time;
    public SlotSelectionModel(String date, String time) {
        this.date = date;
        this.time = time;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
