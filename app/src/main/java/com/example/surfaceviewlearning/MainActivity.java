package com.example.surfaceviewlearning;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;
    SmileyEmojiView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        view = findViewById(R.id.smiley_view);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                view.startAnimation();
            }
        });

    }
}