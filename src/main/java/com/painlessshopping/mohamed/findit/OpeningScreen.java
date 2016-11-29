package com.painlessshopping.mohamed.findit;

/**
 * Created by samuel on 22/11/16.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OpeningScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_screen);

        Button loginBtn = (Button)findViewById(R.id.login_button);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OpeningScreen.this, LoginActivity.class));
            }
        });

        Button createAccountBtn = (Button)findViewById(R.id.create_account_button);

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OpeningScreen.this, NewAccountActivity.class));

            }
        });
    }
}
