package com.example.moviesfaction.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviesfaction.R;
import com.example.moviesfaction.adapter.RecyclerViewAdapter;
import com.example.moviesfaction.model.MovieModel;
import com.example.moviesfaction.service.ApiService;
import com.google.protobuf.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    EditText searchEditText;
    Button searchButton;
    ImageView listImage;
    ImageView searchImage;
    ImageView accountImage;
    ImageView homeImage;
    Button nextPageSearch;
    int page=1;

    RecyclerView recyclerView2;
    ArrayList<MovieModel.Results> searchResults;

    public String BASE_URL = "https://api.themoviedb.org";
    public String BASE_PHOTO_URL = "https://image.tmdb.org/t/p/w500";
    public String API_KEY = "1bf3c5469807a4b8cb7a0a8a888014b0";
    public Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        recyclerView2 = findViewById(R.id.recyclerView2);
        listImage = findViewById(R.id.userListImageView3);
        searchImage = findViewById(R.id.searchImageView3);
        accountImage = findViewById(R.id.accountImageView3);
        homeImage = findViewById(R.id.homeListImageView3);
        nextPageSearch = findViewById(R.id.nextPageSearchButton);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(1);
            }
        });

        accountImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,AccountActivity.class);
                startActivity(intent);
            }
        });

        homeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,FeedActivity.class);
                startActivity(intent);
            }
        });

        nextPageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(page++);
            }
        });


    }

    public void search(int page){

        ApiService apiService = retrofit.create(ApiService.class);

        if(searchEditText.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter search word !", Toast.LENGTH_SHORT).show();
        }
        else{

            Call<MovieModel> modelCall = apiService.search("movie",API_KEY,searchEditText.getText().toString(),page);

            modelCall.enqueue(new Callback<MovieModel>() {
                @Override
                public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {

                    if(response.isSuccessful()){

                        MovieModel model = response.body();
                        List<MovieModel.Results> results = model.getResults();
                        searchResults = new ArrayList<>(results);

                        if(model.total_page >= page){
                            RecyclerViewAdapter adapter = new RecyclerViewAdapter(searchResults,getApplicationContext());
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView2.setLayoutManager(layoutManager);
                            recyclerView2.setItemAnimator(new DefaultItemAnimator());
                            recyclerView2.setAdapter(adapter);
                        }
                        else{
                            Toast.makeText(SearchActivity.this, "There is no more!", Toast.LENGTH_SHORT).show();
                        }



                    }

                }

                @Override
                public void onFailure(Call<MovieModel> call, Throwable t) {

                }
            });

        }
    }
}