package com.fruitvendorapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class UpgradePlanModel implements Parcelable {
    @SerializedName("djstripe_id")
    private String djstripeId;
    @SerializedName("product")
    private ProductPlanModel product;
    @SerializedName("id")
    private String id;
    @SerializedName("livemode")
    private Boolean livemode;
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
    @SerializedName("active")
    private Boolean active;
    @SerializedName("aggregate_usage")
    private String aggregateUsage;
    @SerializedName("amount")
    private String amount;
    @SerializedName("amount_decimal")
    private String amountDecimal;
    @SerializedName("billing_scheme")
    private String billingScheme;
    @SerializedName("currency")
    private String currency;
    @SerializedName("interval")
    private String interval;
    @SerializedName("interval_count")
    private String intervalCount;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("tiers")
    private String tiers;
    @SerializedName("tiers_mode")
    private String tiersMode;
    @SerializedName("transform_usage")
    private String transformUsage;
    @SerializedName("trial_period_days")
    private String trialPeriodDays;
    @SerializedName("usage_type")
    private String usageType;
    @SerializedName("djstripe_owner_account")
    private String djstripeOwnerAccount;

    public String getDjstripeId() {
        return djstripeId;
    }

    public void setDjstripeId(String djstripeId) {
        this.djstripeId = djstripeId;
    }

    public ProductPlanModel getProduct() {
        return product;
    }

    public void setProduct(ProductPlanModel product) {
        this.product = product;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getLivemode() {
        return livemode;
    }

    public void setLivemode(Boolean livemode) {
        this.livemode = livemode;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAggregateUsage() {
        return aggregateUsage;
    }

    public void setAggregateUsage(String aggregateUsage) {
        this.aggregateUsage = aggregateUsage;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmountDecimal() {
        return amountDecimal;
    }

    public void setAmountDecimal(String amountDecimal) {
        this.amountDecimal = amountDecimal;
    }

    public String getBillingScheme() {
        return billingScheme;
    }

    public void setBillingScheme(String billingScheme) {
        this.billingScheme = billingScheme;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getIntervalCount() {
        return intervalCount;
    }

    public void setIntervalCount(String intervalCount) {
        this.intervalCount = intervalCount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTiers() {
        return tiers;
    }

    public void setTiers(String tiers) {
        this.tiers = tiers;
    }

    public String getTiersMode() {
        return tiersMode;
    }

    public void setTiersMode(String tiersMode) {
        this.tiersMode = tiersMode;
    }

    public String getTransformUsage() {
        return transformUsage;
    }

    public void setTransformUsage(String transformUsage) {
        this.transformUsage = transformUsage;
    }

    public String getTrialPeriodDays() {
        return trialPeriodDays;
    }

    public void setTrialPeriodDays(String trialPeriodDays) {
        this.trialPeriodDays = trialPeriodDays;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public String getDjstripeOwnerAccount() {
        return djstripeOwnerAccount;
    }

    public void setDjstripeOwnerAccount(String djstripeOwnerAccount) {
        this.djstripeOwnerAccount = djstripeOwnerAccount;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(djstripeId);
        dest.writeValue(product);
        dest.writeValue(id);
        dest.writeValue(livemode);
        dest.writeValue(created);
        dest.writeValue(metadata);
        dest.writeValue(description);
        dest.writeValue(djstripeCreated);
        dest.writeValue(djstripeUpdated);
        dest.writeValue(active);
        dest.writeValue(aggregateUsage);
        dest.writeValue(amount);
        dest.writeValue(amountDecimal);
        dest.writeValue(billingScheme);
        dest.writeValue(currency);
        dest.writeValue(interval);
        dest.writeValue(intervalCount);
        dest.writeValue(nickname);
        dest.writeValue(tiers);
        dest.writeValue(tiersMode);
        dest.writeValue(transformUsage);
        dest.writeValue(trialPeriodDays);
        dest.writeValue(usageType);
        dest.writeValue(djstripeOwnerAccount);
    }

    protected UpgradePlanModel(Parcel in) {
        this.djstripeId = ((String) in.readValue((String.class.getClassLoader())));
        this.product = ((ProductPlanModel) in.readValue((ProductPlanModel.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.livemode = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.created = ((String) in.readValue((String.class.getClassLoader())));
        this.metadata = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.djstripeCreated = ((String) in.readValue((String.class.getClassLoader())));
        this.djstripeUpdated = ((String) in.readValue((String.class.getClassLoader())));
        this.active = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.aggregateUsage = ((String) in.readValue((String.class.getClassLoader())));
        this.amount = ((String) in.readValue((String.class.getClassLoader())));
        this.amountDecimal = ((String) in.readValue((String.class.getClassLoader())));
        this.billingScheme = ((String) in.readValue((String.class.getClassLoader())));
        this.currency = ((String) in.readValue((String.class.getClassLoader())));
        this.interval = ((String) in.readValue((String.class.getClassLoader())));
        this.intervalCount = ((String) in.readValue((String.class.getClassLoader())));
        this.nickname = ((String) in.readValue((String.class.getClassLoader())));
        this.tiers = ((String) in.readValue((String.class.getClassLoader())));
        this.tiersMode = ((String) in.readValue((String.class.getClassLoader())));
        this.transformUsage = ((String) in.readValue((String.class.getClassLoader())));
        this.trialPeriodDays = ((String) in.readValue((String.class.getClassLoader())));
        this.usageType = ((String) in.readValue((String.class.getClassLoader())));
        this.djstripeOwnerAccount = ((String) in.readValue((String.class.getClassLoader())));
    }
    public static final Creator<UpgradePlanModel> CREATOR = new Creator<UpgradePlanModel>() {
        @Override
        public UpgradePlanModel createFromParcel(Parcel source) {
            return new UpgradePlanModel(source);
        }

        @Override
        public UpgradePlanModel[] newArray(int size) {
            return new UpgradePlanModel[size];
        }
    };


}
