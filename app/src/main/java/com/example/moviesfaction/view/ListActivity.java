package com.example.moviesfaction.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.moviesfaction.adapter.ListAdapter;
import com.example.moviesfaction.R;
import com.example.moviesfaction.model.List;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    ImageView listTitleImage;
    ImageView homeIcon;
    ImageView userListIcon;
    ImageView searchIcon;
    ImageView accountIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);
        listTitleImage = findViewById(R.id.listTitleImage);
        homeIcon = findViewById(R.id.homeListImageViewList);
        userListIcon = findViewById(R.id.homeListImageViewList);
        searchIcon = findViewById(R.id.searchImageViewList);
        accountIcon = findViewById(R.id.accountImageViewList);





        while(true){

            Intent intent = getIntent();
            String title = intent.getStringExtra("title");
            String date = intent.getStringExtra("date");
            String overview = intent.getStringExtra("overview");
            String posterpath = intent.getStringExtra("posterpath");

            ArrayList<List> list = new ArrayList<>();
            list.add(new List(title,date,overview,posterpath));
            ListAdapter listAdapter = new ListAdapter(this,R.layout.movie_list,list);

            listView.setAdapter(listAdapter);

            break;
        }







        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ListActivity.this, FeedActivity.class);
                startActivity(intent1);
            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ListActivity.this, SearchActivity.class);
                startActivity(intent1);
            }
        });

        accountIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ListActivity.this, AccountActivity.class);
                startActivity(intent1);
            }
        });





    }
}