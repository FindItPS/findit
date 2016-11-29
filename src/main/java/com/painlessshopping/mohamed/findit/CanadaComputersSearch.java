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

            System.out.println(doc.toString());
            System.out.println("End of toString.");

            parse(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parse(Document d){

        resultsOdd = d.select("productListing-odd");
        resultsEven = d.select("productListing-even");

        //Add resultsOdd and resultsEven to the master results Element array
        for(int i = 0; i < resultsOdd.size(); i++){
            results.add(resultsOdd.get(i));
        }

        for(int j = 0; j <resultsEven.size();j++){
            results.add(resultsEven.get(j));
        }

        System.out.println("SIZE");
        System.out.println(results.size());
    }

    public double fetchPrice(int i){

        try{
            Elements productData = results.get(i).getElementsByTag("td");
            System.out.println("PRODUCT DATA:");
            System.out.println(productData.toString());

            //Parses a double after the first chart "$" and sets that to price
            price = Double.parseDouble(productData.get(2).text()
                    .substring(1 , productData.get(2).text().length()));

        } catch (NullPointerException e){
            e.printStackTrace();
        }

        return price;
    }

    public String fetchDescription(int i){

        try{
            Elements productData = results.get(i).getElementsByTag("td");
            System.out.println(productData.toString());

            //Selects the item description from canada computers website
            description = productData.get(1)
                    .select("form#compare > div.item_description > a").text();

            System.out.println("DESCRIPTION:");
            System.out.println(description);

        } catch (NullPointerException e){
            e.printStackTrace();
        }

        return description;
    }




}
