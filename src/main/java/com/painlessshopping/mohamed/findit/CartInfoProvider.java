package com.painlessshopping.mohamed.findit;

import java.util.ArrayList;

/**
 * Created by Abdourahmane on 2016-12-12.
 */

public class CartInfoProvider {

    private static ArrayList<Item> cart = new ArrayList<Item>();

    public static void addToCart(Item i){

        cart.add(i);


    }

    public static void removeFromCart(Item i){

        cart.remove(i);


    }

    public static ArrayList<Item> getCart(){

        return cart;
    }
}
