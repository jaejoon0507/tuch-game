package com.hosun0507.touchgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class secondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

    }
    public void startbutto(View view){
        Intent intent = new Intent(secondActivity.this, MainActivity.class);
        startActivity(intent);
    }
}

