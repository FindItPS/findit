package com.painlessshopping.mohamed.findit;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.Locale;

/**
 * Runs language settings screen and allows for languages to be changed
 *
 * Created by Sam 05/01/2017
 */

public class LangSettings extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ListView mDrawerList;
    private String[] mPlanetTitles;

    //Sets default language, changed when user presses for a different one
    static String appCurrentLanguage = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(ThemeHandler.getTheme());
        super.onCreate(savedInstanceState);

        //Sets language
        Locale locale = new Locale(appCurrentLanguage);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,
                getResources().getDisplayMetrics());
        setContentView(R.layout.activity_lang_settings);

        //Initializes toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Creates buttons
        Button enLangButton = (Button)findViewById(R.id.buttonEn);
        Button frLangButton = (Button)findViewById(R.id.buttonFr);
        Button jaLangButton = (Button)findViewById(R.id.buttonJa);
        Button zhLangButton = (Button)findViewById(R.id.buttonZh);

        //Changes language if button is pressed
        enLangButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                appCurrentLanguage = "en";

                LanguageHandler.setLang(appCurrentLanguage);
                Locale locale = new Locale(appCurrentLanguage);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getResources().updateConfiguration(config,
                        getResources().getDisplayMetrics());

                SharedPreferences.Editor editor = getSharedPreferences(OpeningScreen.KEY_PREFS_NAME , MODE_PRIVATE).edit();
                editor.putString("language", appCurrentLanguage);
                editor.commit();
                startActivity(new Intent(LangSettings.this, LangSettings.class));
            }
        });

        //Changes language if button is pressed
        frLangButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                appCurrentLanguage = "fr";

                LanguageHandler.setLang(appCurrentLanguage);
                Locale locale = new Locale(appCurrentLanguage);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getResources().updateConfiguration(config,
                        getResources().getDisplayMetrics());

                SharedPreferences.Editor editor = getSharedPreferences(OpeningScreen.KEY_PREFS_NAME , MODE_PRIVATE).edit();
                editor.putString("language", appCurrentLanguage);
                editor.commit();
                startActivity(new Intent(LangSettings.this, LangSettings.class));
            }
        });

        //Changes language if button is pressed
        jaLangButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                appCurrentLanguage = "ja";
                LanguageHandler.setLang(appCurrentLanguage);

                Locale locale = new Locale(appCurrentLanguage);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getResources().updateConfiguration(config,
                        getResources().getDisplayMetrics());

                SharedPreferences.Editor editor = getSharedPreferences(OpeningScreen.KEY_PREFS_NAME , MODE_PRIVATE).edit();
                editor.putString("language", appCurrentLanguage);
                editor.commit();
                startActivity(new Intent(LangSettings.this, LangSettings.class));
            }
        });

        //Changes language if button is pressed
        zhLangButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                appCurrentLanguage = "zh";
                LanguageHandler.setLang(appCurrentLanguage);

                Locale locale = new Locale(appCurrentLanguage);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getResources().updateConfiguration(config,
                        getResources().getDisplayMetrics());

                SharedPreferences.Editor editor = getSharedPreferences(OpeningScreen.KEY_PREFS_NAME , MODE_PRIVATE).edit();
                editor.putString("language", appCurrentLanguage);
                editor.commit();
                startActivity(new Intent(LangSettings.this, LangSettings.class));
            }
        });

        //Navigation Bar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Sets Toolbar Text
        setTitle(getString(R.string.lang_name));


    }//End of OnCreate**************************************

    //Controls back button presses
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


    /**
     * Controls navigation bar button reactions
     *
     * @param item Selected item on the navigation bar
     */

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

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}//End of Class
