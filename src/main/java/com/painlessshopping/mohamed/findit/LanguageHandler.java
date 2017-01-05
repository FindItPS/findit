package com.painlessshopping.mohamed.findit;

/**
 * Created by Abdourahmane on 2017-01-05.
 */

public class LanguageHandler {

    public static final String defaultLang = "en";

    public static String lang = "en";

    public static void setLang(String l){
        lang = l;
    }

    public static String getLang(){
        return lang;
    }

}
