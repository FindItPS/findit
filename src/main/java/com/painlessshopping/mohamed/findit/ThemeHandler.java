package com.painlessshopping.mohamed.findit;

/**
 * Created by Abdourahmane on 2017-01-02.
 */

public class ThemeHandler {

    public final static int THEME_DEFAULT = R.style.Default;
    public static int theme = R.style.Amethyst;

    public static void setTheme(int themeID){
        theme = themeID;
    }

    public static int getTheme(){
        return theme;
    }

}
