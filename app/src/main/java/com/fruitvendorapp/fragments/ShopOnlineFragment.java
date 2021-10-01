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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.fruitvendorapp.adapters.ShopOnlineProductAdapter;
import com.fruitvendorapp.interfaces.CategoryItemSelectInterface;
import com.fruitvendorapp.model.CategoryModel;
import com.fruitvendorapp.model.ProductModel;
import com.fruitvendorapp.model.ShopOnlineModel;
import com.fruitvendorapp.server_networking.JsonArrayResponseListener;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.ProgressDialogUtil;
import com.fruitvendorapp.utilities.SessionManager;
import com.fruitvendorapp.utilities.Urls;
import com.google.android.material.tabs.TabLayout;
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

import static com.fruitvendorapp.activities.DashboardActivity.isSearchShopOnlineVisible;
import static com.fruitvendorapp.activities.DashboardActivity.isShopOnlineFilterVisible;

public class ShopOnlineFragment extends Fragment implements JsonArrayResponseListener, View.OnClickListener, CategoryItemSelectInterface {
    private static final String TAG = ShopOnlineFragment.class.getSimpleName();
    @BindView(R.id.rv_cate_product)
    RecyclerView rvCateProduct;
    @BindView(R.id.tab_category)
    TabLayout tabCategory;
    @BindView(R.id.ll_no_record)
    LinearLayout llNoRecordFound;
    @BindView(R.id.iv_filter)
    ImageView ivFilter;
    @BindView(R.id.auto_search)
    EditText edAutoSearch;
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
    @BindView(R.id.tv_product_title)
    TextView tvProductTitle;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;

    private Context mContext;
    private ProgressDialogUtil progressDialogUtil;
    private ArrayList<CategoryModel> categoryModelArrayList;
    private String vendor_id = "";
    private ArrayList<ProductModel> productList;
    private ShopOnlineProductAdapter listAdapter;
    private String categoryNm = "All Category";
    private String category_id = "", type = "";
    ArrayList<ShopOnlineModel> shopOnlineModels = new ArrayList<>();
    private boolean isCategory = false;
    private int categoryindex = 0;

    public void getVendorId(String vendor_id) {
        this.vendor_id = vendor_id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_online, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }


    private void initView() {
        progressDialogUtil = new ProgressDialogUtil(mContext);
        ivFilter.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        tvCategory.setOnClickListener(this);
        if (isSearchShopOnlineVisible) {
            isSearchShopOnlineVisible = true;
            edAutoSearch.setVisibility(View.VISIBLE);
            edAutoSearch.requestFocus();
            BaseUtility.showSoftKeyboard(edAutoSearch, Objects.requireNonNull(getActivity()));
        } else {
            isSearchShopOnlineVisible = false;
            edAutoSearch.setVisibility(View.GONE);
            edAutoSearch.clearFocus();
            BaseUtility.showSoftKeyboard(edAutoSearch, Objects.requireNonNull(getActivity()));
        }
        if (isShopOnlineFilterVisible) {
            isShopOnlineFilterVisible = true;
            llFilterView.setVisibility(View.VISIBLE);
            tvClear.setVisibility(View.VISIBLE);

        } else {
            isShopOnlineFilterVisible = false;
            llFilterView.setVisibility(View.GONE);
            tvClear.setVisibility(View.GONE);
        }

        getShopOnlineProductApi();
        getCategoryApi();
        getPopularityTypeSpinner();
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

        swipe_layout.setOnRefreshListener(() -> {
            swipe_layout.setRefreshing(true);
            getShopOnlineProductApi();
            getCategoryApi();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    // this method search news by title
    private void filter(String text) {
        if (productList != null) {
            ArrayList<ProductModel> temp = new ArrayList<>();
            for (ProductModel d : productList) {
                if (!TextUtils.isEmpty(d.getTitle())) {
                    if (d.getTitle().toLowerCase().contains(text)) {
                        temp.add(d);
                    }
                }
            }
            if (productList != null && !productList.isEmpty()) {
                listAdapter.updateList(temp);
            }
        }
    }


    //implement product Api  for get} All product and Category
    private void getCategoryApi() {
        if (ConnectionUtil.isInternetOn(mContext)) {
            //  progressDialogUtil.showDialog();
            RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_CATEGORY, mContext, Urls.CATEGORY_LIST_URL, this);
        } else {
            BaseUtility.toastMsg(mContext, getString(R.string.no_internet_connection));
        }
        swipe_layout.setRefreshing(false);
    }

    private void getShopOnlineProductApi() {
        if (ConnectionUtil.isInternetOn(mContext)) {
            progressDialogUtil.showDialog();
            if (!TextUtils.isEmpty(vendor_id)) {
                Log.e(TAG, Urls.SHOP_ONLINE_URL + vendor_id + "&type=" + "SHOPONLINE" + "&sort=" + type);
                RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_SHOP_ONLINE_PRODUCT, mContext, Urls.SHOP_ONLINE_URL + vendor_id + "&type=" + "SHOPONLINE" + "&sort=" + type, this);
            } else {
                Log.e(TAG, Urls.SHOP_ONLINE_URL + new SessionManager(mContext).getVendorId() + "&type=" + "SHOPONLINE" + "&sort=" + type);
                RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_SHOP_ONLINE_PRODUCT, mContext, Urls.SHOP_ONLINE_URL + new SessionManager(mContext).getVendorId() + "&type=" + "SHOPONLINE" + "&sort=" + type, this);
            }
        } else {
            BaseUtility.toastMsg(mContext, getString(R.string.no_internet_connection));
        }
        swipe_layout.setRefreshing(false);

    }

    @Override
    public void onSuccess(int requestCode, JSONArray json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_SHOP_ONLINE_PRODUCT:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
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
                    rvCateProduct.setVisibility(View.VISIBLE);
                    llNoRecordFound.setVisibility(View.GONE);
                } else {
                    rvCateProduct.setVisibility(View.GONE);
                    // ivFilter.setVisibility(View.GONE);
                    llNoRecordFound.setVisibility(View.VISIBLE);
                    tvProductTitle.setText("Add a ShopOnline product by clicking on the plus icon");
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
                getShopOnlineProductApi();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setCategory(ArrayList<ShopOnlineModel> productLists) {
        for (ShopOnlineModel categoryModel : productLists) {
            TabLayout.Tab tab = tabCategory.newTab();
            tab.setCustomView(R.layout.tab_shop_online);
            tab.setText(categoryModel.getName());
            tabCategory.addTab(tab);

        }
        productList = shopOnlineModels.get(0).getProducts();
        setProduct(shopOnlineModels.get(0).getProducts());
        tabCategory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                categoryindex = tab.getPosition();
                setProduct(shopOnlineModels.get(categoryindex).getProducts());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void setProduct(ArrayList<ProductModel> list) {
        if (list != null && !list.isEmpty()) {
            GridLayoutManager manager = new GridLayoutManager(mContext, 3);
            manager.setOrientation(RecyclerView.VERTICAL);
            listAdapter = new ShopOnlineProductAdapter(getActivity());
            listAdapter.setData(list);
            rvCateProduct.setAdapter(listAdapter);
            rvCateProduct.setLayoutManager(manager);
            rvCateProduct.setVisibility(View.VISIBLE);
            //  ivFilter.setVisibility(View.VISIBLE);
            //llNoRecordFound.setVisibility(View.GONE);
        } else {

            rvCateProduct.setVisibility(View.GONE);
            //   ivFilter.setVisibility(View.GONE);
            // llNoRecordFound.setVisibility(View.VISIBLE);
        }

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
        getShopOnlineProductApi();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_filter:
               /* if (llFilterView.getVisibility() == View.GONE) {
                    llFilterView.setVisibility(View.VISIBLE);
                    tvClear.setVisibility(View.VISIBLE);
                } else {
                    llFilterView.setVisibility(View.GONE);
                    tvClear.setVisibility(View.GONE);
                }*/
                break;
            case R.id.tv_clear:
                for (CategoryModel categoryModel : categoryModelArrayList) {
                    categoryModel.setSelected(false);
                    category_id = "";
                }
                getPopularityTypeSpinner();
                //  tvCategory.setText("All Category");
                getShopOnlineProductApi();
                break;
            case R.id.tv_category:
                if (categoryModelArrayList != null && !categoryModelArrayList.isEmpty()) {
                    selectCategoryDialog(categoryModelArrayList, this);
                }
                break;
        }

    }
}