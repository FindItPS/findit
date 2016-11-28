package com.painlessshopping.mohamed.findit;

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

    public SearchQuery(String store, String query){

    }
}
