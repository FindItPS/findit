package com.painlessshopping.mohamed.findit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Abdourahmane on 2016-11-28.
 */

public class Item{

    String title, store, link;
    Double price;

    public Item(String t, String s, Double p, String l){
        setTitle(t);
        setStore(s);
        setLink(l);
        setPrice(p);
    }
    public void setTitle(String t){

        if(!t.isEmpty()){
            title = t;
        } else {
            title = "Unspecified";
        }
    }

    public void setStore(String s){

        if(!s.isEmpty()){
            store = s;
        } else {
            store = "Unspecified";
        }
    }

    public void setPrice(Double p){


        price = p;

    }

    public String getTitle(){
        return this.title;
    }


    public Double getPrice(){

        return this.price;
    }

    public String getStore() {
        return this.store;
    }
    @Override
    public String toString() {
        return getTitle() + " from  " + store + " costs $" + getPrice() + ".";
    }


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

