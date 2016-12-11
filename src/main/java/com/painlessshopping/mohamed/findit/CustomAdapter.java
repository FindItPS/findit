package com.painlessshopping.mohamed.findit;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
        ImageView storeLogo;
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
        Item item=(Item)object;

        switch (v.getId())
        {
            case R.id.store:
                Snackbar.make(v, "This item is being sold at " + item.getStore() + "." , Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.itemTitle = (TextView) convertView.findViewById(R.id.title);
            //viewHolder.itemStore = (TextView) convertView.findViewById(R.id.type);
            viewHolder.itemPrice = (TextView) convertView.findViewById(R.id.price);
            viewHolder.storeLogo = (ImageView) convertView.findViewById(R.id.store);

            result = convertView;

            convertView.setTag(viewHolder);

        viewHolder.itemTitle.setText(item.getTitle());
        viewHolder.itemPrice.setText("$" + item.getPrice());
        viewHolder.storeLogo.setOnClickListener(this);
        viewHolder.storeLogo.setTag(position);

        // Return the completed view to render on screen
        return convertView;

    }
}

