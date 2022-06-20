package pers.chenbo.shownews.ui.home;

import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import pers.chenbo.shownews.model.Article;
import pers.chenbo.shownews.model.NewsResponse;
import pers.chenbo.shownews.repository.NewsRepository;

public class HomeViewModel extends ViewModel {

    private final NewsRepository repository;
    private final MutableLiveData<Integer> pageInput = new MutableLiveData<>();

    public HomeViewModel(NewsRepository newsRepository) {
        this.repository = newsRepository;
    }

    public boolean isFetched() {
        return pageInput.getValue() != null;
    }

    // event
    public LiveData<NewsResponse> getTopHeadlines() { return Transformations.switchMap(pageInput, repository::getTopHeadlines); }
    public void setPageInput(int page) { pageInput.setValue(page); }
    public void setPageIncrementByOne() { pageInput.setValue(pageInput.getValue() + 1);}
    public void setFavoriteArticleInput(Article article) {
        repository.favoriteArticle(article);
    }
    public void deleteSavedArticle(Article article) {
        repository.deleteSavedArticle(article);
    }
}
