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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Abdourahmane on 2016-11-28.
 */

public class WalmartSearch extends SearchQuery{

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
     * @param query Provides the search term
     */

    public WalmartSearch(Context context, String query) {

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

            //Loads website with WebView to fetch results
            browser.loadUrl("http://www.walmart.ca/search/" + query);
            browser.loadUrl(browser.getUrl());
            final String link = browser.getUrl();

            //Processes pages of results
            new fetcher(c).execute(link);
            new fetcher(c).execute(link + "/page-2");
            new fetcher(c).execute(link + "/page-3");


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
            pdialog.setTitle(R.string.finding_results);
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

                System.out.println(doc.toString());
                finalDoc = doc.select("body div.thumb-inner-wrap");



            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("SIZE: " + finalDoc.size());
            return finalDoc;
        }


        @Override
        protected void onPostExecute(Elements result) {

            processed = crunchResults(result);
            System.out.println(processed.size() + " results have been crunched by Walmart.");
            Search.adapter.addAll(processed);
            System.out.println("Adapter Notified by Walmart");

            pdialog.dismiss();

            Search.adapter.notifyDataSetChanged();


        }
    }


    public ArrayList<Item> crunchResults(Elements e){

        ArrayList<Item> results = new ArrayList<Item>();

        try {

            for (int i = 0; i < e.size(); i++) {

                Element ele = e.get(i);

                //Separates required details from the HTML including link, name and price
                String description = ele.select("h1 > a").first().text();

                System.out.println("TITLE " + description);
                String unflink = ele.select("h1 > a").first().attr("href");
                String link = "http://www.walmart.ca" + unflink;

                System.out.println("LINK " + link);
                String pricestring = ele.select("span[data-analytics-type=product-price]").attr("data-analytics-value");
                System.out.println("PRICE " + pricestring);
                price = Double.parseDouble(pricestring);

                String store = "Walmart";

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
