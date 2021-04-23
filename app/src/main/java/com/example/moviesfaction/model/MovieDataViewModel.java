package com.example.moviesfaction.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.moviesfaction.database.MovieData;
import com.example.moviesfaction.database.MovieDataRepository;
import com.example.moviesfaction.database.RoomDB;

import java.util.List;

public class MovieDataViewModel extends AndroidViewModel {

    private MovieDataRepository repository;
    private LiveData<List<MovieData>> listLiveData;
    private RoomDB database;

    public MovieDataViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieDataRepository(application);
        listLiveData = repository.getListLiveData();
        database = RoomDB.getInstance(application);
    }

    public void insert(MovieData movieData){
        repository.insert(movieData);
    }

    public void delete(MovieData movieData){
        repository.delete(movieData);
    }

    public LiveData<List<MovieData>> getListLiveData(){
        return listLiveData;
    }

    public int getDatabaseMovie(int id){
        return repository.getDatabaseMovie(id);
    }
}
