package com.example.moviesfaction.model;

public class List {

    String title;
    String posterpath;

    public List(String title, String posterpath){
        this.title = title;
        this.posterpath = posterpath;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterpath() {
        return posterpath;
    }

}