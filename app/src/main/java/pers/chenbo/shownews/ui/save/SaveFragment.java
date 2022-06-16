package pers.chenbo.shownews.ui.save;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pers.chenbo.shownews.databinding.FragmentSaveBinding;
import pers.chenbo.shownews.model.Article;
import pers.chenbo.shownews.repository.NewsRepository;
import pers.chenbo.shownews.repository.NewsViewModelFactory;
import pers.chenbo.shownews.ui.search.SearchNewsAdapter;
import pers.chenbo.shownews.ui.search.SearchViewModel;

public class SaveFragment extends Fragment {

    private SaveViewModel viewModel;
    private FragmentSaveBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSaveBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SavedNewsAdapter newsAdapter = new SavedNewsAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.newsSavedRecyclerView.setLayoutManager(linearLayoutManager);
        binding.newsSavedRecyclerView.setAdapter(newsAdapter);

        NewsRepository repository = new NewsRepository();
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(SaveViewModel.class);
        viewModel
                .getAllSavedArticles()
                .observe(
                        getViewLifecycleOwner(),
                        savedArticles -> {
                            if (savedArticles != null) {
                                Log.d("SaveFragment", savedArticles.toString());
                                newsAdapter.setArticles(savedArticles);
                            }
                        });

        newsAdapter.setItemCallback(new SavedNewsAdapter.ItemCallback() {
            @Override
            public void onOpenDetails(Article article) {
                SaveFragmentDirections.ActionNavigationSaveToNavigationDetail direction = SaveFragmentDirections.actionNavigationSaveToNavigationDetail(article);
                NavHostFragment.findNavController(SaveFragment.this).navigate(direction);
            }

            @Override
            public void onRemoveFavorite(Article article) {
                viewModel.deleteSavedArticle(article);
            }
        });
    }
}