package com.fruitvendorapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DjstripeOwnerAccount implements Parcelable {
    @SerializedName("djstripe_id")
    private String djstripeId;
    @SerializedName("id")
    private String id;
    @SerializedName("livemode")
    private String livemode;
    @SerializedName("created")
    private String created;
    @SerializedName("metadata")
    private String metadata;
    @SerializedName("description")
    private String description;
    @SerializedName("djstripe_created")
    private String djstripeCreated;
    @SerializedName("djstripe_updated")
    private String djstripeUpdated;
    @SerializedName("business_profile")
    private String businessProfile;
    @SerializedName("business_type")
    private String businessType;
    @SerializedName("charges_enabled")
    private Boolean chargesEnabled;
    @SerializedName("country")
    private String country;
    @SerializedName("company")
    private String company;
    @SerializedName("default_currency")
    private String defaultCurrency;
    @SerializedName("details_submitted")
    private Boolean detailsSubmitted;
    @SerializedName("email")
    private String email;
    @SerializedName("individual")
    private String individual;
    @SerializedName("payouts_enabled")
    private Boolean payoutsEnabled;
    @SerializedName("product_description")
    private String productDescription;
    @SerializedName("requirements")
    private String requirements;
    @SerializedName("settings")
    private String settings;
    @SerializedName("type")
    private String type;
    @SerializedName("tos_acceptance")
    private String tosAcceptance;

    public String getDjstripeId() {
        return djstripeId;
    }

    public void setDjstripeId(String djstripeId) {
        this.djstripeId = djstripeId;
    }

    public String getLivemode() {
        return livemode;
    }

    public void setLivemode(String livemode) {
        this.livemode = livemode;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDjstripeCreated() {
        return djstripeCreated;
    }

    public void setDjstripeCreated(String djstripeCreated) {
        this.djstripeCreated = djstripeCreated;
    }

    public String getDjstripeUpdated() {
        return djstripeUpdated;
    }

    public void setDjstripeUpdated(String djstripeUpdated) {
        this.djstripeUpdated = djstripeUpdated;
    }

    public String getBusinessProfile() {
        return businessProfile;
    }

    public void setBusinessProfile(String businessProfile) {
        this.businessProfile = businessProfile;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public Boolean getChargesEnabled() {
        return chargesEnabled;
    }

    public void setChargesEnabled(Boolean chargesEnabled) {
        this.chargesEnabled = chargesEnabled;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public Boolean getDetailsSubmitted() {
        return detailsSubmitted;
    }

    public void setDetailsSubmitted(Boolean detailsSubmitted) {
        this.detailsSubmitted = detailsSubmitted;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIndividual() {
        return individual;
    }

    public void setIndividual(String individual) {
        this.individual = individual;
    }

    public Boolean getPayoutsEnabled() {
        return payoutsEnabled;
    }

    public void setPayoutsEnabled(Boolean payoutsEnabled) {
        this.payoutsEnabled = payoutsEnabled;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTosAcceptance() {
        return tosAcceptance;
    }

    public void setTosAcceptance(String tosAcceptance) {
        this.tosAcceptance = tosAcceptance;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(djstripeId);
        dest.writeValue(id);
        dest.writeValue(livemode);
        dest.writeValue(created);
        dest.writeValue(metadata);
        dest.writeValue(description);
        dest.writeValue(djstripeCreated);
        dest.writeValue(djstripeUpdated);
        dest.writeValue(businessProfile);
        dest.writeValue(businessType);
        dest.writeValue(chargesEnabled);
        dest.writeValue(country);
        dest.writeValue(company);
        dest.writeValue(defaultCurrency);
        dest.writeValue(detailsSubmitted);
        dest.writeValue(email);
        dest.writeValue(individual);
        dest.writeValue(payoutsEnabled);
        dest.writeValue(productDescription);
        dest.writeValue(requirements);
        dest.writeValue(settings);
        dest.writeValue(type);
        dest.writeValue(tosAcceptance);
    }

    protected DjstripeOwnerAccount(Parcel in) {
        this.djstripeId = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.livemode = ((String) in.readValue((String.class.getClassLoader())));
        this.created = ((String) in.readValue((String.class.getClassLoader())));
        this.metadata = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.djstripeCreated = ((String) in.readValue((String.class.getClassLoader())));
        this.djstripeUpdated = ((String) in.readValue((String.class.getClassLoader())));
        this.businessProfile = ((String) in.readValue((String.class.getClassLoader())));
        this.businessType = ((String) in.readValue((String.class.getClassLoader())));
        this.chargesEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.country = ((String) in.readValue((String.class.getClassLoader())));
        this.company = ((String) in.readValue((String.class.getClassLoader())));
        this.defaultCurrency = ((String) in.readValue((String.class.getClassLoader())));
        this.detailsSubmitted = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.individual = ((String) in.readValue((String.class.getClassLoader())));
        this.payoutsEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.productDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.requirements = ((String) in.readValue((String.class.getClassLoader())));
        this.settings = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.tosAcceptance = ((String) in.readValue((String.class.getClassLoader())));
    }

    public static final Creator<DjstripeOwnerAccount> CREATOR = new Creator<DjstripeOwnerAccount>() {
        @Override
        public DjstripeOwnerAccount createFromParcel(Parcel source) {
            return new DjstripeOwnerAccount(source);
        }

        @Override
        public DjstripeOwnerAccount[] newArray(int size) {
            return new DjstripeOwnerAccount[size];
        }
    };

}
