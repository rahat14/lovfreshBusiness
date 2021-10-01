package com.fruitvendorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.adapters.ProductSliderAdapter;
import com.fruitvendorapp.model.ImageModel;
import com.fruitvendorapp.model.ProductAttributeModel;
import com.fruitvendorapp.model.ProductModel;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.server_networking.ResponseListener;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.ProgressDialogUtil;
import com.fruitvendorapp.utilities.SessionManager;
import com.fruitvendorapp.utilities.Urls;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener {
    private static final String TAG = ProductDetailActivity.class.getSimpleName();
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_product_title)
    TextView tvProductTitle;
    @BindView(R.id.tv_des_detail)
    TextView tvProductDescription;
    @BindView(R.id.tv_descripation)
    TextView tvDescripationTitle;

    @BindView(R.id.tv_product_amount)
    TextView tvProductAmt;
    @BindView(R.id.vp_image_slider)
    ViewPager vpImage;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.left_nav)
    ImageButton ivLeftArrow;
    @BindView(R.id.right_nav)
    ImageButton ivRightArrow;
    @BindView(R.id.ll_mainView)
    LinearLayout llMainView;
    @BindView(R.id.iv_holder_image)
    RoundedImageView riHolderImg;
    @BindView(R.id.btn_edit)
    Button btnEdit;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_hide)
    Button btnHide;
    @BindView(R.id.tv_stock_price)
    TextView tvStockPrice;
    @BindView(R.id.product_sale)
    TextView tvProductSale;
    @BindView(R.id.tv_promo_price)
    TextView tvPromoPrice;
    @BindView(R.id.tv_cal_amt)
    TextView tvPromoAmt;
    @BindView(R.id.tv_stand_price)
    TextView tvStandPrice;


    private String productId = "";
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ProgressDialogUtil progressDialogUtil;
    private int count = 0;
    private String quantity, unit_title = "", unit_id = "", unit_value = "";
    private boolean isItemExist = false;
    int defaultSelectedIndex = 0;
    private String existingVolume;
    private String unitAllValue = "";
    private String itemImage = "";
    private int totalVolume = 0;
    private ProductModel productModel;
    private Boolean isHide = false;
    String unit = "", unitvalue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing ProductDetailActivity");
        init();
    }

    private void init() {
        if (getIntent().hasExtra(Constant.PRODUCT_ID)) {
            productId = getIntent().getStringExtra(Constant.PRODUCT_ID);
            isHide = getIntent().getExtras().getBoolean(Constant.IS_HIDE);
            if (isHide.equals(true)) {
                btnHide.setText("UnHide");
            } else {
                btnHide.setText("Hide");
            }
        }
        progressDialogUtil = new ProgressDialogUtil(this);
        tvToolbarTitle.setText(R.string.product_detail);
        ivLeftArrow.setOnClickListener(this);
        ivRightArrow.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnHide.setOnClickListener(this);
        getProductDetailApi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProductDetailApi();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.left_nav:
                int tab = vpImage.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    vpImage.setCurrentItem(tab);
                } else if (tab == 0) {
                    vpImage.setCurrentItem(tab);
                }
                break;
            case R.id.right_nav:
                int tab1 = vpImage.getCurrentItem();
                tab1++;
                vpImage.setCurrentItem(tab1);
                break;
            case R.id.btn_edit:
                Intent intent = new Intent(this, EditProductActivity.class);
                intent.putExtra(Constant.PRODUCT_ID, productId);
                intent.putExtra(Constant.IS_HIDE, isHide);
                startActivity(intent);
                break;
            case R.id.btn_delete:
                deleteProductApi();
                break;
            case R.id.btn_hide:
                hideProductApi();
                break;

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


    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_PRODUCT_DETAIL:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                llMainView.setVisibility(View.VISIBLE);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                productModel = gson.fromJson(String.valueOf(json), ProductModel.class);
                if (productModel != null) {
                    tvProductTitle.setText(productModel.getTitle());
                    ArrayList<ProductAttributeModel> productAttributes = productModel.getProductAttributes();
                    if (productAttributes != null && !productAttributes.isEmpty() && productAttributes.size() > 0) {
                        unitvalue = productAttributes.get(0).getUom().getValue();
                        unit = productAttributes.get(0).getUom().getName();
                        //viewHolder.tvUnit.setText(unitvalue + "" + unit);
                    }
                    if (!TextUtils.isEmpty(productModel.getPromotional_price()) && productModel.getPromotional_price() != null) {
                        double price = Double.parseDouble(productModel.getPrice());
                        double promo_price = Double.parseDouble(productModel.getPromotional_price());
                        System.out.println("int  x = " + (float) price);
                        tvPromoPrice.setText("$" + (float) promo_price + " " + "per" + " " + unit);
                        tvPromoAmt.setText("" + (float) price);
                        tvStandPrice.setText("was $" + productModel.getPrice());
                        tvProductAmt.setVisibility(View.GONE);
                        tvStandPrice.setVisibility(View.VISIBLE);
                        tvPromoAmt.setVisibility(View.GONE);
                        tvPromoPrice.setVisibility(View.VISIBLE);

                    } else {
                        tvProductAmt.setVisibility(View.VISIBLE);
                        tvStandPrice.setVisibility(View.GONE);
                        tvPromoAmt.setVisibility(View.GONE);
                        tvPromoPrice.setVisibility(View.GONE);
                        tvProductAmt.setText("$ " + productModel.getPrice() + " " + "per" + " " + unit);
                    }

                    if (!TextUtils.isEmpty(productModel.getShortDescription())) {
                        tvProductDescription.setText(productModel.getShortDescription());
                        tvProductDescription.setVisibility(View.VISIBLE);
                        tvDescripationTitle.setVisibility(View.VISIBLE);
                    } else {
                        tvProductDescription.setVisibility(View.GONE);
                        tvDescripationTitle.setVisibility(View.GONE);
                    }

                    ArrayList<ProductAttributeModel> unitList = productModel.getProductAttributes();
                    if (unitList != null && !unitList.isEmpty()) {
                        String stock = unitList.get(0).getStock();
                        String unit = unitList.get(0).getUom().getName();
                        String product_sell = unitList.get(0).getTotalSellProduct();
                        tvStockPrice.setText(stock + " " + unit);
                        tvProductSale.setText(product_sell + " " + unit);
                    } else {

                    }
                    ArrayList<ImageModel> imageslist = productModel.getProduct_images();
                    if (imageslist != null && !imageslist.isEmpty() && imageslist.size() > 0) {
                        itemImage = imageslist.get(0).getImage();
                        Log.e(TAG, String.valueOf(imageslist.size()));
                        setBannerAdapter(imageslist);
                        vpImage.setVisibility(View.VISIBLE);
                        riHolderImg.setVisibility(View.GONE);
                        // ivLeftArrow.setVisibility(View.VISIBLE);
                        // ivRightArrow.setVisibility(View.VISIBLE);

                    } else {
                        vpImage.setVisibility(View.GONE);
                        riHolderImg.setVisibility(View.VISIBLE);
                        //  ivLeftArrow.setVisibility(View.GONE);
                        //   ivRightArrow.setVisibility(View.GONE);
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
                onBackPressed();
                break;

        }

    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }

    private void setBannerAdapter(ArrayList<ImageModel> imageslist) {
        String size = String.valueOf(imageslist.size());
        Log.e(TAG, "sixhdhdhhdh" + size);
        if (!size.equals("1")) {
            ivLeftArrow.setVisibility(View.VISIBLE);
            ivRightArrow.setVisibility(View.VISIBLE);
        } else {
            ivLeftArrow.setVisibility(View.GONE);
            ivRightArrow.setVisibility(View.GONE);

        }
        ProductSliderAdapter productSliderAdapter = new ProductSliderAdapter(this);
        productSliderAdapter.setData(imageslist);
        vpImage.setAdapter(productSliderAdapter);
        //setCurrentItem(1);
        vpImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPage = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        final Runnable update = () -> {
            if (currentPage == NUM_PAGES) {
                currentPage = 0;
            }
            vpImage.setCurrentItem(currentPage++, true);
        };

    }


}