package pers.chenbo.shownews.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import pers.chenbo.shownews.model.NewsResponse;
import pers.chenbo.shownews.repository.NewsRepository;

public class SearchViewModel extends ViewModel {
    private final NewsRepository repository;
    private final MutableLiveData<String> searchInput = new MutableLiveData<>();

    public SearchViewModel(NewsRepository repository) {
        this.repository = repository;
    }
    public void setSearchInput(String query) {
        searchInput.setValue(query);
    }
    public LiveData<NewsResponse> searchNews() {
        return Transformations.switchMap(searchInput, repository::searchNews);
    }
}
