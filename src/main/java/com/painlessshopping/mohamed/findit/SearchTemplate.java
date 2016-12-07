package com.painlessshopping.mohamed.findit;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.webkit.WebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Abdourahmane on 2016-12-07.
 */

public class SearchTemplate extends SearchQuery{

    //You do not need a resultsEven object. This was specific to CANADA COMPUTERS' WEBSITE
    public Elements resultsEven, finalDoc;
    private ArrayList<Item> processed;

    //This basically is just so that the class knows which Activity we're working with
    private Context c;

    /**
     * Constructor method
     * @param view The WebView that is being used to render the JavaScript in the Search Activity
     * @param context The context taken from the webview (So that the asynctask can show progress)
     */
    public SearchTemplate(WebView view, Context context) {

        //Get the link from the WebView, and save it in a final string so it can be accessed from worker thread
        final String link = view.getUrl();

        c = context;
        new SearchTemplate.fetcher(context).execute(link);


    }

    /**
     * This subclass is a worker thread meaning it does work in the background while the user interface is doing something else
     * This is done to prevent "lag".
     * To call this class you must write fetcher(Context c).execute(The link you want to connect to)
     *
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

        //This return elements because the postExecute() method needs an Elements object to parse its results
        @Override
        protected Elements doInBackground(String... strings) {

            //You can pass in multiple strings, so this line just says to use the first string
            String link = strings[0];

            //For Debug Purposes, Do NOT Remove - **Important**
            System.out.println("Connecting to: " + link);

            try {
                doc = Jsoup.connect(link)
                        .ignoreContentType(true)
                        .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
                        .timeout(10000)
                        .get();


                finalDoc = doc.select("Put your own logic here.");


            } catch (IOException e) {
                e.printStackTrace();
            }

            return finalDoc;
        }


        @Override
        protected void onPostExecute(Elements result) {


            //This line clears the list of info in the Search activity
            //I should probably be using a getter method but adapter is a static variable so it shouldn't matter
            Search.adapter.clear();

            //parse seperates document into elements
            //crunch results formats those elements into item objects
            //I am saving the result of this to an ArrayList<Item> called "processed"
            processed = crunchResults(parse(result));

            //For debug purposes, do NOT remove - **Important**
            System.out.println(processed.size() + " results have been crunched. \nNotifying List Adapter...");

            //Adds all of the processed results to the list of info in Search activity
            Search.adapter.addAll(processed);

            //Refreshes the list so that they actually show up
            Search.adapter.notifyDataSetChanged();

            //For debug purposes, do NOt remove - **Important
            System.out.println("Adapter has been notified.");

            //Closes the progress dialog called pdialog assigned to the AsyncTask
            pdialog.cancel();




        }
    }

    /**
     * This class stores the relevant results retrieved from the Asynctask in one Elements object for manipulation
     * @param r The elements retrieved from the Asynctask "fetcher"
     */
    public Elements parse(Elements r){

        //NOTE***************************************************************
        //This method is specific to CANADACOMPUTERS.CA you will need to find a different way to seperate
        //the html into blocks that contain information of INDIVIDUAL elements

        //In this case, they were staggered into odd and even results just to be annoying so I added
        //the even results to the odd results to retrieve the full list
        results = r.select("tr.productListing-odd");
        resultsEven = r.select("tr.productListing-even");

        //Add the "even" product listings to results array
//        for(int j = 0; j <resultsEven.size();j++){
//            results.add(resultsEven.get(j));
//        }
//        System.out.println(results.size() + " Results have been returned.");

        return results;
    }



        public ArrayList<Item> crunchResults(Elements e){

        ArrayList<Item> results = new ArrayList<Item>();

        try {

            for (int i = 0; i < e.size(); i++) {


                //The code up until the "*****" is all Canada Computers Specific
                //Replace with your own logic
                Element descriptions = e.get(i).select("td").get(1);

                String description = descriptions.select("div.item_description > a").first().text();

                Element prices = e.get(i).select("td").get(2);

                String pricestring = prices.toString().substring(prices.toString().indexOf("$") + 1);
                int endIndex = 0;

                while (Character.isDigit(pricestring.charAt(endIndex))) {
                    endIndex++;
                }

                pricestring = pricestring.substring(0, endIndex);

                price = Double.parseDouble(pricestring);


                //*******************************************

                String store = "YOUR STORE NAME HERE";

                //Adds the formatted item to an ArrayList of items
                results.add(new Item(description, store, price));

                //Prints the object's to String to console
                //For debug purposes, do NOT remove - **Important
                System.out.println(results.get(i).toString());
            }
        } catch (Exception a){
            a.printStackTrace();
        }

        return results;
    }

}
