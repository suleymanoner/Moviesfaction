package com.example.moviesfaction.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesfaction.view.ListActivity;
import com.example.moviesfaction.R;
import com.example.moviesfaction.model.MovieModel;
import com.example.moviesfaction.view.FeedActivity;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public ArrayList<MovieModel.Results> resultList;
    public Context context;

    public RecyclerViewAdapter(ArrayList<MovieModel.Results> list, Context context) {
        this.resultList = list;
        this.context = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView posterImage;
        ImageView addIcon;
        TextView movieName;
        TextView date;
        TextView overview;
        int pos = getAdapterPosition();


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            posterImage = itemView.findViewById(R.id.movie_poster);
            addIcon = itemView.findViewById(R.id.addImage);
            movieName = itemView.findViewById(R.id.movieNameText);
            date = itemView.findViewById(R.id.dateText);
            overview = itemView.findViewById(R.id.overviewText);


            // I tried here when I click addIcon image, it will take data. But I can't start activity here.

            /*
            addIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Toast.makeText(context, "You clicked : " + pos, Toast.LENGTH_SHORT).show();

                    String titleClicked = resultList.get(pos).title;
                    String dateClicked = resultList.get(pos).release_date;
                    String overviewClicked = resultList.get(pos).overview;
                    String posterpathClicked = resultList.get(pos).poster_path;

                    Intent intent = new Intent(context, ListActivity.class);
                    intent.putExtra("title",titleClicked);
                    intent.putExtra("date",dateClicked);
                    intent.putExtra("overview",overviewClicked);
                    intent.putExtra("posterpath",posterpathClicked);

                }
            });
            */

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_2, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (position < getItemCount()) {
            String title = resultList.get(position).getTitle();
            String date = resultList.get(position).getRelease_date();
            String overview = resultList.get(position).getOverview();
            String posterPath = resultList.get(position).getPoster_path();

            holder.movieName.setText(title);
            holder.date.setText(date);
            holder.overview.setText(overview);

            if (posterPath == null) {
                holder.posterImage.setImageDrawable(context.getDrawable(R.drawable.no_poster));
            } else {
                Glide.with(context).load(FeedActivity.BASE_PHOTO_URL + posterPath).into(holder.posterImage);
            }


            int finalPosition = position;
            holder.addIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Toast.makeText(context, "You clicked : " + finalPosition, Toast.LENGTH_SHORT).show();

                    String titleClicked = resultList.get(finalPosition).title;
                    String dateClicked = resultList.get(finalPosition).release_date;
                    String overviewClicked = resultList.get(finalPosition).overview;
                    String posterpathClicked = resultList.get(finalPosition).poster_path;

                    Intent intent = new Intent(context, ListActivity.class);
                    intent.putExtra("title",titleClicked);
                    intent.putExtra("date",dateClicked);
                    intent.putExtra("overview",overviewClicked);
                    intent.putExtra("posterpath",posterpathClicked);
                    context.startActivity(intent);

                }
            });

        }
        position++;
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

}