package com.painlessshopping.mohamed.findit;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCart extends AppCompatActivity {

    ArrayList<Item> Items = new ArrayList<>(); ;
    ListView listView;
    public static CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        ListView listView=(ListView)findViewById(R.id.cart);
        adapter= new CartAdapter(Items, this);
        listView.setAdapter(adapter);

        TextView empty = (TextView) findViewById(R.id.empty);
        listView.setEmptyView(empty);

        adapter.addAll(CartInfoProvider.getCart());
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart_bar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                final CharSequence sortTypes[] = new CharSequence[] {"Price: Low to High", "Price: High to Low",
                        "Name: A to Z", "Name: Z to A", "Proximity: Close to Far"};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Sort Result By");
                builder.setItems(sortTypes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sortList(sortTypes[which].toString());
                    }
                });
                builder.show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void sortList(String type){

        ArrayList<Item> results = new ArrayList<Item>();

        for(int i =0;i <adapter.getCount(); i++){
            results.add(adapter.getItem(i));
        }

        adapter.clear();

        switch(type){

            case "Price: Low to High" :
                adapter.addAll(Item.sortItems(results, "Price: Low to High"));
                break;

            case "Price: High to Low" :
                adapter.addAll(Item.sortItems(results, "Price: High to Low"));
                break;

            case "Name: A to Z" :
                adapter.addAll(Item.sortItems(results, "Name: A to Z"));
                break;

            case "Name: Z to A" :
                adapter.addAll(Item.sortItems(results, "Name: Z to A"));
                break;

            case "Proximity: Close to Far":
                adapter.addAll(Item.sortItems(results, "Proximity: Close to Far"));
                break;

            default:
                adapter.addAll(results);
                System.out.println("Default Sort has been carried out.");
                break;
        }

        adapter.notifyDataSetChanged();

    }
}
