package com.example.moviesfaction.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviesfaction.adapter.ListAdapter;
import com.example.moviesfaction.R;
import com.example.moviesfaction.adapter.RecyclerViewAdapter;
import com.example.moviesfaction.model.List;
import com.example.moviesfaction.model.MovieDetailsModel;
import com.example.moviesfaction.service.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    ImageView listTitleImage;
    ImageView homeIcon;
    ImageView userListIcon;
    ImageView searchIcon;
    ImageView accountIcon;

    public static SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);
        listTitleImage = findViewById(R.id.listTitleImage);
        homeIcon = findViewById(R.id.homeListImageViewList);
        userListIcon = findViewById(R.id.homeListImageViewList);
        searchIcon = findViewById(R.id.searchImageViewList);
        accountIcon = findViewById(R.id.accountImageViewList);

        sharedPreferences = getApplicationContext().getSharedPreferences("Save", Context.MODE_PRIVATE);

        String title = sharedPreferences.getString(String.valueOf(1),"No movie");
        String poster = sharedPreferences.getString(String.valueOf(2),"No poster");

        ArrayList<List> list = new ArrayList<>();

        list.add(new List(title,poster));

        ListAdapter listAdapter = new ListAdapter(this,R.layout.movie_list,list);

        listView.setAdapter(listAdapter);


        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ListActivity.this, FeedActivity.class);
                startActivity(intent1);
            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ListActivity.this, SearchActivity.class);
                startActivity(intent1);
            }
        });

        accountIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ListActivity.this, AccountActivity.class);
                startActivity(intent1);
            }
        });
    }
}


