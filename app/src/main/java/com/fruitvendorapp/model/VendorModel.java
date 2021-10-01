package com.fruitvendorapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fruitvendorapp.utilities.Constant;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class VendorModel implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("address")
    private AddressModel address;
    @SerializedName("images")
    private ArrayList<ImageModel> images;
    @SerializedName("banner_images")
    private ArrayList<BannerImage> bannerImages;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName(Constant.TITLE)
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("user")
    private UserModel user;
    @SerializedName("categories")
    private ArrayList<CategoryModel> categories;
    @SerializedName("is_updated")
    private Boolean isUpdated;
    @SerializedName("abn")
    private String abn;
    @SerializedName("loyal")
    private String loyal;
    @SerializedName("website")
    private String website;
    @SerializedName("email")
    private String email;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("customer")
    private String customer;
    @SerializedName("subscription")
    private String subscription;
    public ArrayList<BannerImage> getBannerImages() {
        return bannerImages;
    }

    public ArrayList<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<CategoryModel> categories) {
        this.categories = categories;
    }

    public void setBannerImages(ArrayList<BannerImage> bannerImages) {
        this.bannerImages = bannerImages;
    }

    public ArrayList<ImageModel> getImages() {
        return images;
    }

    public void setImages(ArrayList<ImageModel> images) {
        this.images = images;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Boolean getUpdated() {
        return isUpdated;
    }

    public void setUpdated(Boolean updated) {
        isUpdated = updated;
    }

    public String getAbn() {
        return abn;
    }

    public void setAbn(String abn) {
        this.abn = abn;
    }

    public String getLoyal() {
        return loyal;
    }

    public void setLoyal(String loyal) {
        this.loyal = loyal;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(address);
        dest.writeValue(images);
        dest.writeValue(bannerImages);
        dest.writeValue(imageUrl);
        dest.writeValue(title);
        dest.writeValue(description);
        dest.writeValue(user);
        dest.writeList(categories);
        dest.writeValue(isUpdated);
        dest.writeValue(abn);
        dest.writeValue(loyal);
        dest.writeValue(website);
        dest.writeValue(email);
        dest.writeValue(mobile);
        dest.writeValue(customer);
        dest.writeValue(subscription);


    }
    protected VendorModel(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.address = ((AddressModel) in.readValue((AddressModel.class.getClassLoader())));
        this.images = in.createTypedArrayList(ImageModel.CREATOR);
        this.bannerImages = in.createTypedArrayList(BannerImage.CREATOR);
        this.imageUrl =((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.user = ((UserModel) in.readValue((UserModel.class.getClassLoader())));
        this.categories = in.createTypedArrayList(CategoryModel.CREATOR);
        this.isUpdated = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.abn = ((String) in.readValue((String.class.getClassLoader())));
        this.loyal = ((String) in.readValue((String.class.getClassLoader())));
        this.website = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.mobile = ((String) in.readValue((String.class.getClassLoader())));
        this.customer = ((String) in.readValue((Object.class.getClassLoader())));
        this.subscription = ((String) in.readValue((Object.class.getClassLoader())));

    }


    public static final Creator<VendorModel> CREATOR = new Creator<VendorModel>() {
        @Override
        public VendorModel createFromParcel(Parcel source) {
            return new VendorModel(source);
        }

        @Override
        public  VendorModel[] newArray(int size) {
            return new VendorModel[size];
        }
    };

}
