package com.painlessshopping.mohamed.findit;

/**
 * Created by Abdourahmane on 2016-11-28.
 */

public class Item {

    String title, store;
    Double price;

    public Item(String t, String s, Double p){
        setTitle(t);
        setStore(s);
        //setDescription(d);
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

        if(p != 0){
            price = p;
        } else {
            System.out.println("There was an Error Setting the Price of the object " + getTitle() + ".");
        }
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


}