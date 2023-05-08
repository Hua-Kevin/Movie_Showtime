package com.example.movie_showtime;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity3 extends AppCompatActivity {

    private Button b1, b2;

    EditText theatreInput;

    String theatre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        theatreInput = (EditText) findViewById(R.id.TheatreInput);


        b1 = findViewById(R.id.next_button);
        b1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        theatre = theatreInput.getText().toString();

                        if (theatre.matches("")) {
                            Toast.makeText(MainActivity3.this, "You did not input anything for theatre name", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            theatre = theatre.replace(" ", "+");

                            Intent intent = getIntent();


                            Intent next_intent = new Intent(MainActivity3.this, MainActivity4.class);
                            next_intent.putExtra("location", intent.getStringExtra("location"));
                            next_intent.putExtra("theatre", theatre);
                            startActivity(next_intent);
                        }
                    }
                }
        );



        b2 = findViewById(R.id.back_button);
        b2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }
//    public void openActivity2() {
//        Intent i = new Intent(MainActivity.this,MainActivity2.class);
//        startActivity(i);
//    }


}