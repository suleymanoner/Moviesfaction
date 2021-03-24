package com.example.moviesfaction.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesfaction.R;
import com.example.moviesfaction.model.MovieModel;
import com.example.moviesfaction.view.FeedActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    public ArrayList<MovieModel.Results> resultList;
    public Context context;
    int total = 0;

    public RecyclerViewAdapter(ArrayList<MovieModel.Results> list, Context context){
        this.resultList = list;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        /*
        public ImageView moviePosterList;
        public TextView movieTitleList;
        public TextView movieDateList;
        public TextView movieOverviewList;
         */

        ImageView posterImage;
        ImageView addIcon;
        TextView movieName;
        TextView date;
        TextView overview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            /*
            moviePosterList = itemView.findViewById(R.id.moviePosterListImage);
            movieTitleList = itemView.findViewById(R.id.movieTitleListText);
            movieDateList = itemView.findViewById(R.id.movieDateListText);
            movieOverviewList = itemView.findViewById(R.id.movieOverviewListText);
             */

            posterImage = itemView.findViewById(R.id.movie_poster);
            addIcon = itemView.findViewById(R.id.addImage);
            movieName = itemView.findViewById(R.id.movieNameText);
            date = itemView.findViewById(R.id.dateText);
            overview = itemView.findViewById(R.id.overviewText);

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //R.layout.movie_list
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_2,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String BASE_PHOTO_URL = "https://image.tmdb.org/t/p/w500";

        if(total < getItemCount()){
            String title = resultList.get(total).getTitle();
            String date = resultList.get(total).getRelease_date();
            String overview = resultList.get(total).getOverview();
            String posterPath = resultList.get(total).getPoster_path();

            holder.movieName.setText(title);
            holder.date.setText(date);
            holder.overview.setText(overview);

            if(posterPath == null){
                holder.posterImage.setImageDrawable(context.getResources().getDrawable(R.drawable.no_poster_image));
            }
            else{
                Glide.with(context).load(BASE_PHOTO_URL + posterPath).into(holder.posterImage);
            }


            /*
            holder.movieTitleList.setText(title);
            holder.movieDateList.setText(date);
            holder.movieOverviewList.setText(overview);
            Glide.with(context).load(BASE_PHOTO_URL + posterPath).into(holder.moviePosterList);
             */
        }

        total++;
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

}