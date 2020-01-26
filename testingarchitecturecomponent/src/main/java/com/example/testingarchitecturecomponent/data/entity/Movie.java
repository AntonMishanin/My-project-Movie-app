package com.example.testingarchitecturecomponent.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Movie {

    @PrimaryKey
    public int id;

    public String title;
    public String overview;

    @SerializedName("release_date")
    @Expose
    public String releaseDate;

    @SerializedName("poster_path")
    @Expose
    public String image;

    public String getTitle() {
        return title;
    }

    public int getMovieId(){
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
