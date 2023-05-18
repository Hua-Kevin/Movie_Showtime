package com.example.movie_showtime;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button b1, b2;

    EditText locationInput;

    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationInput = (EditText) findViewById(R.id.locationInput);


        b1 = findViewById(R.id.FT_button);
        b1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        location = locationInput.getText().toString();

                        if (location.matches("")) {
                            Toast.makeText(MainActivity.this, "You did not input anything for location", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            location = location.replace(" ", "+");

                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                            intent.putExtra("location", location);
                            startActivity(intent);
                        }
                    }
                }
        );



        b2 = findViewById(R.id.FM_button);
        b2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        location = locationInput.getText().toString();

                        if (location.matches("")) {
                            Toast.makeText(MainActivity.this, "You did not input anything for location", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            location = location.replace(" ", "+");

                            Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                            intent.putExtra("location", location);
                            startActivity(intent);
                        }
                    }
                }
        );
    }
//    public void openActivity2() {
//        Intent i = new Intent(MainActivity.this,MainActivity2.class);
//        startActivity(i);
//    }


}