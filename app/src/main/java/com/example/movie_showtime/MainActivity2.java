package com.example.movie_showtime;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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

public class MainActivity2 extends AppCompatActivity {

    //String url = "https://jsonplaceholder.typicode.com/photos";

    private Button button;
    String getLoc;

    private String api_key = "7ed203f759bf7655ac75628f3dc19e2da784eab745ea3b186ce8206da84721b2";

    private RequestQueue queue;

    private String url, url2;

    TextView textbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        getLoc = intent.getStringExtra("location");

        url = "https://serpapi.com/search.json?q=theater&location="+getLoc+"&hl=en&gl=us&api_key=" + api_key;

        button = findViewById(R.id.button);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );


        Button refresh = (Button) findViewById(R.id.Act2_refresh);

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
        queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                //TextView textView = (TextView)findViewById(R.id.textView2);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONObject localResults  = jsonResponse.getJSONObject("local_results");
                    JSONArray placesArray = localResults.getJSONArray("places");

                    for (int i=0; i<3;i++) {            //get Theatre

                        JSONObject placeObject = placesArray.getJSONObject(i);

                        String place = placeObject.getString("title").toString();


                        String resourceName = "T" + i;
                        int resourceId = getResources().getIdentifier(resourceName, "id", getPackageName());
                        TextView editText = findViewById(resourceId);


                        editText.setText(place);



                        url2 = "https://serpapi.com/search.json?q=movies+"+place+"&location="+getLoc+"&hl=en&gl=us&api_key=" + api_key;
                        sendSecondRequest(url2, i);
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

    private void sendSecondRequest(String secondUrl, int row) {
        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, secondUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //TextView textView = (TextView)findViewById(R.id.textView2);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray showtimesArray;
                    JSONObject showtimesObject;
                    JSONArray moviesArray = null;

                    if (jsonResponse.has("showtimes")) {

                        showtimesArray = jsonResponse.getJSONArray("showtimes");
                        showtimesObject = showtimesArray.getJSONObject(0);       //get Today's movies
                        moviesArray = showtimesObject.getJSONArray("movies");

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
                    int j = 0;
                    for (j = 0; j < 3; j++) {            //get Movie for theatre
                        String resourceName = "M" + String.valueOf(row) + String.format("%01d", j);
                        int resourceId = getResources().getIdentifier(resourceName, "id", getPackageName());
                        TextView editText = findViewById(resourceId);
                        JSONObject movie = moviesArray.getJSONObject(j);

                        String movie_name = movie.getString("name").toString();
                        String movie_link = movie.getString("link").toString();


                        //editText.setText("HA");
                        editText.setText(movie_name);

                        String resourceName2 = "L" + String.valueOf(row) + String.format("%01d", j);
                        int resourceId2 = getResources().getIdentifier(resourceName2, "id", getPackageName());
                        TextView editText2 = findViewById(resourceId2);
                        editText2.setMovementMethod(LinkMovementMethod.getInstance());

                        String text = String.format("<a href=\"%s\">link</a>", movie_link);
                        //editText2.setText("YOO");
                        editText2.setText(Html.fromHtml(text, 0));

                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error :" + error.toString());
            }
        });

        queue.add(stringRequest);
    }

}