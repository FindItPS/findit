package com.painlessshopping.mohamed.findit;

import android.content.Context;
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
 * Created by Abdourahmane on 2016-12-02.
 */

public class CustomAdapter extends ArrayAdapter<Item> implements View.OnClickListener{

    private ArrayList<Item> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView itemTitle;
        TextView itemStore;
        TextView itemPrice;
        ImageView storeLogo, addToCart;
    }

    public CustomAdapter(ArrayList<Item> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        final Item item=(Item)object;

        final View view = v;
        CharSequence actions[];

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
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.itemTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.itemStore = (TextView) convertView.findViewById(R.id.retailer);
            viewHolder.itemPrice = (TextView) convertView.findViewById(R.id.price);
            viewHolder.storeLogo = (ImageView) convertView.findViewById(R.id.more);
            viewHolder.addToCart = (ImageView) convertView.findViewById(R.id.addCart);

            result = convertView;

            convertView.setTag(viewHolder);

        viewHolder.itemTitle.setText(item.getTitle());
        viewHolder.itemStore.setText(item.getStore());
        viewHolder.itemPrice.setText("$" + item.getPrice());
        viewHolder.storeLogo.setOnClickListener(this);
        viewHolder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CartInfoProvider.addToCart(item);
                final ImageView imageView = (ImageView) v.findViewById(R.id.addCart);
                imageView.setImageResource(R.drawable.ic_shopping_cart_black_24dp);
                imageView.setClickable(false);

                Snackbar.make(result, R.string.added_cart, Snackbar.LENGTH_INDEFINITE)
                        .setAction(mContext.getString(R.string.undo), new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        CartInfoProvider.removeFromCart(item);
                        imageView.setImageResource(R.drawable.ic_add_shopping_cart_black_24dp);
                        imageView.setClickable(true);
                    }

                }).show();

            }
        });
        viewHolder.storeLogo.setTag(position);

        // Return the completed view to render on screen
        return convertView;

    }
}

