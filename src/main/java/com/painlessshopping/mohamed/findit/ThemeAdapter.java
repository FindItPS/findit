package com.painlessshopping.mohamed.findit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Controls theme formatting
 *
 * Created by Abdourahmane on 2017-01-05.
 */

public class ThemeAdapter  extends BaseAdapter {

    private ArrayList<Item> dataSet;
    Context mContext;
    View listView;
    int colorPrimary, colorPrimaryDark, colorAccent;

    // View lookup cache
    private static class ViewHolder {
        TextView themeTitle;
        Button select;
        View primary, primaryDark, accent;
    }

    public ThemeAdapter (Context context, View lv) {
        mContext=context;
        listView = lv;

    }

    @Override
    public int getCount() {
        return themeIDs.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        final int pos = position;

        viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.row_theme, parent, false);
        viewHolder.themeTitle = (TextView) convertView.findViewById(R.id.theme);
        viewHolder.select = (Button) convertView.findViewById(R.id.select);
        viewHolder.primary = (View) convertView.findViewById(R.id.primary);
        viewHolder.primaryDark = (View) convertView.findViewById(R.id.primaryDark);
        viewHolder.accent = (View) convertView.findViewById(R.id.accent);

        result = convertView;

        convertView.setTag(viewHolder);

        String title = mContext.getResources().getResourceEntryName(themeIDs[position]);
        TypedValue typedValue = new TypedValue();
        ContextThemeWrapper wrapper = new ContextThemeWrapper(mContext, themeIDs[position]);

        Resources.Theme theme = wrapper.getTheme();

        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        colorPrimary = typedValue.data;

        theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        colorPrimaryDark = typedValue.data;

        theme.resolveAttribute(R.attr.colorAccent, typedValue, true);
        colorAccent = typedValue.data;


        viewHolder.themeTitle.setText(title);
        viewHolder.primary.setBackgroundColor(colorPrimary);
        viewHolder.primaryDark.setBackgroundColor(colorPrimaryDark);
        viewHolder.accent.setBackgroundColor(colorAccent);

        viewHolder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ThemeHandler.setTheme(themeIDs[pos]);
                SharedPreferences.Editor editor = mContext.getSharedPreferences("MyPref", mContext.MODE_PRIVATE).edit();
                editor.putInt("theme", themeIDs[pos]);
                editor.commit();
                mContext.startActivity(new Intent(mContext, Display.class));

            }
        });

        // Return the completed view to render on screen
        return convertView;

    }

    private Integer[] themeIDs = {
            R.style.Default, R.style.Autumn, R.style.Winter, R.style.Spring, R.style.Summer, R.style.Amethyst
    };

}
