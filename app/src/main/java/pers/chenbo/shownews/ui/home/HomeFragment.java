package pers.chenbo.shownews.ui.home;

import static pers.chenbo.shownews.repository.NewsRepository.PAGE_SIZE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.util.List;

import pers.chenbo.shownews.databinding.FragmentHomeBinding;
import pers.chenbo.shownews.model.Article;
import pers.chenbo.shownews.repository.NewsRepository;
import pers.chenbo.shownews.repository.NewsViewModelFactory;

public class HomeFragment extends Fragment implements CardStackListener {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private CardStackLayoutManager cardStackLayoutManager;
    private CardSwipeAdapter cardSwipeAdapter;
    private List<Article> articles;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate means reading a layout xml to translate them in Java code
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Handle like and dislike
        binding.homeLikeButton.setOnClickListener(v -> swipeCard(Direction.Right));
        binding.homeUnlikeButton.setOnClickListener(v -> swipeCard(Direction.Left));
        binding.homeRewindButton.setOnClickListener(v -> stepBack(Direction.Bottom));

        cardSwipeAdapter = new CardSwipeAdapter();
        cardStackLayoutManager = new CardStackLayoutManager(requireContext(), this);
        // 实现了卡片的叠加可视效果
        cardStackLayoutManager.setStackFrom(StackFrom.Top);
        binding.homeCardStackView.setLayoutManager(cardStackLayoutManager);
        binding.homeCardStackView.setAdapter(cardSwipeAdapter);

        NewsRepository repository = new NewsRepository();
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(HomeViewModel.class);
        if (!viewModel.isFetched()) {
            viewModel.setPageInput(1);
        }
        viewModel.getTopHeadlines()
                .observe(
                getViewLifecycleOwner(),
                newsResponse -> {
                    if (newsResponse != null) {
                        Log.d("HomeFragment", newsResponse.toString());
                        articles = newsResponse.articles;
                        cardSwipeAdapter.setArticles(articles);
                    }
                    if (savedInstanceState != null && savedInstanceState.containsKey("leavePosition")) {
                        Log.d("CardStackView", "position " + savedInstanceState.getInt("leavePosition"));
                        cardStackLayoutManager.scrollToPosition(savedInstanceState.getInt("leavePosition"));
                        savedInstanceState.remove("leavePosition");
                    }
                });
    }

    private void swipeCard(Direction direction) {
        SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                .setDirection(direction)
                .setDuration(Duration.Normal.duration)
                .build();
        cardStackLayoutManager.setSwipeAnimationSetting(setting);
        binding.homeCardStackView.swipe();
    }

    private void stepBack(Direction direction) {
        RewindAnimationSetting setting = new RewindAnimationSetting.Builder()
                .setDirection(direction)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(new DecelerateInterpolator())
                .build();
        cardStackLayoutManager.setRewindAnimationSetting(setting);
        binding.homeCardStackView.rewind();
    }

    @Override
    public void onCardSwiped(Direction direction) {
        // based on the direction of swiping, delete if exists (left) or save if not exists
        Article article = articles.get(cardStackLayoutManager.getTopPosition() - 1);
        if (direction == Direction.Left) {
            Log.d("CardStackView", "Unliked " + cardStackLayoutManager.getTopPosition());
            viewModel.deleteSavedArticle(article);
        } else {
            Log.d("CardStackView", "Liked " + cardStackLayoutManager.getTopPosition());
            viewModel.setFavoriteArticleInput(article);
        }
        if (cardStackLayoutManager.getTopPosition() == PAGE_SIZE) {
            viewModel.setPageIncrementByOne();
        }
    }

    @Override
    public void onCardDragging(Direction direction, float v) {}
    @Override
    public void onCardRewound() {}
    @Override
    public void onCardCanceled() {}
    @Override
    public void onCardAppeared(View view, int i) {}
    @Override
    public void onCardDisappeared(View view, int i) {}

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d("lifecycle-fragment","onSaveInstanceState");
        Integer leavePosition = Integer.valueOf(cardStackLayoutManager.getTopPosition());
        outState.putInt("leavePosition", leavePosition);
        super.onSaveInstanceState(outState);
    }
}