package com.painlessshopping.mohamed.findit.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.painlessshopping.mohamed.findit.BestBuySearch;
import com.painlessshopping.mohamed.findit.BookSearch;
import com.painlessshopping.mohamed.findit.CanadaComputersSearch;
import com.painlessshopping.mohamed.findit.ChaptersIndigoSearch;
import com.painlessshopping.mohamed.findit.ClothingSearch;
import com.painlessshopping.mohamed.findit.EBGamesSearch;
import com.painlessshopping.mohamed.findit.FoodSearch;
import com.painlessshopping.mohamed.findit.HnMSearch;
import com.painlessshopping.mohamed.findit.LoblawsSearch;
import com.painlessshopping.mohamed.findit.MarksSearch;
import com.painlessshopping.mohamed.findit.MastermindToysSearch;
import com.painlessshopping.mohamed.findit.PetSearch;
import com.painlessshopping.mohamed.findit.PetSmartSearch;
import com.painlessshopping.mohamed.findit.RCSuperstoreSearch;
import com.painlessshopping.mohamed.findit.RootsSearch;
import com.painlessshopping.mohamed.findit.Search;
import com.painlessshopping.mohamed.findit.SportChekSearch;
import com.painlessshopping.mohamed.findit.StaplesSearch;
import com.painlessshopping.mohamed.findit.TechSearch;
import com.painlessshopping.mohamed.findit.ToySearch;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Controls different types of searches and their divisions
 *
 * Created by Mohamed on 2016-11-28.
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
    public enum SearchType { BOOK_SEARCH, FOOD_SEARCH, PETS_SEARCH, TECH_SEARCH, TOYS_SEARCH, CLOTHING_SEARCH };
    public static final int BOOK_SEARCH = 1, FOOD_SEARCH = 2, PETS_SEARCH = 3, TECH_SEARCH = 4, TOYS_SEARCH = 5, CLOTHING_SEARCH = 6;

    public SearchQuery(){

    }

    private void searchTopic(SearchType type, Search handler) {
        //
    }

    public SearchQuery(Context c, String q, int type){
        if(type == BOOK_SEARCH){
            if(q.length() > 3){
                BookSearch.adapter.clear();
		        new ChaptersIndigoSearch(c, q);
                new MastermindToysSearch(c, q);
            } else {
                Toast.makeText(c, "The Minimum Query Length is 3 Characters", Toast.LENGTH_LONG).show();
            }
        } else if (type == FOOD_SEARCH){
            if(q.length() > 3){
                FoodSearch.adapter.clear();
                new RCSuperstoreSearch(c, q);
                new LoblawsSearch(c, q);
            } else {
                Toast.makeText(c, "The Minimum Query Length is 3 Characters", Toast.LENGTH_LONG).show();
            }
        } else if (type == PETS_SEARCH){
            if(q.length() > 3){
                PetSearch.adapter.clear();
                new PetSmartSearch(c, q);
                new RCSuperstoreSearch(c, q);
            } else {
                Toast.makeText(c, "The Minimum Query Length is 3 Characters", Toast.LENGTH_LONG).show();
            }
        } else if (type == TECH_SEARCH){
            if(q.length() > 3){
                TechSearch.adapter.clear();
                new CanadaComputersSearch(c, q);
                new BestBuySearch(c, q);
                new StaplesSearch(c, q);
                new EBGamesSearch(c, q);
            } else {
                Toast.makeText(c, "The Minimum Query Length is 3 Characters", Toast.LENGTH_LONG).show();
            }
        } else if (type == TOYS_SEARCH){
            if(q.length() > 3){
                ToySearch.adapter.clear();
                new MastermindToysSearch(c, q);
                new ChaptersIndigoSearch(c, q);
            } else {
                Toast.makeText(c, "The Minimum Query Length is 3 Characters", Toast.LENGTH_LONG).show();
            }
        } else if (type == CLOTHING_SEARCH){
            if(q.length() > 3){
                ClothingSearch.adapter.clear();
                new RootsSearch(c, q);
                new HnMSearch(c, q);
                new MarksSearch(c, q);
                new SportChekSearch(c, q);
            } else {
                Toast.makeText(c, "The Minimum Query Length is 3 Characters", Toast.LENGTH_LONG).show();
            }
        }
    }
}
