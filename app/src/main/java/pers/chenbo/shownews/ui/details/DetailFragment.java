package pers.chenbo.shownews.ui.details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import pers.chenbo.shownews.R;
import pers.chenbo.shownews.databinding.FragmentDetailBinding;
import pers.chenbo.shownews.model.Article;

public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;


    public DetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Article article = DetailFragmentArgs.fromBundle(getArguments()).getArticle();
        binding.detailAuthorTextView.setText(article.author);
        binding.detailContentTextView.setText(article.content);
        binding.detailTitleTextView.setText(article.title);
        binding.detailDateTextView.setText(article.publishedAt);
        binding.detailDescriptionTextView.setText(article.description);
        Picasso.get().load(article.urlToImage).into(binding.detailImageView);
    }
}