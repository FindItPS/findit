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
    public static int BOOK_QUEUE = 4 , CLOTHING_QUEUE = 3, FOOD_QUEUE = 2, PETS_QUEUE = 4, TECH_QUEUE = 10, TOYS_QUEUE = 4;
    public static final int BOOK_SEARCH = 1, FOOD_SEARCH = 2, PETS_SEARCH = 3, TECH_SEARCH = 4, TOYS_SEARCH = 5, CLOTHING_SEARCH = 6;
    public static ArrayList<Item> masterList = new ArrayList<Item>();

    public static void makeRequest(Context c, ArrayList<Item> processed, int type){
        switch(type){
            case BOOK_SEARCH:
                if(BOOK_QUEUE != 0){
                    masterList.addAll(processed);
                    System.out.println("HANDLED REQUESET #" + BOOK_QUEUE);
                    BOOK_QUEUE--;
                }

                if(BOOK_QUEUE == 0){
                    ListView list = (ListView) ((Activity) c).findViewById(R.id.listView);
                    Snackbar.make(list, masterList.size() + R.string.results_found, Snackbar.LENGTH_LONG ).show();
                    System.out.println(masterList.size() + "Results have been REACHED.");

                    masterList.clear();
                    BOOK_QUEUE = 4;

                }
                break;
            case FOOD_SEARCH:
                if(FOOD_QUEUE != 0){
                    masterList.addAll(processed);
                    System.out.println("HANDLED REQUESET #" + Math.abs(FOOD_QUEUE - 3));
                    FOOD_QUEUE--;
                }

                if(FOOD_QUEUE == 0){
                    ListView list = (ListView) ((Activity) c).findViewById(R.id.listView);
                    Snackbar.make(list, masterList.size() + R.string.results_found, Snackbar.LENGTH_LONG ).show();
                    System.out.println(masterList.size() + "Results have been REACHED.");

                    masterList.clear();
                    FOOD_QUEUE = 2;

                }
                break;
            case PETS_SEARCH:
                if(PETS_QUEUE != 0){
                    masterList.addAll(processed);
                    System.out.println("HANDLED REQUESET #" + Math.abs(PETS_QUEUE - 5));
                    PETS_QUEUE--;
                }

                if(PETS_QUEUE == 0){
                    ListView list = (ListView) ((Activity) c).findViewById(R.id.listView);
                    Snackbar.make(list, masterList.size() + R.string.results_found, Snackbar.LENGTH_LONG ).show();
                    System.out.println(masterList.size() + "Results have been REACHED.");

                    masterList.clear();
                    PETS_QUEUE = 4;

                }
                break;
            case TECH_SEARCH:
                if(TECH_QUEUE != 0){
                    masterList.addAll(processed);
                    System.out.println("HANDLED REQUESET #" + Math.abs(TECH_QUEUE - 11));
                    TECH_QUEUE--;
                }

                if(TECH_QUEUE == 0){
                    ListView list = (ListView) ((Activity) c).findViewById(R.id.listView);
                    Snackbar.make(list, masterList.size() + R.string.results_found, Snackbar.LENGTH_LONG ).show();
                    System.out.println(masterList.size() + "Results have been REACHED.");

                    masterList.clear();
                    TECH_QUEUE = 10;

                }
                break;
            case TOYS_SEARCH:
                if(TOYS_QUEUE != 0){
                    masterList.addAll(processed);
                    System.out.println("HANDLED REQUESET #" + Math.abs(TOYS_QUEUE - 5));
                    TOYS_QUEUE--;
                }

                if(TOYS_QUEUE == 0){
                    ListView list = (ListView) ((Activity) c).findViewById(R.id.listView);
                    Snackbar.make(list, masterList.size() + R.string.results_found, Snackbar.LENGTH_LONG ).show();
                    System.out.println(masterList.size() + "Results have been REACHED.");

                    masterList.clear();
                    TOYS_QUEUE = 4;

                }
                break;
            case CLOTHING_SEARCH:
                if(CLOTHING_QUEUE != 0){
                    masterList.addAll(processed);
                    System.out.println("HANDLED REQUESET #" + Math.abs(CLOTHING_QUEUE - 4));
                    CLOTHING_QUEUE--;
                }

                if(CLOTHING_QUEUE == 0){
                    ListView list = (ListView) ((Activity) c).findViewById(R.id.listView);
                    Snackbar.make(list, masterList.size() + R.string.results_found, Snackbar.LENGTH_LONG ).show();
                    System.out.println(masterList.size() + "Results have been REACHED.");

                    masterList.clear();
                    CLOTHING_QUEUE = 3;

                }
                break;
        }
    }



}
