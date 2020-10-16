package com.painlessshopping.mohamed.findit;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.painlessshopping.mohamed.findit.model.Item;
import com.painlessshopping.mohamed.findit.model.SearchQuery;
import com.painlessshopping.mohamed.findit.viewmodel.SearchQueueHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Sam on 2016-12-29.
 */

public class SportChekSearch extends SearchQuery {

    //You do not need a resultsEven object. This was specific to CANADA COMPUTERS' WEBSITE
    public Elements resultsEven;
    public Elements finalDoc;
    public JSONArray items;
    private ArrayList<Item> processed;
    private final Handler uiHandler = new Handler();
    public int status = 0;

    //This basically is just so that the class knows which Activity we're working with
    private Context c;

    protected class JSHtmlInterface {
        @android.webkit.JavascriptInterface
        public void showHTML(String html) {
            final String htmlContent = html;

            uiHandler.post(
                    new Runnable() {
                        @Override
                        public void run() {
                            Document doc = Jsoup.parse(htmlContent);
                        }
                    }
            );
        }
    }

    /**
     * Constructor method
     * @param context The context taken from the webview (So that the asynctask can show progress)
     */
    public SportChekSearch(Context context, String query) {

        final Context c = context;

        try {
            final WebView browser = new WebView(c);
            browser.setVisibility(View.INVISIBLE);
            browser.setLayerType(View.LAYER_TYPE_NONE, null);
            browser.getSettings().setJavaScriptEnabled(true);
            browser.getSettings().setBlockNetworkImage(true);
            browser.getSettings().setDomStorageEnabled(true);
            browser.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            browser.getSettings().setLoadsImagesAutomatically(false);
            browser.getSettings().setGeolocationEnabled(false);
            browser.getSettings().setSupportZoom(false);
            browser.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            browser.addJavascriptInterface(new JSHtmlInterface(), "JSBridge");

            browser.setWebViewClient(
                    new WebViewClient() {

                        @Override
                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                            super.onPageStarted(view, url, favicon);
                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            browser.loadUrl("javascript:window.JSBridge.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");

                        }
                    }
            );


            browser
                    .loadUrl("https://www.sportchek.ca/services/sportchek/search-and-promote/products?q="
                            + query.replaceAll(" ", "+")
                            + "&count=30&page=1&lang=en");
            browser.loadUrl(browser.getUrl());
            final String link = browser.getUrl();
            new fetcher(c).execute(link);




        }
        catch(Exception e){
            e.printStackTrace();
        }

        //Get the link from the WebView, and save it in a final string so it can be accessed from worker thread


    }

    /**
     * This subclass is a worker thread meaning it does work in the background while the user interface is doing something else
     * This is done to prevent "lag".
     * To call this class you must write fetcher(Context c).execute(The link you want to connect to)
     *
     */
    class fetcher extends AsyncTask<String, Void, JSONArray> {

        Context mContext;
        ProgressDialog pdialog;

        public fetcher(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new ProgressDialog(mContext);
            pdialog.setTitle(R.string.finding_results);
            pdialog.setCancelable(false);
            pdialog.show();
        }

        //This return elements because the postExecute() method needs an Elements object to parse its results
        @Override
        protected JSONArray doInBackground(String... strings) {

            //You can pass in multiple strings, so this line just says to use the first string
            String link = strings[0];

            //For Debug Purposes, Do NOT Remove - **Important**
            System.out.println("Connecting to: " + link);

            try {

                System.out.println(readUrl(link));

                String json = readUrl(link);
                JSONObject parent = new JSONObject(json);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    items = parent.getJSONArray("products");
                }




            } catch (Exception e) {
                e.printStackTrace();
            }

            return items;
        }


        @Override
        protected void onPostExecute(JSONArray result) {


            //This line clears the list of info in the Search activity
            //I should probably be using a getter method but adapter is a static variable so it shouldn't matter


            //parse seperates document into elements
            //crunch results formats those elements into item objects
            //I am saving the result of this to an ArrayList<Item> called "processed"
            processed = crunchResults(result);

            //For debug purposes, do NOT remove - **Important**
            System.out.println(processed.size() + " results have been crunched by Sport Chek.");

            //Adds all of the processed results to the list of info in Search activity
            ClothingSearch.adapter.addAll(processed);


            //For debug purposes, do NOt remove - **Important
            System.out.println("Adapter has been notified by Sport Chek.");

            //Closes the progress dialog called pdialog assigned to the AsyncTask

            pdialog.dismiss();

            ClothingSearch.adapter.notifyDataSetChanged();
            SearchQueueHandler.makeRequest(mContext, processed, SearchQueueHandler.CLOTHING_SEARCH);




        }
    }



    public ArrayList<Item> crunchResults(JSONArray jsonArray){

        ArrayList<Item> results = new ArrayList<Item>();

        try {

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject j = jsonArray.getJSONObject(i);

                String title = j.getString("title");
                String link = "https://www.sportchek.ca" + j.getJSONArray("productPageUrls").getString(0) + ".html";

                price = j.getDouble("price");

                String store = "Sport Chek";

                results.add(new Item(title, store, price, link));
                System.out.println(results.get(i).toString());



            }
        } catch (Exception a){
            a.printStackTrace();
        }

        return results;
    }

    public int getStatus(){
        return status;
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

}
