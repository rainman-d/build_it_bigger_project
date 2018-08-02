package com.drainey.lib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {
    private static final String DEFAULT_JOKE = "Why did the chicken cross the road?\nTo get to the other side!";
    private TextView mJokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        mJokeTextView = (TextView)findViewById(R.id.tv_joke_text);
        String joke = DEFAULT_JOKE;
        if(getIntent().hasExtra(Intent.EXTRA_TEXT)){
            joke = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        }
        mJokeTextView.setText(joke);
    }
}
