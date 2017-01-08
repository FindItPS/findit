package com.painlessshopping.mohamed.findit;

import java.util.ArrayList;

/**
 * Controls adding, removing and fetching items from cart
 *
 * Created by Abdourahmane on 2016-12-12.
 */

public class CartInfoProvider {

    private static ArrayList<Item> cart = new ArrayList<Item>();

    public static void addToCart(Item i){

        //Adds an item to the cart
        cart.add(i);
    }

    public static void removeFromCart(Item i){

        //Removes an item from the cart
        cart.remove(i);
    }

    public static ArrayList<Item> getCart(){

        //Get the full list of items in the cart
        return cart;
    }
}
