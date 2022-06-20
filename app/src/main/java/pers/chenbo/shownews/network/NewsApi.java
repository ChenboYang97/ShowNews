package pers.chenbo.shownews.network;

import pers.chenbo.shownews.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("everything")
    Call<NewsResponse> getEverything(
            @Query("q") String query, @Query("pageSize") int pageSize, @Query("sortBy") String sortBy, @Query("language") String language);

    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlines(
            @Query("country") String country, @Query("pageSize") int pageSize, @Query("page") int page);
}
