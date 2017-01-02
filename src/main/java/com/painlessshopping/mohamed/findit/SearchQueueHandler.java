package com.painlessshopping.mohamed.findit;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Abdourahmane on 2017-01-02.
 */

public class SearchQueueHandler {
    public static int BOOK_QUEUE = 4 , CLOTHING_QUEUE = 3, FOOD_QUEUE = 2, PET_QUEUE = 4, TECH_QUEUE = 13, TOY_QUEUE = 4;
    public static final int BOOK_SEARCH = 1, FOOD_SEARCH = 2, PETS_SEARCH = 3, TECH_SEARCH = 4, TOYS_SEARCH = 5, CLOTHING_SEARCH = 6;
    public static ArrayList<Item> masterList = new ArrayList<Item>();

    public static void makeRequest(Context c, ArrayList<Item> processed, int type){
        switch(type){
            case BOOK_SEARCH:
                if(BOOK_QUEUE != 0){
                    masterList.addAll(processed);
                    System.out.println("HANDLING REQUESET #" + BOOK_QUEUE);
                    BOOK_QUEUE--;
                }

                if(BOOK_QUEUE == 0){
                    ListView list = (ListView) ((Activity) c).findViewById(R.id.listView);
                    Snackbar.make(list, masterList.size() + " results have been returned", Snackbar.LENGTH_LONG ).show();
                    System.out.println(masterList.size() + "Results have been REACHED.");

                    masterList.clear();
                    BOOK_QUEUE = 4;

                }
                break;
            case FOOD_SEARCH:
                break;
            case PETS_SEARCH:
                break;
            case TECH_SEARCH:
                break;
            case TOYS_SEARCH:
                break;
            case CLOTHING_SEARCH:
                break;
        }
    }



}
