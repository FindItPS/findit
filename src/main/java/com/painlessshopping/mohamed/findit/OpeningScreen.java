package com.painlessshopping.mohamed.findit;

//Imports libraries

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

/**
 * "Loading Screen" for the app
 *
 * Created by samuel on 22/11/16.
 */
public class OpeningScreen extends AppCompatActivity {

    
    //Creates activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(ThemeHandler.getTheme());
        super.onCreate(savedInstanceState);
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
