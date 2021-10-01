package com.fruitvendorapp.model;

public class StoreModel {
    private int header_img;
    private String title1;

    public StoreModel(int header_img, String title1) {
        this.header_img = header_img;
        this.title1 = title1;
    }

    public StoreModel(String title1) {
        this.title1 = title1;
    }

    public int getHeader_img() {
        return header_img;
    }

    public void setHeader_img(int header_img) {
        this.header_img = header_img;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }


}
