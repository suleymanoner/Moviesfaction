package com.example.moviesfaction.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.moviesfaction.service.Dao;

import java.util.List;

public class MovieDataRepository {

    private Dao dao;
    private LiveData<List<MovieData>> listLiveData;
    private RoomDB database;

    public MovieDataRepository(Application application){
        database = RoomDB.getInstance(application);
        dao = database.dao();
        listLiveData = dao.getAllData();
    }

    public void insert(MovieData movieData){
        new insertAsyncTask(dao).execute(movieData);
    }

    public void delete(MovieData movieData){
        new deleteAsyncTask(dao).execute(movieData);
    }

    public int getDatabaseMovie(int id){
        return database.dao().getDatabaseMovie(id);
    }

    public LiveData<List<MovieData>> getListLiveData(){
        return listLiveData;
    }

    private static class insertAsyncTask extends AsyncTask<MovieData,Void,Void> {

        private Dao mainDao;

        private insertAsyncTask(Dao dao){
            this.mainDao = dao;
        }


        @Override
        protected Void doInBackground(MovieData... movieData) {
            mainDao.insert(movieData[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<MovieData,Void,Void> {

        private Dao mainDao;

        private deleteAsyncTask(Dao dao){
            this.mainDao = dao;
        }


        @Override
        protected Void doInBackground(MovieData... movieData) {
            mainDao.delete(movieData[0]);
            return null;
        }
    }
}
