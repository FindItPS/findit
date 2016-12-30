package com.painlessshopping.mohamed.findit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Abdourahmane on 2016-12-29.
 */

public class ImageAdapter extends BaseAdapter implements View.OnClickListener {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }


    public Object getItem(int position) {
        return mThumbIds[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setTag(position);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setOnClickListener(this);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.books_icon_large, R.drawable.food_icon_large,
            R.drawable.pets_icon_large, R.drawable.tech_icon_large,
            R.drawable.toys_icon_large, R.drawable.clothing_icon_large

    };

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        final int id = mThumbIds[position];

        switch (id)
        {
            case R.drawable.books_icon_large:
                mContext.startActivity(new Intent(mContext, BookSearch.class));
                break;
            case R.drawable.food_icon_large:
                mContext.startActivity(new Intent(mContext, FoodSearch.class));
                break;
            case R.drawable.pets_icon_large:
                mContext.startActivity(new Intent(mContext, PetSearch.class));
                break;
            case R.drawable.tech_icon_large:
                mContext.startActivity(new Intent(mContext, TechSearch.class));

                break;
            case R.drawable.toys_icon_large:
                mContext.startActivity(new Intent(mContext, ToySearch.class));

                break;
            case R.drawable.clothing_icon_large:
                mContext.startActivity(new Intent(mContext, ClothingSearch.class));

                break;
        }
    }
}