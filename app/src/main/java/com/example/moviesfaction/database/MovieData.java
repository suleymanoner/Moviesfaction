package com.example.moviesfaction.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class MovieData {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "movie_id")
    private int movie_id;

    @ColumnInfo(name = "movie_name")
    private String movie_name;

    @ColumnInfo(name = "movie_poster")
    private String movie_poster;


    public MovieData(int movie_id, String movie_name, String movie_poster){
        this.movie_id = movie_id;
        this.movie_name = movie_name;
        this.movie_poster = movie_poster;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public String getMovie_poster() {
        return movie_poster;
    }
}
