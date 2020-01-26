package com.example.testingarchitecturecomponent.ui.single_movie;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.testingarchitecturecomponent.R;
import com.example.testingarchitecturecomponent.data.api.Retrofit;
import com.example.testingarchitecturecomponent.data.entity.Movie;
import com.example.testingarchitecturecomponent.databinding.ActivityMovieBinding;
import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity {

    MovieViewModel movieViewModel;
    ActivityMovieBinding binding;
    int movie_id;

    ImageView imagePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        getIntentFromMainActivity();
        init();
        bind();
    }

    private void getIntentFromMainActivity() {
        movie_id = getIntent().getIntExtra("movie_id", 1);
    }

    private void init() {
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie);

        imagePoster = findViewById(R.id.imagePoster);
    }

    private void bind() {
        movieViewModel.getLiveDataMovie(movie_id).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                binding.setMovie(movie);
                fetchImagePoster(movie);
            }
        });
    }

    private void fetchImagePoster(Movie movie){
        Picasso.with(this)
                .load(Retrofit.BASE_URL_FOR_IMAGE + movie.getImage())
                .into(imagePoster);
    }
}
