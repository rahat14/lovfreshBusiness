package com.fruitvendorapp.interfaces;

import com.fruitvendorapp.model.TimeSlotModel;

import java.util.ArrayList;

public interface ItemSelectTimeListInterface {
    void itemTime(boolean isSelect, ArrayList<TimeSlotModel> list, String flag);
}
