package com.example.testingarchitecturecomponent.ui.single_movie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.testingarchitecturecomponent.data.entity.Movie;
import com.example.testingarchitecturecomponent.ui.single_movie.MovieRepository;

public class MovieViewModel extends ViewModel {

    private MovieRepository movieRepository;
    private LiveData<Movie> movieLiveData;

    public LiveData<Movie> getLiveDataMovie(int movie_id){
        movieRepository = new MovieRepository();
        movieLiveData = movieRepository.getLiveDataMovieDetails(movie_id);
        return movieLiveData;
    }

}
