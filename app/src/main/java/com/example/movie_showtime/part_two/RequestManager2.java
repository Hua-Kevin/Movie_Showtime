package com.example.movie_showtime.part_two;

import android.content.Context;
import android.widget.Toast;

import com.example.movie_showtime.Listeners.OnMoviesApiListener;
import com.example.movie_showtime.Listeners.OnSearchApiListener;
import com.example.movie_showtime.Listeners.SearchTheatreApiListener;
import com.example.movie_showtime.Models.SearchApiResponse;
import com.example.movie_showtime.Models.TMovie.MovieApiResponse;
import com.example.movie_showtime.Models.Theatre.TheatreApiResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class RequestManager2 {
    Context context;
    List<Object> responses = new ArrayList<>();
    List<Object> Cast_responses = new ArrayList<>();

    String api_key = "2e7390ad0444c3e2408985fbe1db37516f422c38d4bcadb10e26e82be0f93e99";
    String engine = "google";
    Retrofit retrofit_2 = new Retrofit.Builder()
            .baseUrl("https://serpapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://imdb8.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    int actor_count = 2;

    public RequestManager2(Context context) {
        this.context = context;
    }

    public void searchTheatre(SearchTheatreApiListener listener, String location) {
        getTheatres getTheatresDetail = retrofit_2.create(getTheatres.class);
        Call<TheatreApiResponse> call = getTheatresDetail.callTheatres("theatre", location, engine, api_key);

        call.enqueue(new Callback<TheatreApiResponse>() {
            @Override
            public void onResponse(Call<TheatreApiResponse> call, Response<TheatreApiResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Couldn't fetch Data.", Toast.LENGTH_SHORT);
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<TheatreApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }


    public void searchMovies(OnMoviesApiListener listener, String theatre_name, String location) {
        getMovie getMovie = retrofit_2.create(RequestManager2.getMovie.class);
        String call_string = "movies " + theatre_name;
        Call<MovieApiResponse> call = getMovie.callMovie(call_string, location, engine, api_key);

        call.enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> D_response) {
                if (!D_response.isSuccessful()) {
                    Toast.makeText(context, "Couldn't fetch Data.", Toast.LENGTH_SHORT);
                    return;
                }

                /*List<MovieArrayObject> MovieList = D_response.body().getShowtimes().get(0).getMovies();
                for (int i = 0; i < MovieList.size(); i++) {
                    searchMovieDetails(listener, MovieList.get(i).getName());
                    responses.add(D_response.body());
                }*/
                listener.onResponse(D_response.body());
            }

            @Override
            public void onFailure(Call<MovieApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void searchMovieDetails(OnSearchApiListener listener, String movie_name) {
        getMovieDetail getMovieDetail = retrofit.create(RequestManager2.getMovieDetail.class);
        Call<SearchApiResponse> call = getMovieDetail.callMoviesDetail(movie_name);

        call.enqueue(new Callback<SearchApiResponse>() {
            @Override
            public void onResponse(Call<SearchApiResponse> call, Response<SearchApiResponse> D_response) {
                if (!D_response.isSuccessful()) {
                    Toast.makeText(context, "Couldn't fetch Data.", Toast.LENGTH_SHORT);
                    return;
                }
                listener.onResponse(D_response.body());
            }

            @Override
            public void onFailure(Call<SearchApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }


    public interface getTheatres {
        /*@Headers({
                "engine: google",
                "api_key: 2e7390ad0444c3e2408985fbe1db37516f422c38d4bcadb10e26e82be0f93e99"
        })  //doesn't work
        */
        @GET("search.json")
        Call<TheatreApiResponse> callTheatres(
                @Query("q") String theatre,
                @Query("location") String location,
                @Query("engine") String engine,
                @Query("api_key") String api_key
         );
    }

    public interface getMovie {
        /*@Headers({
                "engine: google",
                "api_key: 2e7390ad0444c3e2408985fbe1db37516f422c38d4bcadb10e26e82be0f93e99"
        })  //doesn't work
        */
        @GET("search.json")
        Call<MovieApiResponse> callMovie(
                @Query("q") String call_string,
                @Query("location") String location,
                @Query("engine") String engine,
                @Query("api_key") String api_key
        );
    }

    public interface getMovieDetail {
        @Headers({
                "Accept: application/json",
                "X-RapidAPI-Host: imdb8.p.rapidapi.com",
                "X-RapidAPI-Key: 443432aa27msh0916dc69cf7323cp15a4f6jsn638dc4405ac4"
        })
        @GET("title/find")
        Call<SearchApiResponse> callMoviesDetail(
                @Query("q") String movie_name
        );
    }

}
