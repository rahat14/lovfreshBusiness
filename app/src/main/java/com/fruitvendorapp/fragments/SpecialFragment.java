package com.fruitvendorapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.adapters.CategoryFilterAdapter;
import com.fruitvendorapp.adapters.ScheduleProductAdapter;
import com.fruitvendorapp.adapters.SpecialProductAdapter;
import com.fruitvendorapp.interfaces.CategoryItemSelectInterface;
import com.fruitvendorapp.interfaces.ItemDeleteInterface;
import com.fruitvendorapp.interfaces.ItemSelectInterface;
import com.fruitvendorapp.model.CategoryModel;
import com.fruitvendorapp.model.ProductModel;
import com.fruitvendorapp.model.ShopOnlineModel;
import com.fruitvendorapp.server_networking.JsonArrayResponseListener;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.server_networking.ResponseListener;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.DialogUtility;
import com.fruitvendorapp.utilities.ProgressDialogUtil;
import com.fruitvendorapp.utilities.SessionManager;
import com.fruitvendorapp.utilities.Urls;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.fruitvendorapp.activities.DashboardActivity.isSearchSpecialVisible;
import static com.fruitvendorapp.activities.DashboardActivity.isSpecialFilterVisible;

public class SpecialFragment extends Fragment implements JsonArrayResponseListener, View.OnClickListener, ResponseListener, CategoryItemSelectInterface, ItemSelectInterface, ItemDeleteInterface {
    private static final String TAG = SpecialFragment.class.getSimpleName();
    private Context mContext;
    @BindView(R.id.auto_search)
    EditText edProductSearch;
    @BindView(R.id.tab_view)
    TabLayout tabView;
    @BindView(R.id.rv_schedule)
    RecyclerView rvSchedule;
    @BindView(R.id.rv_day_product)
    RecyclerView rvDayProduct;
    @BindView(R.id.rl_special_view)
    RelativeLayout rlSpecialView;
    @BindView(R.id.rl_schedule_view)
    RelativeLayout rlScheduleView;
    @BindView(R.id.ll_no_record)
    LinearLayout llNoRecordFound;
    @BindView(R.id.ll_btn_view)
    LinearLayout llBtnView;
    @BindView(R.id.iv_filter)
    ImageView ivFilter;
    @BindView(R.id.btn_hide)
    Button btnHide;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.ivFilterView)
    LinearLayout llFilterView;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.sp_popularity)
    Spinner spPopularityMenu;
    @BindView(R.id.sp_category)
    Spinner spCategory;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.tab_category)
    TabLayout tabCategory;
    @BindView(R.id.tab_sch_category)
    TabLayout tab_sch_category;
    @BindView(R.id.tv_product_title)
    TextView tvProductTitle;
    @BindView(R.id.tv_special_title)
    TextView tvSpecialtitle;
    @BindView(R.id.tv_schedule_title)
    TextView tvScheduletitle;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;

    private List<Integer> deleteList;
    private ArrayList<ProductModel> list;
    private ArrayList<ProductModel> productModelArrayList;
    private ArrayList<CategoryModel> categoryModelArrayList;
    private ProgressDialogUtil progressDialogUtil;
    private String vendor_id = "";
    private SpecialProductAdapter productListAdapter;
    private ScheduleProductAdapter scheduleProductAdapter;
    private String product_id = "";
    private String categoryNm = "All Category";
    private String category_id = "", type = "", cate_name = "";
    private boolean isCategory = false;
    private int categoryindex = 0;
    ArrayList<ShopOnlineModel> shopOnlineModels = new ArrayList<>();

    public void getVendorId(String vendor_id) {
        this.vendor_id = vendor_id;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_special, container, false);
        ButterKnife.bind(this, view);
        if (isSearchSpecialVisible) {
            isSearchSpecialVisible = true;
            edProductSearch.setVisibility(View.VISIBLE);
            edProductSearch.requestFocus();
            BaseUtility.showSoftKeyboard(edProductSearch, Objects.requireNonNull(getActivity()));
        } else {
            isSearchSpecialVisible = false;
            edProductSearch.setVisibility(View.GONE);
            edProductSearch.clearFocus();
            BaseUtility.showSoftKeyboard(edProductSearch, Objects.requireNonNull(getActivity()));
        }

        if (isSpecialFilterVisible) {
            isSpecialFilterVisible = true;
            llFilterView.setVisibility(View.VISIBLE);
            tvClear.setVisibility(View.VISIBLE);

        } else {
            isSpecialFilterVisible = false;
            llFilterView.setVisibility(View.GONE);
            tvClear.setVisibility(View.GONE);
        }

        initView();
        return view;
    }

    private void initView() {
        progressDialogUtil = new ProgressDialogUtil(mContext);
        ivFilter.setOnClickListener(this);
        tvCategory.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        btnHide.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        setSpecialAndScheduleTab();
        getCategoryApi();
        getPopularityTypeSpinner();
        // getProductList();
        try {
            edProductSearch.addTextChangedListener(new TextWatcher() {
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

        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_layout.setRefreshing(true);
                getCategoryApi();
                if (rlSpecialView.getVisibility() == View.VISIBLE) {
                    getSpecialProductList();
                } else if (rlScheduleView.getVisibility() == View.VISIBLE) {
                    getScheduleProductList();
                    setScheduleAdpter();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //setSpecialAndScheduleTab();
    }

    // this method search news by title
    private void filter(String text) {
        if (productModelArrayList != null && !productModelArrayList.isEmpty()) {
            ArrayList<ProductModel> temp = new ArrayList<>();
            for (ProductModel d : productModelArrayList) {
                if (!TextUtils.isEmpty(d.getTitle())) {
                    if (d.getTitle().toLowerCase().contains(text)) {
                        temp.add(d);
                    }
                }
            }
            if (productModelArrayList != null && !productModelArrayList.isEmpty()) {
                if (temp.size() > 0) {
                    productListAdapter.updateList(temp);
                    rvDayProduct.setVisibility(View.VISIBLE);
                    llNoRecordFound.setVisibility(View.GONE);
                } else {
                    rvDayProduct.setVisibility(View.GONE);
                    llNoRecordFound.setVisibility(View.VISIBLE);
                }

            }
        } else if (list != null && !list.isEmpty()) {
            ArrayList<ProductModel> temp = new ArrayList<>();
            for (ProductModel d : list) {
                if (!TextUtils.isEmpty(d.getTitle())) {
                    if (d.getTitle().toLowerCase().contains(text)) {
                        temp.add(d);
                    }
                }
            }
            if (list != null && !list.isEmpty()) {
                if (temp.size() > 0) {
                    scheduleProductAdapter.updateList(temp);
                    rvSchedule.setVisibility(View.VISIBLE);
                    llNoRecordFound.setVisibility(View.GONE);
                } else {
                    rvSchedule.setVisibility(View.GONE);
                    llNoRecordFound.setVisibility(View.VISIBLE);
                }

            }

        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void setSpecialAndScheduleTab() {
        tabView.removeAllTabs();
        tabView.addTab(tabView.newTab().setText(getString(R.string.todays_special)), true);
        tabView.addTab(tabView.newTab().setText(getString(R.string.blackboard_special)));
        rlScheduleView.setVisibility(View.GONE);
        rlSpecialView.setVisibility(View.VISIBLE);
        //tabCategory.setVisibility(View.GONE);
        getSpecialProductList();
        tabView.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals(getString(R.string.todays_special))) {
                    rlScheduleView.setVisibility(View.GONE);
                    rlSpecialView.setVisibility(View.VISIBLE);
                    tabCategory.setVisibility(View.VISIBLE);
                    tab_sch_category.setVisibility(View.GONE);
                    getSpecialProductList();
                } else if (tab.getText().equals(getString(R.string.blackboard_special))) {
                    rlScheduleView.setVisibility(View.VISIBLE);
                    rlSpecialView.setVisibility(View.GONE);
                    tabCategory.setVisibility(View.GONE);
                    tab_sch_category.setVisibility(View.VISIBLE);
                    getScheduleProductList();
                    setScheduleAdpter();
                }
                //setCurrentTabFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


    }

    //implement product Api  for get} All product and Category
    private void getCategoryApi() {
        if (ConnectionUtil.isInternetOn(mContext)) {
            //  progressDialogUtil.showDialog();
            RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_CATEGORY, mContext, Urls.CATEGORY_LIST_URL, this);
        } else {
            BaseUtility.toastMsg(mContext, getString(R.string.no_internet_connection));
        }
    }

    //implement Address Api  for get All Address
    private void deleteProductApi() {
        if (ConnectionUtil.isInternetOn(mContext)) {
            progressDialogUtil.showDialog();
            RequestHelper.PostTokenRequest(NetworkHelper.REQ_CODE_DELETE_MULTIPLE_PRODUCT, mContext, Urls.PRODUCT_MULITPLE_DELETE_URL, new NetworkHelper(mContext).deleteIdsJson(deleteList), this);
        } else {
            BaseUtility.toastMsg(mContext, getString(R.string.no_internet_connection));
        }
    }

    private void hideProductApi() {
        if (ConnectionUtil.isInternetOn(mContext)) {
            progressDialogUtil.showDialog();
            if (btnHide.getText().toString().equals("Hide")) {
                RequestHelper.PostTokenRequest(NetworkHelper.REQ_CODE_HIDE_MULTIPLE_PRODUCT, mContext, Urls.PRODUCT_MULITPLE_HIDE_URL, new NetworkHelper(mContext).hideIdsJson(deleteList, "hide"), this);
            } else {
                RequestHelper.PostTokenRequest(NetworkHelper.REQ_CODE_HIDE_MULTIPLE_PRODUCT, mContext, Urls.PRODUCT_MULITPLE_HIDE_URL, new NetworkHelper(mContext).hideIdsJson(deleteList, "unhide"), this);
            }
        } else {
            BaseUtility.toastMsg(mContext, getString(R.string.no_internet_connection));
        }
    }


    private void getSpecialProductList() {
        if (ConnectionUtil.isInternetOn(mContext)) {
            if (!isSearchSpecialVisible)
                progressDialogUtil.showDialog();
            if (!TextUtils.isEmpty(vendor_id)) {
                Log.e(TAG, Urls.SHOP_ONLINE_URL + vendor_id + "&type=" + "SPL" + "&sort=" + type);
                RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_SPECIAL_LIST, mContext, Urls.SHOP_ONLINE_URL + vendor_id + "&type=" + "SPL" + "&sort=" + type, this);
            } else {
                Log.e(TAG, Urls.SHOP_ONLINE_URL + new SessionManager(mContext).getVendorId() + "&type=" + "SPL" + "&sort=" + type);
                RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_SPECIAL_LIST, mContext, Urls.SHOP_ONLINE_URL + new SessionManager(mContext).getVendorId() + "&type=" + "SPL" + "&sort=" + type, this);
            }
        } else {
            BaseUtility.toastMsg(mContext, getString(R.string.no_internet_connection));
        }
        swipe_layout.setRefreshing(false);
    }

    private void getScheduleProductList() {
        if (ConnectionUtil.isInternetOn(mContext)) {
            if (!isSearchSpecialVisible)
                progressDialogUtil.showDialog();
            if (!TextUtils.isEmpty(vendor_id)) {
                Log.e(TAG, Urls.SHOP_ONLINE_URL + vendor_id + "&type=" + "SCH" + "&sort=" + type);
                RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_SCHEDULE_LIST, mContext, Urls.SHOP_ONLINE_URL + vendor_id + "&type=" + "SCH" + "&sort=" + type, this);
            } else {
                Log.e(TAG, Urls.SHOP_ONLINE_URL + new SessionManager(mContext).getVendorId() + "&type=" + "SCH" + "&sort=" + type);
                RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_SCHEDULE_LIST, mContext, Urls.SHOP_ONLINE_URL + new SessionManager(mContext).getVendorId() + "&type=" + "SCH" + "&sort=" + type, this);
            }
        } else {
            BaseUtility.toastMsg(mContext, getString(R.string.no_internet_connection));
        }
        swipe_layout.setRefreshing(false);
    }

    @Override
    public void onSuccess(int requestCode, JSONArray json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_SPECIAL_LIST:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                productModelArrayList = new ArrayList<>();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Type vendorType = new TypeToken<List<ShopOnlineModel>>() {
                }.getType();
                shopOnlineModels = new ArrayList<>();
                shopOnlineModels = gson.fromJson(json.toString(), vendorType);
                if (shopOnlineModels != null && !shopOnlineModels.isEmpty()) {
                    if (isCategory) {
                        setProduct(shopOnlineModels.get(categoryindex).getProducts());
                    } else {
                        tabCategory.removeAllTabs();
                        setCategory(shopOnlineModels);
                    }
                    isCategory = false;
                    tab_sch_category.setVisibility(View.GONE);
                    tabCategory.setVisibility(View.VISIBLE);
                    rvDayProduct.setVisibility(View.VISIBLE);
                    llNoRecordFound.setVisibility(View.GONE);
                } else {
                    tabCategory.removeAllTabs();
                    tab_sch_category.removeAllTabs();
                    tab_sch_category.setVisibility(View.GONE);
                    tabCategory.setVisibility(View.GONE);
                    tvSpecialtitle.setVisibility(View.GONE);
                    rvDayProduct.setVisibility(View.GONE);
                    llNoRecordFound.setVisibility(View.VISIBLE);
                    tvProductTitle.setText("Add a Special product by clicking on the plus icon");
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
            case NetworkHelper.REQ_CODE_GET_SCHEDULE_LIST:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, "REQ_CODE_GET_SCHEDULE_LIST" + json.toString());
                GsonBuilder gsonBuilder2 = new GsonBuilder();
                Gson gson2 = gsonBuilder2.create();
                Type schedule_type = new TypeToken<List<ShopOnlineModel>>() {
                }.getType();
                ArrayList<ShopOnlineModel> scheduleList = gson2.fromJson(json.toString(), schedule_type);
                if (scheduleList != null && !scheduleList.isEmpty()) {
                    if (isCategory) {
                        setSchProduct(scheduleList.get(categoryindex).getProducts());
                    } else {
                        tab_sch_category.removeAllTabs();
                        setSchCategory(scheduleList);
                    }
                    isCategory = false;
                    tab_sch_category.setVisibility(View.VISIBLE);
                    /*for (int j = 0; j < scheduleList.size(); j++) {
                        list = scheduleList.get(j).getProducts();
                        scheduleProductAdapter.setData(list);
                        scheduleProductAdapter.notifyDataSetChanged();
                    }*/
                    tabCategory.setVisibility(View.GONE);
                    tvScheduletitle.setVisibility(View.VISIBLE);
                    rvDayProduct.setVisibility(View.VISIBLE);
                    llNoRecordFound.setVisibility(View.GONE);
                } else {
                    tabCategory.removeAllTabs();
                    tab_sch_category.removeAllTabs();
                    tab_sch_category.setVisibility(View.GONE);
                    tabCategory.setVisibility(View.GONE);
                    tvScheduletitle.setVisibility(View.GONE);
                    rvDayProduct.setVisibility(View.GONE);
                    llNoRecordFound.setVisibility(View.VISIBLE);
                    tvProductTitle.setText("Add a Schedule product by clicking on the plus icon");
                }
                break;
        }
    }

    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_DELETE_MULTIPLE_PRODUCT:
            case NetworkHelper.REQ_CODE_HIDE_MULTIPLE_PRODUCT:
                Log.e(TAG, json.toString());
                progressDialogUtil.dismissDialog();
                String message = json.optString(Constant.MESSAGE);
                BaseUtility.toastMsg(mContext, message);
                setSpecialAndScheduleTab();
                break;

        }
    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.iv_filter:
                BaseUtility.sendActivityIntent(mContext, FilterActivity.class);
                break;*/
            case R.id.iv_filter:
                if (llFilterView.getVisibility() == View.GONE) {
                    llFilterView.setVisibility(View.VISIBLE);
                    tvClear.setVisibility(View.VISIBLE);
                } else {
                    llFilterView.setVisibility(View.GONE);
                    tvClear.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_hide:
                getProductId();
                hideProductApi();
                break;
            case R.id.btn_delete:
                DialogUtility.deleteProductAlert(mContext, this);
                break;
            case R.id.tv_clear:
                for (CategoryModel categoryModel : categoryModelArrayList) {
                    categoryModel.setSelected(false);
                    category_id = "";
                }
                getPopularityTypeSpinner();
                tvCategory.setText("All Category");
                setSpecialAndScheduleTab();
                break;
            case R.id.tv_category:
                if (categoryModelArrayList != null && !categoryModelArrayList.isEmpty()) {
                    selectCategoryDialog(categoryModelArrayList, this);
                }
                break;
        }
    }

    private void setCategory(ArrayList<ShopOnlineModel> shopOnlineModelArrayList) {
        for (ShopOnlineModel categoryModel : shopOnlineModelArrayList) {
            TabLayout.Tab tab = tabCategory.newTab();
            tab.setCustomView(R.layout.tab_shop_online);
            tab.setText(categoryModel.getName());
            tabCategory.addTab(tab);

        }
        productModelArrayList = shopOnlineModels.get(0).getProducts();
        setProduct(productModelArrayList);
        tabCategory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e(TAG, String.valueOf(tab.getPosition()));
                edProductSearch.setText("");
                BaseUtility.hideKeyboard(edProductSearch, mContext);
                categoryindex = tab.getPosition();
                productModelArrayList = shopOnlineModels.get(categoryindex).getProducts();
                setProduct(productModelArrayList);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setSchCategory(ArrayList<ShopOnlineModel> shopOnlineModelArrayList) {
        for (ShopOnlineModel categoryModel : shopOnlineModelArrayList) {
            TabLayout.Tab tab = tab_sch_category.newTab();
            tab.setCustomView(R.layout.tab_shop_online);
            tab.setText(categoryModel.getName());
            tab_sch_category.addTab(tab);
        }
        list = shopOnlineModelArrayList.get(0).getProducts();
        setSchProduct(list);
        tab_sch_category.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e(TAG, String.valueOf(tab.getPosition()));
                edProductSearch.setText("");
                BaseUtility.hideKeyboard(edProductSearch, mContext);
                categoryindex = tab.getPosition();
                list = shopOnlineModelArrayList.get(categoryindex).getProducts();
                setSchProduct(list);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setScheduleAdpter() {
        GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        manager.setOrientation(RecyclerView.VERTICAL);
        scheduleProductAdapter = new ScheduleProductAdapter(getActivity());
        rvSchedule.setAdapter(scheduleProductAdapter);
        rvSchedule.setLayoutManager(manager);
    }

    private void setSchProduct(ArrayList<ProductModel> list) {
        if (list != null && !list.isEmpty()) {
            GridLayoutManager manager = new GridLayoutManager(mContext, 2);
            manager.setOrientation(RecyclerView.VERTICAL);
            scheduleProductAdapter = new ScheduleProductAdapter(getActivity());
            scheduleProductAdapter.setData(list);
            rvSchedule.setAdapter(scheduleProductAdapter);
            rvSchedule.setLayoutManager(manager);
            rvSchedule.setVisibility(View.VISIBLE);
            llNoRecordFound.setVisibility(View.GONE);
        } else {
            rvDayProduct.setVisibility(View.GONE);
            llNoRecordFound.setVisibility(View.VISIBLE);
        }

    }

    private void setProduct(ArrayList<ProductModel> list) {
        if (list != null && !list.isEmpty()) {
            GridLayoutManager manager = new GridLayoutManager(mContext, 2);
            manager.setOrientation(RecyclerView.VERTICAL);
            productListAdapter = new SpecialProductAdapter(getActivity(), this);
            productListAdapter.setData(list);
            rvDayProduct.setAdapter(productListAdapter);
            rvDayProduct.setLayoutManager(manager);
            rvDayProduct.setVisibility(View.VISIBLE);
            llNoRecordFound.setVisibility(View.GONE);
        } else {
            rvDayProduct.setVisibility(View.GONE);
            llNoRecordFound.setVisibility(View.VISIBLE);
        }

    }

    private void getPopularityTypeSpinner() {
        spPopularityMenu.setAdapter(new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.array_popularity)));
        spPopularityMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getSelectedItem().toString();
                if (type.equals("A to Z(Product name)")) {
                    type = "a_to_z";
                } else if (type.equals("Z to A(Product name)")) {
                    type = "z_to_a";
                } else if (type.equals("New")) {
                    type = "new";
                } else if (type.equals("Price Low to High")) {
                    type = "low_to_high";
                } else if (type.equals("Price High to Low")) {
                    type = "high_to_low";
                }

                isCategory = true;
                if (rlSpecialView.getVisibility() == View.VISIBLE) {
                    getSpecialProductList();
                } else if (rlScheduleView.getVisibility() == View.VISIBLE) {
                    getScheduleProductList();
                    setScheduleAdpter();
                }
//                getSpecialProductList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getProductId() {
        product_id = "";
        String selectedProductId = "";
        if (productModelArrayList != null && !productModelArrayList.isEmpty()) {
            deleteList = new ArrayList<>();
            for (ProductModel model : productModelArrayList) {
                if (model.isSelected()) {
                    selectedProductId = String.format("%s,%s", selectedProductId, model.getProduct_id());
                    Log.e(TAG, "selectedProductId = " + selectedProductId);
                    product_id = selectedProductId.replaceFirst(",", "");
                    Log.e(TAG, "product_id = " + product_id);
                    deleteList.add(Integer.parseInt(model.getProduct_id()));
                    Log.e("list-------", String.valueOf(deleteList.size()));

                }

            }

        }
    }


    @Override
    public void itemSelect(String id, String title) {
        List<Boolean> test = new ArrayList<>();
        boolean isSelected = false;
        llBtnView.setVisibility(View.VISIBLE);
        for (ProductModel model : productModelArrayList) {
            if (model.isSelected()) {
                test.add(model.getHide());
                Log.e("true", "first select");
                if (model.getHide()) {
                    Log.e("true", "trueedd_second_select");
                    btnHide.setText("Unhide");
                    btnHide.setVisibility(View.VISIBLE);
                } else {
                    Log.e("false", "falseeee_third_select");
                    btnHide.setText("Hide");
                    btnHide.setVisibility(View.VISIBLE);
                }
                isSelected = true;

            }
            if (test.contains(true) && test.contains(false)) {
                Log.e("true", "both");
                btnHide.setVisibility(View.GONE);

            }
        }
        //llBtnView.setVisibility(View.GONE);
        if (!isSelected) {
            btnHide.setText("Hide");
            // btnHide.setVisibility(View.VISIBLE);
            llBtnView.setVisibility(View.GONE);
            Log.e("false", "falseeee_fourth_select");
        }

    }

    @Override
    public void itemDelete() {
        getProductId();
        deleteProductApi();
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
                }
            }
        }
        return name;
    }

    public void selectCategoryDialog(ArrayList<CategoryModel> list, CategoryItemSelectInterface categoryItemSelectInterface) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogview = inflater.inflate(R.layout.dialog_category, null);
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(mContext);
        dialogbuilder.setView(dialogview);
        final AlertDialog alertDialog = dialogbuilder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        RecyclerView recyclerView = dialogview.findViewById(R.id.rv_category);
        TextView tvCancel = dialogview.findViewById(R.id.tv_cancel);
        TextView tvOkey = dialogview.findViewById(R.id.tv_okey);
        CategoryFilterAdapter adapter = new CategoryFilterAdapter(mContext);
        adapter.setData(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
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
                    BaseUtility.toastMsg(mContext, getString(R.string.select_state));
                } else {
                    categoryItemSelectInterface.itemCateSelect(category_id, name);
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }

    @Override
    public void itemCateSelect(String id, String title) {
        category_id = id;
        categoryNm = title;
        tvCategory.setText(categoryNm);
        //      getProductList(category_id);
    }
}