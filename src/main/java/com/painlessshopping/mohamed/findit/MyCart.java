package com.painlessshopping.mohamed.findit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Controls the cart activity
 */

public class MyCart extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener   {

    ArrayList<Item> Items = new ArrayList<>(); ;
    ListView listView;
    public static CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(ThemeHandler.getTheme());
        super.onCreate(savedInstanceState);

        //Sets the Language
        Locale locale = new Locale(LanguageHandler.getLang());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,
                getResources().getDisplayMetrics());
        setContentView(R.layout.activity_my_cart);

        //Initializes the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Navigation bar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Item list view
        ListView listView=(ListView)findViewById(R.id.cart);
        adapter= new CartAdapter(Items, this, listView);
        listView.setAdapter(adapter);

        TextView empty = (TextView) findViewById(R.id.empty);
        listView.setEmptyView(empty);

        //Refers to the CarAdapter for formatting
        adapter.addAll(CartInfoProvider.getCart());
        adapter.notifyDataSetChanged();

        //Sets toolbar text
        setTitle(getString(R.string.cart_name));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart_bar, menu);

        return true;
    }

    //Cart Share function
    private void shareIt(){
        if(adapter.getCount() > 0){

            //Opens Android share dialog
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");

            //Defines text that will be send by user
            String shareBody = getString(R.string.share_subject) + "\n\n";
            for(int i= 0; i <adapter.getCount(); i++){
                shareBody += (i+1) + ". " + adapter.getItem(i).toString() + "\n\n";
            }

            //More text
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));
        } else {
            //If they have no items, tell them
            Toast.makeText(this, getString(R.string.empty_cart_share), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Controls toolbar button and their ability to sort list items
     *
     * @param item Items on the tool bar menu
     * @return Identifies that the sort has been completed
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                final CharSequence sortTypes[] = new CharSequence[] {getResources().getString(R.string.sort_plh), getResources().getString(R.string.sort_phl),
                        getResources().getString(R.string.sort_naz), getResources().getString(R.string.sort_nza)};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.sort_rby));
                builder.setItems(sortTypes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sortList(sortTypes[which].toString());
                    }
                });
                builder.show();
                return true;

            case R.id.action_share:
                shareIt();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * Controls sort
     *
     * @param type Type of sort
     */
    public void sortList(String type){

        ArrayList<Item> results = new ArrayList<Item>();

        for(int i =0;i <adapter.getCount(); i++){
            results.add(adapter.getItem(i));
        }

        adapter.clear();

        final String pricelh = getResources().getString(R.string.sort_plh);
        final String pricehl = getResources().getString(R.string.sort_phl);

        final String nameaz = getResources().getString(R.string.sort_naz);
        final String nameza = getResources().getString(R.string.sort_nza);

        if(type.equals(pricelh)){
            adapter.addAll(Item.sortItems(results, "Price: Low to High"));

        } else if(type.equals(pricehl)){
            adapter.addAll(Item.sortItems(results, "Price: High to Low"));

        } else if(type.equals(nameaz)){
            adapter.addAll(Item.sortItems(results, "Name: A to Z"));

        } else if(type.equals(nameza)){
            adapter.addAll(Item.sortItems(results, "Name: Z to A"));
        }

        adapter.notifyDataSetChanged();

    }

    //Controls back button press
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
            startActivity(new Intent(MyCart.this, HomeScreen.class));

        }  else if (id == R.id.nav_my_cart) {
            startActivity(new Intent(MyCart.this, MyCart.class));

        } else if (id == R.id.nav_featured_stores) {
            startActivity(new Intent(MyCart.this, FeaturedScreen.class));

        } else if (id == R.id.nav_language_settings) {
            startActivity(new Intent(MyCart.this, LangSettings.class));

        } else if (id == R.id.nav_display) {
            startActivity(new Intent(MyCart.this, Display.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}//End of Class
