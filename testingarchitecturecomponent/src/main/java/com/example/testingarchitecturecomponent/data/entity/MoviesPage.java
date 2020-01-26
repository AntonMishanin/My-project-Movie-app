package com.example.testingarchitecturecomponent.data.entity;

import com.example.testingarchitecturecomponent.data.entity.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesPage {

    @SerializedName("results")
    @Expose
    List<Movie> movieList;

    public List<Movie> getMovieList(){
        return movieList;
    }
}
