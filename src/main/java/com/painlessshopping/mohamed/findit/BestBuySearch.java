package com.painlessshopping.mohamed.findit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class controls Best Buy searches (sending a query to the website, retrieving and processing the information)
 *
 * Created by Samuel on 2016-11-29.
 */

public class BestBuySearch extends SearchQuery{

    public ArrayList<Item> BestBuySearch(String query){

        //Using JSoup, the code connects to the Best Buy product search
        //The search is configured to return certain attributes (ex. item name, availibilty) in a json format
        try {
            doc = Jsoup.connect("https://api.bestbuy.com/v1/products((search=" + query + "))?apiKey=qbBfecRN2JqSzbliyHCC0zMN&sort=name.asc&show=name,inStoreAvailability,regularPrice,salePrice&format=json")
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
                    .get();
            
            
            //TESTING: REMOVE IN FUTURE
            //Prints results to console for testing purposes
            System.out.println(doc.toString());
            System.out.println("End of toString.");
            
            //Save search results to be manipulated with parse method
            String searchResults = doc.toString();

            return transferItems(searchResults);
            
        //Exception Catch
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    //Takes JSoup output and transfers it into readable elements
    public ArrayList<Item> transferItems(String completeResults){
        
        //Separates the completeResults string into an ArrayList of strings based on where the line breaks are
        ArrayList<String> listResults = new ArrayList<String> (Arrays.asList(completeResults.split(System.getProperty("line.separator"))));

        //Deletes lines 1-11 of the search query
        //Provided the search is done correctly these lines are all piece of irrelevant information
        for (int j = 10; j == 0; j--) {
          
          listResults.remove(j);
        }
        
        //Deletes useless punctuation at the bottom of the search results
        for (int j = listResults.size() - 1; j ==listResults.size() - 3; j--){
          
          listResults.remove(j);
        }
        
        //TESTING: REMOVE IN FUTURE
        //Prints string to console so we can see the correct lines were deleted
        for (int i = 0; i < listResults.size(); i++){
          
          System.out.println(listResults.get(i));
        }
        
        //Calculate the number of items and saves it
        int numberOfItems = listResults.size()/6;
        
        //Array to store items that will be created for later access
        ArrayList<Item> bestBuySearchItems = new ArrayList<Item>();
        
        //Loop that creates item objects to be added to the displayed search result list
        for (int j = numberOfItems; j == 0; j --){
          
          //Sets the name of the item
          //NOTE: PRINTLN IS FOR TESTING WILL BE REMOVED LATER
          String name = listResults.get(1).substring(9, listResults.get(1).length() - 3);
          System.out.println(name);
          
          //Sets the price of the item
          //NOTE: PRINTLN IS FOR TESTING WILL BE REMOVED LATER
          double value = Double.parseDouble(listResults.get(2).substring(9, listResults.get(2).length() - 3));
          System.out.println(price);
          
          //Sets store name
          String store = "Best Buy";
          
          //Creates item
          bestBuySearchItems.add(new Item(name, store, price));
          
          //Removes used lines from the code
          for (int i = 5; i == 0; i --){
            
            listResults.remove(i);
          }
        }
        
        return bestBuySearchItems;
    }

}
