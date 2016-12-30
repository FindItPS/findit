package com.painlessshopping.mohamed.findit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.webkit.WebView;
import android.widget.Toast;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by Abdourahmane on 2016-11-28.
 */

public class SearchQuery {

    public Document doc;
    public Elements results;
    public String description;
    public double price;
    //This number is to be increased with all of the stores implemented
    private int status = -2;
    private ProgressDialog dialog;
    private static int ccstatus, bbstatus = 0;

    public SearchQuery(){

    }

    public SearchQuery(Context c, String q){

        if(q.length() >= 3){

            Search.adapter.clear();

            //new CanadaComputersSearch(c, q);
            //new BestBuySearch(c, q);
            //new StaplesSearch(c, q);
            //new ChaptersIndigoSearch(c, q);
            //new PetSmartSearch(c, q);
            //new WalmartSearch(c, q);
            //new EBGamesSearch(c, q);
            //new RCSuperstoreSearch(c, q);
            //new MastermindToysSearch(c, q);
            new RootsSearch(c, q);
            //new SportChekSearch(c, q);
            //new LoblawsSearch(c, q);
            //new GapSearch(c, q);

        } else {
            Toast.makeText(c, "The Minimum Query Length is 3 Characters", Toast.LENGTH_LONG).show();
        }




    }


}
