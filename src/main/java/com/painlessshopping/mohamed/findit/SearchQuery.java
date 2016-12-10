package com.painlessshopping.mohamed.findit;

import android.app.Activity;
import android.content.Context;
import android.webkit.WebView;

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

    public SearchQuery(){

    }

    public SearchQuery(Activity a, Context c){

        Search.adapter.clear();
        new CanadaComputersSearch(a, c);
        new SearchTemplate(a, c);




    }

}
