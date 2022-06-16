package pers.chenbo.shownews.ui.save;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import pers.chenbo.shownews.model.Article;
import pers.chenbo.shownews.model.NewsResponse;
import pers.chenbo.shownews.repository.NewsRepository;

public class SaveViewModel extends ViewModel {

    private final NewsRepository repository;
    private final MutableLiveData<String> searchInput = new MutableLiveData<>();

    public SaveViewModel(NewsRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Article>> getAllSavedArticles() { return repository.getAllSavedArticles(); }
    public void deleteSavedArticle(Article article) {
        repository.deleteSavedArticle(article);
    }
}
