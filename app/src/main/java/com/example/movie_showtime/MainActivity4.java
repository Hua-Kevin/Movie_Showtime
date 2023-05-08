package com.example.movie_showtime;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity4 extends AppCompatActivity {

    //String url = "https://jsonplaceholder.typicode.com/photos";

    private Button button;

    String getLoc, getTheatre;

    private String api_key = "7ed203f759bf7655ac75628f3dc19e2da784eab745ea3b186ce8206da84721b2";

    private String url, url2;
    //String url = "https://serpapi.com/search.json?q=theater&location=Toronto,+Ontario,+Canada&hl=en&gl=us&api_key=" + api_key;
    //private String url2 = "https://serpapi.com/search.json?q=theater&location=Toronto,+Ontario,+Canada&hl=en&gl=us&api_key=" + api_key;

    //https://serpapi.com/search.json?q=movies+Imagine+Cinemas+Market+Square&location=Toronto,+Ontario,+Canada&hl=en&gl=us

    TextView textbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        TextView TheatreView = (TextView)findViewById(R.id.textView5);


        Intent intent = getIntent();
        getLoc = intent.getStringExtra("location");
        getTheatre = intent.getStringExtra("theatre");

        TheatreView.setText("Theatre:\n" + getTheatre.replace("+", " "));

        url = "https://serpapi.com/search.json?q=movies+"+getTheatre+"&location="+getLoc+"&hl=en&gl=us&api_key=" + api_key;

        button = findViewById(R.id.button3);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

        Button refresh = (Button) findViewById(R.id.btn_refresh);

        //Listening to button event
        refresh.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                getData();


            }
        });



        getData();

    }



    private void getData() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                TextView textView = (TextView)findViewById(R.id.textView6);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                textView.setText("");
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray showtimesArray;
                    JSONObject showtimesObject;
                    JSONArray moviesArray = null;
                    boolean Was_Showtimes = false;

                    if (jsonResponse.has("showtimes")) {

                        showtimesArray = jsonResponse.getJSONArray("showtimes");
                        showtimesObject = showtimesArray.getJSONObject(0);       //get Today's movies
                        moviesArray = showtimesObject.getJSONArray("movies");
                        Was_Showtimes = true;

                    } else if (jsonResponse.has("knowledge_graph")) {
                        int desired_pos = 1;


                        // Get the "knowledge_graph" object
                        JSONObject knowledgeGraphObject = jsonResponse.getJSONObject("knowledge_graph");

                        // Get the names of all the properties in the "knowledge_graph" object
                        Iterator<String> keys = knowledgeGraphObject.keys();

                        // Find the array based on its position
                        moviesArray = null;

                        int currentIndex = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();

                            // Check if the current property is an array
                            if (knowledgeGraphObject.get(key) instanceof JSONArray) {
                                // Check if it's the desired position
                                if (currentIndex == desired_pos) {
                                    moviesArray = knowledgeGraphObject.getJSONArray(key);
                                    break;
                                }
                                currentIndex++;
                            }
                        }
                    }
                    // Process the moviesArray if not found
                    if (moviesArray == null) {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();//display the response on screen
                        finish();
                    }

                    for (int j=0; j<3;j++) {            //get Movie for theatre


                        JSONObject movie = moviesArray.getJSONObject(j);

                        String name = movie.getString("name").toString();
                        JSONArray extensionArray = movie.getJSONArray("extensions");
                        String fixed_name = removeWords(extensionArray, name);

                        String link = movie.getString("link").toString();

                        if (Was_Showtimes) {
                            JSONArray timeArray = movie.getJSONArray("time");
                            String times = JsonToString(timeArray);

                            //textView.setText(movie.getString("id").toString());

                            String text = String.format("%s<br>%s<br><a href=\"%s\">link</a><br><br>", fixed_name, times, movie.getString("link"));
                            textView.append(Html.fromHtml(text, 0));
                        } else {
                            String text = String.format("%s<br><a href=\"%s\">link</a><br><br>", fixed_name, movie.getString("link"));
                            textView.append(Html.fromHtml(text, 0));
                        }
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


                //textView.setText();
                //Toast.makeText(getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error :" + error.toString());
            }
        });

        queue.add(stringRequest);
    }

    public String JsonToString(JSONArray array) {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < array.length(); i++) {
            String time = array.optString(i);
            stringBuilder.append("\"").append(time).append("\"");

            if (i < array.length() - 1) {
                stringBuilder.append(", ");
            }
        }

        String timeString = stringBuilder.toString();
        return timeString;
    }

    public SpannableString Colorized(String text) {
        SpannableString spannableString = new SpannableString(text);

        String[] words = text.split(" ");
        for (String word : words) {
            int startIndex = text.indexOf(word);
            int endIndex = startIndex + word.length();
            BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.YELLOW);
            spannableString.setSpan(backgroundColorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannableString;
    }


    public String removeWords(JSONArray jsonArray, String input ) {
        //remove all elements found in the jsonArray
        for (int i = 0; i < jsonArray.length(); i++) {
            String element = null;
            try {
                element = jsonArray.getString(i);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            input = input.replace(element, "");
        }

        //remove all age ratings
        //Unneeded: Age ratings already in the JSONArray above if there is any
        /*String[] movieRatings = {"PG-13", "PG", "G", "R", "NC-17"};
        for (int i = 0; i < movieRatings.length; i++) {
            String element = movieRatings[i];
            input = input.replace(element, "");
        }*/


        //remove all characters after the first occurrence of an uppercase letter after a non-space character
        //(Removed) Reason: Depending on the title, the movie title will get cut off instead.
        /*String regex = "[^ ][A-Z].*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        int index = -1;
        if (matcher.find()) {
            index = matcher.start();
        }

        if (index == -1) {
            return input;
        } else {
            return input.substring(0, index+1);
        }*/
        return input;

    }


}