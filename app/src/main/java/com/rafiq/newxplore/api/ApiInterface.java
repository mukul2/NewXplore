package com.rafiq.newxplore.api;

import com.rafiq.newxplore.model.BlogBodyModel;
import com.rafiq.newxplore.model.Category;
import com.rafiq.newxplore.model.TitleModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("fetch_titles.php")
    Call<List<TitleModel>> getAllTitles(@Field("id") String id);

    @GET("get_all_parent_category.php")
    Call<List<Category>> get_all_parent_category();

    @FormUrlEncoded
    @POST("get_secondary_category.php")
    Call<List<Category>> get_secondary_category(@Field("id") String id);

    @FormUrlEncoded
    @POST("getBlogBody.php")
    Call<BlogBodyModel> getBlogBody(@Field("id") String id);
}
