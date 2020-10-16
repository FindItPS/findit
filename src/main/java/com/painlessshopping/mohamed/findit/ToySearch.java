package com.painlessshopping.mohamed.findit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationView;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Controls the Toy search view and processes search queries.
 */

public class ToySearch extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private static Context context;
    ProgressDialog dialog;
    ArrayList<Item> Items = new ArrayList<>(); ;
    ListView listView;
    public static CustomAdapter adapter;
    TextView text;
    static String site;
    Document doc;
    private String sortValue = "";
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private final Handler uiHandler = new Handler();
    //    private ArrayAdapter<String> adapter;
    private ArrayList<String> entries = new ArrayList<>();
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(ThemeHandler.getTheme());
        super.onCreate(savedInstanceState);

        //Set Language
        Locale locale = new Locale(LanguageHandler.getLang());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,
                getResources().getDisplayMetrics());
        setContentView(R.layout.fragment_search);

        //Initializes toolbar
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

        context = this;

        //List of items
        ListView listView=(ListView)findViewById(R.id.listView);
        adapter= new CustomAdapter(Items, this);
        listView.setAdapter(adapter);

        //Search text bar
        TextView empty = (TextView) findViewById(R.id.empty);
        listView.setEmptyView(empty);

        //Toolbar text
        setTitle(getString(R.string.title_activity_toy_search));
        handleIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            new SearchQuery(ToySearch.this, query, 5);


        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager =(SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView =(SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
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
            case R.id.action_settings:
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

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_search, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//            return rootView;
//        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }


    public static Context getAppContext(){
        return ToySearch.context;
    }


    /**
     * Controls sort
     *
     * @param type Type of sort to be executed
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
            startActivity(new Intent(ToySearch.this, HomeScreen.class));

        }  else if (id == R.id.nav_my_cart) {
            startActivity(new Intent(ToySearch.this, MyCart.class));

        } else if (id == R.id.nav_featured_stores) {
            startActivity(new Intent(ToySearch.this, FeaturedScreen.class));

        } else if (id == R.id.nav_language_settings) {
            startActivity(new Intent(ToySearch.this, LangSettings.class));

        } else if (id == R.id.nav_display) {
            startActivity(new Intent(ToySearch.this, Display.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}//End of Class


