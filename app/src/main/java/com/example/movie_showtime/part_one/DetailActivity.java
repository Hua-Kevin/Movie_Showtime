package com.example.movie_showtime.part_one;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie_showtime.R;
import com.example.movie_showtime.Adapters.CastRecyclerAdapter;
import com.example.movie_showtime.Listeners.OnDetailsApiListener;
import com.example.movie_showtime.Models.CastApiResponse;
import com.example.movie_showtime.Models.DetailApiResponse;
import com.example.movie_showtime.Models.TrailerApiResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    TextView textView_movieName,textView_movieReleased,textView_movieRunTime,textView_movieRating,textView_movieVotes, textView_moviePlot;
    ImageView imageView_moviePoster;
    RecyclerView recycler_movieCast;
    CastRecyclerAdapter adapter;
    RequestManager manager;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textView_movieName = findViewById(R.id.textView_movieName);
        textView_movieReleased = findViewById(R.id.textView_movieReleased);
        textView_movieRunTime = findViewById(R.id.textView_movieRunTime);
        textView_movieRating = findViewById(R.id.textView_movieRating);
        textView_movieVotes = findViewById(R.id.textView_movieVotes);
        imageView_moviePoster = findViewById(R.id.imageView_moviePoster);
        textView_moviePlot = findViewById(R.id.textView_moviePlot);
        recycler_movieCast = findViewById(R.id.recycler_movieCast);

        manager = new RequestManager(this);

        String movie_ID = getIntent().getStringExtra("data").split("/")[2];

        dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait...");
        dialog.show();

        manager.searchMovieDetails(listener, movie_ID);


    }

    private OnDetailsApiListener listener = new OnDetailsApiListener() {
        @Override
        //public void onResponse(DetailApiResponse response) {
        public void onResponse(List response) {
            dialog.dismiss();
            if (response.equals(null)) {
                Toast.makeText(DetailActivity.this, "Error!!!", Toast.LENGTH_SHORT).show();
                return;
            }
            showResults(response);
        }

        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(DetailActivity.this, "Error!!", Toast.LENGTH_SHORT).show();
        }
    };

    private void showResults(List<Object> response) {
        System.out.println("HI");
        DetailApiResponse res1 = (DetailApiResponse) response.get(0);
        TrailerApiResponse res2 = (TrailerApiResponse) response.get(1);
        List res3 = (List) response.get(2);
        List res4 = (List) response.get(3);


        textView_movieName.setText(res1.getTitle().getTitle());
        textView_movieReleased.setText("Year Released: " + res1.getTitle().getYear());
        textView_movieRunTime.setText("Length" + res1.getTitle().getRunningTimeInMinutes());
        textView_movieRating.setText("Rating: " + res1.getRatings().getRating());
        textView_movieVotes.setText(res1.getRatings().getRatingCount() + " Votes");
        textView_moviePlot.setText(res1.getPlotSummary().getText());

        try {
            Picasso.get().load(res1.getTitle().getImageModel().getUrl()).into(imageView_moviePoster);
        } catch (Exception e) {
            e.printStackTrace();
        }

        recycler_movieCast.setHasFixedSize(true);
        recycler_movieCast.setLayoutManager(new GridLayoutManager(this, 1));
        //adapter = new CastRecyclerAdapter(this, response.getCast());



        List<CastApiResponse> lisst = convertJsonToCastApiResponse(res4);

        adapter = new CastRecyclerAdapter(this, lisst);
        recycler_movieCast.setAdapter(adapter);

        // List<Cast> only has actor, actor id and character role. 3 String values
    }

    public List<CastApiResponse> convertJsonToCastApiResponse(List<JsonObject> jsonObjects) {
        List<CastApiResponse> castApiResponses = new ArrayList<>();

        for (JsonObject jsonObject : jsonObjects) {
            CastApiResponse castApiResponse = new CastApiResponse();

            // Iterate over the properties of the root JsonObject
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                JsonObject nmObject = entry.getValue().getAsJsonObject();

                JsonObject actorObject = nmObject.getAsJsonObject("name");
                String actor = "";
                String actorId = "";
                if (actorObject != null) {
                    actor = actorObject.get("name").getAsString();
                    actorId = actorObject.get("id").getAsString().split("/")[2];
                }

                JsonArray charactersArray = nmObject.getAsJsonArray("charname");
                JsonObject characterObject = charactersArray.get(0).getAsJsonObject();
                JsonArray characters = characterObject.getAsJsonArray("characters");

                /*StringBuilder characterBuilder = new StringBuilder();

                for (JsonElement characterElement : characters) {
                    String character = characterElement.getAsString();
                    characterBuilder.append(character).append(", ");
                }
                String character = characterBuilder.toString();
                // Remove the trailing ", " from the character string
                character = character.substring(0, character.length() - 2);*/

                String character = characters.get(0).getAsString();

                castApiResponse.setActor(actor);
                castApiResponse.setActor_id(actorId);
                castApiResponse.setCharacter(character);

                castApiResponses.add(castApiResponse);
                break;  // Exit the loop once a valid entry is found

            }
        }
        return castApiResponses;

        /*
        for (JsonObject jsonObject : jsonObjects) {
            CastApiResponse castApiResponse = new CastApiResponse();




            JsonObject nameObject = jsonObject.getAsJsonObject("name");
            if (nameObject != null) {
                // Extract actor name
                String actorName = nameObject.get("name").getAsString();
                castApiResponse.setActor(actorName);

                // Extract actor ID
                String actorId = nameObject.get("actor_id").getAsString();
                castApiResponse.setActor_id(actorId);
            }



            // Extract character names
            JsonArray charNameArray = jsonObject.getAsJsonArray("charname");
            if (charNameArray != null && charNameArray.size() > 0) {
                JsonObject charNameObject = charNameArray.get(0).getAsJsonObject();
                if (charNameObject != null) {
                    JsonArray charactersArray = charNameObject.getAsJsonArray("characters");
                    if (charactersArray != null && charactersArray.size() > 0) {
                        StringBuilder characterNames = new StringBuilder();
                        for (JsonElement element : charactersArray) {
                            String character = element.getAsString();
                            characterNames.append(character).append(", ");
                        }
                        characterNames.setLength(characterNames.length() - 2);  // Remove the last comma and space
                        castApiResponse.setCharacter(characterNames.toString());
                    }
                }
            }

            castApiResponses.add(castApiResponse);
        }

        return castApiResponses;*/
    }

}