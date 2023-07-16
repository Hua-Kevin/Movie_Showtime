package com.example.movie_showtime.part_two;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie_showtime.Adapters.HomeRecyclerAdapter_2;
import com.example.movie_showtime.Listeners.OnMoviesApiListener;
import com.example.movie_showtime.Listeners.onMovieClickedListener;
import com.example.movie_showtime.Models.TMovie.MovieApiResponse;

import com.example.movie_showtime.R;

public class DetailActivity_theatre extends AppCompatActivity {
    TextView textView_TheatreName,textView_TheatreAddress,textView_TheatreDescription,textView_TheatreRating, textView_TheatreDirection;
    RecyclerView recycler_movie;
    HomeRecyclerAdapter_2 adapter;
    RequestManager2 manager;
    ProgressDialog dialog;
    String theatre_name, location;

    onMovieClickedListener movie_listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theatre_detail);

        textView_TheatreName = findViewById(R.id.textView_TheatreName);
        textView_TheatreAddress = findViewById(R.id.textView_TheatreAddress);
        textView_TheatreDescription = findViewById(R.id.textView_TheatreDescription);
        textView_TheatreDirection = findViewById(R.id.textView_TheatreDirection);
        textView_TheatreRating = findViewById(R.id.textView_TheatreRating);
        recycler_movie = findViewById(R.id.recycler_view_movie);

        manager = new RequestManager2(this);

        theatre_name = getIntent().getStringExtra("theatre");
        location = getIntent().getStringExtra("location");

        dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait...");
        dialog.show();

        manager.searchMovies(listener, theatre_name, location);


    }

    private OnMoviesApiListener listener = new OnMoviesApiListener() {
        @Override
        //public void onResponse(MovieApiResponse response) {
        public void onResponse(MovieApiResponse response) {
            dialog.dismiss();
            if (response.equals(null)) {
                Toast.makeText(DetailActivity_theatre.this, "Error!!!", Toast.LENGTH_SHORT).show();
                return;
            }
            showResults(response);
        }

        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(DetailActivity_theatre.this, "Error!!", Toast.LENGTH_SHORT).show();
        }

    };


    private void showResults(MovieApiResponse res1) {
        System.out.println("HI");


        textView_TheatreName.setText(res1.getKnowledge_graph().getTitle());
        textView_TheatreAddress.setText(res1.getKnowledge_graph().getAddress());
        textView_TheatreDescription.setText(res1.getKnowledge_graph().getType());

        textView_TheatreDirection.setMovementMethod(LinkMovementMethod.getInstance());
        String text = String.format("<a href=\"%s\">direction</a>", res1.getKnowledge_graph().getDirections());
        textView_TheatreDirection.setText(Html.fromHtml(text, 0));

        textView_TheatreRating.setText("Rating: " + res1.getKnowledge_graph().getRating());

        recycler_movie.setHasFixedSize(true);
        recycler_movie.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new HomeRecyclerAdapter_2(this, res1.getShowtimes().get(0).getMovies(), movie_listener);
        recycler_movie.setAdapter(adapter);

    }


}