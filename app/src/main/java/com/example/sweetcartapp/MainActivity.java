package com.example.sweetcartapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sweetcartapp.ShoppersRoom.ShoppersStop;


public class MainActivity extends AppCompatActivity {

    public final String ISLOGIN = "islogin";
    public final String LOGIN_STATS = "loginStats";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            sharedPreferences = getSharedPreferences(LOGIN_STATS, Context.MODE_PRIVATE);
            Boolean statusLogin = sharedPreferences.getBoolean(ISLOGIN, false);
            if (statusLogin) {
                //
                startWelcomePageHere();
            }
        } catch (Exception e) {

        }


    }

    private void startWelcomePageHere() {
        Intent i = new Intent(this, ShoppersStop.class);
        startActivity(i);
        finish();
    }

    public void starthere(View view) {
        Intent intent = new Intent(this, PhoneAuthActivity.class);
        startActivity(intent);
    }
}