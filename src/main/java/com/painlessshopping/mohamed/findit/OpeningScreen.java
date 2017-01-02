package com.painlessshopping.mohamed.findit;

/**
 * Created by samuel on 22/11/16.
 */

//Imports libraries
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OpeningScreen extends AppCompatActivity {

    
    //Creates activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_screen);

        //Set a timer for the screen
        new CountDownTimer(3000, 1000){

            public void onTick(long millisUntilFinished){

                System.out.println("HomeScreen timer second remaining:" + millisUntilFinished / 1000);
            }

            public void onFinish(){

                startActivity(new Intent(OpeningScreen.this, HomeScreen.class));
            }
        }.start();

        //Intialize the continue button NOT CURRENTLY USED
        /*Button continuebutton = (Button) findViewById(R.id.continue_button);


        //Define the continue button and what it does
        continuebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OpeningScreen.this, HomeScreen.class));
            }
        });*/


    }

}
