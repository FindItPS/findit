package com.painlessshopping.mohamed.findit;

/**
 * Controls the Featured Stores screen.
 *
 * Created by samuel on 8/12/16.
 */

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.painlessshopping.mohamed.findit.viewmodel.FeaturedStoresAdapter;
import com.painlessshopping.mohamed.findit.viewmodel.LanguageHandler;
import com.painlessshopping.mohamed.findit.viewmodel.ThemeHandler;

import android.widget.GridView;

import java.util.Locale;


public class FeaturedScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener   {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(ThemeHandler.getTheme());
    super.onCreate(savedInstanceState);

        //Sets languages
        Locale locale = new Locale(LanguageHandler.getLang());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,
                getResources().getDisplayMetrics());
        setContentView(R.layout.activity_featured_screen);

        //Initializes toolbar at the top of the screen view
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Navigation Bar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Creates GridView of images that contain the stores
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new FeaturedStoresAdapter(this));

        setTitle(getString(R.string.ft_name));
}

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
        android.app.FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_search) {
            startActivity(new Intent(FeaturedScreen.this, HomeScreen.class));

        }  else if (id == R.id.nav_my_cart) {
            startActivity(new Intent(FeaturedScreen.this, MyCart.class));

        } else if (id == R.id.nav_featured_stores) {
            startActivity(new Intent(FeaturedScreen.this, FeaturedScreen.class));

        } else if (id == R.id.nav_language_settings) {
            startActivity(new Intent(FeaturedScreen.this, LangSettings.class));

        } else if (id == R.id.nav_display) {
            startActivity(new Intent(FeaturedScreen.this, Display.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}//End of Class
