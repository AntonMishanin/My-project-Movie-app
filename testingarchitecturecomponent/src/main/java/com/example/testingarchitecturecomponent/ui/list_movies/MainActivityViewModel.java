package com.example.testingarchitecturecomponent.ui.list_movies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.testingarchitecturecomponent.data.entity.Movie;
import com.example.testingarchitecturecomponent.data.entity.MoviesPage;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private MainActivityRepository repository;
    private MutableLiveData<MoviesPage> mutableLiveData;

    private Application application;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }


    public LiveData<MoviesPage> getLiveData(int page) {
        if (mutableLiveData == null) {
            if (repository == null) {
                repository = new MainActivityRepository(application);
            }
            mutableLiveData = repository.getLiveData(page);
        }
        return mutableLiveData;
    }

    public void paging(int page){
        repository.paging(page);
    }

    public List<Movie> getLiveDataFromDb() {

        List<Movie> movies = repository.getListFromDb();

        return movies;
    }


}
