package com.painlessshopping.mohamed.findit;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Sam 05/01/2017
 */

public class LangSettings extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ListView mDrawerList;
    private String[] mPlanetTitles;

    static String appCurrentLanguage = "en";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(ThemeHandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lang_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Locale locale = new Locale(appCurrentLanguage);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_lang_settings);

        Button enLangButton = (Button)findViewById(R.id.buttonEn);
        Button frLangButton = (Button)findViewById(R.id.buttonFr);
        Button jaLangButton = (Button)findViewById(R.id.buttonJa);
        Button zhLangButton = (Button)findViewById(R.id.buttonZh);

        enLangButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                appCurrentLanguage = "en";

                Locale locale = new Locale(appCurrentLanguage);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                setContentView(R.layout.activity_lang_settings);
            }
        });

        frLangButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                appCurrentLanguage = "fr";

                Locale locale = new Locale(appCurrentLanguage);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                setContentView(R.layout.activity_lang_settings);
            }
        });

        jaLangButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                appCurrentLanguage = "ja";

                Locale locale = new Locale(appCurrentLanguage);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                setContentView(R.layout.activity_lang_settings);
            }
        });

        zhLangButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                appCurrentLanguage = "zh";

                Locale locale = new Locale(appCurrentLanguage);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                setContentView(R.layout.activity_lang_settings);
            }
        });






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));





    }//End of OnCreate**************************************

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_search) {
            startActivity(new Intent(LangSettings.this, HomeScreen.class));

        }  else if (id == R.id.nav_my_cart) {
            startActivity(new Intent(LangSettings.this, MyCart.class));

        } else if (id == R.id.nav_featured_stores) {
            startActivity(new Intent(LangSettings.this, FeaturedScreen.class));

        } else if (id == R.id.nav_language_settings) {
            startActivity(new Intent(LangSettings.this, LangSettings.class));

        } else if (id == R.id.nav_location_settings) {
            startActivity(new Intent(LangSettings.this, MapsActivity.class));

        } else if (id == R.id.nav_display) {
            startActivity(new Intent(LangSettings.this, Display.class));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}//End of Class
