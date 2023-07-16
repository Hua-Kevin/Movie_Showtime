package com.example.movie_showtime.part_one;

import android.content.Context;
import android.widget.Toast;

import com.example.movie_showtime.Listeners.OnDetailsApiListener;
import com.example.movie_showtime.Listeners.OnSearchApiListener;
import com.example.movie_showtime.Models.DetailApiResponse;
import com.example.movie_showtime.Models.SearchApiResponse;
import com.example.movie_showtime.Models.TrailerApiResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    List<Object> responses = new ArrayList<>();
    List<Object> Cast_responses = new ArrayList<>();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://imdb8.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    int actor_count = 2;

    public RequestManager(Context context) {
        this.context = context;
    }

    public void searchMovies(OnSearchApiListener listener, String movie_name) {
        getMovies getMovies = retrofit.create(RequestManager.getMovies.class);
        Call<SearchApiResponse> call = getMovies.callMovies(movie_name);

        call.enqueue(new Callback<SearchApiResponse>() {
            @Override
            public void onResponse(Call<SearchApiResponse> call, Response<SearchApiResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Couldn't fetch Data.", Toast.LENGTH_SHORT);
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<SearchApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void searchMovieDetails(OnDetailsApiListener listener, String movie_ID) {
        getMovieDetail getMovieDetail = retrofit.create(RequestManager.getMovieDetail.class);
        Call<DetailApiResponse> call = getMovieDetail.callMovieDetail(movie_ID);

        call.enqueue(new Callback<DetailApiResponse>() {
            @Override
            public void onResponse(Call<DetailApiResponse> call, Response<DetailApiResponse> D_response) {
                if (!D_response.isSuccessful()) {
                    Toast.makeText(context, "Couldn't fetch Data.", Toast.LENGTH_SHORT);
                    return;
                } else {

                    responses.add(D_response.body());

                    searchMovieTrailer(listener, movie_ID);
                }
            }

            @Override
            public void onFailure(Call<DetailApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void searchMovieTrailer(OnDetailsApiListener listener, String movie_ID) {
        getMovieTrailer getMovieTrailer = retrofit.create(RequestManager.getMovieTrailer.class);
        Call<TrailerApiResponse> call = getMovieTrailer.callMovieTrailer(movie_ID);

        call.enqueue(new Callback<TrailerApiResponse>() {
            @Override
            public void onResponse(Call<TrailerApiResponse> call, Response<TrailerApiResponse> T_response) {
                if (!T_response.isSuccessful()) {
                    Toast.makeText(context, "Couldn't fetch Data.", Toast.LENGTH_SHORT);
                    return;
                } else {
                    responses.add(T_response.body());
                    searchMovieActor(listener, movie_ID);
                }
            }

            @Override
            public void onFailure(Call<TrailerApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void searchMovieActor(OnDetailsApiListener listener, String movie_ID) {
        getMovieActor getMovieActor = retrofit.create(RequestManager.getMovieActor.class);
        Call<List<String>> call = getMovieActor.callMovieActor(movie_ID);

        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> A_response) {
                if (!A_response.isSuccessful()) {
                    Toast.makeText(context, "Couldn't fetch Data.", Toast.LENGTH_SHORT);
                    return;
                } else {
                    responses.add(A_response.body());

                   // searchMovieCast(listener, A_response, movie_ID);

                    List<String> response_id = A_response.body();
                    List<String> extractedValues = new ArrayList<>();

                    if (actor_count > response_id.size()) {
                        actor_count = response_id.size();
                    }

                    int count = 0;
                    for (String actor : response_id) {
                        if (count == actor_count) {
                            break;
                        }
                        String Actor_id = actor.split("/")[2];
                        extractedValues.add(Actor_id);
                        searchMovieCast2(listener, Actor_id, movie_ID);
                        count++;
                    }

                    //responses.add(Cast_responses);
                    //listener.onResponse(responses);
                    //searchMovieCast(listener, extractedValues, movie_ID);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void searchMovieCast2(OnDetailsApiListener listener, String Actor_id, String movie_ID) {


        getMovieCast getMovieCast = retrofit.create(RequestManager.getMovieCast.class);
        Call<ResponseBody> call = getMovieCast.callMovieCast(Actor_id, movie_ID);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> C_response) {
                if (!C_response.isSuccessful()) {
                    Toast.makeText(context, "Couldn't fetch Data.", Toast.LENGTH_SHORT);
                    return;
                } else {
                    String responseString = null;
                    try {
                        responseString = C_response.body().string();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    JsonObject jsonObject = new Gson().fromJson(responseString, JsonObject.class);
                    Cast_responses.add(jsonObject);

                    if (Cast_responses.size() == actor_count) {  // change to n times
                        responses.add(Cast_responses);
                        listener.onResponse(responses);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });

        //responses.add(responseList);
        //listener.onResponse(C_response.body());

    }

//    public void searchMovieCast(OnDetailsApiListener listener, List<String> Actor_ids, String movie_ID) {
//
//
//        List<JSONObject> responseList = new ArrayList<>();
//
//        int actor_num = 5;
//        if (Actor_ids.size() < 5) {
//            actor_num = Actor_ids.size();
//        }
//
//        getMovieCast getMovieCast = retrofit.create(RequestManager.getMovieCast.class);
//
//        for (int i = 0; i < actor_num; i++) {
//            String id = Actor_ids.get(i);
//            Call<JSONObject> call = getMovieCast.callMovieCast(Actor_ids.get(i), movie_ID);
//
//            call.enqueue(new Callback<JSONObject>() {
//                @Override
//                public void onResponse(Call<JSONObject> call, Response<JSONObject> C_response) {
//                    if (!C_response.isSuccessful()) {
//                        Toast.makeText(context, "Couldn't fetch Data.", Toast.LENGTH_SHORT);
//                        return;
//                    }
//                    responseList.add(C_response.body());
//                }
//
//                @Override
//                public void onFailure(Call<JSONObject> call, Throwable t) {
//                    listener.onError(t.getMessage());
//                }
//            });
//        }
//        responses.add(responseList);
//
//        listener.onResponse(responses);
//
//
//    }

    public interface getMovies {
        @Headers({
                "Accept: application/json",
                "X-RapidAPI-Host: imdb8.p.rapidapi.com",
                "X-RapidAPI-Key: 443432aa27msh0916dc69cf7323cp15a4f6jsn638dc4405ac4"
        })
        @GET("title/find")
        Call<SearchApiResponse> callMovies(
                @Query("q") String movie_name
        );
    }

    public interface getMovieDetail {
        @Headers({
                "Accept: application/json",
                "X-RapidAPI-Host: imdb8.p.rapidapi.com",
                "X-RapidAPI-Key: 443432aa27msh0916dc69cf7323cp15a4f6jsn638dc4405ac4"
        })
        @GET("title/get-overview-details")
        Call<DetailApiResponse> callMovieDetail(
                @Query("tconst") String movie_ID
        );
    }

    public interface getMovieTrailer {
        @Headers({
                "Accept: application/json",
                "X-RapidAPI-Host: imdb8.p.rapidapi.com",
                "X-RapidAPI-Key: 443432aa27msh0916dc69cf7323cp15a4f6jsn638dc4405ac4"
        })
        @GET("title/get-videos")
        Call<TrailerApiResponse> callMovieTrailer(
                @Query("tconst") String movie_ID
        );
    }

    public interface getMovieActor {
        @Headers({
                "Accept: application/json",
                "X-RapidAPI-Host: imdb8.p.rapidapi.com",
                "X-RapidAPI-Key: 443432aa27msh0916dc69cf7323cp15a4f6jsn638dc4405ac4"
        })
        @GET("title/get-top-cast")
        Call<List<String>> callMovieActor(
                @Query("tconst") String movie_ID
        );
    }

    public interface getMovieCast {
        @Headers({
                "Accept: application/json",
                "X-RapidAPI-Host: imdb8.p.rapidapi.com",
                "X-RapidAPI-Key: 443432aa27msh0916dc69cf7323cp15a4f6jsn638dc4405ac4"
        })
        @GET("title/get-charname-list")
        Call<ResponseBody> callMovieCast(
                @Query("id") String Actor_id,
                @Query("tconst") String movie_ID
        );
    }

}

//tt4154796     nm0000375
/*
get-details:
 - image, title, runtime, year released

get-top-cast
 - actor ids

get-overview-detail
 - image, title, runtime, year released, rating & count, genres, plot outline & plot summary

get-charname-list
 - actor image, name
get-meta-data
 - title, image, ratings, year released, runtime, genre, roles

get-bio
 - name, image, birth, gender, height,

get-filmography-appearances
 - character/role array, image object


 */