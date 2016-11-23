package com.painlessshopping.mohamed.findit;

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
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class Search extends AppCompatActivity{

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
    private ArrayAdapter<String> adapter;
    private ArrayList<String> entries = new ArrayList<>();
    private ProgressDialog progressDialog;

//    private class JSHtmlInterface {
//        @android.webkit.JavascriptInterface
//        public void showHTML(String html) {
//            final String htmlContent = html;
//
//            uiHandler.post(
//                    new Runnable() {
//                        @Override
//                        public void run() {
//                            Document doc = Jsoup.parse(htmlContent);
//                            System.out.println(doc.toString());
//                            Elements elements = doc.select("body > div#main > div#cnt > div.mw > div#rcnt > " +
//                                    "div.col > div#center_col > div.res > div#search > div > div#ires > div#rso > " +
//                                    "div.sh-sr__shop-result-group._G2d > div.sh-pr__product-results > div.g.psgi");
//                            entries.clear();
//                            for (Element element : elements) {
//                                String title = element.select("div.psgicont > h3 > a.pstl").first().text();
//                                String imgUrl = element.select("div.psgicont > div.psgextra > div._tyb.shop__secondary > span.price").first().text();
//                                entries.add(title + "\n" + imgUrl);
//                            }
//                            adapter.notifyDataSetChanged();
//                        }
//                    }
//            );
//        }
//    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_search);
//
//        ListView listView = (ListView) findViewById(R.id.listView);
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, entries);
//        listView.setAdapter(adapter);
//
//        progressDialog = ProgressDialog.show(this, "Loading","Please wait...", true);
//        progressDialog.setCancelable(false);
//
//        try {
//            final WebView browser = new WebView(this);
//            browser.setVisibility(View.INVISIBLE);
//            browser.setLayerType(View.LAYER_TYPE_NONE,null);
//            browser.getSettings().setJavaScriptEnabled(true);
//            browser.getSettings().setBlockNetworkImage(true);
//            browser.getSettings().setDomStorageEnabled(false);
//            browser.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//            browser.getSettings().setLoadsImagesAutomatically(false);
//            browser.getSettings().setGeolocationEnabled(false);
//            browser.getSettings().setSupportZoom(false);
//
//            browser.addJavascriptInterface(new JSHtmlInterface(), "JSBridge");
//
//            browser.setWebViewClient(
//                    new WebViewClient() {
//
//                        @Override
//                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                            progressDialog.show();
//                            super.onPageStarted(view, url, favicon);
//                        }
//
//                        @Override
//                        public void onPageFinished(WebView view, String url) {
//                            browser.loadUrl("javascript:window.JSBridge.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
//                            progressDialog.dismiss();
//                        }
//                    }
//            );
//
//            findViewById(R.id.buttonDown).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    uiHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
////                            int page = Integer.parseInt(browser.getUrl().split("-")[1]);
////                            int newPage = page > 1 ? page-1 : 1;
//                            browser.loadUrl("https://www.google.ca/search?q=scarf+walmart&tbm=shop");
//                            browser.loadUrl(browser.getUrl()); // not sure why this is needed, but doesn't update without it on my device
//                            if(getSupportActionBar()!=null) getSupportActionBar().setTitle(browser.getUrl());
//                        }
//                    });
//                }
//            });
//
//            findViewById(R.id.buttonUp).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    uiHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
////                            int page = Integer.parseInt(browser.getUrl().split("-")[1]);
////                            int newPage = page+1;
//                            browser.loadUrl("https://www.google.ca/search?q=scarf+walmart&tbm=shop");
//                            browser.loadUrl(browser.getUrl()); // not sure why this is needed, but doesn't update without it on my device
//                            if(getSupportActionBar()!=null) getSupportActionBar().setTitle(browser.getUrl());
//                        }
//                    });
//                }
//            });
//
//            browser.loadUrl("https://www.google.ca/search?q=scarf+walmart&tbm=shop");
//            if(getSupportActionBar()!=null) getSupportActionBar().setTitle(browser.getUrl());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        // Create the adapter that will return a fragment for each of the three
//        // primary sections of the activity.
//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//
//        // Set up the ViewPager with the sections adapter.
//        mViewPager = (ViewPager) findViewById(R.id.container);
//        mViewPager.setAdapter(mSectionsPagerAdapter);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        final Context myApp = this;
        text = (TextView) findViewById(R.id.textView);


        Button fetchBtn = (Button) findViewById(R.id.buttonUp);

        fetchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                fetcher fetch = new fetcher();
                fetch.execute();

            }
        });


    }

    public class Item {

        String title, brand, description;
        //ArrayList<String> stores;
        Double price;

        public Item(String t, String b, Double p){
            setTitle(t);
            setBrand(b);
            //setDescription(d);
            setPrice(p);
        }
        public void setTitle(String t){

            if(!t.isEmpty()){
                title = t;
            } else {
                title = "Unspecified";
            }
        }

        public void setBrand(String b){

            if(!b.isEmpty()){
                brand = b;
            } else {
                brand = "Unspecified";
            }
        }

//        public void setDescription(String d){
//
//            if(!d.isEmpty()){
//                description = d;
//            } else {
//                description = "Unspecified";
//            }
//        }

//        public void addStore(String s){
//
//            if(!s.isEmpty()){
//                    stores.add(s);
//            } else {
//                //Do nothing
//            }
//        }
//
//        public void removeStore(String s){
//            stores.remove(s);
//        }

        public void setPrice(Double p){

            if(p != 0){
                price = p;
            } else {
                //Do nothing
            }
        }

        public String getTitle(){
            return this.title;
        }

        public String getBrand(){
            return this.brand;
        }

        public String getDescription(){
            return this.description;
        }

//        public ArrayList<String> getStores(){
//            return this.stores;
//        }

        public Double getPrice(){
            return this.price;
        }

        @Override
        public String toString() {
            return "Item : " + getTitle() + ", from : " + brand + "costs : " + getPrice();
        }
    }



    public class fetcher extends AsyncTask<Void, Void, Integer>{

        @Override
        protected Integer doInBackground(Void... voids) {
            try{

                Connection.Response response = Jsoup.connect("https://www.google.ca/search?q=scarf+walmart&tbm=shop")
                        //.ignoreContentType(true)
                        .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
                        //.referrer("http://www.google.ca")
                        //.timeout(1000000)
                        //.followRedirects(true)
                        .execute();

                //response = Jsoup.connect(response.url());

                doc = response.parse();

//                Connection con = Jsoup.connect("http://www.tesco.com/direct/blocks/catalog/productlisting/infiniteBrowse.jsp?&view=grid&catId=4294967294+4294814304&sortBy=&searchquery=espresso+machine&offset=20&lazyload=true")
//                        .ignoreContentType(true);
//                Connection.Response res = con.execute();
//                String rawJSON = res.body();
//
//                JSONObject o = (JSONObject) JSONValue.parse(html);
//                String html = (String) o.get("products");



                System.out.println("*********************************");

                //******** FOR LOGCAT**************
                System.out.println(doc.toString());
                //*********************************

                System.out.println("*********************************");

                System.out.println("Finished");
                Elements items = doc.select("body#gsr > div#main > div#cnt >  div.mw > div#rcnt > div.col > div#center_col > div#res > div#search > div > div#ires > div#rso > div.sh-sr__shop-result-group._G2d > div.sh-pr__product-results > div.g.psgi > " +
                        "div.psgicont");

                ArrayList<Item> results = null;



                for(int i = 0; i < items.size() ; i++){
                    String title = items.get(i).select("h3.pstl").text();
                    //String brand = items.get(i).select("");
                    //String description = items.get(i).select();
                    String store = items.get(i).select("div.psgextra > div._tyb shop__secondary").text();

                    Double price = Double.parseDouble(items.get(i).select("div.psgextra > div._tyb shop__secondary > span.price").text());
                    results.add(new Item(title, store, price));

                    System.out.println(results.get(i).toString());

                }




            } catch (IOException e){
                e.printStackTrace();
            }

            return 1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
        }
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
