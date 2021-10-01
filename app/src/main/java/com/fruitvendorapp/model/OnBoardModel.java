package com.fruitvendorapp.model;

public class OnBoardModel {
    private int header_img;
    private String title1,title2;
    private String banner_img;

    public OnBoardModel(int header_img, String title1, String title2) {
        this.header_img = header_img;
        this.title1 = title1;
        this.title2 = title2;
    }

    public OnBoardModel(int header_img) {
        this.header_img = header_img;
    }

    public OnBoardModel(String banner_img) {
        this.banner_img = banner_img;
    }

    public String getBanner_img() {
        return banner_img;
    }

    public void setBanner_img(String banner_img) {
        this.banner_img = banner_img;
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

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }
}
