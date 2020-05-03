package com.rafiq.newxplore.api;

import androidx.annotation.NonNull;

import com.rafiq.newxplore.model.BlogBodyModel;
import com.rafiq.newxplore.model.Category;
import com.rafiq.newxplore.model.TitleModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Api {
    private static Api dataManager = null;
    public static String ERROR_MSG = "Network Error.Try again";

    public static Api getInstance() {

        if (dataManager == null) {
            dataManager = new Api();
        }

        return dataManager;
    }

    public void getTitlesList(String id, final ApiListener.TitleDownloadListener listener) {

        ApiClient.getApiInterface().getAllTitles(id).enqueue(new Callback<List<TitleModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TitleModel>> call, @NonNull Response<List<TitleModel>> response) {
                if (response != null) {
                    listener.onTitleDownloadSuccess(response.body());

                } else {
                    listener.onTitleDownloadFailed(ERROR_MSG);

                }

            }

            @Override
            public void onFailure(@NonNull Call<List<TitleModel>> call, @NonNull Throwable t) {
                listener.onTitleDownloadFailed(t.getLocalizedMessage());
            }
        });
    }

    public void get_all_parent_category(final ApiListener.ParentCategoryDownloadListener listener) {

        ApiClient.getApiInterface().get_all_parent_category().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                if (response != null) {
                    listener.onParentCategoryDownloadSuccess(response.body());

                } else {
                    listener.onParentCategoryDownloadFailed(ERROR_MSG);

                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                listener.onParentCategoryDownloadFailed(t.getLocalizedMessage());
            }
        });
    }

    public void get_all_secondary_category(String id, final ApiListener.ParentCategoryDownloadListener listener) {

        ApiClient.getApiInterface().get_secondary_category(id).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                if (response != null) {
                    listener.onParentCategoryDownloadSuccess(response.body());

                } else {
                    listener.onParentCategoryDownloadFailed(ERROR_MSG);

                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                listener.onParentCategoryDownloadFailed(t.getLocalizedMessage());
            }
        });
    }

    public void getBlogBody(String id, final ApiListener.BlogBodyDownloadListener listener) {

        ApiClient.getApiInterface().getBlogBody(id).enqueue(new Callback<BlogBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<BlogBodyModel> call, @NonNull Response<BlogBodyModel> response) {
                if (response != null) {
                    listener.onBlogBodyDownloadSuccess(response.body());

                } else {
                    listener.onBlogBodyDownloadFailed(ERROR_MSG);

                }

            }

            @Override
            public void onFailure(@NonNull Call<BlogBodyModel> call, @NonNull Throwable t) {
                listener.onBlogBodyDownloadFailed(t.getLocalizedMessage());
            }
        });
    }
}
