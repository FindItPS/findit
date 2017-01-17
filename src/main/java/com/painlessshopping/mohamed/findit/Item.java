package com.painlessshopping.mohamed.findit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Creates Item (Object type) to store details for website-collected information
 *
 * Created by Mohamed on 2016-11-28.
 */

public class Item{

    String title, store, link;
    Double price;

    //Constructor
    public Item(String t, String s, Double p, String l){
        setTitle(t);
        setStore(s);
        setLink(l);
        setPrice(p);
    }

    //Set Title
    public void setTitle(String t){

        if(!t.isEmpty()){
            title = t;
        } else {
            title = "Unspecified";
        }
    }

    //Set Store
    public void setStore(String s){

        if(!s.isEmpty()){
            store = s;
        } else {
            store = "Unspecified";
        }
    }

    //Set Price
    public void setPrice(Double p){


        price = p;

    }

    //Get Title
    public String getTitle(){
        return this.title;
    }


    //Get Price
    public Double getPrice(){

        return this.price;
    }

    //Get Store
    public String getStore() {
        return this.store;
    }

    //Item toString
    @Override
    public String toString() {
        return "$" + getPrice() + " " + getTitle() + " from  " + store + "\n" + getLink();
    }

    //Item sorts
    public static ArrayList<Item> sortItems(ArrayList<Item> unsorted, String sort){


        switch(sort){

            case "Price: Low to High" :

                Collections.sort(unsorted, ItemSort.PRICE_LOW_HIGH );
                return unsorted;

            case "Price: High to Low" :

                Collections.sort(unsorted, ItemSort.PRICE_HIGH_LOW);
                return unsorted;

            case "Name: A to Z" :

                Collections.sort(unsorted, ItemSort.NAME_A_Z);
                return unsorted;

            case "Name: Z to A" :

                Collections.sort(unsorted, ItemSort.NAME_Z_A);
                return unsorted;

            case "Proximity: Close to Far":
                //Does not work yet :)
                Collections.sort(unsorted, ItemSort.PROXIMITY_CLOSE_FAR);
                return unsorted;

        }

        return unsorted;
    }



    //Item sorts
    enum ItemSort implements Comparator<Item> {
        PRICE_LOW_HIGH {
            @Override
            public int compare(Item i1, Item i2) {
                return (int) (i1.getPrice() - i2.getPrice());
            }
        },

        PRICE_HIGH_LOW {
            @Override
            public int compare(Item i1, Item i2) {
                return (int) -(i1.getPrice() - i2.getPrice());
            }
        },
        NAME_A_Z {
            @Override
            public int compare(Item i1, Item i2) {
                if (i1.getTitle() == i2.getTitle()) {
                    return 0;
                }
                if (i1.getTitle() == null) {
                    return -1;
                }
                if (i2.getTitle() == null) {
                    return 1;
                }
                return (i1.getTitle().compareTo(i2.getTitle()));
            }
        },

        NAME_Z_A {
            @Override
            public int compare(Item i1, Item i2) {
                if (i1.getTitle() == i2.getTitle()) {
                    return 0;
                }
                if (i1.getTitle() == null) {
                    return 1;
                }
                if (i2.getTitle() == null) {
                    return -1;
                }
                return -(i1.getTitle().compareTo(i2.getTitle()));
            }
        },

        PROXIMITY_CLOSE_FAR {
            @Override
            public int compare(Item i1, Item i2) {
                return 0;
            }
        }
    }

    //Set Link
    public void setLink(String l){
        if(!l.isEmpty()){
            link = l;
        } else {
            System.out.println("There was an Error setting the link of the item: " + getTitle());
        }
    }

    public String getLink(){
        return link;
    }
}

