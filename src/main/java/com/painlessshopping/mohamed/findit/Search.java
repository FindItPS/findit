package com.painlessshopping.mohamed.findit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class Search extends AppCompatActivity {

    ArrayList<Item> Items = new ArrayList<>(); ;
    ListView listView;
    public static CustomAdapter adapter;
    TextView text;
    static String site;
    Document doc;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private final Handler uiHandler = new Handler();
//    private ArrayAdapter<String> adapter;
    private ArrayList<String> entries = new ArrayList<>();
    private ProgressDialog progressDialog;

    protected class JSHtmlInterface {
        @android.webkit.JavascriptInterface
        public void showHTML(String html) {
            final String htmlContent = html;

            uiHandler.post(
                    new Runnable() {
                        @Override
                        public void run() {
                            Document doc = Jsoup.parse(htmlContent);
                            Elements elements = doc.select("#online_movies > div > div");
                            entries.clear();
                            for (Element element : elements) {
                                String title = element.select("div.l-description .float-left > div:nth-child(1) > a").first().attr("title");
                                String imgUrl = element.select("div.l-image.float-left > a > img.lazy").first().attr("data-original");
                                entries.add(title + "\n" + imgUrl);
                            }

                        }
                    }
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);


            findViewById(R.id.buttonDown).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {


                        }
                    });
                }
            });

            findViewById(R.id.buttonUp).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            });

             findViewById(R.id.searchBtn).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            new SearchQuery(Search.this, Search.this);
                        }
                    });

                }
            });


            ListView listView=(ListView)findViewById(R.id.listView);
            adapter= new CustomAdapter(Items, this);
            listView.setAdapter(adapter);


//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                    Item item= Items.get(position);
//
//                    Snackbar.make(view, item.getTitle()+"\n"+item.getStore()+" Price: "+item.getPrice(), Snackbar.LENGTH_LONG)
//                            .setAction("No action", null).show();
//                }
//            });


    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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


}


