package com.jmm.healthit.network;

import com.jmm.healthit.model.news.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewApiService {

    @GET("everything")
    Call<ResponseModel> getLatestNews(
            @Query("q") String source,
            @Query("apiKey") String apiKey
    );
}
