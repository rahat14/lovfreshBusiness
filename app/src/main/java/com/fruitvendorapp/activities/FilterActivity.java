package com.fruitvendorapp.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.adapters.CategoryFilterAdapter;
import com.fruitvendorapp.adapters.ProductListAdapter;
import com.fruitvendorapp.interfaces.ItemSelectInterface;
import com.fruitvendorapp.model.CategoryModel;
import com.fruitvendorapp.model.ProductModel;
import com.fruitvendorapp.server_networking.JsonArrayResponseListener;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.ProgressDialogUtil;
import com.fruitvendorapp.utilities.SessionManager;
import com.fruitvendorapp.utilities.Urls;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener, JsonArrayResponseListener, ItemSelectInterface {
    private static final String TAG = FilterActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.sb_price)
    SeekBar sbPrice;
    @BindView(R.id.tv_prices)
    TextView tvPrice;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.rv_product_list)
    RecyclerView rvProductList;
    @BindView(R.id.ll_no_record)
    LinearLayout llNoRecordFound;
    @BindView(R.id.sp_category)
    Spinner spCategory;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    private ProgressDialogUtil progressDialogUtil;
    private String max_price = "";
    private String min_price = "0";
    private String type = "";
    private String categoryNm = "All Category";
    private String category_id = "";
    private ArrayList<ProductModel> productModelArrayList;
    private ProductListAdapter productListAdapter;
    private String vendor_id = "";
    private ArrayList<CategoryModel> categoryModelArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing FilterActivity");
        init();
    }

    private void init() {
        progressDialogUtil = new ProgressDialogUtil(this);
        tvToolbarTitle.setText("Product Filter");
        tvCategory.setText(categoryNm);
        ivBack.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        tvCategory.setOnClickListener(this);
        setProductAdapter();
        getCategoryApi();
        getProductFilterList("Clear", "", "", "");
        sbPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e(TAG, "price value--------" + String.valueOf(progress));
                max_price = String.valueOf(progress);
                tvPrice.setText("$" + String.valueOf(progress));
                getProductFilterList("", min_price, max_price, category_id);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_clear:
                for (CategoryModel categoryModel : categoryModelArrayList) {
                    categoryModel.setSelected(false);
                    category_id = "";
                }
                sbPrice.setProgress(0);
                tvPrice.setText("$0");
                tvCategory.setText("All Category");
                getProductFilterList("Clear", "", "", "");
                break;
            case R.id.tv_category:
                if (categoryModelArrayList != null && !categoryModelArrayList.isEmpty()) {
                    selectCategoryDialog(categoryModelArrayList, this);
                }
                break;

        }
    }

    private void setProductAdapter() {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setOrientation(RecyclerView.VERTICAL);
        rvProductList.setLayoutManager(manager);
        productListAdapter = new ProductListAdapter(this);
        rvProductList.setAdapter(productListAdapter);
    }


    //implement product Api  for get} All product and Category
    private void getCategoryApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            //  progressDialogUtil.showDialog();
            RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_CATEGORY, this, Urls.CATEGORY_LIST_URL, this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    public void getProductFilterList(String title, String min_price, String max_price, String category_id) {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            if (title.equals("Clear")) {
                Log.e(TAG, "Url" + Urls.PRODUCT_FILTER_URL+"vendor=" + new SessionManager(this).getVendorId()+"&type=SPL");
                RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_FILTER_PRODUCT, this, Urls.PRODUCT_FILTER_URL + "vendor=" + new SessionManager(this).getVendorId()+"&type=SPL", this);
            } else {
                Log.e(TAG, "Url" + Urls.PRODUCT_FILTER_URL + "vendor=" + vendor_id + "&min_price=" + min_price + "&max_price=" + max_price + "&categories=" + category_id);
                RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_FILTER_PRODUCT, this, Urls.PRODUCT_FILTER_URL + "vendor=" + new SessionManager(this).getVendorId()+"&type=SPL"+ "&min_price=" + min_price + "&max_price=" + max_price + "&categories=" + category_id, this);
            }
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }


    @Override
    public void onSuccess(int requestCode, JSONArray json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_FILTER_PRODUCT:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Type productType = new TypeToken<List<ProductModel>>() {
                }.getType();
                productModelArrayList = gson.fromJson(json.toString(), productType);
                if (productModelArrayList != null && !productModelArrayList.isEmpty()) {
                    productListAdapter.setData(productModelArrayList,"");
                    productListAdapter.notifyDataSetChanged();
                    rvProductList.setVisibility(View.VISIBLE);
                    llNoRecordFound.setVisibility(View.GONE);
                } else {
                    rvProductList.setVisibility(View.GONE);
                    llNoRecordFound.setVisibility(View.VISIBLE);
                }
                break;

            case NetworkHelper.REQ_CODE_GET_CATEGORY:
                Log.e(TAG, json.toString());
                GsonBuilder gsonBuilder1 = new GsonBuilder();
                Gson gson1 = gsonBuilder1.create();
                Type categoryType = new TypeToken<List<CategoryModel>>() {
                }.getType();
                categoryModelArrayList = gson1.fromJson(json.toString(), categoryType);
                break;

        }

    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }

    private String getCategoryId() {
        category_id = "";
        String name = "";
        int categ_id = 0;
        String selectedCategoryId = "";
        String SelectedName = "";
        if (categoryModelArrayList != null && !categoryModelArrayList.isEmpty()) {
            for (CategoryModel model : categoryModelArrayList) {
                if (model.isSelected()) {
                    selectedCategoryId = String.format("%s,%s", selectedCategoryId, model.getId());
                    SelectedName = String.format("%s,%s", SelectedName, model.getName());
                    category_id = selectedCategoryId.replaceFirst(",", "");
                    name = SelectedName.replaceFirst(",", "");
                } } }
        return name;
    }

    public void selectCategoryDialog(ArrayList<CategoryModel> list, ItemSelectInterface selectInterface) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogview = inflater.inflate(R.layout.dialog_category, null);
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        dialogbuilder.setView(dialogview);
        final AlertDialog alertDialog = dialogbuilder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        RecyclerView recyclerView = dialogview.findViewById(R.id.rv_category);
        TextView tvCancel = dialogview.findViewById(R.id.tv_cancel);
        TextView tvOkey = dialogview.findViewById(R.id.tv_okey);
        CategoryFilterAdapter adapter = new CategoryFilterAdapter(this);
        adapter.setData(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
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
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }

    @Override
    public void itemSelect(String id, String title) {
        category_id = id;
        categoryNm = title;
        tvCategory.setText(categoryNm);
        getProductFilterList("", "", "", category_id);
    }

}