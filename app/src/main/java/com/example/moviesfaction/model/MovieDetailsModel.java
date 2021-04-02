package com.example.moviesfaction.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class MovieDetailsModel {

    @SerializedName("homepage")
    public String homepage;

    @SerializedName("original_title")
    public String original_title;

    @SerializedName("original_language")
    public String original_language;

    @SerializedName("overview")
    public String overview;

    @SerializedName("poster_path")
    public String poster_path;

    @SerializedName("release_date")
    public String release_date;

    @SerializedName("vote_average")
    public double vote_average;

    @SerializedName("genres")
    public JsonArray genres;


    public String genre1(){
        JsonArray i1 = null;
        String gen = "";
        if(genres != null){
            i1 = genres.get(0).getAsJsonArray();
            gen = i1.get(2).toString();
        }
        return gen;
    }

    public String genre2(){
        JsonArray i2 = null;
        String gen = "";
        if(genres != null){
            i2 = genres.get(1).getAsJsonArray();
            gen = i2.get(2).toString();
        }
        return gen;
    }

    public String genre3(){
        JsonArray i3 = null;
        String gen = "";
        if(genres != null){
            i3 = genres.get(2).getAsJsonArray();
            gen = i3.get(2).toString();
        }
        return gen;
    }


    public String getHomepage() {
        return homepage;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getGenre1(){
        return genre1();
    }

    public String getGenre2(){
        return genre2();
    }

    public String getGenre3(){
        return genre3();
    }
}
