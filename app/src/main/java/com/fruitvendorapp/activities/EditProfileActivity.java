package com.fruitvendorapp.activities;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.fruitvendorapp.R;
import com.fruitvendorapp.adapters.DeliverTypeAdapter;
import com.fruitvendorapp.adapters.FruitBannerAdapter;
import com.fruitvendorapp.model.BannerImage;
import com.fruitvendorapp.model.DeliveryType;
import com.fruitvendorapp.model.VendorModel;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.server_networking.ResponseListener;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.FileUtil;
import com.fruitvendorapp.utilities.ProgressDialogUtil;
import com.fruitvendorapp.utilities.SessionManager;
import com.fruitvendorapp.utilities.Urls;
import com.fruitvendorapp.utilities.Validation;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import io.sentry.Sentry;

import static com.fruitvendorapp.utilities.Constant.STATUS;
import static com.fruitvendorapp.utilities.Constant.TWILIO_API_KEY;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener {
    private static final String TAG = ProfileSettingActivity.class.getSimpleName();
    private static final int UPDATE_PROFILE = 100;
    private static final int YOUR_SELECT_PICTURE_REQUEST_CODE = 101;
    private static final int YOUR_SELECT_MULTIUPLE_PICTURE_REQUEST_CODE = 103;
    private static final int ZXING_CAMERA_PERMISSION = 102;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_edit)
    TextView tvEditBtn;

    @BindView(R.id.ed_name)
    AppCompatEditText edName;
    @BindView(R.id.ed_email)
    AppCompatEditText edEmail;
    @BindView(R.id.ed_phone)
    AppCompatEditText edPhone;
    @BindView(R.id.ed_address)
    AppCompatEditText edAddress;
    @BindView(R.id.ed_website)
    AppCompatEditText edWebSite;
    @BindView(R.id.ed_abn)
    AppCompatEditText edAbn;
    @BindView(R.id.iv_add_image)
    ImageView ivAddImage;
    @BindView(R.id.cmv_profile)
    ImageView cmvUserImage;
    @BindView(R.id.iv_holder_image)
    ImageView ivHolderImage;
    @BindView(R.id.iv_add_banner)
    TextView ivAddBanner;
    @BindView(R.id.delivery_title)
    TextView deliveryTitle;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.rv_deliver)
    RecyclerView rvDeliver;
    @BindView(R.id.tv_country_code)
    AppCompatEditText tvCountryCode;
    @BindView(R.id.ccp)
    CountryCodePicker ccp;

    ViewPager vpImageSlider;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private ProgressDialogUtil progressDialogUtil;
    private String shop_name = "", phone = "", email = "", abn = "", website = "", address = "", countryCode = "";
    private String selectedImagePath;
    private File image;
    private Uri outputFileUri;
    private ArrayList<BannerImage> imagesEncodedList;
    int openFlag;
    private List<File> multiPartDataArrayList;
    private DeliverTypeAdapter deliverTypeAdapter;
    private ArrayList<DeliveryType> deliveryTypes;
    private String deliver_id = "", mobileno = "", email_address = "";
    private boolean isPlaceSelect;
    private String latitude = "", longitude = "";
    private LatLng mCenterLatLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing ProfileSettingActivity");
        init();
    }

    private void init() {
        vpImageSlider = findViewById(R.id.vp_image_slider);
        progressDialogUtil = new ProgressDialogUtil(this);
        tvToolbarTitle.setText(getString(R.string.profile_setting));
        ivBack.setOnClickListener(this);
        ivAddImage.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        ivAddBanner.setOnClickListener(this);
        tvEditBtn.setOnClickListener(this);
        edAddress.setOnClickListener(this);
        getVendorApi();
        // Initialize Places.
        Places.initialize(getApplicationContext(), getString(R.string.places_key));

// Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);
        try {
            latitude = new SessionManager(this).getAddress().getString(Constant.LATITUDE);
            longitude = new SessionManager(this).getAddress().getString(Constant.LONGITUDE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_add_image:
                openFlag = 1;
                checkAndRequestPermissions(openFlag);
                break;
            case R.id.iv_add_banner:
                openFlag = 2;
                checkAndRequestPermissions(openFlag);
                break;
            case R.id.btn_update:
                attemptProfile();
                break;
            case R.id.iv_edit:
                BaseUtility.sendActivityIntent(this, EditProfileActivity.class);
                break;
            case R.id.ed_address:
                // Specify the fields to return.
                List<Place.Field> placeFields = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.ID,
                        Place.Field.LAT_LNG,
                        Place.Field.NAME);
// Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, placeFields)
                        .build(this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
                break;
        }
    }

    private void attemptProfile() {
        edName.setError(null);
        edEmail.setError(null);
        edPhone.setError(null);
        edWebSite.setError(null);
        edAbn.setError(null);
        edAddress.setError(null);

        shop_name = edName.getText().toString();
        phone = edPhone.getText().toString();
        email = edEmail.getText().toString();
        website = edWebSite.getText().toString();
        abn = edAbn.getText().toString();
        address = edAddress.getText().toString();
        //    countryCode =tvCountryCode.getText().toString();
        countryCode = ccp.getSelectedCountryCode();
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = ccp.getSelectedCountryCode();
                Log.e(TAG, countryCode);
            }
        });

        if (TextUtils.isEmpty(shop_name)) {
            edName.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(phone)) {
            edPhone.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(phone)) {
            edPhone.setError(getString(R.string.error_field_required));
        } else if (!Validation.isValidPhone(phone)) {
            edPhone.setError(getString(R.string.invalid_mobile));

        } /*else if (!Validation.isValidMobile(phone)) {
            edPhone.setError(getString(R.string.error_invalid_mobile));
        }*/ else if (TextUtils.isEmpty(email)) {
            edEmail.setError(getString(R.string.error_field_required));
        } else if (!Validation.isValidEmail(email)) {
            edEmail.setError(getString(R.string.error_invalid_email));
        } else if (TextUtils.isEmpty(abn)) {
            edAbn.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(address)) {
            edAddress.setError(getString(R.string.error_field_required));
        } else {
            if (!mobileno.equals(edPhone.getText().toString()) || !email_address.equals(edEmail.getText().toString())) {
                callOTPSendAPI(phone, "+" + countryCode);
            } else {
                uploadEditProfile();
            }
        }
    }


    public void getVendorApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            Log.e(TAG, new SessionManager(this).getToken());
            progressDialogUtil.showDialog();
            Log.e(TAG, Urls.SIGNUP_URL + Integer.parseInt(new SessionManager(this).getVendorId()));
            RequestHelper.getRequestWithToken(NetworkHelper.REQ_CODE_GET_VENDOR_DETAIL, this, Urls.SIGNUP_URL + new SessionManager(this).getVendorId(), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }


    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        if (requestCode == NetworkHelper.REQ_CODE_GET_VENDOR_DETAIL) {
            progressDialogUtil.dismissDialog();
            Log.e(TAG, json.toString());
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            VendorModel vendorModel = gson.fromJson(String.valueOf(json), VendorModel.class);
            if (vendorModel != null) {
                edName.setText(vendorModel.getTitle());
                email_address = vendorModel.getEmail();
                edEmail.setText(vendorModel.getEmail());
                mobileno = vendorModel.getUser().getNational_number();
                edPhone.setText(vendorModel.getUser().getNational_number());
                countryCode = vendorModel.getUser().getCountry_code();
                ccp.setCountryForPhoneCode(Integer.parseInt(countryCode));
                tvCountryCode.setText("+" + vendorModel.getUser().getCountry_code());
                if (vendorModel.getAddress() != null) {
                    new SessionManager(this).saveAddressDetail(vendorModel.getAddress());
                    edAddress.setText(vendorModel.getAddress().getAddress());
                }
                edWebSite.setText(vendorModel.getWebsite());
                edAbn.setText(vendorModel.getAbn());
                if (!TextUtils.isEmpty(vendorModel.getImageUrl())) {
                    Glide.with(this).load(Urls.IMAGE_URL + vendorModel.getImageUrl()).placeholder(R.drawable.ic_fruit_img).error(R.drawable.ic_fruit_img).into(cmvUserImage);
                } else {
                }

                ArrayList<BannerImage> imagesEncodedList = vendorModel.getBannerImages();
                if (imagesEncodedList != null && !imagesEncodedList.isEmpty()) {
                    setBannerAdapter(imagesEncodedList, "1");
                } else {

                }
            } else {

            }
        }
        if (requestCode == NetworkHelper.REQ_CODE_TWILIO_OTP_SEND) {
            progressDialogUtil.dismissDialog();
            Log.e(TAG, json.toString());
            try {
                if (Boolean.parseBoolean(json.optString(STATUS))) {
                    BaseUtility.toastMsg(this, "OTP Send Successfully");
                    Intent intent = new Intent(EditProfileActivity.this, OTPVerifyActivity.class);
                    intent.putExtra(Constant.COUNTRY_CODE, countryCode);
                    intent.putExtra(Constant.PHONE_NUMBER, edPhone.getText().toString());
                    intent.putExtra(Constant.FROM_PLACE, "edit_profile");
                    startActivityForResult(intent, UPDATE_PROFILE);
                    //finish();
                } else {
                    BaseUtility.toastMsg(this, json.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION: {
                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        openImageIntent(openFlag);
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                            new BaseUtility().showDialogOK(this, getString(R.string.camera_permission_req),
                                    (dialog, which) -> {
                                        switch (which) {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                checkAndRequestPermissions(openFlag);
                                                break;
                                            case DialogInterface.BUTTON_NEGATIVE:
                                                break;
                                        }
                                    });
                        } else {
                            Toast.makeText(this, R.string.setting_enable_permission, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                }
            }
        }
    }

    private void checkAndRequestPermissions(int openFlag) {
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        /*if (write != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                write = ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE);
            }
        }*/
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (write != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), ZXING_CAMERA_PERMISSION);

        } else {
            openImageIntent(openFlag);
        }
    }

    // this method open gallery and camera
    private void openImageIntent(int openFlag) {
        if (openFlag == 1) {
            final File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "LovFreshVendor" + File.separator);
            root.mkdirs();
            final String fname = "img_" + System.currentTimeMillis() + ".jpg";
            final File sdImageMainDirectory = new File(root, fname);
            outputFileUri = Uri.fromFile(sdImageMainDirectory);
            final List<Intent> cameraIntents = new ArrayList<Intent>();
            final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            final PackageManager packageManager = this.getPackageManager();
            final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
            for (ResolveInfo res : listCam) {
                final String packageName = res.activityInfo.packageName;
                final Intent intent = new Intent(captureIntent);
                intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                intent.setPackage(packageName);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                cameraIntents.add(intent);
            }
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            final Intent chooserIntent = Intent.createChooser(photoPickerIntent, "Select Source");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));
            startActivityForResult(chooserIntent, YOUR_SELECT_PICTURE_REQUEST_CODE);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), YOUR_SELECT_MULTIUPLE_PICTURE_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == YOUR_SELECT_PICTURE_REQUEST_CODE && resultCode == RESULT_OK) {
            final boolean isCamera;
            if (data == null) {
                isCamera = true;
            } else {
                final String action = data.getAction();
                if (action == null) {
                    isCamera = false;
                } else {
                    isCamera = action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
                }
            }
            Uri selectedImageUri;
            String path;
            if (isCamera) {
                selectedImageUri = outputFileUri;
                try {
                    selectedImagePath = String.valueOf(new Compressor(this).compressToFile(FileUtil.from(this, selectedImageUri)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                cmvUserImage.setImageBitmap(bitmap);
            } else {
                selectedImageUri = data == null ? null : data.getData();
                try {
                    selectedImagePath = String.valueOf(new Compressor(this).compressToFile(FileUtil.from(this, data.getData())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                Log.e(TAG, selectedImagePath + "...");
                cmvUserImage.setImageBitmap(bitmap);
            }
        } else if (requestCode == YOUR_SELECT_MULTIUPLE_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                imagesEncodedList = new ArrayList<>();
                if (data.getData() != null) {
                    try {
                        String imagePath = String.valueOf(new Compressor(this).compressToFile(FileUtil.from(this, data.getData())));
                        imagesEncodedList.add(new BannerImage(imagePath));
                        Log.e(TAG, String.valueOf(imagesEncodedList.size()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        try {
                            String imagePath = String.valueOf(new Compressor(this).compressToFile(FileUtil.from(this, mClipData.getItemAt(i).getUri())));
                            imagesEncodedList.add(new BannerImage(imagePath));
                            Log.e(TAG, String.valueOf(imagesEncodedList.size()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Log.e(TAG, ";;;;;;;;;;;;;;;;;" + String.valueOf(imagesEncodedList.size()));
                ivHolderImage.setVisibility(View.GONE);
                setBannerAdapter(imagesEncodedList, "");
            } catch (Exception e) {
                BaseUtility.toastMsg(this, getString(R.string.error_something_went_wrong));
            }

        } else if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + place.getAddress() + place.getLatLng());
                isPlaceSelect = true;
                mCenterLatLong = place.getLatLng();
                if (mCenterLatLong != null) {
                    latitude = String.valueOf(mCenterLatLong.latitude);
                    longitude = String.valueOf(mCenterLatLong.longitude);
                }
                edAddress.setText(place.getAddress());
                edAddress.setError(null);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                assert data != null;
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        } else if (requestCode == UPDATE_PROFILE) {
            uploadEditProfile();
        }
    }

    // implement this method for edit user profile  with image and without image
    private void uploadEditProfile() {
        Log.e(TAG, new SessionManager(this).getToken());
        progressDialogUtil.showDialog();
        if (imagesEncodedList != null && !imagesEncodedList.isEmpty()) {
            multiPartDataArrayList = new ArrayList<>();
            for (int i = 0; i < imagesEncodedList.size(); i++) {
                if (!TextUtils.isEmpty(imagesEncodedList.get(i).getImage())) {
                    File images = new File(imagesEncodedList.get(i).getImage());
                    Log.e(TAG, "images = " + imagesEncodedList.get(i).getImage());
                    multiPartDataArrayList.add(images);
                }
            }
        }
        if (multiPartDataArrayList != null && multiPartDataArrayList.size() > 0 && !TextUtils.isEmpty(selectedImagePath)) {
            image = new File(selectedImagePath);
            Log.e(TAG, "image = " + image);
            try {
                AndroidNetworking.upload(Urls.VENDOR_SHOP_PROFILE_URL)
                        .addHeaders("Authorization", "Token " + new SessionManager(this).getToken())
                        .addHeaders("Content-Type", "multipart/form-data")
                        .addMultipartFile(Constant.IMAGE, image)
                        .addMultipartFileList(Constant.BANNER_IMAGES, multiPartDataArrayList)
                        .addMultipartParameter(Constant.TITLE, shop_name)
                        .addMultipartParameter(Constant.EMAIL, email)
                        .addMultipartParameter(Constant.MOBILE, "+" + countryCode + phone)
                        .addMultipartParameter(Constant.ABN, abn)
                        .addMultipartParameter(Constant.WEBSITE, website)
                        .addMultipartParameter(Constant.ADD_ID, new SessionManager(this).getAddress().get(Constant.ADD_ID).toString())
                        .addMultipartParameter(Constant.ADDRESS, address)
                        .addMultipartParameter(Constant.LATITUDE, latitude)
                        .addMultipartParameter(Constant.LONGITUDE, longitude)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                progressDialogUtil.dismissDialog();
                                Log.e(TAG, "response" + jsonObject.toString());
                                String message = jsonObject.optString(Constant.MESSAGE);
                                try {
                                    GsonBuilder gsonBuilder = new GsonBuilder();
                                    Gson gson = gsonBuilder.create();
                                    VendorModel vendorModel = gson.fromJson(String.valueOf(jsonObject), VendorModel.class);
                                    SessionManager sessionManager = new SessionManager(EditProfileActivity.this);
                                    sessionManager.setShopTitle(vendorModel.getTitle());
                                    onBackPressed();
                                    Toast.makeText(EditProfileActivity.this, "Update Profile Successfully", Toast.LENGTH_SHORT).show();
                                    /*if (!mobileno.equals(edPhone.getText().toString()) || !email_address.equals(edEmail.getText().toString())) {
                                        callOTPSendAPI(phone, "+" + countryCode);
                                    } else {
                                        onBackPressed();
                                        Toast.makeText(EditProfileActivity.this, "Update Profile Successfully", Toast.LENGTH_SHORT).show();
                                    }*/
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e(TAG, "error" + e.getMessage());
                                }
                                BaseUtility.deleteImageCacheFile(EditProfileActivity.this, imagesEncodedList);
                            }

                            @Override
                            public void onError(ANError error) {
                                progressDialogUtil.dismissDialog();
                                //   Log.e(TAG, error.getErrorBody() + error.toString());
                                if (error.getErrorCode() == 400) {
                                    String json = error.getErrorBody();
                                    try {
                                        JSONObject object = new JSONObject(json);
                                        String error1 = object.optString(Constant.ERROR);
                                        if (error1.length() > 0) {
                                            Toast.makeText(EditProfileActivity.this, error1, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (multiPartDataArrayList != null && multiPartDataArrayList.size() > 0) {
            try {
                AndroidNetworking.upload(Urls.VENDOR_SHOP_PROFILE_URL)
                        .addHeaders("Authorization", "Token " + new SessionManager(this).getToken())
                        .addHeaders("Content-Type", "multipart/form-data")
                        .addMultipartParameter(Constant.IMAGE, "")
                        .addMultipartFileList(Constant.BANNER_IMAGES, multiPartDataArrayList)
                        .addMultipartParameter(Constant.TITLE, shop_name)
                        .addMultipartParameter(Constant.EMAIL, email)
                        .addMultipartParameter(Constant.MOBILE, "+" + countryCode + phone)
                        .addMultipartParameter(Constant.ABN, abn)
                        .addMultipartParameter(Constant.WEBSITE, website)
                        .addMultipartParameter(Constant.ADD_ID, new SessionManager(this).getAddress().get(Constant.ADD_ID).toString())
                        .addMultipartParameter(Constant.ADDRESS, address)
                        .addMultipartParameter(Constant.LATITUDE, latitude)
                        .addMultipartParameter(Constant.LONGITUDE, longitude)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                progressDialogUtil.dismissDialog();
                                Log.e(TAG, "response" + jsonObject.toString());
                                String message = jsonObject.optString(Constant.MESSAGE);
                                try {
                                    GsonBuilder gsonBuilder = new GsonBuilder();
                                    Gson gson = gsonBuilder.create();
                                    VendorModel vendorModel = gson.fromJson(String.valueOf(jsonObject), VendorModel.class);
                                    SessionManager sessionManager = new SessionManager(EditProfileActivity.this);
                                    sessionManager.setShopTitle(vendorModel.getTitle());
                                    onBackPressed();
                                    Toast.makeText(EditProfileActivity.this, "Update Profile Successfully", Toast.LENGTH_SHORT).show();
                                   /* if (!mobileno.equals(edPhone.getText().toString()) || !email_address.equals(edEmail.getText().toString())) {
                                        callOTPSendAPI(phone, "+" + countryCode);
                                    } else {
                                        onBackPressed();
                                        Toast.makeText(EditProfileActivity.this, "Update Profile Successfully", Toast.LENGTH_SHORT).show();
                                    }*/
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e(TAG, "error" + e.getMessage());
                                }
                                BaseUtility.deleteImageCacheFile(EditProfileActivity.this, imagesEncodedList);
                            }

                            @Override
                            public void onError(ANError error) {
                                progressDialogUtil.dismissDialog();
                                Log.e(TAG, error.toString());
                                //Log.e(TAG, error.getErrorBody() + error.toString());
                                //BaseUtility.toastMsg(ProfileScreenActivity.this, getString(R.string.server_error));
                                if (error.getErrorCode() == 400) {
                                    String json = error.getErrorBody();
                                    try {
                                        JSONObject object = new JSONObject(json);
                                        String error1 = object.optString(Constant.ERROR);
                                        if (error1.length() > 0) {
                                            Toast.makeText(EditProfileActivity.this, error1, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (!TextUtils.isEmpty(selectedImagePath)) {
            image = new File(selectedImagePath);
            Log.e(TAG, "image = " + image);
            try {
                AndroidNetworking.upload(Urls.VENDOR_SHOP_PROFILE_URL)
                        .addHeaders("Authorization", "Token " + new SessionManager(this).getToken())
                        .addHeaders("Content-Type", "multipart/form-data")
                        .addMultipartFile(Constant.IMAGE, image)
                        .addMultipartFileList(Constant.BANNER_IMAGES, null)
                        .addMultipartParameter(Constant.TITLE, shop_name)
                        .addMultipartParameter(Constant.EMAIL, email)
                        .addMultipartParameter(Constant.MOBILE, "+" + countryCode + phone)
                        .addMultipartParameter(Constant.ABN, abn)
                        .addMultipartParameter(Constant.WEBSITE, website)
                        .addMultipartParameter(Constant.ADD_ID, new SessionManager(this).getAddress().get(Constant.ADD_ID).toString())
                        .addMultipartParameter(Constant.ADDRESS, address)
                        .addMultipartParameter(Constant.LATITUDE, latitude)
                        .addMultipartParameter(Constant.LONGITUDE, longitude)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                progressDialogUtil.dismissDialog();
                                Log.e(TAG, "response" + jsonObject.toString());
                                String message = jsonObject.optString(Constant.MESSAGE);
                                try {
                                    GsonBuilder gsonBuilder = new GsonBuilder();
                                    Gson gson = gsonBuilder.create();
                                    VendorModel vendorModel = gson.fromJson(String.valueOf(jsonObject), VendorModel.class);
                                    SessionManager sessionManager = new SessionManager(EditProfileActivity.this);
                                    sessionManager.setShopTitle(vendorModel.getTitle());
                                    onBackPressed();
                                    Toast.makeText(EditProfileActivity.this, "Update Profile Successfully", Toast.LENGTH_SHORT).show();
                                    /*if (!mobileno.equals(edPhone.getText().toString()) || !email_address.equals(edEmail.getText().toString())) {
                                        callOTPSendAPI(phone, "+" + countryCode);
                                    } else {
                                        onBackPressed();
                                        Toast.makeText(EditProfileActivity.this, "Update Profile Successfully", Toast.LENGTH_SHORT).show();
                                    }*/
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e(TAG, "error" + e.getMessage());
                                }
                                //  BaseUtility.deleteImageCacheFile(EditProfileActivity.this,imagesEncodedList);
                            }

                            @Override
                            public void onError(ANError error) {
                                progressDialogUtil.dismissDialog();
                                Log.e(TAG, error.toString());
                                //Log.e(TAG, error.getErrorBody() + error.toString());
                                //BaseUtility.toastMsg(ProfileScreenActivity.this, getString(R.string.server_error));
                                if (error.getErrorCode() == 400) {
                                    String json = error.getErrorBody();
                                    try {
                                        JSONObject object = new JSONObject(json);
                                        String error1 = object.optString(Constant.ERROR);
                                        if (error1.length() > 0) {
                                            Toast.makeText(EditProfileActivity.this, error1, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                AndroidNetworking.upload(Urls.VENDOR_SHOP_PROFILE_URL)
                        .addHeaders("Authorization", "Token " + new SessionManager(this).getToken())
                        .addHeaders("Content-Type", "multipart/form-data")
                        .addMultipartParameter(Constant.IMAGE, "")
                        .addMultipartFileList(Constant.BANNER_IMAGES, null)
                        .addMultipartParameter(Constant.TITLE, shop_name)
                        .addMultipartParameter(Constant.EMAIL, email)
                        .addMultipartParameter(Constant.MOBILE, "+" + countryCode + phone)
                        .addMultipartParameter(Constant.ABN, abn)
                        .addMultipartParameter(Constant.WEBSITE, website)
                        .addMultipartParameter(Constant.ADD_ID, new SessionManager(this).getAddress().get(Constant.ADD_ID).toString())
                        .addMultipartParameter(Constant.ADDRESS, address)
                        .addMultipartParameter(Constant.LATITUDE, latitude)
                        .addMultipartParameter(Constant.LONGITUDE, longitude)
                        //.addMultipartParameter(Constant.DELIVERY_TYPE, deliver_id)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                progressDialogUtil.dismissDialog();
                                Log.e(TAG, "response" + jsonObject.toString());
                                String message = jsonObject.optString(Constant.MESSAGE);
                                try {
                                    GsonBuilder gsonBuilder = new GsonBuilder();
                                    Gson gson = gsonBuilder.create();
                                    VendorModel vendorModel = gson.fromJson(String.valueOf(jsonObject), VendorModel.class);
                                    SessionManager sessionManager = new SessionManager(EditProfileActivity.this);
                                    sessionManager.setShopTitle(vendorModel.getTitle());
                                    onBackPressed();
                                    Toast.makeText(EditProfileActivity.this, "Update Profile Successfully", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                    Toast.makeText(EditProfileActivity.this, "Update Profile Successfully", Toast.LENGTH_SHORT).show();
                                    /*if (!edPhone.getText().toString().equals(mobileno) || !edEmail.getText().toString().equals(email_address)) {
                                        callOTPSendAPI(phone, "+" + countryCode);
                                    } else {
                                        onBackPressed();
                                        Toast.makeText(EditProfileActivity.this, "Update Profile Successfully", Toast.LENGTH_SHORT).show();
                                    }*/
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e(TAG, "error" + e.getMessage());
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                progressDialogUtil.dismissDialog();
                                Log.e(TAG, error.toString());
                                //Log.e(TAG, error.getErrorBody() + error.toString());
                                //BaseUtility.toastMsg(ProfileScreenActivity.this, getString(R.string.server_error));
                                if (error.getErrorCode() == 400) {
                                    String json = error.getErrorBody();
                                    try {
                                        JSONObject object = new JSONObject(json);
                                        String error1 = object.optString(Constant.ERROR);
                                        if (error1.length() > 0) {
                                            Toast.makeText(EditProfileActivity.this, error1, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void callOTPSendAPI(String mobileNumber, String countryCode) {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.PostTwilioRequest(NetworkHelper.REQ_CODE_TWILIO_OTP_SEND, this, TWILIO_API_KEY, Urls.SEND_TWILIO_OTP_URL,
                    new NetworkHelper(this).twilioOtpJson(mobileNumber, countryCode), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    // set Banner Adapter
    private void setBannerAdapter(ArrayList<BannerImage> bannerModelArrayList, String flag) {
        FruitBannerAdapter fruitBannerAdapter = new FruitBannerAdapter(this, bannerModelArrayList, flag);
        vpImageSlider.setAdapter(fruitBannerAdapter);
        vpImageSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPage = position;
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    int pageCount = bannerModelArrayList.size();
                }
            }
        });

    }
}