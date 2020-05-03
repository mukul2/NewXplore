package com.rafiq.newxplore.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.rafiq.newxplore.Data.Config.API_BASE_URL;

/**
 * Created by TAOHID on 1/21/2018.
 */

public class ApiClient {

    private static final String BASE_URL = API_BASE_URL;
    private static Retrofit retrofit = null;

    private static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiInterface getApiInterface() {
        return getClient().create(ApiInterface.class);
    }
}
