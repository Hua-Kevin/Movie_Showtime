package com.example.movie_showtime;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class Main_Page extends AppCompatActivity {

    private Button b1, b2;

    EditText locationInput;

    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        b1 = findViewById(R.id.SearchMButton);
        b1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            Intent intent = new Intent(Main_Page.this, Upcoming_Page1.class);
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