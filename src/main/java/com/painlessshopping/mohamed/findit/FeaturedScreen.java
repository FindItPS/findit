package com.painlessshopping.mohamed.findit;

/**
 * Created by samuel on 8/12/16.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appdatasearch.Feature;

public class FeaturedScreen extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_featured_screen);

    GridView gridview = (GridView) findViewById(R.id.gridview);
    gridview.setAdapter(new FeaturedStoresAdapter(this));

    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v,
        int position, long id) {
            Toast.makeText(FeaturedScreen.this, "" + position,
                    Toast.LENGTH_SHORT).show();
        }
    });
}


}
