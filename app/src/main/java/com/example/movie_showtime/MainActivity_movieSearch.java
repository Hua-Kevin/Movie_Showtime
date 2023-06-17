package com.example.movie_showtime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie_showtime.Adapters.HomeRecyclerAdapter;
import com.example.movie_showtime.Listeners.OnMovieClickListener;
import com.example.movie_showtime.Listeners.OnSearchApiListener;
import com.example.movie_showtime.Models.SearchApiResponse;

public class MainActivity_movieSearch extends AppCompatActivity implements OnMovieClickListener {
    SearchView search_view;
    RecyclerView recycler_view_home;
    HomeRecyclerAdapter adapter;
    RequestManager manager;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);



        search_view = findViewById(R.id.search_view);
        recycler_view_home = findViewById(R.id.recycler_view_home);

        dialog = new ProgressDialog(this);
        manager = new RequestManager(this);



        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Please wait...");
                dialog.show();
                manager.searchMovies(listener, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private final OnSearchApiListener listener = new OnSearchApiListener() {
        @Override
        public void onResponse(SearchApiResponse response) {
            dialog.dismiss();
            if (response == null) {
                Toast.makeText(MainActivity_movieSearch.this, "No Data Available.", Toast.LENGTH_SHORT).show();
                return;
            }
            showResult(response);
        }

        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(MainActivity_movieSearch.this, "An Error Occured!", Toast.LENGTH_SHORT).show();
        }
    };

    private void showResult(SearchApiResponse response) {
        recycler_view_home.setHasFixedSize(true);
        recycler_view_home.setLayoutManager(new GridLayoutManager(MainActivity_movieSearch.this, 2));
        adapter = new HomeRecyclerAdapter(this, response.getResults(), this);
        recycler_view_home.setAdapter(adapter);
    }

    @Override
    public void onMovieClicked(String id) {
        //Toast.makeText(MainActivity.this, id, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity_movieSearch.this, DetailActivity.class);
        intent.putExtra("data", id);
        startActivity(intent);
    }
}