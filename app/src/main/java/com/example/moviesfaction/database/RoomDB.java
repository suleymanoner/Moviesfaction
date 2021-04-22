package com.example.moviesfaction.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.moviesfaction.service.Dao;

@Database(entities = {MovieData.class}, version = 1)
public abstract class RoomDB extends RoomDatabase{

    private static RoomDB database;

    private static String DATABASE_NAME = "movies_database";

    public abstract Dao dao();

    public synchronized static RoomDB getInstance(Context context){

        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return database;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDBAsyncTask(database).execute();
        }
    };

    private static class populateDBAsyncTask extends AsyncTask<Void,Void,Void> {

        private Dao dao;

        private populateDBAsyncTask(RoomDB db){
            dao = db.dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //dao.insert(new MainData(1,"Example","/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg"));
            return null;
        }
    }





}
