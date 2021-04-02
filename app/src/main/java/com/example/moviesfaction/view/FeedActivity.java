package com.example.moviesfaction.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.moviesfaction.R;
import com.example.moviesfaction.adapter.ListAdapter;
import com.example.moviesfaction.adapter.RecyclerViewAdapter;
import com.example.moviesfaction.model.MovieModel;
import com.example.moviesfaction.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedActivity extends AppCompatActivity {

    ImageView userListImageView;
    ImageView searchImageView;
    ImageView accountImageView;
    ImageView homeImageView;
    Button nextPage;
    public int page=1;

    public String BASE_URL = "https://api.themoviedb.org";
    public static final String BASE_PHOTO_URL = "https://image.tmdb.org/t/p/w500";
    public String API_KEY = "1bf3c5469807a4b8cb7a0a8a888014b0";
    public Retrofit retrofit;

    public ArrayList<MovieModel.Results> movieResults;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        userListImageView = findViewById(R.id.userListImageView);
        searchImageView = findViewById(R.id.searchImageView);
        accountImageView = findViewById(R.id.accountImageView);
        homeImageView = findViewById(R.id.homeListImageView);
        nextPage= findViewById(R.id.nextPage);

        recyclerView = findViewById(R.id.recyclerView);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        accountImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedActivity.this,AccountActivity.class);
                startActivity(intent);
            }
        });

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMovies(page++);
            }
        });

        userListImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedActivity.this,ListActivity.class);
                startActivity(intent);
            }
        });



        loadMovies(page);

    }



    public void loadMovies(int page){

        ApiService apiService = retrofit.create(ApiService.class);

        Call<MovieModel> modelC = apiService.getPages("popular",API_KEY,page);

        modelC.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {

                if(response.isSuccessful()){

                    MovieModel movieModel = response.body();
                    List<MovieModel.Results> list = movieModel.getResults();
                    movieResults = new ArrayList<>(list);

                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(movieResults,getApplicationContext());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    //SpaceItemDecorator decorator = new SpaceItemDecorator(5);
                    //recyclerView.addItemDecoration(decorator);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                Toast.makeText(FeedActivity.this, "Data couldn't load!", Toast.LENGTH_SHORT).show();
            }
        });

    }









}