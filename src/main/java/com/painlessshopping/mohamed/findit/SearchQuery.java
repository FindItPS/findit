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
    public final int BOOK_SEARCH = 1, FOOD_SEARCH = 2, PETS_SEARCH = 3, TECH_SEARCH = 4, TOYS_SEARCH = 5, CLOTHING_SEARCH = 6;


    public SearchQuery(){

    }

    public SearchQuery(Context c, String q, int type){

        if(type == BOOK_SEARCH){
            if(q.length() >= 3){

                BookSearch.adapter.clear();
                new ChaptersIndigoSearch(c, q);
                new MastermindToysSearch(c, q);

            } else {
                Toast.makeText(c, "The Minimum Query Length is 3 Characters", Toast.LENGTH_LONG).show();
            }

        }else if (type == FOOD_SEARCH){
            if(q.length() >= 3){

                FoodSearch.adapter.clear();
                new RCSuperstoreSearch(c, q);
                new LoblawsSearch(c, q);

            } else {
                Toast.makeText(c, "The Minimum Query Length is 3 Characters", Toast.LENGTH_LONG).show();
            }

        }else if (type == PETS_SEARCH){
            if(q.length() >= 3){

                PetSearch.adapter.clear();
                new PetSmartSearch(c, q);
                new RCSuperstoreSearch(c, q);

            } else {
                Toast.makeText(c, "The Minimum Query Length is 3 Characters", Toast.LENGTH_LONG).show();
            }

        }else if (type == TECH_SEARCH){
            if(q.length() >= 3){

                TechSearch.adapter.clear();
                new CanadaComputersSearch(c, q);
                new BestBuySearch(c, q);
                new StaplesSearch(c, q);
                new EBGamesSearch(c, q);

            } else {
                Toast.makeText(c, "The Minimum Query Length is 3 Characters", Toast.LENGTH_LONG).show();
            }

        }else if (type == TOYS_SEARCH){
            if(q.length() >= 3){

                ToySearch.adapter.clear();
                new MastermindToysSearch(c, q);
                new ChaptersIndigoSearch(c, q);

            } else {
                Toast.makeText(c, "The Minimum Query Length is 3 Characters", Toast.LENGTH_LONG).show();
            }

        }else if (type == CLOTHING_SEARCH){
            if(q.length() >= 3){

                ClothingSearch.adapter.clear();
                new RootsSearch(c, q);
                new SportChekSearch(c, q);
                new GapSearch(c, q);

            } else {
                Toast.makeText(c, "The Minimum Query Length is 3 Characters", Toast.LENGTH_LONG).show();
            }

        }
    }


}
