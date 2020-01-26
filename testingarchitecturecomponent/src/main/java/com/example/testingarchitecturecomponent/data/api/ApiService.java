package com.example.testingarchitecturecomponent.data.api;

import com.example.testingarchitecturecomponent.data.entity.Movie;
import com.example.testingarchitecturecomponent.data.entity.MoviesPage;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie/{movie_id}")
    Single<Movie> getMovieDetails(@Path("movie_id") int movie_id);

    @GET("movie/popular")
    Single<MoviesPage> getPopularMovies(@Query("page") int page);
}
