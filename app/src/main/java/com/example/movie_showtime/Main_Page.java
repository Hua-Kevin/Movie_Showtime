package com.example.movie_showtime;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;


import android.content.pm.ApplicationInfo;

public class Main_Page extends AppCompatActivity {

    private Button b1, b2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


        ApplicationInfo ai = null;
        try {
            ai = getPackageManager().getApplicationInfo(getPackageName(),
                    PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        Bundle metaData = ai.metaData;

        //metaData.getString("key");  // Returns -36235050
        //Toast.makeText(getApplicationContext(), "key:" + metaData.getString("rapid_api"), Toast.LENGTH_LONG).show();








        b1 = findViewById(R.id.SearchMButton);
        b1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            Intent intent = new Intent(Main_Page.this, MainActivity_movieSearch.class);
                            startActivity(intent);
                    }
                }
        );



        b2 = findViewById(R.id.Upcoming_Button);
        b2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            Intent intent = new Intent(Main_Page.this, MainActivity.class);
                            startActivity(intent);
                    }
                }
        );
    }



}