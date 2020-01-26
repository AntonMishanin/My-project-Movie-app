package com.example.testingarchitecturecomponent.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.testingarchitecturecomponent.data.entity.Movie;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MoviesPageDao {


    @Query("SELECT * FROM movie")
    Flowable<List<Movie>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Movie> moviesList);

  //  @Query("DELETE FROM movie")
    //void deleteAllMovies();


    /*
    @Query("SELECT * FROM moviespage")
    List<Employee> getAll();

    @Query("SELECT * FROM moviespage WHERE id = :id")
    MoviesPage getById(long id);

    @Insert
    void insert(Employee employee);

    @Update
    void update(Employee employee);

    @Delete
    void delete(Employee employee);
*/
}
