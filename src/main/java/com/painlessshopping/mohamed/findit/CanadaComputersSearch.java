package com.painlessshopping.mohamed.findit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Abdourahmane on 2016-11-28.
 */

public class CanadaComputersSearch extends SearchQuery{

    public Elements resultsOdd, resultsEven;


    public CanadaComputersSearch(String query){

        try {
            doc = Jsoup.connect("http://www.canadacomputers.com/simple_search.php?keywords=" + query)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
                    .get();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parse(Document d){

        resultsOdd = d.getElementsByClass("productListing-odd");
        resultsEven = d.getElementsByClass("productListing-even");

        //Add resultsOdd and resultsEven to the master results Element array
        for(int i = 0; i < resultsOdd.size(); i++){
            results.add(resultsOdd.get(i));
        }

        for(int j = 0; j <resultsEven.size();j++){
            results.add(resultsEven.get(j));
        }

    }

    public double fetchPrice(){
        return price;
    }

    public String fetchDescription(){
        return description;
    }


}
