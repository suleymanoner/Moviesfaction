package com.example.moviesfaction.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;

public class MovieModel {

    @SerializedName("results")
    public List<Results> results;

    @SerializedName("page")
    public int page;

    public List<Results> getResults() {
        return results;
    }

    public int getPage() {
        return page;
    }

    public static class Results{

        @SerializedName("title")
        public String title;

        @SerializedName("release_date")
        public String release_date;

        @SerializedName("overview")
        public String overview;

        @SerializedName("poster_path")
        public String poster_path;

        public String getTitle() {
            return title;
        }

        public String getRelease_date() {
            return release_date;
        }

        public String getOverview() {
            return overview;
        }

        public String getPoster_path(){
            return poster_path;
        }
    }

}