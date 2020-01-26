package com.example.testingarchitecturecomponent.ui.list_movies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.testingarchitecturecomponent.R;
import com.example.testingarchitecturecomponent.data.entity.Movie;
import com.example.testingarchitecturecomponent.data.entity.MoviesPage;
import com.example.testingarchitecturecomponent.databinding.ActivityMainBinding;
import com.example.testingarchitecturecomponent.ui.single_movie.MovieActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.OnItemClickListener {

    MainActivityViewModel viewModel;
    Adapter adapter;
    RecyclerView recyclerView;
    ActivityMainBinding binding;
    GridLayoutManager gridLayoutManager;

    public static final int NEXT_PAGE = 1;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        binding();
        pagination();
    }

    private void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new Adapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(MainActivity.this);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
    }

    private void binding() {
        binding.setMainactivity(this);

        viewModel.getLiveData(page).observe(this, new Observer<MoviesPage>() {
            @Override
            public void onChanged(@Nullable MoviesPage moviesPage) {
                adapter.setMovies(moviesPage.getMovieList());
            }
        });
    }

    private void pagination() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = gridLayoutManager.getChildCount();
                    int totalItemCount = gridLayoutManager.getItemCount();
                    int pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        page += NEXT_PAGE;
                        viewModel.paging(page);
                    }
                }
            }
        });
    }

    @Override
    public void openMovieActivity(int movieId) {
        Intent intent = new Intent(MainActivity.this, MovieActivity.class);
        intent.putExtra("movie_id", movieId);
        startActivity(intent);
    }
}
