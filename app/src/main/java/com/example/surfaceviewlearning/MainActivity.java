package com.example.surfaceviewlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
                button.setEnabled(false);
                view.startAnimation();
            }
        });
        view.registerAnimationListener(new SmileyEmojiView.AnimationListener() {
            @Override
            public void onAnimationEnd() {
                button.setEnabled(true);
            }
        }, new Handler(Looper.getMainLooper()));
    }
}