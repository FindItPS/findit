package com.painlessshopping.mohamed.findit;

/**
 * Created by samuel on 22/11/16.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        Button createAccountBtn = (Button)findViewById(R.id.email_new_account_form_button);

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(NewAccountActivity.this, LoginActivity.class));
            }
        });
    }
}
