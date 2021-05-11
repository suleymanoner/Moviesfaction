package com.example.moviesfaction.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesfaction.R;
import com.example.moviesfaction.database.MovieData;
import com.example.moviesfaction.database.RoomDB;
import com.example.moviesfaction.model.MovieModel;
import com.example.moviesfaction.view.activity.MovieDetailsActivity;
import com.example.moviesfaction.view.fragment.HomeFragment;
import com.example.moviesfaction.view.fragment.ListFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder>{

    private List<MovieData> dataList = new ArrayList<>();
    private Context context;


    public FavoritesAdapter(Context context, List<MovieData> list){
        this.context = context;
        this.dataList = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.ViewHolder holder, int position) {


        MovieData data = dataList.get(position);

        holder.movieTitle.setText(data.getMovie_name());

        Glide.with(context).load(HomeFragment.BASE_PHOTO_URL + data.getMovie_poster()).into(holder.poster);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieData data = dataList.get(holder.getAdapterPosition());

                ListFragment.viewModel.delete(data);
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());
            }
        });

        holder.detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int movie_id = dataList.get(position).getMovie_id();
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("movie_id",movie_id);
                context.startActivity(intent);
            }
        });

    }


    public void setDataList(List<MovieData> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;
        TextView movieTitle;
        FloatingActionButton deleteButton;
        FloatingActionButton detailsButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.postermimagelist);
            movieTitle = itemView.findViewById(R.id.movietitlelist);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            detailsButton = itemView.findViewById(R.id.detailsButton);
        }
    }
}
