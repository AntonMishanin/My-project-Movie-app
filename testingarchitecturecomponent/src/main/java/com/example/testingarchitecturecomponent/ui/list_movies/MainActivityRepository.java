package com.example.testingarchitecturecomponent.ui.list_movies;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.testingarchitecturecomponent.data.api.Retrofit;
import com.example.testingarchitecturecomponent.data.database.AppDatabase;
import com.example.testingarchitecturecomponent.data.database.MoviesPageDao;
import com.example.testingarchitecturecomponent.data.entity.Movie;
import com.example.testingarchitecturecomponent.data.entity.MoviesPage;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivityRepository {

    private MutableLiveData<MoviesPage> mutableLiveData;

    private MoviesPageDao moviesPageDao;
    private AppDatabase appDatabase;

    private List<Movie> movies = new ArrayList<>();
    private Context context;

    public MainActivityRepository(Application application) {
        this.context = application;
        appDatabase = AppDatabase.getInstance(context);
        moviesPageDao = appDatabase.moviesPageDao();
    }


    public void loadListMovie(int page) {

        Retrofit.getApiService().getPopularMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<MoviesPage>() {
                    @Override
                    public void onSuccess(MoviesPage moviesPage) {
                        mutableLiveData.postValue(moviesPage);
                        setDb(moviesPage.getMovieList());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
    }

    public MutableLiveData<MoviesPage> getLiveData(int page) {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
        }
        loadListMovie(page);
        return mutableLiveData;
    }

    public void paging(int page){
        loadListMovie(page);
    }

    private void setDb(List<Movie> movies) {
        new InsertNoteAsyncTask(moviesPageDao).execute(movies);
    }

    @SuppressLint("CheckResult")
    private void getDb() {
        moviesPageDao.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies2) throws Exception {
                        movies = movies2;
                    }
                });
    }

    public List<Movie> getListFromDb() {
        getDb();
        return movies;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<List<Movie>, Void, Void> {

        private MoviesPageDao moviesPageDao;

        private InsertNoteAsyncTask(MoviesPageDao moviesPageDao) {
            this.moviesPageDao = moviesPageDao;
        }

        @Override
        protected Void doInBackground(List<Movie>... movies) {
            moviesPageDao.insert(movies[0]);
            return null;
        }
    }

}
