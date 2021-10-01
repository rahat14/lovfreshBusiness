package com.fruitvendorapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fruitvendorapp.utilities.Constant;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class UserModel implements Parcelable {
    @SerializedName(Constant.USER_ID)
    private String user_id;
    @SerializedName(Constant.TOKEN)
    private String token;
    @SerializedName(Constant.PASSWORD)
    private String password;
    @SerializedName(Constant.LAST_LOGIN)
    private String lastLogin;
    @SerializedName(Constant.IS_SUPER_USER)
    private Boolean isSuperuser;
    @SerializedName(Constant.USER_NAME)
    private String username;
    @SerializedName(Constant.FIRST_NAME)
    private String firstName;
    @SerializedName(Constant.LAST_NAME)
    private String lastName;
    @SerializedName(Constant.EMAIL)
    private String email;
    @SerializedName(Constant.NATIONAL_NUMBER)
    private String national_number;
   @SerializedName(Constant.COUNTRY_CODE)
    private String country_code;

    @SerializedName(Constant.IS_STAFF)
    private Boolean isStaff;
    @SerializedName(Constant.DATE_JOINED)
    private String dateJoined;
    @SerializedName(Constant.PHONE_NUMBER)
    private String phoneNumber;
    @SerializedName(Constant.IS_VENDOR)
    private Boolean isVendor;
    @SerializedName("is_client")
    private Boolean isClient;
    @SerializedName(Constant.IS_ACTIVE)
    private Boolean isActive;
    @SerializedName("groups")
    private List<Object> groups = null;
    @SerializedName("user_permissions")
    private List<Object> userPermissions = null;

    public String getNational_number() {
        return national_number;
    }

    public void setNational_number(String national_number) {
        this.national_number = national_number;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Boolean getSuperuser() {
        return isSuperuser;
    }

    public void setSuperuser(Boolean superuser) {
        isSuperuser = superuser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStaff() {
        return isStaff;
    }

    public void setStaff(Boolean staff) {
        isStaff = staff;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getVendor() {
        return isVendor;
    }

    public void setVendor(Boolean vendor) {
        isVendor = vendor;
    }

    public Boolean getClient() {
        return isClient;
    }

    public void setClient(Boolean client) {
        isClient = client;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<Object> getGroups() {
        return groups;
    }

    public void setGroups(List<Object> groups) {
        this.groups = groups;
    }

    public List<Object> getUserPermissions() {
        return userPermissions;
    }

    public void setUserPermissions(List<Object> userPermissions) {
        this.userPermissions = userPermissions;
    }


    public static Creator<UserModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_id);
        dest.writeString(this.national_number);
        dest.writeString(this.country_code);
        dest.writeString(this.token);
        dest.writeString(this.password);
        dest.writeString(this.lastLogin);
        dest.writeString(this.username);
        dest.writeString(this.firstName);
        dest.writeString(this.lastLogin);
        dest.writeString(this.email);
        dest.writeString(this.phoneNumber);
        dest.writeBoolean(this.isSuperuser);
        dest.writeBoolean(this.isActive);
        dest.writeBoolean(this.isClient);
        dest.writeBoolean(this.isVendor);
        dest.writeBoolean(this.isStaff);
        dest.writeList(groups);
        dest.writeList(userPermissions);

    }
    protected UserModel(Parcel in) {
        this.user_id = ((String) in.readValue((String.class.getClassLoader())));
        this.national_number = ((String) in.readValue((String.class.getClassLoader())));
        this.country_code = ((String) in.readValue((String.class.getClassLoader())));
        this.token = ((String) in.readValue((String.class.getClassLoader())));
        this.password = ((String) in.readValue((String.class.getClassLoader())));
        this.lastLogin = ((String) in.readValue((String.class.getClassLoader())));
        this.isSuperuser = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.username = ((String) in.readValue((String.class.getClassLoader())));
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.isStaff = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.dateJoined = ((String) in.readValue((String.class.getClassLoader())));
        this.phoneNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.isVendor = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.isClient = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.isActive = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.groups, (Object.class.getClassLoader()));
        in.readList(this.userPermissions, (Object.class.getClassLoader()));
    }
    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

}
