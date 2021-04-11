package com.example.moviesfaction.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moviesfaction.R;
import com.example.moviesfaction.adapter.ListAdapter;
import com.example.moviesfaction.model.List;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    ImageView listTitleImage;
    ListView listView;

    public static SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list,container,false);
        listTitleImage = v.findViewById(R.id.listTitleImage);
        listView = v.findViewById(R.id.listView);

        sharedPreferences = getContext().getSharedPreferences("Save", Context.MODE_PRIVATE);

        String title = sharedPreferences.getString(String.valueOf(1),"No movie");
        String poster = sharedPreferences.getString(String.valueOf(2),"No poster");

        ArrayList<List> list = new ArrayList<>();

        list.add(new List(title,poster));

        ListAdapter listAdapter = new ListAdapter(getContext(),R.layout.movie_list,list);

        listView.setAdapter(listAdapter);

        return v;
    }
}
