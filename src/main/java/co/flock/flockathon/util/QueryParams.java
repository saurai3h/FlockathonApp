/*
 * Copyright (C) 2015 Media.net Advertising FZ-LLC All Rights Reserved
 */

package co.flock.flockathon.util;

import java.util.Hashtable;
import java.util.Set;

public class QueryParams extends Hashtable<String,String[]> {

    public static String DEFAULT_REGEX = "^[A-Za-z0-9]+$";
    public static String UTF = "UTF-8";
    public static String ISO = "ISO-8859-1";

    public QueryParams(Hashtable<String,String[]> t) {
        super(t);
    }

    public String[] getSafe(String key){
        return this.containsKey(key)?this.get(key):null;
    }

    public String getSafeParam(String param, String defaultValue){
        try {
            return this.containsKey(param)?emptyToDefault((this.get(param))[0],defaultValue):defaultValue;
        }catch (Exception e){
            return defaultValue;
        }
    }

    public String getSafeParam(String param){
        return getSafeParam(param,null);
    }

    public String emptyToDefault(String target, String defaultValue){
        return target.trim().length()>0?target:defaultValue;
    }

    public String regexSanitize(String param){
        return regexSanitize(param, null);
    }

    public String regexSanitize(String param, String defaultValue){
        return regexSanitize(DEFAULT_REGEX,param, defaultValue);
    }

    public String regexSanitize(String regex, String param, String defaultValue){
        try {
            if(!this.containsKey(param)) return defaultValue;
            String paramValue = this.getParameter(param);
            return paramValue.matches(regex)?emptyToDefault(paramValue,defaultValue):defaultValue;
        }catch (Exception e){
            return defaultValue;
        }
    }

    public String getParameter(String key){
        return (this.get(key))[0];
    }

    @Override
    public String toString() {

        Set<String> keys = this.keySet();
        StringBuilder stringBuilder = new StringBuilder();
        for (String key: keys){
            stringBuilder.append(key);
            stringBuilder.append(" : ");
            stringBuilder.append(this.getSafeParam(key));
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}

