package com.example.testingarchitecturecomponent.ui.single_movie;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.testingarchitecturecomponent.data.api.Retrofit;
import com.example.testingarchitecturecomponent.data.entity.Movie;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieRepository {

    private MutableLiveData<Movie> mutableLiveData;

    public MutableLiveData<Movie> getLiveDataMovieDetails(int movie_id) {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
        }
        loadMovieDetails(movie_id);
        return mutableLiveData;
    }

    private void loadMovieDetails(int movie_id) {

        Retrofit.getApiService().getMovieDetails(movie_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Movie>() {
                    @Override
                    public void onSuccess(Movie movie) {
                        mutableLiveData.postValue(movie);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
    }
}
