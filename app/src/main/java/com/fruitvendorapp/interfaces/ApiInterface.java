package com.fruitvendorapp.interfaces;

import com.fruitvendorapp.utilities.Urls;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface ApiInterface {
    @Multipart
    @PUT(Urls.PRODUCT_UPLOAD_URL + "{id}/")
    Call<JSONObject> putProfileData(@Header("Authorization") String token,
                                    @Path("id") String id,
                                //    @Part List<MultipartBody.Part> images,
                                    @Part("title") RequestBody titles,
                                    @Part("short_description") RequestBody sortDes,
                                    @Part("long_description") RequestBody longDes,
                                    @Part("price") RequestBody price,
                                    @Part("type") RequestBody type,
                                    @Part("unit") RequestBody unitId,
                                    @Part("stock") RequestBody stock,
                                    @Part("categories") List<Integer> categories);
}
