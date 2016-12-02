package com.painlessshopping.mohamed.findit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Abdourahmane on 2016-11-28.
 */

public class CanadaComputersSearch extends SearchQuery{

    public Elements resultsEven, finalDoc;

    /**
     * Constructor method
     * @param view The WebView that is being used to render the JavaScript in the Search Activity
     * @param context The context taken from the webview (So that the asynctask can show progress)
     */
    public CanadaComputersSearch(WebView view, Context context) {

        //Get the link from the WebView, and save it in a final string so it can be accessed from worker thread
        final String link = view.getUrl();
        new fetcher(context).execute(link);
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

            System.out.println(link);

            try {
                doc = Jsoup.connect(link)
                        .ignoreContentType(true)
                        .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
                        .timeout(10000)
                        .get();

                finalDoc = doc.select("body tbody");


            } catch (IOException e) {
                e.printStackTrace();
            }

            return finalDoc;
        }


        @Override
        protected void onPostExecute(Elements result) {


            pdialog.cancel();
            parse(result);


        }
    }

    /**
     * This class stores the relevant results retrieved from the Asynctask in one Elements object for manipulation
     * @param r The elements retrieved from the Asynctask "fetcher"
     */
    public void parse(Elements r){

        results = r.select("tr.productListing-odd");
        resultsEven = r.select("tr.productListing-even");

        //Add the "even" product listings to results array
        for(int j = 0; j <resultsEven.size();j++){
            results.add(resultsEven.get(j));
        }
        System.out.println(results.size() + " Results have been returned.");
        fetchPrice(results);
        fetchDescription(results);
    }


    /**
     *This method returns the price(s) for the search elements passed in
     * @param productData the results scraped from the site
     * @return the product price(s)
     */
    public double fetchPrice(Elements productData){

        try{

            //Parses a double after the first chart "$" and sets that to price for every element
            for(int i = 0; i < productData.size(); i++){
                Element prices = productData.get(i).select("td").get(2);

                //For some reason I have to substring this twice seperately because it doesn't work otherwise
                String pricestring = prices.toString().substring(prices.toString().indexOf("$") + 1);
                int endIndex = 0;

                while(Character.isDigit(pricestring.charAt(endIndex))){
                        endIndex++;
                    }


                pricestring = pricestring.substring(0, endIndex);
                //Parses the double as an actual price
                price = Double.parseDouble(pricestring);

                //Prints the price to console for testing purposes
                System.out.println("PRICE " + (i+1) + ": " + price);

            }



        } catch (NullPointerException e){
            e.printStackTrace();
        }

        return price;
    }


    /**
     * This method returns the description(s) for the search elements passed in
     * @param productData the results scraped from the site
     * @return the product description
     */
    public String fetchDescription(Elements productData){

        try{


            for(int i = 0; i < productData.size(); i++){
                Element ele = productData.get(i).select("td").get(1);

                String description = ele.select("div.item_description > a").first().text();

                //Prints the description to console for testing purposes
                System.out.println("DESCRIPTION " + (i + 1) + ": " + description);

            }

        } catch (NullPointerException e){
            e.printStackTrace();
        }

        return description;
    }




}
