package com.fruitvendorapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class ProductPlanModel implements Parcelable {
    @SerializedName("djstripe_id")
    private String djstripeId;
    @SerializedName("djstripe_owner_account")
    private DjstripeOwnerAccount djstripeOwnerAccount;
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
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("active")
    private Boolean active;
    @SerializedName("attributes")
    private String attributes;
    @SerializedName("caption")
    private String caption;
    @SerializedName("deactivate_on")
    private String deactivateOn;
    @SerializedName("images")
    private String images;
    @SerializedName("package_dimensions")
    private String packageDimensions;
    @SerializedName("shippable")
    private String shippable;
    @SerializedName("url")
    private String url;
    @SerializedName("statement_descriptor")
    private String statementDescriptor;
    @SerializedName("unit_label")
    private String unitLabel;

    public String getDjstripeId() {
        return djstripeId;
    }

    public void setDjstripeId(String djstripeId) {
        this.djstripeId = djstripeId;
    }

    public DjstripeOwnerAccount getDjstripeOwnerAccount() {
        return djstripeOwnerAccount;
    }

    public void setDjstripeOwnerAccount(DjstripeOwnerAccount djstripeOwnerAccount) {
        this.djstripeOwnerAccount = djstripeOwnerAccount;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDeactivateOn() {
        return deactivateOn;
    }

    public void setDeactivateOn(String deactivateOn) {
        this.deactivateOn = deactivateOn;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getPackageDimensions() {
        return packageDimensions;
    }

    public void setPackageDimensions(String packageDimensions) {
        this.packageDimensions = packageDimensions;
    }

    public String getShippable() {
        return shippable;
    }

    public void setShippable(String shippable) {
        this.shippable = shippable;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatementDescriptor() {
        return statementDescriptor;
    }

    public void setStatementDescriptor(String statementDescriptor) {
        this.statementDescriptor = statementDescriptor;
    }

    public String getUnitLabel() {
        return unitLabel;
    }

    public void setUnitLabel(String unitLabel) {
        this.unitLabel = unitLabel;
    }


    protected ProductPlanModel(Parcel in) {
        this.djstripeId = ((String) in.readValue((String.class.getClassLoader())));
        this.djstripeOwnerAccount = ((DjstripeOwnerAccount) in.readValue((DjstripeOwnerAccount.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.livemode = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.created = ((String) in.readValue((String.class.getClassLoader())));
        this.metadata = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.djstripeCreated = ((String) in.readValue((String.class.getClassLoader())));
        this.djstripeUpdated = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.active = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.attributes = ((String) in.readValue((String.class.getClassLoader())));
        this.caption = ((String) in.readValue((String.class.getClassLoader())));
        this.deactivateOn = ((String) in.readValue((String.class.getClassLoader())));
        this.images = ((String) in.readValue((String.class.getClassLoader())));
        this.packageDimensions = ((String) in.readValue((String.class.getClassLoader())));
        this.shippable = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.statementDescriptor = ((String) in.readValue((String.class.getClassLoader())));
        this.unitLabel = ((String) in.readValue((String.class.getClassLoader())));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(djstripeId);
        dest.writeValue(djstripeOwnerAccount);
        dest.writeValue(id);
        dest.writeValue(livemode);
        dest.writeValue(created);
        dest.writeValue(metadata);
        dest.writeValue(description);
        dest.writeValue(djstripeCreated);
        dest.writeValue(djstripeUpdated);
        dest.writeValue(name);
        dest.writeValue(type);
        dest.writeValue(active);
        dest.writeValue(attributes);
        dest.writeValue(caption);
        dest.writeValue(deactivateOn);
        dest.writeValue(images);
        dest.writeValue(packageDimensions);
        dest.writeValue(shippable);
        dest.writeValue(url);
        dest.writeValue(statementDescriptor);
        dest.writeValue(unitLabel);
    }
    public static final Creator<ProductPlanModel> CREATOR = new Creator<ProductPlanModel>() {
        @Override
        public ProductPlanModel createFromParcel(Parcel source) {
            return new ProductPlanModel(source);
        }

        @Override
        public ProductPlanModel[] newArray(int size) {
            return new ProductPlanModel[size];
        }
    };


}
