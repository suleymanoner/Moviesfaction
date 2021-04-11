package com.example.moviesfaction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.moviesfaction.R;
import com.example.moviesfaction.model.List;
import com.example.moviesfaction.model.MovieDetailsModel;
import com.example.moviesfaction.view.activity.MovieDetailsActivity;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<List> {

    private Context context;
    private int resource;

    public ListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<List> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        convertView = layoutInflater.inflate(resource,parent,false);

        TextView title = convertView.findViewById(R.id.movietitlelist);
        ImageView poster = convertView.findViewById(R.id.postermimagelist);

        title.setText(getItem(position).getTitle());
        Glide.with(context).load(MovieDetailsActivity.BASE_PHOTO_URL + getItem(position).getPosterpath()).into(poster);

        return convertView;
    }
}