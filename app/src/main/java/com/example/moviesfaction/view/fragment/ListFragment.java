package com.example.moviesfaction.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesfaction.R;
import com.example.moviesfaction.adapter.FavoritesAdapter;
import com.example.moviesfaction.database.MovieData;
import com.example.moviesfaction.database.RoomDB;
import com.example.moviesfaction.model.MovieDataViewModel;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment {

    ImageView listTitleImage;
    RecyclerView favoritesRecycler;

    List<MovieData> dataList = new ArrayList<>();

    FavoritesAdapter adapter;

    public static MovieDataViewModel viewModel;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list,container,false);
        listTitleImage = v.findViewById(R.id.listTitleImage);
        favoritesRecycler = v.findViewById(R.id.favoritesListRecyclerView);

        favoritesRecycler.setLayoutManager(new LinearLayoutManager(v.getContext()));

        favoritesRecycler.setHasFixedSize(true);

        adapter = new FavoritesAdapter(v.getContext(),dataList);

        favoritesRecycler.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(MovieDataViewModel.class);

        viewModel.getListLiveData().observe(this, new Observer<List<MovieData>>() {
            @Override
            public void onChanged(List<MovieData> movieData) {
                adapter.setDataList(movieData);
            }
        });


        return v;
    }
}
