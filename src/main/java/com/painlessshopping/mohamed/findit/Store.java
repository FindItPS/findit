package com.painlessshopping.mohamed.findit;

/**
 * Created by Abdourahmane on 2016-12-15.
 */

public class Store {


    String imageResource, name;

    public Store(String n, String ir){
        setImageResource(ir);
        setName(n);
    }

    public String getImageResource(){
        return imageResource;
    }

    public String getName(){
        return name;
    }

    public void setImageResource(String ir){
        imageResource = ir;

    }

    public void setName(String n){
        name = n;
    }
}

