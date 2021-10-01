package com.fruitvendorapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.fruitvendorapp.R;
import com.fruitvendorapp.adapters.HomeBannerAdapter;
import com.fruitvendorapp.adapters.ProductListAdapter;
import com.fruitvendorapp.model.BannerImage;
import com.fruitvendorapp.model.CategoryModel;
import com.fruitvendorapp.model.ProductModel;
import com.fruitvendorapp.model.VendorModel;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.server_networking.ResponseListener;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.ProgressDialogUtil;
import com.fruitvendorapp.utilities.SessionManager;
import com.fruitvendorapp.utilities.Urls;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

import static com.fruitvendorapp.activities.DashboardActivity.isSearchHomeVisible;

public class HomeFragment extends Fragment implements ResponseListener {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private Context mContext;
    @BindView(R.id.rv_products)
    RecyclerView rvProduct;
    @BindView(R.id.ll_no_record)
    LinearLayout llNoRecordFound;
    @BindView(R.id.ll_view_main)
    LinearLayout llViewMain;
    @BindView(R.id.tv_shop_title)
    TextView tvShopTitle;
    @BindView(R.id.tv_product_title)
    TextView tvProductTitle;
    @BindView(R.id.iv_img)
    CircleImageView cmvUserImage;
    @BindView(R.id.rl_slider)
    RelativeLayout rlSliderView;
    @BindView(R.id.vp_image_slider)
    ViewPager vpImageSlider;
    @BindView(R.id.indicator)
    CircleIndicator cIndicator;
    @BindView(R.id.auto_search)
    public EditText edAutoSearch;
    @BindView(R.id.tab_category)
    TabLayout tabCategory;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;

    private ProgressDialogUtil progressDialogUtil;
    private ProductListAdapter productListAdapter;
    private ArrayList<ProductModel> productModelArrayList;
    public ArrayList<CategoryModel> categoryModels;
    private String vendor_id = "", shopTitle = "";
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private boolean isCategory = false;
    private int categoryindex = 0;
    public static View rootView;

    public void getVendorId(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_product_list, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        setUpSearchBar();
        getVendorApi();
        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_layout.setRefreshing(true);
                getVendorApi();
            }
        });
        return rootView;
    }

    public void setUpSearchBar() {
        ButterKnife.bind(this, rootView);
        if (isSearchHomeVisible) {
            isSearchHomeVisible = true;
            edAutoSearch.setVisibility(View.VISIBLE);
            edAutoSearch.requestFocus();
            //BaseUtility.showSoftKeyboard(edAutoSearch, Objects.requireNonNull(mContext));
        } else {
            isSearchHomeVisible = false;
            edAutoSearch.setVisibility(View.GONE);
            edAutoSearch.clearFocus();
            edAutoSearch.setText("");
            mContext = getActivity();
            // BaseUtility.showSoftKeyboard(edAutoSearch, Objects.requireNonNull(mContext));
        }
    }

    public void initView() {
        edAutoSearch = rootView.findViewById(R.id.auto_search);
        progressDialogUtil = new ProgressDialogUtil(mContext);
        //  getVendorApi();
        // getProductList();
        try {
            edAutoSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String s = editable.toString();
                    s = s.toLowerCase();
                    filter(s);
                }
            });


        } catch (Exception e) {
            Log.e(TAG, "error = " + e.getMessage());
        }
    }

    // this method search news by title
    private void filter(String text) {
        if (productModelArrayList != null) {
            ArrayList<ProductModel> temp = new ArrayList<>();
            for (ProductModel d : productModelArrayList) {
                if (!TextUtils.isEmpty(d.getTitle())) {
                    if (d.getTitle().toLowerCase().contains(text.toLowerCase())) {
                        temp.add(d);
                    }
                }
            }
            if (productModelArrayList != null && !productModelArrayList.isEmpty()) {
                productListAdapter.updateList(temp);
            }
            if (temp.size() == 0) {
                rvProduct.setVisibility(View.GONE);
                llNoRecordFound.setVisibility(View.VISIBLE);
                tvProductTitle.setText("Add a new product by clicking on the plus icon");
            }
        } else {
            rvProduct.setVisibility(View.GONE);
            llNoRecordFound.setVisibility(View.VISIBLE);
            tvProductTitle.setText("Add a new product by clicking on the plus icon");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        //getVendorApi();
    }


   /* private void setProductListAdapter() {
        GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        manager.setOrientation(RecyclerView.VERTICAL);
        productListAdapter = new ProductListAdapter(mContext);
        rvProduct.setAdapter(productListAdapter);
        rvProduct.setLayoutManager(manager);
    }*/


    public void getVendorApi() {
        if (ConnectionUtil.isInternetOn(mContext)) {
            progressDialogUtil.showDialog();
            Log.e(TAG, Urls.GET_VENDOR + new SessionManager(mContext).getVendorId());
            RequestHelper.getRequestWithToken(NetworkHelper.REQ_CODE_GET_VENDOR_DETAIL, mContext, Urls.GET_VENDOR + new SessionManager(mContext).getVendorId(), this);
        } else {
            BaseUtility.toastMsg(mContext, mContext.getResources().getString(R.string.no_internet_connection));
        }
        edAutoSearch.setText("");
        swipe_layout.setRefreshing(false);
    }


    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        if (requestCode == NetworkHelper.REQ_CODE_GET_VENDOR_DETAIL) {
            llViewMain.setVisibility(View.VISIBLE);
            progressDialogUtil.dismissDialog();
            Log.e(TAG, json.toString());
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            VendorModel vendorModel = gson.fromJson(String.valueOf(json), VendorModel.class);
            if (vendorModel != null) {
                tvShopTitle.setText(vendorModel.getTitle());
                if (!TextUtils.isEmpty(vendorModel.getImageUrl())) {
                    Glide.with(mContext).load(Urls.IMAGE_URL + vendorModel.getImageUrl()).placeholder(R.drawable.ic_fruit_img).error(R.drawable.ic_fruit_img).into(cmvUserImage);
                } else {
                }

                ArrayList<BannerImage> imageArrayList = vendorModel.getBannerImages();
                if (imageArrayList != null && !imageArrayList.isEmpty() && imageArrayList.size() > 0) {
                    rlSliderView.setVisibility(View.VISIBLE);
                    setBannerAdapter(imageArrayList);
                } else {
                    rlSliderView.setVisibility(View.GONE);
                }
                categoryModels = new ArrayList<>();
                categoryModels = vendorModel.getCategories();
                //tabCategory.removeAllTabs();
                if (categoryModels != null && !categoryModels.isEmpty()) {
                    tabCategory.removeAllTabs();
                    setCategory(categoryModels);
                    /*if (isCategory) {
                        setProduct(categoryModels.get(categoryindex).getProducts());
                    } else {
                        tabCategory.removeAllTabs();
                        setCategory(categoryModels);
                    }*/
                    isCategory = false;
                    rvProduct.setVisibility(View.VISIBLE);
                    llNoRecordFound.setVisibility(View.GONE);

                } else {
                    rvProduct.setVisibility(View.GONE);
                    llNoRecordFound.setVisibility(View.VISIBLE);
                    tvProductTitle.setText("Add a new product by clicking on the plus icon");
                }

            } else {
                rvProduct.setVisibility(View.GONE);
                llNoRecordFound.setVisibility(View.VISIBLE);
                tvProductTitle.setText("Add a new product by clicking on the plus icon");
            }
        }

        if (requestCode == NetworkHelper.REQ_CODE_GET_PRODUCTS) {
            Log.e(TAG, json.toString());
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            CategoryModel categoryModel = gson.fromJson(String.valueOf(json), CategoryModel.class);
            if (categoryModel != null) {
                productModelArrayList = categoryModel.getProducts();
                setProduct(productModelArrayList);
                rvProduct.setVisibility(View.VISIBLE);
                llNoRecordFound.setVisibility(View.GONE);
            } else {
                rvProduct.setVisibility(View.GONE);
                llNoRecordFound.setVisibility(View.VISIBLE);
                tvProductTitle.setText("Add a new product by clicking on the plus icon");
            }
            progressDialogUtil.dismissDialog();
        }
    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }

    private void setCategory(ArrayList<CategoryModel> categoryModelArrayList) {
        for (CategoryModel categoryModel : categoryModelArrayList) {
            TabLayout.Tab tab = tabCategory.newTab();
            tab.setCustomView(R.layout.tab);
            tab.setText(categoryModel.getName());
            tabCategory.addTab(tab);

        }
        getProductsFromCategory(categoryModels.get(categoryindex).getId());
        //productModelArrayList = categoryModels.get(0).getProducts();
        //setProduct(productModelArrayList);
        tabCategory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e(TAG, String.valueOf(tab.getPosition()));
                try {
                    categoryindex = tab.getPosition();
                    getProductsFromCategory(categoryModels.get(categoryindex).getId());
                    //setProduct(categoryModels.get(categoryindex).getProducts());
                } catch (IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                    Log.e(TAG, ex.getMessage());
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getProductsFromCategory(String categoryId) {
        if (ConnectionUtil.isInternetOn(mContext)) {
            progressDialogUtil.showDialog();
            Log.e(TAG, Urls.GET_PRODUCTS + categoryId);
            RequestHelper.getRequestWithToken(NetworkHelper.REQ_CODE_GET_PRODUCTS, mContext, Urls.GET_PRODUCTS + categoryId, this);
        } else {
            BaseUtility.toastMsg(mContext, mContext.getResources().getString(R.string.no_internet_connection));
        }
    }

    private void setProduct(ArrayList<ProductModel> list) {
        if (list != null && !list.isEmpty()) {
            GridLayoutManager manager = new GridLayoutManager(mContext, 2);
            manager.setOrientation(RecyclerView.VERTICAL);
            rvProduct.setLayoutManager(manager);
            productListAdapter = new ProductListAdapter(getActivity());
            productListAdapter.setData(list, "");
            rvProduct.setAdapter(productListAdapter);
            rvProduct.setVisibility(View.VISIBLE);
            llNoRecordFound.setVisibility(View.GONE);
            productModelArrayList = list;
            if (edAutoSearch.getText().toString().length() > 0) {
                filter(edAutoSearch.getText().toString());
            }
        } else {
            rvProduct.setVisibility(View.GONE);
            llNoRecordFound.setVisibility(View.VISIBLE);
            tvProductTitle.setText("Add a new product by clicking on the plus icon");
        }
  /*Type productType = new TypeToken<List<ProductModel>>() {
                }.getType();
                productModelArrayList = gson.fromJson(json.toString(), productType);
                if (productModelArrayList != null && !productModelArrayList.isEmpty()) {
                    productListAdapter.setData(productModelArrayList);
                    productListAdapter.notifyDataSetChanged();
                    rvProduct.setVisibility(View.VISIBLE);
                    llNoRecordFound.setVisibility(View.GONE);
                } else {
                    rvProduct.setVisibility(View.GONE);
                    llNoRecordFound.setVisibility(View.VISIBLE);
                    tvProductTitle.setText("Add a new product by clicking on the plus icon");
                }*/


    }

    // set Banner Adapter
    private void setBannerAdapter(ArrayList<BannerImage> imageArrayList) {
        HomeBannerAdapter bannerAdapter = new HomeBannerAdapter(getActivity());
        bannerAdapter.setData(imageArrayList);
        vpImageSlider.setAdapter(bannerAdapter);
        cIndicator.setViewPager(vpImageSlider);
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
                    int pageCount = imageArrayList.size();
                    if (currentPage == 0) {
                        vpImageSlider.setCurrentItem(pageCount - 1, false);
                    } else if (currentPage == pageCount - 1) {
                        vpImageSlider.setCurrentItem(0, false);
                    }
                }
            }
        });
        final Handler handler = new Handler();
        final Runnable update = () -> {
            if (currentPage == NUM_PAGES) {
                currentPage = 0;
            }
            vpImageSlider.setCurrentItem(currentPage++, true);
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 4000, 6000);
    }


}
