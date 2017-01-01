package com.painlessshopping.mohamed.findit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Abdourahmane on 2016-12-12.
 */

public class CartAdapter extends ArrayAdapter<Item> implements View.OnClickListener{

    private ArrayList<Item> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView itemTitle;
        TextView itemStore;
        TextView itemPrice;
        ImageView storeLogo;
        ImageView removeFromCart;
    }

    public CartAdapter (ArrayList<Item> data, Context context) {
        super(context, R.layout.row_cart, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        final Item item=(Item)object;

        CharSequence actions[];

        final View view = v;
        switch (v.getId())
        {

            case R.id.more:
                System.out.println(item.getLink());
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink()));
                mContext.startActivity(browserIntent);
                break;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Item item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_cart, parent, false);
            viewHolder.itemTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.itemStore = (TextView) convertView.findViewById(R.id.retailer);
            viewHolder.itemPrice = (TextView) convertView.findViewById(R.id.price);
            viewHolder.storeLogo = (ImageView) convertView.findViewById(R.id.more);
            viewHolder.removeFromCart = (ImageView) convertView.findViewById(R.id.removeCart);

            result = convertView;

            convertView.setTag(viewHolder);

        viewHolder.itemTitle.setText(item.getTitle());
        viewHolder.itemStore.setText("From " + item.getStore());
        viewHolder.itemPrice.setText("$" + item.getPrice());
        viewHolder.storeLogo.setOnClickListener(this);

        viewHolder.removeFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartInfoProvider.removeFromCart(item);
                MyCart.adapter.remove(item);
                MyCart.adapter.notifyDataSetChanged();

                Snackbar.make(result, "This item was removed from your cart", Snackbar.LENGTH_INDEFINITE).setAction("UNDO", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        CartInfoProvider.addToCart(item);
                        MyCart.adapter.add(item);
                        MyCart.adapter.notifyDataSetChanged();
                    }

                }).show();

            }
        });


        viewHolder.storeLogo.setTag(position);

        // Return the completed view to render on screen
        return convertView;

    }
}
