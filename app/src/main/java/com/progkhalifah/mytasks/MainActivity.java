package com.progkhalifah.mytasks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isFirstTime();
            }
        }, 1500);


    }// TODO: 10/13/2021 end of onCreate

    private void isFirstTime() {
        //for checking if the app is running for the first time
        //we need to save the value to shared preference
        SharedPreferences preferences = getApplication().getSharedPreferences("onBoard", Context.MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean("isFirstTime",true);
        //default value true
        if (isFirstTime){
            //if its true then its first time and we will change it false
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstTime", false);
            editor.apply();

            //start onBoard Activity
            startActivity(new Intent(MainActivity.this, OnBoardActivity.class));
            finish();
        }else {
            startActivity(new Intent(MainActivity.this, HomePage.class));
            finish();
        }

    }


}// TODO: 10/13/2021 end of all