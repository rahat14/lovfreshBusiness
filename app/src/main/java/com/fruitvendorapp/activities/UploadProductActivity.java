package com.fruitvendorapp.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fruitvendorapp.R;
import com.fruitvendorapp.adapters.CategoryFilterAdapter;
import com.fruitvendorapp.adapters.UnitSpinnerAdapter;
import com.fruitvendorapp.interfaces.ItemSelectInterface;
import com.fruitvendorapp.model.CategoryModel;
import com.fruitvendorapp.model.ProductImageModel;
import com.fruitvendorapp.model.UnitModel;
import com.fruitvendorapp.server_networking.JsonArrayResponseListener;
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
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import io.sentry.Sentry;

public class UploadProductActivity extends AppCompatActivity implements View.OnClickListener, JsonArrayResponseListener, ItemSelectInterface, ResponseListener {
    private static final String TAG = UploadProductActivity.class.getSimpleName();
    private static final int YOUR_SELECT_PICTURE_REQUEST_CODE = 101;
    private static final int ZXING_CAMERA_PERMISSION = 102;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.ed_title)
    AppCompatEditText edTitle;
    @BindView(R.id.ed_descripation)
    AppCompatEditText edDescripation;
    @BindView(R.id.ed_other_info)
    AppCompatEditText edOtherInfo;
    @BindView(R.id.ed_price)
    AppCompatEditText edPrice;
    @BindView(R.id.ed_quantity)
    AppCompatEditText edQuanity;
    @BindView(R.id.ed_stock_amt)
    AppCompatEditText edStockAmt;
    @BindView(R.id.sp_unit)
    Spinner spUnit;
    @BindView(R.id.sp_special)
    Spinner spSpecial;
    @BindView(R.id.tv_category)
    TextView tvCategroy;
    @BindView(R.id.sp_arro)
    ImageView ivCatgeory;
    @BindView(R.id.rl_category)
    RelativeLayout rlCatgeory;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_image1)
    RoundedImageView ivProduct1;
    @BindView(R.id.iv_image2)
    RoundedImageView ivProduct2;
    @BindView(R.id.iv_image3)
    RoundedImageView ivProduct3;
    @BindView(R.id.iv_image4)
    RoundedImageView ivProduct4;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.ed_start_date)
    TextView edStartDate;
    @BindView(R.id.ed_end_date)
    TextView edEndDate;
    @BindView(R.id.ll_schedule_view)
    LinearLayout llTypeView;
    @BindView(R.id.cb_promot_p)
    CheckBox cbPromotionPrice;
    @BindView(R.id.ed_pro_price)
    EditText edPromoPrice;

    private String selectedImagePath;
    private String indexType = "";
    private ArrayList<ProductImageModel> imageModelArrayList;
    private ProgressDialogUtil progressDialogUtil;
    private ArrayList<CategoryModel> categoryModelArrayList;
    private String categoryNm = "All Category";
    private String category_id = "", unitId = "";
    private String title = "", long_descri = "", short_descri = "", price = "", stock_amt = "";
    private String startDate, endDate, startDate_, endDate_;
    private int checkDateTime = 0;
    private ArrayList<Integer> list;
    private int categ_id = 0;
    private String type = "", flag = "", productId = "", quantity = "", promo_p = "";
    // CropImage.ActivityResult result;
    boolean isPromotionPrice = false;
    final String regex = "^\\-?(\\d{0,3}|\\d{0,3}\\.\\d{0,2})$";
    private final int GALLERY_ACTIVITY_CODE = 200;
    private final int RESULT_CROP = 400;
    private CategoryFilterAdapter adapter;
    private AlertDialog categoryAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing UploadProductActivity");
        init();
    }

    private void init() {
        progressDialogUtil = new ProgressDialogUtil(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        imageModelArrayList = new ArrayList<>();
        imageModelArrayList.add(new ProductImageModel(0));
        imageModelArrayList.add(new ProductImageModel(1));
        imageModelArrayList.add(new ProductImageModel(2));
        imageModelArrayList.add(new ProductImageModel(3));
        tvToolbarTitle.setText(R.string.upload_your_pro);
        ivBack.setOnClickListener(this);
        ivProduct1.setOnClickListener(this);
        ivProduct2.setOnClickListener(this);
        ivProduct3.setOnClickListener(this);
        ivProduct4.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
        rlCatgeory.setOnClickListener(this);
        // ivCatgeory.setOnClickListener(this);
        edStartDate.setOnClickListener(this);
        edEndDate.setOnClickListener(this);
        getTypeSpinner();
        getCategoryListApi();
        getUnitListApi();
        cbPromotionPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    isPromotionPrice = true;
                    Log.e(TAG, String.valueOf(isPromotionPrice));
                    edPromoPrice.setVisibility(View.VISIBLE);
                } else {
                    isPromotionPrice = false;
                    Log.e(TAG, String.valueOf(isPromotionPrice));
                    edPromoPrice.setVisibility(View.GONE);
                }

            }
        });

        edQuanity.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned destination, int destinationStart, int destinationEnd) {
                        if (end > start) {
                            // adding: filter
                            // build the resulting text
                            String destinationString = destination.toString();
                            String resultingTxt = destinationString.substring(0, destinationStart) + source.subSequence(start, end) + destinationString.substring(destinationEnd);
                            // return null to accept the input or empty to reject it
                            return resultingTxt.matches(regex) ? null : "";
                        }
                        // removing: always accept
                        return null;
                    }
                }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_image1:
                indexType = "0";
                checkAndRequestPermissions();
                break;
            case R.id.iv_image2:
                indexType = "1";
                checkAndRequestPermissions();
                break;
            case R.id.iv_image3:
                indexType = "2";
                checkAndRequestPermissions();
                break;
            case R.id.iv_image4:
                indexType = "3";
                checkAndRequestPermissions();
                break;
            case R.id.btn_upload:
                uploadProductValidation();
                break;
            case R.id.rl_category:
                if (categoryModelArrayList != null && !categoryModelArrayList.isEmpty()) {
                    selectCategoryDialog(categoryModelArrayList, this);
                }
                break;
            case R.id.ed_start_date:
                checkDateTime = 1;
                openDatePicker(checkDateTime);
                // BaseUtility.openDatePicker(this, edStartDate, checkDateTime);
                break;
            case R.id.ed_end_date:
                checkDateTime = 2;
                openDatePicker(checkDateTime);
                // BaseUtility.openDatePicker(this, edEndDate, checkDateTime);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCategoryListApi();
        //    selectCategoryDialog(categoryModelArrayList, this);


    }

    private void getTypeSpinner() {
        spSpecial.setAdapter(new ArrayAdapter<String>(UploadProductActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.array_special_type)));
        spSpecial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getSelectedItem().toString();
                Log.e("Type_special", type);
                if (type.equals("Set Schedule")) {
                    llTypeView.setVisibility(View.VISIBLE);
                } else {
                    llTypeView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void uploadProductValidation() {
        edTitle.setError(null);
        edDescripation.setError(null);
        //  edOtherInfo.setError(null);
        edPrice.setError(null);
        edStockAmt.setError(null);
        edStartDate.setError(null);
        edEndDate.setError(null);
        edQuanity.setError(null);
        title = edTitle.getText().toString();
        long_descri = edOtherInfo.getText().toString();
        short_descri = edDescripation.getText().toString();
        price = edPrice.getText().toString();
        stock_amt = edStockAmt.getText().toString();
        quantity = edQuanity.getText().toString();
        promo_p = edPromoPrice.getText().toString();

        if (TextUtils.isEmpty(title)) {
            edTitle.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(price)) {
            edPrice.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(quantity)) {
            edQuanity.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(stock_amt)) {
            edStockAmt.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(category_id)) {
            Toast.makeText(this, "Please Select Category", Toast.LENGTH_SHORT).show();
        } else if (isPromotionPrice && TextUtils.isEmpty(promo_p)) {
            edPromoPrice.setError(getString(R.string.error_field_required));
        } else if (isPromotionPrice && Double.parseDouble(promo_p) >= Double.parseDouble(price)) {
            BaseUtility.toastMsg(this, "Promotional Price should be less than original price.");
        } else {
            if (!imageModelArrayList.get(0).getProduct_img().isEmpty() || !imageModelArrayList.get(1).getProduct_img().isEmpty() || !imageModelArrayList.get(2).getProduct_img().isEmpty() || !imageModelArrayList.get(3).getProduct_img().isEmpty()) {
                //if(imageModelArrayList != null && !imageModelArrayList.isEmpty()){
                List<File> multiPartDataArrayList = new ArrayList<>();
                for (int i = 0; i < imageModelArrayList.size(); i++) {
                    if (!TextUtils.isEmpty(imageModelArrayList.get(i).getProduct_img())) {
                        File image = new File(imageModelArrayList.get(i).getProduct_img());
                        Log.e(TAG, "image = " + imageModelArrayList.get(i).getProduct_img());
                        multiPartDataArrayList.add(image);

                    }
                }
                Log.e("Type----->", type);
                if (type.equals("Set Schedule")) {
                    if (TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate)) {
                        BaseUtility.toastMsg(this, "Please add schedule date");
                    } else {
                        if (TextUtils.isEmpty(promo_p)) {
                            promo_p = "";
                        }
                        uploadProductApi(type, multiPartDataArrayList);
                    }


                } else {
                    if (TextUtils.isEmpty(promo_p)) {
                        promo_p = "";
                    }
                    uploadProductApi(type, multiPartDataArrayList);

                }
            } else {
                BaseUtility.toastMsg(this, "Please add Product Image");
            }
        }
    }

    // check read, write and camera run time permission
    private void checkAndRequestPermissions() {
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), ZXING_CAMERA_PERMISSION);

        } else {
            ImagePicker.Companion.with(this)
                    .crop(3f, 2f)
                    /*.galleryMimeTypes(
                             mimeTypes = arrayOf(
                                    "image/png",
                                    "image/jpg",
                                    "image/jpeg"
                            )
                    )*///Crop image(Optional), Check Customization for more option
                    .compress(1024)
                    .saveDir(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "LovFreshVendor"))//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)
                    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start();

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            Uri fileUri = data == null ? null : data.getData();
            // ivProduct1.setImageURI(fileUri);
            //You can get File object from intent
            try {
                selectedImagePath = String.valueOf(new Compressor(this).compressToFile(FileUtil.from(this, fileUri)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e(TAG, selectedImagePath + "..." + String.valueOf(imageModelArrayList.size()));

            if (indexType.equals("0")) {
                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                ivProduct1.setImageBitmap(bitmap);
                imageModelArrayList.get(0).setProduct_img(selectedImagePath);
            }
            if (indexType.equals("1")) {
                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                ivProduct2.setImageBitmap(bitmap);
                imageModelArrayList.get(1).setProduct_img(selectedImagePath);
            }
            if (indexType.equals("2")) {
                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                ivProduct3.setImageBitmap(bitmap);
                imageModelArrayList.get(2).setProduct_img(selectedImagePath);
            }
            if (indexType.equals("3")) {
                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                ivProduct4.setImageBitmap(bitmap);
                imageModelArrayList.get(3).setProduct_img(selectedImagePath);
            }
            //You can also get File Path from intent

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Log.e(TAG, "data" + data.getData());
            if (data.getStringExtra(ImagePicker.EXTRA_ERROR).length() > 0) {
                Toast.makeText(this, data.getStringExtra(ImagePicker.EXTRA_ERROR), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Log.e(TAG, "taskCancelled");
            //Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }


    private void getUnitListApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            //  progressDialogUtil.showDialog();
            RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_UNIT_LIST, this, Urls.UNIT_LIST_URL, this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }


    private void getCategoryListApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_CATEGROY_LIST, this, Urls.CATEGORY_LIST_URL, this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }

    }

    private void addCategoryApi(String cate_nm) {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.PostTokenRequest(NetworkHelper.REQ_CODE_ADD_CATEGROY, this, Urls.CATEGORY_LIST_URL, new NetworkHelper(this).addCatgeoryJson(cate_nm, new SessionManager(this).getVendorId()), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }

    }


    @Override
    public void onSuccess(int requestCode, JSONArray json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_CATEGROY_LIST:
                Log.e(TAG, json.toString());
                progressDialogUtil.dismissDialog();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Type categoryType = new TypeToken<List<CategoryModel>>() {
                }.getType();
                categoryModelArrayList = gson.fromJson(json.toString(), categoryType);

                break;
            case NetworkHelper.REQ_CODE_GET_UNIT_LIST:
                Log.e(TAG, json.toString());
                GsonBuilder gsonBuilder1 = new GsonBuilder();
                Gson gson1 = gsonBuilder1.create();
                Type unitType = new TypeToken<List<UnitModel>>() {
                }.getType();
                ArrayList<UnitModel> unitModelArrayList = gson1.fromJson(json.toString(), unitType);
                if (unitModelArrayList != null && !unitModelArrayList.isEmpty()) {
                    UnitSpinnerAdapter unitSpinnerAdapter = new UnitSpinnerAdapter(this, R.layout.unit_spinner, R.id.tv_title, unitModelArrayList);
                    spUnit.setAdapter(unitSpinnerAdapter);
                    if (!TextUtils.isEmpty(unitId)) {
                        spUnit.setSelection(Integer.parseInt(unitId));
                    }
                    spUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            UnitModel model = unitSpinnerAdapter.getItem(position);
                            unitId = model.getId();
                            Log.e(TAG, "unit_id............" + unitId);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                break;
        }
    }


    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_ADD_CATEGROY:
                Log.e(TAG, json.toString());
                progressDialogUtil.dismissDialog();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                CategoryModel categoryModel = gson.fromJson(String.valueOf(json), CategoryModel.class);
                if (categoryModel != null) {
                    categoryModelArrayList.add(categoryModel);
                }
                selectCategoryDialog(categoryModelArrayList, this);
                BaseUtility.toastMsg(this, "Add Category Successfully");
                break;
        }
    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }

    private void uploadProductApi(String type, List<File> multiPartDataArrayList) {
        if (type.equals("Special For Today")) {
            progressDialogUtil.showDialog();
            Log.e("token--------->", "Token " + new SessionManager(this).getToken());
            AndroidNetworking.upload(Urls.PRODUCT_UPLOAD_URL)
                    .addHeaders("Authorization", "Token " + new SessionManager(this).getToken())
                    .addHeaders("Content-Type", "multipart/form-data")
                    .addMultipartFileList(Constant.IMAGES, multiPartDataArrayList)
                    .addMultipartParameter(Constant.TITLE, title)
                    .addMultipartParameter(Constant.SHORT_DESCRIPATION, short_descri)
                    .addMultipartParameter(Constant.LONG_DESCRIPATION, long_descri)
                    .addMultipartParameter(Constant.PRICE, price)
                    .addMultipartParameter(Constant.TYPE, "SPL")
                    .addMultipartParameter(Constant.UNIT, unitId)
                    .addMultipartParameter(Constant.QUANTITY, quantity)
                    .addMultipartParameter(Constant.STOCK, stock_amt)
                    .addMultipartParameter(Constant.CATEGORIES, category_id)
                    .addMultipartParameter(Constant.PROMOTION, String.valueOf(isPromotionPrice))
                    .addMultipartParameter(Constant.PROMOTION_PRICE, promo_p)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            progressDialogUtil.dismissDialog();
                            Log.e("response upload = ", jsonObject.toString());
                            BaseUtility.toastMsg(UploadProductActivity.this, "Upload Product Successfully");
                            onBackPressed();
                            BaseUtility.deleteRecursive(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "LovFreshVendor"));
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e(TAG, "error=  " + anError.getMessage() + "..." + anError.getErrorBody());
                            progressDialogUtil.dismissDialog();
                            if (anError.getErrorCode() != 0) {
                                Log.e(TAG, "onError errorCode : " + anError.getErrorCode());
                                if (anError.getErrorCode() == 400) {
                                    String json = anError.getErrorBody();
                                    try {
                                        JSONObject object = new JSONObject(json);
                                        if (object.has(Constant.PRICE)) {
                                            JSONArray jsonArray = object.getJSONArray(Constant.PRICE);
                                            String price_error = jsonArray.getString(0);
                                            BaseUtility.toastMsg(UploadProductActivity.this, price_error);

                                            //  Toast.makeText(UploadProductActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }


                            }
                        }
                    });

        } else if (type.equals("Set Schedule")) {
            progressDialogUtil.showDialog();
            Log.e("token--------->", "Token " + new SessionManager(this).getToken());
            AndroidNetworking.upload(Urls.PRODUCT_UPLOAD_URL)
                    .addHeaders("Authorization", "Token " + new SessionManager(this).getToken())
                    .addHeaders("Content-Type", "multipart/form-data")
                    .addMultipartFileList(Constant.IMAGES, multiPartDataArrayList)
                    .addMultipartParameter(Constant.TITLE, title)
                    .addMultipartParameter(Constant.SHORT_DESCRIPATION, short_descri)
                    .addMultipartParameter(Constant.LONG_DESCRIPATION, long_descri)
                    .addMultipartParameter(Constant.PRICE, price)
                    .addMultipartParameter(Constant.TYPE, "SCH")
                    .addMultipartParameter(Constant.START_DATE, startDate)
                    .addMultipartParameter(Constant.END_DATE, endDate)
                    .addMultipartParameter(Constant.UNIT, unitId)
                    .addMultipartParameter(Constant.QUANTITY, quantity)
                    .addMultipartParameter(Constant.STOCK, stock_amt)
                    .addMultipartParameter(Constant.CATEGORIES, category_id)
                    .addMultipartParameter(Constant.PROMOTION, String.valueOf(isPromotionPrice))
                    .addMultipartParameter(Constant.PROMOTION_PRICE, promo_p)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            progressDialogUtil.dismissDialog();
                            Log.e("response upload = ", jsonObject.toString());
                            BaseUtility.toastMsg(UploadProductActivity.this, "Upload Product Successfully");
                            onBackPressed();
                            BaseUtility.deleteRecursive(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "LovFreshVendor"));

                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e(TAG, "error=  " + anError.getMessage() + "..." + anError.getErrorBody());
                            progressDialogUtil.dismissDialog();
                            if (anError.getErrorCode() != 0) {
                                Log.e(TAG, "onError errorCode : " + anError.getErrorCode());
                                if (anError.getErrorCode() == 400) {
                                    String json = anError.getErrorBody();
                                    try {
                                        JSONObject object = new JSONObject(json);
                                        if (object.has(Constant.PRICE)) {
                                            JSONArray jsonArray = object.getJSONArray(Constant.PRICE);
                                            String price_error = jsonArray.getString(0);
                                            BaseUtility.toastMsg(UploadProductActivity.this, price_error);

                                            //  Toast.makeText(UploadProductActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            //  Toast.makeText(UploadProductActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                        }
                    });

        } else if (type.equals("Shop online")) {
            progressDialogUtil.showDialog();
            Log.e("token--------->", "Token " + new SessionManager(this).getToken());
            AndroidNetworking.upload(Urls.PRODUCT_UPLOAD_URL)
                    .addHeaders("Authorization", "Token " + new SessionManager(this).getToken())
                    .addHeaders("Content-Type", "multipart/form-data")
                    .addMultipartFileList(Constant.IMAGES, multiPartDataArrayList)
                    .addMultipartParameter(Constant.TITLE, title)
                    .addMultipartParameter(Constant.SHORT_DESCRIPATION, short_descri)
                    .addMultipartParameter(Constant.LONG_DESCRIPATION, long_descri)
                    .addMultipartParameter(Constant.PRICE, price)
                    .addMultipartParameter(Constant.TYPE, "SHOPONLINE")
                    .addMultipartParameter(Constant.UNIT, unitId)
                    .addMultipartParameter(Constant.QUANTITY, quantity)
                    .addMultipartParameter(Constant.STOCK, stock_amt)
                    .addMultipartParameter(Constant.CATEGORIES, category_id)
                    .addMultipartParameter(Constant.PROMOTION, String.valueOf(isPromotionPrice))
                    .addMultipartParameter(Constant.PROMOTION_PRICE, promo_p)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            progressDialogUtil.dismissDialog();
                            Log.e("response upload = ", jsonObject.toString());
                            BaseUtility.toastMsg(UploadProductActivity.this, "Upload Product Successfully");
                            onBackPressed();
                            BaseUtility.deleteRecursive(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "LovFreshVendor"));
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e(TAG, "error=  " + anError.getMessage() + "..." + anError.getErrorBody());
                            progressDialogUtil.dismissDialog();
                            if (anError.getErrorCode() != 0) {
                                Log.e(TAG, "onError errorCode : " + anError.getErrorCode());
                                if (anError.getErrorCode() == 400) {
                                    String json = anError.getErrorBody();
                                    try {
                                        JSONObject object = new JSONObject(json);
                                        if (object.has(Constant.PRICE)) {
                                            JSONArray jsonArray = object.getJSONArray(Constant.PRICE);
                                            String price_error = jsonArray.getString(0);
                                            BaseUtility.toastMsg(UploadProductActivity.this, price_error);

                                            //  Toast.makeText(UploadProductActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    });
        }
    }

    public void selectCategoryDialog(ArrayList<CategoryModel> list, ItemSelectInterface selectInterface) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogview = inflater.inflate(R.layout.dialog_add_category, null);
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        dialogbuilder.setView(dialogview);
        categoryAlertDialog = dialogbuilder.create();
        Objects.requireNonNull(categoryAlertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        RecyclerView recyclerView = dialogview.findViewById(R.id.rv_category);
        TextView tvCancel = dialogview.findViewById(R.id.tv_cancel);
        TextView tvOkey = dialogview.findViewById(R.id.tv_okey);
        TextView tvAdd = dialogview.findViewById(R.id.tv_add);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoryFilterAdapter(this);
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryAlertDialog.dismiss();
            }
        });
        tvOkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = getCategoryId();
                if (TextUtils.isEmpty(category_id)) {
                    BaseUtility.toastMsg(getApplicationContext(), getString(R.string.select_state));
                } else {
                    selectInterface.itemSelect(category_id, name);
                    categoryAlertDialog.dismiss();
                }
            }
        });
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddCategory(selectInterface, list);
                //  alertDialog.dismiss();
            }
        });

        categoryAlertDialog.show();
    }

    private String getCategoryId() {
        category_id = "";
        String name = "";
        int categ_id = 0;
        String selectedCategoryId = "";
        String SelectedName = "";
        if (categoryModelArrayList != null && !categoryModelArrayList.isEmpty()) {
            list = new ArrayList<>();
            for (CategoryModel model : categoryModelArrayList) {
                if (model.isSelected()) {
                    selectedCategoryId = String.format("%s,%s", selectedCategoryId, model.getId());
                    SelectedName = String.format("%s,%s", SelectedName, model.getName());
                    category_id = selectedCategoryId.replaceFirst(",", "");
                    name = SelectedName.replaceFirst(",", "");
                    try {
                        categ_id = Integer.parseInt(category_id);
                        list.add(categ_id);
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return name;
    }

    @Override
    public void itemSelect(String id, String title) {
        if (!TextUtils.isEmpty(id)) {
            category_id = id;
            categoryNm = title;
            tvCategroy.setText(categoryNm);
        } else {
            addCategoryApi(title);
        }
    }

    public void openDatePicker(int checkDateTime) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        //launch datepicker modal
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Log.e("", "DATE SELECTED " + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        if (checkDateTime == 1) {
                            String startDate_ = BaseUtility.convertFormatDate(year, monthOfYear, dayOfMonth);
                            startDate = BaseUtility.convertStartAndEndFormat(startDate_);
                            edStartDate.setText(startDate_);
                            Log.e("startdate", startDate);
                        } else {
                            String endDate_ = BaseUtility.convertFormatDate(year, monthOfYear, dayOfMonth);
                            endDate = BaseUtility.convertStartAndEndFormat(endDate_);
                            edEndDate.setText(endDate_);
                            Log.e(" endDate", endDate);
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    public void dialogAddCategory(ItemSelectInterface itemSelectInterface, ArrayList<CategoryModel> list) {
        // custom dialog
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogview = inflater.inflate(R.layout.dialog_add_category_view, null);
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        dialogbuilder.setView(dialogview);
        final AlertDialog alertDialog = dialogbuilder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button btnSubmit = dialogview.findViewById(R.id.btn_submit);
        Button btnCancel = dialogview.findViewById(R.id.btn_cancel);
        AppCompatEditText edCateNm = dialogview.findViewById(R.id.ed_cate_nm);
        AppCompatEditText edCateSlug = dialogview.findViewById(R.id.ed_cate_slug);
        btnCancel.setOnClickListener(v -> alertDialog.dismiss());
        btnSubmit.setOnClickListener(v -> {
            String cate_nm = edCateNm.getText().toString();
            //String cate_slug = edCateSlug.getText().toString();
            if (TextUtils.isEmpty(cate_nm)) {
                BaseUtility.toastMsg(this, getString(R.string.error_field_required));
            } else if (isCategoryExist(list, cate_nm)) {
                BaseUtility.toastMsg(this, getString(R.string.category_already_exist));
            } else {
                itemSelectInterface.itemSelect("", cate_nm);
                alertDialog.dismiss();
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private boolean isCategoryExist(ArrayList<CategoryModel> list, String title) {
        for (CategoryModel m : list) {
            if (m.getName().equalsIgnoreCase(title)) {
                return true;
            }
        }
        return false;
    }

}