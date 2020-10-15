package com.painlessshopping.mohamed.findit;

/**
 * Handles themes
 *
 * Created by Mohamed on 2017-01-02.
 */

public class ThemeHandler {

    public final static int THEME_DEFAULT = R.style.Default;
    public static int theme = THEME_DEFAULT;

    public static void setTheme(int themeID){
        theme = themeID;
    }

    public static int getTheme(){
        return theme;
    }

}
