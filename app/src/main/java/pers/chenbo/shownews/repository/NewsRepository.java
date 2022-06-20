package pers.chenbo.shownews.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import pers.chenbo.shownews.ShowNewsApplication;
import pers.chenbo.shownews.database.ShowNewsDatabase;
import pers.chenbo.shownews.model.Article;
import pers.chenbo.shownews.model.NewsResponse;
import pers.chenbo.shownews.network.NewsApi;
import pers.chenbo.shownews.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private final NewsApi newsApi;
    private final ShowNewsDatabase database;
    public static final String COUNTRY_INPUT = "us";
    public static final int PAGE_SIZE = 40;
    public static final String SORT_BY = "relevancy";
    public static final String LANGUAGE = "en";

    public NewsRepository() {
        newsApi = RetrofitClient.newInstance().create(NewsApi.class);
        database = ShowNewsApplication.getDatabase();
    }

    public LiveData<NewsResponse> getTopHeadlines(int page) {
        MutableLiveData<NewsResponse> topHeadlinesLiveData = new MutableLiveData<>();
        Call<NewsResponse> responseCall = newsApi.getTopHeadlines(COUNTRY_INPUT, PAGE_SIZE, page);
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

    public LiveData<NewsResponse> searchNews(String query) {
        MutableLiveData<NewsResponse> everyThingLiveData = new MutableLiveData<>();
        Call<NewsResponse> responseCall = newsApi.getEverything(query, PAGE_SIZE, SORT_BY, LANGUAGE);
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

    public LiveData<Boolean> favoriteArticle(Article article) {
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();
        new FavoriteAsyncTask(database, resultLiveData).execute(article);
        return resultLiveData;
    }

    private static class FavoriteAsyncTask extends AsyncTask<Article, Void, Boolean> {

        private final ShowNewsDatabase database;
        private final MutableLiveData<Boolean> liveData;

        private FavoriteAsyncTask(ShowNewsDatabase database, MutableLiveData<Boolean> liveData) {
            this.database = database;
            this.liveData = liveData;
        }

        @Override
        protected Boolean doInBackground(Article... articles) {
            Article article = articles[0];
            try {
                database.articleDao().saveArticle(article);
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            liveData.setValue(success);
        }
    }

    public LiveData<List<Article>> getAllSavedArticles() {
        return database.articleDao().getAllArticle();
    }

    public void deleteSavedArticle(Article article) {
        AsyncTask.execute(() -> database.articleDao().deleteArticle(article));
    }

}
