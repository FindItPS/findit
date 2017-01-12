package com.painlessshopping.mohamed.findit;

//Imports libraries

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;

/**
 * "Loading Screen" for the app
 *
 * Created by samuel on 22/11/16.
 */
public class OpeningScreen extends AppCompatActivity {


    private boolean firstLaunch;
    //Creates activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        boolean firstLaunch = preferences.getBoolean("firstlaunch", true);

        System.out.println("FIRST LANUNCH? " + firstLaunch);

        if(firstLaunch == true){
            SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
            editor.putString("language", "en");
            editor.putInt("theme", R.style.Default);
            editor.putBoolean("firstLaunch", false);
            editor.commit();
            System.out.println(preferences.getBoolean("firstLaunch", true));
        }


        LanguageHandler.setLang(preferences.getString("language", "en"));
        ThemeHandler.setTheme(preferences.getInt("theme", R.style.Default));


        setTheme(ThemeHandler.getTheme());
        super.onCreate(savedInstanceState);

        Locale locale = new Locale(LanguageHandler.getLang());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,
                getResources().getDisplayMetrics());

        setContentView(R.layout.activity_opening_screen);



        //Set a 3s timer for the screen
        new CountDownTimer(3000, 1000){

            //Necessary method
            //Reports remaining time in console
            public void onTick(long millisUntilFinished){

                System.out.println("HomeScreen timer second remaining:" + millisUntilFinished / 1000);
            }

            //Once finished, open the home screen
            public void onFinish(){

                startActivity(new Intent(OpeningScreen.this, HomeScreen.class));
            }
        }.start();

    }
}
