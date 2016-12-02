package com.painlessshopping.mohamed.findit;

/**
 * Created by Abdourahmane on 2016-11-28.
 */

public class Item {

    String title, brand, description;
    //ArrayList<String> stores;
    Double price;

    public Item(String t, String b, Double p){
        setTitle(t);
        setBrand(b);
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

    public void setBrand(String b){

        if(!b.isEmpty()){
            brand = b;
        } else {
            brand = "Unspecified";
        }
    }

    public void setPrice(Double p){

        if(p != 0){
            price = p;
        } else {
            //Do nothing
        }
    }

    public String getTitle(){
        return this.title;
    }

    public String getBrand(){
        return this.brand;
    }

    public String getDescription(){
        return this.description;
    }

//        public ArrayList<String> getStores(){
//            return this.stores;
//        }

    public Double getPrice(){
        return this.price;
    }

    @Override
    public String toString() {
        return "Item : " + getTitle() + ", from : " + brand + "costs : " + getPrice();
    }
}