package com.painlessshopping.mohamed.findit;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.concurrent.Executor;

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
    public static final int BOOK_SEARCH = 1, FOOD_SEARCH = 2, PETS_SEARCH = 3, TECH_SEARCH = 4, TOYS_SEARCH = 5, CLOTHING_SEARCH = 6;
    public int deviceNumberOfCores = getNumberOfCores();


    public SearchQuery(){

    }

    public SearchQuery(Context c, String q, int type){

        if(type == BOOK_SEARCH){
            if(q.length() > 3){

                BookSearch.adapter.clear();

                //If the processor has 8+ cores run the searches in parallel
                //Otherwise, run them sequentially
                if(deviceNumberOfCores > 3){

                    //My (Sam's) tries at it
                    //Currently all producing errors

                    //This?
                    new ChaptersIndigoSearch(c, q).fetcher.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    new MastermindToysSearch(c, q).fetcher.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                    //Or maybe this?
                    //(Correct me if I'm wrong but I think this runs it sequentially too?)
                    ChaptersIndigoSearch chapters = new ChaptersIndigoSearch(c, q);
                    MastermindToysSearch mastermind = new MastermindToysSearch(c, q);

                    chapters.fetcher.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    mastermind.fetcher.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                }else{

                    new ChaptersIndigoSearch(c, q);
                    new MastermindToysSearch(c, q);
                }


            } else {
                Toast.makeText(c, "The Minimum Query Length is 3 Characters", Toast.LENGTH_LONG).show();
            }

        }else if (type == FOOD_SEARCH){
            if(q.length() > 3){

                FoodSearch.adapter.clear();
                new RCSuperstoreSearch(c, q);
                new LoblawsSearch(c, q);

            } else {
                Toast.makeText(c, "The Minimum Query Length is 3 Characters", Toast.LENGTH_LONG).show();
            }

        }else if (type == PETS_SEARCH){
            if(q.length() > 3){

                PetSearch.adapter.clear();
                new PetSmartSearch(c, q);
                new RCSuperstoreSearch(c, q);

            } else {
                Toast.makeText(c, "The Minimum Query Length is 3 Characters", Toast.LENGTH_LONG).show();
            }

        }else if (type == TECH_SEARCH){
            if(q.length() > 3){

                TechSearch.adapter.clear();
                new CanadaComputersSearch(c, q);
                new BestBuySearch(c, q);
                new StaplesSearch(c, q);
                new EBGamesSearch(c, q);

            } else {
                Toast.makeText(c, "The Minimum Query Length is 3 Characters", Toast.LENGTH_LONG).show();
            }

        }else if (type == TOYS_SEARCH){
            if(q.length() > 3){

                ToySearch.adapter.clear();
                new MastermindToysSearch(c, q);
                new ChaptersIndigoSearch(c, q);

            } else {
                Toast.makeText(c, "The Minimum Query Length is 3 Characters", Toast.LENGTH_LONG).show();
            }

        }else if (type == CLOTHING_SEARCH){
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

    private int getNumberOfCores(){

        return Runtime.getRuntime().availableProcessors();
    }


}
