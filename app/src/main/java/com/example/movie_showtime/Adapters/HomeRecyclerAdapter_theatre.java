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

import com.example.movie_showtime.Listeners.onTheatreClickedListener;
import com.example.movie_showtime.Models.Theatre.TheatreArrayObject;
import com.example.movie_showtime.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeRecyclerAdapter_theatre extends RecyclerView.Adapter<HomeViewHolder_theatre> {
    Context context;
    List<TheatreArrayObject> list;
    onTheatreClickedListener listener;

    public HomeRecyclerAdapter_theatre(Context context, List<TheatreArrayObject> list, onTheatreClickedListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HomeViewHolder_theatre onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder_theatre(LayoutInflater.from(context).inflate(R.layout.home_theatre_list, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder_theatre holder, int position) {
        holder.textView_theatre.setText(list.get(position).getTitle());
        holder.textView_theatre.setSelected(true);
        //JSONObject image = list.get(position).getImage();
        if (list.get(position).getThumbnail() != null) {
            String url = list.get(position).getThumbnail();
            Picasso.get().load(url).into(holder.imageView_thumbnail);
        }
        holder.home_theatre_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTheatreClicked(list.get(position).getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class HomeViewHolder_theatre extends RecyclerView.ViewHolder {

    ImageView imageView_thumbnail;
    TextView textView_theatre;
    CardView home_theatre_container;
    public HomeViewHolder_theatre(@NonNull View itemView) {
        super(itemView);
        imageView_thumbnail = itemView.findViewById(R.id.imageView_thumbnail);
        textView_theatre = itemView.findViewById(R.id.textView_theatre);
        home_theatre_container = itemView.findViewById(R.id.home_theatre_container);



    }
}