package com.example.moviesfaction.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesfaction.view.MovieDetails;
import com.example.moviesfaction.view.ListActivity;
import com.example.moviesfaction.R;
import com.example.moviesfaction.model.MovieModel;
import com.example.moviesfaction.view.FeedActivity;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements PopupMenu.OnMenuItemClickListener {

    public ArrayList<MovieModel.Results> resultList;
    public Context context;
    public int pos = -1;


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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

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
                    pos = holder.getAdapterPosition();
                    showPopup(v);
                }
            });
        }
    }


    private void showPopup(View v) {

        PopupMenu popup = new PopupMenu(context,v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());
        popup.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        // Here I need to position of clicked item in Recyclerview, and I will get title, date etc.

        if(item.getItemId() == R.id.menuAdd){

            // Find here to get position of clicked movie.
            String title = resultList.get(pos).getTitle();
            String date = resultList.get(pos).getRelease_date();
            String overview = resultList.get(pos).getOverview();
            String posterpath = resultList.get(pos).getPoster_path();

            Intent intent1 = new Intent(context,ListActivity.class);
            intent1.putExtra("title",title);
            intent1.putExtra("date",date);
            intent1.putExtra("overview",overview);
            intent1.putExtra("posterpath",posterpath);
            context.startActivity(intent1);
            return true;

        }
        else if(item.getItemId() == R.id.menuShowDetails){

            int movieId = resultList.get(pos).getMovieId();
            Intent intent1 = new Intent(context, MovieDetails.class);
            intent1.putExtra("movie_id",movieId);
            context.startActivity(intent1);
            return true;

        }
        return false;
    }


    @Override
    public int getItemCount() {
        return resultList.size();
    }

}