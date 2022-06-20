package pers.chenbo.shownews.ui.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import pers.chenbo.shownews.R;
import pers.chenbo.shownews.databinding.FragmentSearchBinding;
import pers.chenbo.shownews.model.Article;
import pers.chenbo.shownews.repository.NewsRepository;
import pers.chenbo.shownews.repository.NewsViewModelFactory;

public class SearchFragment extends Fragment {

    private SearchViewModel viewModel;
    private FragmentSearchBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SearchNewsAdapter newsAdapter = new SearchNewsAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.newsResultsRecyclerView.setLayoutManager(linearLayoutManager);
        binding.newsResultsRecyclerView.setAdapter(newsAdapter);

        NewsRepository repository = new NewsRepository();
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(SearchViewModel.class);
        viewModel
                .searchNews()
                .observe(
                        getViewLifecycleOwner(),
                        newsResponse -> {
                            if (newsResponse != null) {
                                Log.d("SearchFragment", newsResponse.toString());
                                newsAdapter.setArticles(newsResponse.articles);
                            }
                        });

        binding.newsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    viewModel.setSearchInput(query);
                }
                binding.newsSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        newsAdapter.setItemCallback(new SearchNewsAdapter.ItemCallback() {
            @Override
            public void onOpenDetails(Article article) {
                SearchFragmentDirections.ActionNavigationSearchToNavigationDetail direction = SearchFragmentDirections.actionNavigationSearchToNavigationDetail(article);
                NavHostFragment.findNavController(SearchFragment.this).navigate(direction);
            }
        });
    }
}