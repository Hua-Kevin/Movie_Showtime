package com.example.movie_showtime.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie_showtime.Listeners.onMovieClickedListener;
import com.example.movie_showtime.Models.TMovie.MovieArrayObject;
import com.example.movie_showtime.R;
import java.util.List;

public class HomeRecyclerAdapter_2 extends RecyclerView.Adapter<HomeViewHolder_2> {
    Context context;
    List<MovieArrayObject> list;
    onMovieClickedListener listener;

    public HomeRecyclerAdapter_2(Context context, List<MovieArrayObject> list, onMovieClickedListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HomeViewHolder_2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder_2(LayoutInflater.from(context).inflate(R.layout.home_movies_list2, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder_2 holder, int position) {
        holder.textView_movie.setText(list.get(position).getName());
        holder.textView_movie.setSelected(true);

        holder.textView_movie_time.setText(list.get(position).getShowing().get(0).getTime().get(0));
        holder.textView_movie_time.setSelected(true);

        //JSONObject image = list.get(position).getImage();
        /*if (list.get(position).getImage() != null) {
            String url = list.get(position).getImage().getUrl();
            Picasso.get().load(url).into(holder.imageView_poster);
        }*/

        holder.home_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listener.onMovieClicked(list.get(position).getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class HomeViewHolder_2 extends RecyclerView.ViewHolder {

    ImageView imageView_poster;
    TextView textView_movie;
    CardView home_container;
    TextView textView_movie_time;
    public HomeViewHolder_2(@NonNull View itemView) {
        super(itemView);
        imageView_poster = itemView.findViewById(R.id.imageView_poster);
        textView_movie = itemView.findViewById(R.id.textView_movie);
        home_container = itemView.findViewById(R.id.home_container);

        textView_movie_time = itemView.findViewById(R.id.textView_movie_time);

    }
}