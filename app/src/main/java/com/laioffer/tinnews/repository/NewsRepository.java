package com.laioffer.tinnews.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.laioffer.tinnews.model.NewsResponse;
import com.laioffer.tinnews.network.NewsApi;
import com.laioffer.tinnews.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private final NewsApi newsApi;
    public NewsRepository() {
        newsApi = RetrofitClient.newInstance().create(NewsApi.class);
    }
    public LiveData<NewsResponse> getTopHeadlines(String country) {
        MutableLiveData<NewsResponse> topHeadlinesLiveData = new MutableLiveData<>();
        Call<NewsResponse> responseCall = newsApi.getTopHeadlines(country);
        responseCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    topHeadlinesLiveData.setValue(response.body());
                    Log.d("getTopHeadlines", response.body().toString());
                } else {
                    topHeadlinesLiveData.setValue(null);
                    Log.d("getTopHeadlines", response.toString());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                topHeadlinesLiveData.setValue(null);
                Log.d("getTopHeadlines", t.toString());
            }
        });
        return topHeadlinesLiveData;
    }

    public LiveData<NewsResponse> searchNews(String country) {
        MutableLiveData<NewsResponse> everyThingLiveData = new MutableLiveData<>();
        Call<NewsResponse> responseCall = newsApi.getEverything(country, 40);
        responseCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    everyThingLiveData.setValue(response.body());
                    Log.d("getRelatedNews", response.body().toString());
                } else {
                    everyThingLiveData.setValue(null);
                    Log.d("getRelatedNews", response.toString());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                everyThingLiveData.setValue(null);
                Log.d("getRelatedNews", t.toString());
            }
        });
        return everyThingLiveData;
    }
}
