package com.fruitvendorapp.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import androidx.annotation.Nullable;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fruitvendorapp.R;
import com.fruitvendorapp.adapters.CategoryAdapter;
import com.fruitvendorapp.adapters.UnitSpinnerAdapter;
import com.fruitvendorapp.interfaces.ApiInterface;
import com.fruitvendorapp.interfaces.ItemDeleteInterface;
import com.fruitvendorapp.interfaces.ItemSelectInterface;
import com.fruitvendorapp.model.CategoryModel;
import com.fruitvendorapp.model.ImageModel;
import com.fruitvendorapp.model.ProductAttributeModel;
import com.fruitvendorapp.model.ProductImageModel;
import com.fruitvendorapp.model.ProductModel;
import com.fruitvendorapp.model.UnitModel;
import com.fruitvendorapp.server_networking.JsonArrayResponseListener;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.server_networking.ResponseListener;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.DialogUtility;
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
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import io.sentry.Sentry;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EditProductActivity extends AppCompatActivity implements View.OnClickListener, JsonArrayResponseListener, ResponseListener, ItemSelectInterface, ItemDeleteInterface {
    private static final String TAG = EditProductActivity.class.getSimpleName();
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
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_hide)
    Button btnHide;
    @BindView(R.id.ed_quantity)
    AppCompatEditText edQuantity;
    @BindView(R.id.cb_promot_p)
    CheckBox cbPromotionPrice;
    @BindView(R.id.rl_category)
    RelativeLayout rlCatgeory;
    @BindView(R.id.ed_pro_price)
    EditText edPromoPrice;
    private String selectedImagePath;
    private String indexType = "";
    private ArrayList<UnitModel> unitModelArrayList;
    private UnitSpinnerAdapter unitSpinnerAdapter;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<Integer> categoryList;
    private ArrayList<ImageModel> imageslist;
    private ArrayList<ImageModel> imagesIds;
    private ArrayList<CategoryModel> categoryModelArrayList;
    private ArrayList<Integer> list;
    private ProgressDialogUtil progressDialogUtil;
    private String categoryNm = "All Category";
    private String category_id = "", unitId = "";
    private String startDate, endDate, startDate_, endDate_;
    private int checkDateTime = 0;
    private int categ_id = 0;
    private String type = "", flag = "", productId = "";
    public static final String MIME_TYPE_IMAGE = "image/*";
    private Boolean isHide = false;
    private CategoryAdapter adapter;
    private boolean isPromotionPrice = false;
    private String title = "", long_descri = "", short_descri = "", price = "", stock_amt = "", quantity = "", promp_p = "", unitIds = "";
    private String imageId = "";
    private AlertDialog categoryAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing EditProductActivity");
        init();
    }

    private void init() {
        progressDialogUtil = new ProgressDialogUtil(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (getIntent().hasExtra(Constant.PRODUCT_ID)) {
            productId = getIntent().getStringExtra(Constant.PRODUCT_ID);
            isHide = getIntent().getExtras().getBoolean(Constant.IS_HIDE);
            if (isHide.equals(true)) {
                btnHide.setText("UnHide");
            } else {
                btnHide.setText("Hide");
            }
        }
        imageslist = new ArrayList<>();
        imageslist.add(new ImageModel(0));
        imageslist.add(new ImageModel(1));
        imageslist.add(new ImageModel(2));
        imageslist.add(new ImageModel(3));
        ivBack.setOnClickListener(this);
        ivProduct1.setOnClickListener(this);
        ivProduct2.setOnClickListener(this);
        ivProduct3.setOnClickListener(this);
        ivProduct4.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
        rlCatgeory.setOnClickListener(this);
        edStartDate.setOnClickListener(this);
        edEndDate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnHide.setOnClickListener(this);
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
        getCategoryListApi();
        getTypeSpinner();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                DialogUtility.updateConfirBackAlert(this);
                //   onBackPressed();
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
                break;
            case R.id.ed_end_date:
                checkDateTime = 2;
                openDatePicker(checkDateTime);
                break;
            case R.id.btn_delete:
                DialogUtility.deleteProductAlert(this, this);
                break;
            case R.id.btn_hide:
                hideProductApi();
                break;
        }
    }


    private void uploadProductValidation() {
        edTitle.setError(null);
        edDescripation.setError(null);
        //  edOtherInfo.setError(null);
        edPrice.setError(null);
        edStockAmt.setError(null);
        edStartDate.setError(null);
        edEndDate.setError(null);
        edQuantity.setError(null);
        title = edTitle.getText().toString();
        short_descri = edDescripation.getText().toString();
        //short_descri = edOtherInfo.getText().toString();
        price = edPrice.getText().toString();
        stock_amt = edStockAmt.getText().toString();
        quantity = edQuantity.getText().toString();
        promp_p = edPromoPrice.getText().toString();
        if (TextUtils.isEmpty(title)) {
            edTitle.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(price)) {
            edPrice.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(stock_amt)) {
            edStockAmt.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(quantity)) {
            edQuantity.setError(getString(R.string.error_field_required));
        } else if (isPromotionPrice && TextUtils.isEmpty(promp_p)) {
            edPromoPrice.setError(getString(R.string.error_field_required));
        } else if (isPromotionPrice && Double.parseDouble(promp_p) >= Double.parseDouble(price)) {
            BaseUtility.toastMsg(this, "Promotional Price should be less than original price.");
        } else {
            Log.e("Type----->", type);
            if (!TextUtils.isEmpty(category_id)) {
                putProductUpdateApi(startDate, endDate, title, short_descri, long_descri, price, type, unitId, stock_amt, category_id);
            } else {
                BaseUtility.toastMsg(this, "Please Select Category");
            }
        }
    }

    //implement Address Api  for get All Address
    private void deleteProductApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.deleteRequestWithToken(this, NetworkHelper.REQ_CODE_DELETE_PRODUCT, Urls.PRODUCT_UPLOAD_URL + productId + "/", this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }

    }

    private void hideProductApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            if (isHide.equals(true)) {
                RequestHelper.PostTokenRequest(NetworkHelper.REQ_CODE_HIDE_PRODUCT, this, Urls.PRODUCT_SINGLE_HIDE_URL, new NetworkHelper(this).hideJson(productId, "unhide"), this);
            } else {
                RequestHelper.PostTokenRequest(NetworkHelper.REQ_CODE_HIDE_PRODUCT, this, Urls.PRODUCT_SINGLE_HIDE_URL, new NetworkHelper(this).hideJson(productId, "hide"), this);
            }
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    private void putProductUpdateApi(String startDate, String endDate, String title, String short_descri, String long_descri, String price, String type, String unitId, String stock_amt, String category_id) {
        if (ConnectionUtil.isInternetOn(this)) {
            if (type.equals("Set Schedule")) {
                if (TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate)) {
                    BaseUtility.toastMsg(this, "Please add schedule date");
                } else {
                    if (TextUtils.isEmpty(promp_p)) {
                        promp_p = null;
                    }
                    progressDialogUtil.showDialog();
                    Log.e(TAG, Urls.PRODUCT_UPLOAD_URL + productId + "/");
                    RequestHelper.putRequestWithToken(this, NetworkHelper.REQ_CODE_UPDATE_PRODUCT, Urls.PRODUCT_UPLOAD_URL + productId + "/", new NetworkHelper(this).updateProductSchedulelJson(startDate, endDate, title, short_descri, long_descri, price, unitId, stock_amt, category_id, quantity, String.valueOf(isPromotionPrice), promp_p), this);
                }

            } else if (type.equals("Special For Today")) {
                if (TextUtils.isEmpty(promp_p)) {
                    promp_p = null;
                }
                progressDialogUtil.showDialog();
                Log.e(TAG, Urls.PRODUCT_UPLOAD_URL + productId + "/");
                RequestHelper.putRequestWithToken(this, NetworkHelper.REQ_CODE_UPDATE_PRODUCT, Urls.PRODUCT_UPLOAD_URL + productId + "/", new NetworkHelper(this).updateProductSpecialJson(title, short_descri, long_descri, price, unitId, stock_amt, category_id, "SPL", quantity, String.valueOf(isPromotionPrice), promp_p), this);
            } else {
                if (TextUtils.isEmpty(promp_p)) {
                    promp_p = null;
                }
                progressDialogUtil.showDialog();
                Log.e(TAG, Urls.PRODUCT_UPLOAD_URL + productId + "/");
                RequestHelper.putRequestWithToken(this, NetworkHelper.REQ_CODE_UPDATE_PRODUCT, Urls.PRODUCT_UPLOAD_URL + productId + "/", new NetworkHelper(this).updateProductSpecialJson(title, short_descri, long_descri, price, unitId, stock_amt, category_id, "SHOPONLINE", quantity, String.valueOf(isPromotionPrice), promp_p), this);

            }
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }


    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_PRODUCT_DETAIL:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                ProductModel productModel = gson.fromJson(String.valueOf(json), ProductModel.class);
                if (productModel != null) {
                    tvToolbarTitle.setText(productModel.getTitle());
                    //title=productModel.getTitle();
                    long_descri = productModel.getLongDescription();
                    short_descri = productModel.getShortDescription();
                    edTitle.setText(productModel.getTitle());
                    edDescripation.setText(productModel.getShortDescription());
                    edOtherInfo.setText(productModel.getShortDescription());
                    edPrice.setText(productModel.getPrice());
                    edQuantity.setText(productModel.getQuantity());
                    isPromotionPrice = productModel.getPromotional();
                    if (isPromotionPrice) {
                        cbPromotionPrice.setChecked(true);
                        edPromoPrice.setText(productModel.getPromotional_price());
                    } else {
                        cbPromotionPrice.setChecked(false);
                    }
                    categoryList = productModel.getCategories();
                    getSelectedCategoryId(categoryList);

                    ArrayList<ProductAttributeModel> unitList = productModel.getProductAttributes();
                    if (unitList != null && !unitList.isEmpty()) {
                        String stock = unitList.get(0).getStock();
                        unitIds = unitList.get(0).getUom().getId();
                        Log.e(TAG, "unitIds---------->" + unitIds);
                        edStockAmt.setText(stock);
                        getUnitListApi();
                    } else {

                    }
                    ArrayList<ImageModel> imageslist = productModel.getProduct_images();
                    imagesIds = productModel.getProduct_images();
                    imagesIds.add(new ImageModel(0));
                    imagesIds.add(new ImageModel(1));
                    imagesIds.add(new ImageModel(2));
                    imagesIds.add(new ImageModel(3));
                    if (imageslist != null && !imageslist.isEmpty()) {
                        if (imageslist.size() > 0) {
                            if (!TextUtils.isEmpty(imageslist.get(0).getImage())) {
                                Glide.with(this).load(imageslist.get(0).getImage()).apply(new RequestOptions().placeholder(R.drawable.ic_photo_icon).error(R.drawable.ic_photo_icon)).into(ivProduct1);
                            }

                        }
                        if (imageslist.size() > 1) {
                            Log.e(TAG, String.valueOf(imageslist.size()));
                            if (!TextUtils.isEmpty(imageslist.get(0).getImage())) {
                                Glide.with(this).load(imageslist.get(0).getImage()).apply(new RequestOptions().placeholder(R.drawable.ic_photo_icon).error(R.drawable.ic_photo_icon)).into(ivProduct1);
                            }
                            if (!TextUtils.isEmpty(imageslist.get(1).getImage())) {
                                Glide.with(this).load(imageslist.get(1).getImage()).apply(new RequestOptions().placeholder(R.drawable.ic_photo_icon).error(R.drawable.ic_photo_icon)).into(ivProduct2);
                            }
                        }
                        if (imageslist.size() > 2) {
                            Log.e(TAG, String.valueOf(imageslist.size()));
                            if (!TextUtils.isEmpty(imageslist.get(0).getImage())) {
                                Glide.with(this).load(imageslist.get(0).getImage()).apply(new RequestOptions().placeholder(R.drawable.ic_photo_icon).error(R.drawable.ic_photo_icon)).into(ivProduct1);
                            }
                            if (!TextUtils.isEmpty(imageslist.get(1).getImage())) {
                                Glide.with(this).load(imageslist.get(1).getImage()).apply(new RequestOptions().placeholder(R.drawable.ic_photo_icon).error(R.drawable.ic_photo_icon)).into(ivProduct2);
                            }
                            if (!TextUtils.isEmpty(imageslist.get(2).getImage())) {
                                Glide.with(this).load(imageslist.get(2).getImage()).apply(new RequestOptions().placeholder(R.drawable.ic_photo_icon).error(R.drawable.ic_photo_icon)).into(ivProduct3);
                            }
                        }
                        if (imageslist.size() > 3) {
                            Log.e(TAG, String.valueOf(imageslist.size()));
                            if (!TextUtils.isEmpty(imageslist.get(0).getImage())) {
                                Glide.with(this).load(imageslist.get(0).getImage()).apply(new RequestOptions().placeholder(R.drawable.ic_photo_icon).error(R.drawable.ic_photo_icon)).into(ivProduct1);
                            }
                            if (!TextUtils.isEmpty(imageslist.get(1).getImage())) {
                                Glide.with(this).load(imageslist.get(1).getImage()).apply(new RequestOptions().placeholder(R.drawable.ic_photo_icon).error(R.drawable.ic_photo_icon)).into(ivProduct2);
                            }
                            if (!TextUtils.isEmpty(imageslist.get(2).getImage())) {
                                Glide.with(this).load(imageslist.get(2).getImage()).apply(new RequestOptions().placeholder(R.drawable.ic_photo_icon).error(R.drawable.ic_photo_icon)).into(ivProduct3);
                            }
                            if (!TextUtils.isEmpty(imageslist.get(3).getImage()) && imageslist.get(3).getImage() != null) {
                                Glide.with(this).load(imageslist.get(3).getImage()).apply(new RequestOptions().placeholder(R.drawable.ic_photo_icon).error(R.drawable.ic_photo_icon)).into(ivProduct4);
                            }
                        }

                    } else {
                        Glide.with(this).load(R.drawable.ic_photo_icon).apply(new RequestOptions().placeholder(R.drawable.ic_photo_icon).error(R.drawable.ic_photo_icon)).into(ivProduct4);
                    }

                    String type1 = productModel.getType();
                    if (type1.equals("SCH")) {
                        type = "Set Schedule";
                        if (type != null) {
                            String start_date = productModel.getStartDateTime();
                            String end_date = productModel.getEndDateTime();
                            String split_s_Date = start_date.substring(0, 10);
                            String split_e_Date = end_date.substring(0, 10);
                            Log.e(TAG, "split_s_Date" + split_s_Date + "-----" + split_e_Date);
                            startDate = BaseUtility.convertGetStartAndEndFormat(split_s_Date);
                            endDate = BaseUtility.convertGetStartAndEndFormat(split_e_Date);
                            edStartDate.setText(split_s_Date);
                            edEndDate.setText(split_e_Date);
                            int spinnerPosition = arrayAdapter.getPosition(type);
                            spSpecial.setSelection(spinnerPosition);
                        }
                    } else if (type1.equals("SPL")) {
                        type = "Special For Today";
                        if (type != null) {
                            int spinnerPosition = arrayAdapter.getPosition(type);
                            spSpecial.setSelection(spinnerPosition);
                        }
                    } else {
                        type = "Shop online";
                        if (type != null) {
                            int spinnerPosition = arrayAdapter.getPosition(type);
                            spSpecial.setSelection(spinnerPosition);
                        }
                    }

                } else {

                }
                break;
            case NetworkHelper.REQ_CODE_DELETE_PRODUCT:
            case NetworkHelper.REQ_CODE_HIDE_PRODUCT:
                Log.e(TAG, json.toString());
                progressDialogUtil.dismissDialog();
                String hide_msg = json.optString(Constant.MESSAGE);
                BaseUtility.toastMsg(this, hide_msg);
                BaseUtility.sendActivityIntent(this, DashboardActivity.class);
                break;
            case NetworkHelper.REQ_CODE_UPDATE_PRODUCT:
                Log.e(TAG, json.toString());
                progressDialogUtil.dismissDialog();
                BaseUtility.toastMsg(this, "Update Product Successfully");
                onBackPressed();
                BaseUtility.deleteRecursive(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "LovFreshVendor"));
                break;
            case NetworkHelper.REQ_CODE_ADD_CATEGROY:
                Log.e(TAG, json.toString());
                progressDialogUtil.dismissDialog();
                GsonBuilder gsonBuilder1 = new GsonBuilder();
                Gson gson1 = gsonBuilder1.create();
                CategoryModel categoryModel = gson1.fromJson(String.valueOf(json), CategoryModel.class);
                if (categoryModel != null) {
                    categoryModelArrayList.add(categoryModel);
                }
                if (categoryAlertDialog != null && categoryAlertDialog.isShowing()) {
                    categoryAlertDialog.dismiss();
                }
                selectCategoryDialog(categoryModelArrayList, this);
                BaseUtility.toastMsg(this, "Add Category Successfully");
                break;
        }
    }

    @Override
    public void onSuccess(int requestCode, JSONArray json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_CATEGROY_LIST:
                Log.e(TAG, json.toString());
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Type categoryType = new TypeToken<List<CategoryModel>>() {
                }.getType();
                categoryModelArrayList = gson.fromJson(json.toString(), categoryType);
                getProductDetailApi();
                break;
            case NetworkHelper.REQ_CODE_GET_UNIT_LIST:
                Log.e(TAG, json.toString());
                GsonBuilder gsonBuilder1 = new GsonBuilder();
                Gson gson1 = gsonBuilder1.create();
                Type unitType = new TypeToken<List<UnitModel>>() {
                }.getType();
                unitModelArrayList = gson1.fromJson(json.toString(), unitType);
                if (unitModelArrayList != null && !unitModelArrayList.isEmpty()) {
                    unitSpinnerAdapter = new UnitSpinnerAdapter(this, R.layout.unit_spinner, R.id.tv_title, unitModelArrayList);
                    spUnit.setAdapter(unitSpinnerAdapter);
                    for (int i = 0; i < unitSpinnerAdapter.getCount(); i++) {
                        String unitId = unitSpinnerAdapter.getItem(i).getId();
                        if (unitIds.equals(unitId)) {
                            int spinnerPosition = unitSpinnerAdapter.getPosition(unitModelArrayList.get(i));
                            spUnit.setSelection(spinnerPosition);
                        }

                    }

                    spUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            // UnitModel model = unitSpinnerAdapter.getItem(i);
                            unitId = unitSpinnerAdapter.getItem(i).getId();
                            // unitId = model.getId();

                            Log.e(TAG, "unit_id............" + unitId);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                break;
        }
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
                        BaseUtility.toastMsg(EditProductActivity.this, price_error);

                        //  Toast.makeText(UploadProductActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), ZXING_CAMERA_PERMISSION);

        } else {
            ImagePicker.Companion.with(this)
                    .crop(3f, 2f)
                    .compress(1024)//Final image size will be less than 1 MB(Optional)
                    .saveDir(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "LovFreshVendor"))
                    .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            Uri fileUri = data == null ? null : data.getData();
            //You can get File object from intent
            try {
                selectedImagePath = String.valueOf(new Compressor(this).compressToFile(FileUtil.from(this, fileUri)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e(TAG, selectedImagePath + "..." + String.valueOf(imageslist.size()));
            if (indexType.equals("0")) {
                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                ivProduct1.setImageBitmap(bitmap);
                imageslist.get(0).setImage(selectedImagePath);

                imageId = imagesIds.get(0).getId();
                Log.e(TAG, "image_id" + imageId);
                updateImagesApi(imageId, selectedImagePath);
            }
            if (indexType.equals("1")) {
                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                ivProduct2.setImageBitmap(bitmap);
                imageslist.get(1).setImage(selectedImagePath);
                imageId = imagesIds.get(1).getId();
                Log.e(TAG, "image_id" + imageslist.get(1).getId());
                updateImagesApi(imageId, selectedImagePath);
            }
            if (indexType.equals("2")) {
                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                ivProduct3.setImageBitmap(bitmap);
                imageslist.get(2).setImage(selectedImagePath);
                imageId = imagesIds.get(2).getId();
                Log.e(TAG, "image_id" + imageId);
                updateImagesApi(imageId, selectedImagePath);
            }
            if (indexType.equals("3")) {
                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                ivProduct4.setImageBitmap(bitmap);
                imageslist.get(3).setImage(selectedImagePath);
                imageId = imagesIds.get(3).getId();
                Log.e(TAG, "image_id" + imageId);
                updateImagesApi(imageId, selectedImagePath);
            }
            //You can also get File Path from intent

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Log.e(TAG, "data" + data.getData());
            //Toast.makeText(this,"data", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "taskCancelled");
            //Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }

    }

    // set Banner slider Images Adapter
    private void getProductDetailApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            Log.e(TAG, new SessionManager(this).getToken());
            progressDialogUtil.showDialog();
            Log.e(TAG, Urls.PRODUCT_DETAIL_URL + productId);
            RequestHelper.getRequestWithToken(NetworkHelper.REQ_CODE_GET_PRODUCT_DETAIL, this, Urls.PRODUCT_DETAIL_URL + productId, this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
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
            //  progressDialogUtil.showDialog();
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


    private void getTypeSpinner() {
        arrayAdapter = new ArrayAdapter<String>(EditProductActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.array_special_type));
        spSpecial.setAdapter(arrayAdapter);
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
        adapter = new CategoryAdapter(this);
        adapter.setData(list, categoryList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                dialogAddCategory(list);
                // alertDialog.dismiss();
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

    private void getSelectedCategoryId(ArrayList<Integer> categoryList) {
        Log.e(TAG, "string getSelectedCategoryId= ");
        String select_cate_id = "";
        if (categoryModelArrayList != null && !categoryModelArrayList.isEmpty()) {
            Log.e(TAG, "string getSelectedCategoryId= ");
            ArrayList<String> list = new ArrayList<>();
            for (CategoryModel model : categoryModelArrayList) {
                //category_id = model.getId();
                categoryNm = model.getName();
                for (int i = 0; i < categoryList.size(); i++) {
                    select_cate_id = String.valueOf(this.categoryList.get(i));
                    if (select_cate_id.equals(model.getId())) {
                        model.setSelected(true);
                        String cat_nm = model.getName();
                        list.add(cat_nm);
                        String s = TextUtils.join(",", list);
                        tvCategroy.setText(s);
                        category_id = model.getId();
                        categoryNm = model.getName();
                        Log.e(TAG, "string select_cate_id= " + select_cate_id + "SelectedName" + s);
                        break;
                        // adapter.setData(categoryModelArrayList, categoryList,this);
                        //adapter.notifyDataSetChanged();
                    }
                    //   adapter.setData(categoryModelArrayList,categoryList,this);
                    //  adapter.notifyDataSetChanged();
                }
            }

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

    @Override
    public void itemSelect(String id, String title) {
        category_id = id;
        categoryNm = title;
        tvCategroy.setText(categoryNm);

    }

    public void dialogAddCategory(ArrayList<CategoryModel> list) {
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
            //  boolean contains = list.contains(cate_nm);
            if (TextUtils.isEmpty(cate_nm)) {
                BaseUtility.toastMsg(this, getString(R.string.error_field_required));
            } else if (isCategoryExist(list, cate_nm)) {
                BaseUtility.toastMsg(this, getString(R.string.category_already_exist));
            } else {
                addCategoryApi(cate_nm);
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

    private void updateImagesApi(String id, String selectedImagePath) {
        progressDialogUtil.showDialog();
        File image = new File(selectedImagePath);
        if (!TextUtils.isEmpty(imageId)) {
            Log.e("token--------->", "Token " + new SessionManager(this).getToken());
            AndroidNetworking.upload(Urls.IMAGE_UPDATE_URL)
                    .addHeaders("Authorization", "Token " + new SessionManager(this).getToken())
                    .addHeaders("Content-Type", "multipart/form-data")
                    .addMultipartFile(Constant.IMAGE_, image)
                    .addMultipartParameter(Constant.IMAGE_ID, id)
                    .addMultipartParameter(Constant.Product_IDD, productId)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            progressDialogUtil.dismissDialog();
                            Log.e("response upload = ", jsonObject.toString());
                            BaseUtility.toastMsg(EditProductActivity.this, "Update Image Successfully");
                            //onBackPressed();
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

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }


                            }
                        }
                    });

        } else {
            Log.e("token--------->", "Token " + new SessionManager(this).getToken());
            AndroidNetworking.upload(Urls.IMAGE_UPDATE_URL)
                    .addHeaders("Authorization", "Token " + new SessionManager(this).getToken())
                    .addHeaders("Content-Type", "multipart/form-data")
                    .addMultipartFile(Constant.IMAGE_, image)
                    .addMultipartParameter(Constant.IMAGE_ID, "")
                    .addMultipartParameter(Constant.Product_IDD, productId)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            progressDialogUtil.dismissDialog();
                            Log.e("response upload = ", jsonObject.toString());
                            BaseUtility.toastMsg(EditProductActivity.this, "Update Image Successfully");
                            //onBackPressed();
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

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }


                            }
                        }
                    });

        }

    }
   /* private void setShopImages() {
        parts = new ArrayList<>();
        for (int i = 0; i < imageModelArrayList.size(); i++) {
            if (!TextUtils.isEmpty(imageModelArrayList.get(i))) {
                File image = new File(imageModelArrayList.get(i));
                Log.e(TAG, "image = " + imageModelArrayList.get(i));
                parts.add(MultipartBody.Part.createFormData("images", image.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), image)));
                Log.e("resp  ", parts.toString());

                //   RequestBody requestFile = RequestBody.create(MediaType.parse(MIME_TYPE_IMAGE), image);
                //  MultipartBody.Part imageRequest = MultipartBody.Part.createFormData("images[" + i + "]", image.getName(), requestFile);
                //   parts.add(imageRequest);
            }
        }
    }*/

    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            try {
                final String s = new String(buffer.readUtf8().getBytes(), "UTF-8");
                Log.e("dbhfghd = ", s);
            } catch (UnsupportedEncodingException e) {
                Log.e("utf8", "conversion", e);
            }
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    @Override
    public void itemDelete() {
        deleteProductApi();
    }

    private int getIndex(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
    }
}
/*private void putUpdateProductApi(String titles, String short_descri, String long_descri, String prices, String type, String unitIds, String stock_amt, String category_id) {
        progressDialogUtil.showDialog();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface selectInterface = retrofit.create(ApiInterface.class);
        //  setShopImages();
        List<Integer> catIds = new ArrayList<>();
        catIds.add(1);
        Call<JSONObject> call = selectInterface.putProfileData(new SessionManager(this).getToken(), productId, RequestBody.create(MediaType.parse("text/plain"), titles), RequestBody.create(MediaType.parse("text/plain"), short_descri), RequestBody.create(MediaType.parse("text/plain"), long_descri), RequestBody.create(MediaType.parse("text/plain"), prices), RequestBody.create(MediaType.parse("text/plain"), type), RequestBody.create(MediaType.parse("text/plain"), unitIds), RequestBody.create(MediaType.parse("text/plain"), stock_amt), catIds);
        bodyToString(call.request().body());
        Log.e("jffffffffffff", "djjjjjjjjjjjj");
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                progressDialogUtil.dismissDialog();
                try {
                    Log.e(TAG, response.body() + "");
                    if (response.code() == 200) {
                        Log.e(TAG, response.body() + "");
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Toast.makeText(EditProductActivity.this, "update data", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("error = ", e.getMessage() + "respossssssssss");
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.e(TAG, t.getMessage() + "errooooooooo");
                progressDialogUtil.dismissDialog();
            }
        });
    }*/


