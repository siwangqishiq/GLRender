package com.xinlan.glrender;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    CustomView customView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customView = findViewById(R.id.unlock);


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                customView.refreshView();
//            }
//        }, 1000);

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        customView.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        customView.onPause();
//    }
}
