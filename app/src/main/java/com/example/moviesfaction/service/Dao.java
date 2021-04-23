package com.example.moviesfaction.service;

import android.graphics.Movie;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moviesfaction.database.MovieData;

import java.util.List;


@androidx.room.Dao
public interface Dao {

    @Insert
    void insert(MovieData movieData);

    @Delete
    void delete(MovieData movieData);

    @Query("SELECT * FROM MOVIES")
    LiveData<List<MovieData>> getAllData();

    @Query("SELECT movie_id FROM MOVIES WHERE movie_id = :id")
    int getDatabaseMovie(int id);

}
