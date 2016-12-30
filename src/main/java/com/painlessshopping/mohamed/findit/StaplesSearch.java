package com.painlessshopping.mohamed.findit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Abdourahmane on 2016-11-28.
 */

public class StaplesSearch extends SearchQuery{

    public Elements resultsEven, finalDoc;
    private ArrayList<Item> processed;
    private final Handler uiHandler = new Handler();
    private int status = 0;
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
    public StaplesSearch(Context context, String query) {

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


//            TextView text = (TextView) a.findViewById(R.id.editText);
//
//            if(text.getText() != null){
            browser.loadUrl("http://www.staples.ca/" + query + "/directory_" + query + "_20051_1_20001?");
            browser.loadUrl(browser.getUrl());
            final String link = browser.getUrl();
            new fetcher(c).execute(link);
            new fetcher(c).execute(link + "fids=&pn=2&sr=true&sby=&min=&max=");
            new fetcher(c).execute(link + "fids=&pn=3&sr=true&sby=&min=&max=");
//
//            }



        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This subclass accesses the CanadaComputers website and fetches results, all the while posting progress
     */
    class fetcher extends AsyncTask<String, Void, Elements> {

        Context mContext;
        ProgressDialog pdialog;

        public fetcher(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new ProgressDialog(mContext);
            pdialog.setTitle("Finding Results!");
            pdialog.setCancelable(false);
            pdialog.show();
        }

        @Override
        protected Elements doInBackground(String... strings) {
            String link = strings[0];

            System.out.println("Connecting to " + link + "\n...");

            try {
                doc = Jsoup.connect(link)
                        .ignoreContentType(true)
                        .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
                        .timeout(10000)
                        .get();

                finalDoc = doc.select("body div.stp--product-list");


            } catch (IOException e) {
                e.printStackTrace();
            }

            return finalDoc;
        }


        @Override
        protected void onPostExecute(Elements result) {

            processed = crunchResults(parse(result));
            System.out.println("Done Crunching Staples");

            TechSearch.adapter.addAll(processed);
            System.out.println("Adapter Notified by Staples");

            pdialog.dismiss();

            TechSearch.adapter.notifyDataSetChanged();


        }
    }

    /**
     * This class stores the relevant results retrieved from the Asynctask in one Elements object for manipulation
     * @param r The elements retrieved from the Asynctask "fetcher"
     */
    public Elements parse(Elements r){

        results = r.select("div.stp--new-product-tile-container.desktop");
        System.out.println(results.size() + " Results have been returned from Staples.");
//        fetchPrice(results);
//        fetchDescription(results);

        return results;
    }

    public ArrayList<Item> crunchResults(Elements e){

        ArrayList<Item> results = new ArrayList<Item>();

        try {

            for (int i = 0; i < e.size(); i++) {

                Element ele = e.get(i);

                String description = ele.select(" div.product-info > a").first().text();

                String id = ele.select(" div.product-info > a").first().attr("href");
                String link = "http://m.staples.ca" + id;

                price = Double.parseDouble(ele.select(" span.discounted-price").text()
                        .substring(1, ele.select(" span.discounted-price").text().length() ));


                String store = "Staples";

                results.add(new Item(description, store, price, link));

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
}
