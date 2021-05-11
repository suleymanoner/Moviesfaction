package com.example.moviesfaction.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesfaction.R;
import com.example.moviesfaction.adapter.RecyclerViewAdapter;
import com.example.moviesfaction.model.MovieModel;
import com.example.moviesfaction.service.ApiService;
import com.example.moviesfaction.view.activity.MovieDetailsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment {

    FloatingActionButton backPage;
    FloatingActionButton nextPage;
    EditText searchEditText;
    Button searchButton;
    RecyclerView recyclerView2;

    int page=1;

    ArrayList<MovieModel.Results> searchResults;

    public String BASE_URL = "https://api.themoviedb.org";
    public String BASE_PHOTO_URL = "https://image.tmdb.org/t/p/w500";
    public String API_KEY = "1bf3c5469807a4b8cb7a0a8a888014b0";
    public Retrofit retrofit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search,container,false);

        backPage = v.findViewById(R.id.backPageInSearch);
        nextPage = v.findViewById(R.id.nextPageInSearch);
        searchEditText = v.findViewById(R.id.searchEditText);
        searchButton = v.findViewById(R.id.searchButton);
        recyclerView2 = v.findViewById(R.id.recyclerView2);



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

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });

        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backPage();
            }
        });


        return v;
    }

    public void search(int page){

        ApiService apiService = retrofit.create(ApiService.class);

        if(searchEditText.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Please enter search word !", Toast.LENGTH_SHORT).show();
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
                            RecyclerViewAdapter adapter = new RecyclerViewAdapter(searchResults,getContext());
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView2.setLayoutManager(layoutManager);
                            recyclerView2.setItemAnimator(new DefaultItemAnimator());
                            recyclerView2.setAdapter(adapter);
                        }
                        else{
                            Toast.makeText(getContext(), "There is no more!", Toast.LENGTH_SHORT).show();
                        }



                    }

                }

                @Override
                public void onFailure(Call<MovieModel> call, Throwable t) {

                }
            });

        }
    }

    public void nextPage(){
        if(searchEditText.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Please first search a movie!", Toast.LENGTH_SHORT).show();
        } else {
            if(page < 0 ){
                Toast.makeText(getContext(), "Last Page!", Toast.LENGTH_SHORT).show();
            } else{
                page = page + 1 ;
                search(page);
            }
        }
    }

    public void backPage(){
        if(searchEditText.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Please first search a movie!", Toast.LENGTH_SHORT).show();
        } else{
            if(page < 0 || page == 1){
                Toast.makeText(getContext(), "Last Page!", Toast.LENGTH_SHORT).show();
            } else{
                page = page - 1;
                if(page == 1){
                    search(page);
                } else{
                    search(page);
                }
            }
        }
    }

}
