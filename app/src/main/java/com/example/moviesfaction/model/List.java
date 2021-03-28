package com.example.moviesfaction.model;

public class List {

    String title;
    String date;
    String overview;
    String posterpath;

    public List(String title, String date, String overview, String posterpath) {
        this.title = title;
        this.date = date;
        this.overview = overview;
        this.posterpath = posterpath;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterpath() {
        return posterpath;
    }
}