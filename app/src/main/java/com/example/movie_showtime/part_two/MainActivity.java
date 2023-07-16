package com.example.movie_showtime.part_two;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie_showtime.Adapters.HomeRecyclerAdapter_theatre;
import com.example.movie_showtime.Listeners.SearchTheatreApiListener;
import com.example.movie_showtime.Listeners.onTheatreClickedListener;
import com.example.movie_showtime.Models.Theatre.TheatreApiResponse;

import com.example.movie_showtime.R;

public class MainActivity extends AppCompatActivity implements onTheatreClickedListener {
    SearchView search_view;
    RecyclerView recycler_view_home;
    HomeRecyclerAdapter_theatre adapter;
    RequestManager2 manager;
    ProgressDialog dialog;

    String location = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_theatre);



        search_view = findViewById(R.id.search_view);
        recycler_view_home = findViewById(R.id.recycler_view_home);

        dialog = new ProgressDialog(this);
        manager = new RequestManager2(this);



        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Please wait...");
                dialog.show();
                location = query;
                manager.searchTheatre(listener, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private final SearchTheatreApiListener listener = new SearchTheatreApiListener() {
        @Override
        public void onResponse(TheatreApiResponse response) {
            dialog.dismiss();
            if (response == null) {
                Toast.makeText(MainActivity.this, "No Data Available.", Toast.LENGTH_SHORT).show();
                return;
            }
            showResult(response);
        }

        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, "An Error Occured!", Toast.LENGTH_SHORT).show();
        }
    };

    private void showResult(TheatreApiResponse response) {
        recycler_view_home.setHasFixedSize(true);
        recycler_view_home.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        adapter = new HomeRecyclerAdapter_theatre(this, response.getLocal_results().getPlaces(), this);
        recycler_view_home.setAdapter(adapter);
    }

    @Override
    public void onTheatreClicked(String id) {       //theatre name & location
        Intent intent = new Intent(MainActivity.this, DetailActivity_theatre.class);
        intent.putExtra("theatre", id);
        intent.putExtra("location", location);
        startActivity(intent);
    }
}