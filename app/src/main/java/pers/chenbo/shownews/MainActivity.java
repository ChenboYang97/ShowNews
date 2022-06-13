package pers.chenbo.shownews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController);

//        // sample on using retrofit
//        NewsApi api = RetrofitClient.newInstance().create(NewsApi.class);
//        // endpoint: baseURL/top-headlines/?q=tesla&pageSize=10
//        Call<NewsResponse> responseCall = api.getTopHeadlines("tesla",10);
//
//        responseCall.enqueue(new Callback<NewsResponse>() {
//            @Override
//            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
//                // success
//                if (response.isSuccessful()) {
//                    NewsResponse news = response.body();
//                    Log.d("getTopHeadlines", news.toString());
//                } else {
//                    Log.d("getTopHeadlines", response.toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<NewsResponse> call, Throwable t) {
//                Log.d("getTopHeadlines", t.toString());
//            }
//        });

        // new a task, make the call<NewsResponse>
        // add task to queue
        // while(true) { retrofit keep check the queue }
        // if queue has 'task', retrofit do task: call endpoint, parse json, etc
        // once retrofit finish the task
        // callback.onResponse(response)
        // otherwise, callback.onFailure(response)
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}