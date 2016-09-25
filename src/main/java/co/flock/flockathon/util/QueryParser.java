/*
 * Copyright (C) 2015 Media.net Advertising FZ-LLC All Rights Reserved
 */

package co.flock.flockathon.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class QueryParser {

    public static Hashtable<String,String[]> parseQueryString(String query) throws UnsupportedEncodingException {
        if(query==null) return new Hashtable<>();
        return getParameterHashTable(query);
    }

    public static Map<String, List<String>> getUrlParameters(String query)
            throws UnsupportedEncodingException {

        Map<String, List<String>> params = new LinkedHashMap<String, List<String>>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            String key;
            if(pair.length>0 && pair[0].length()>0) {
                try {
                    key = URLDecoder.decode(pair[0], "UTF-8");
                }catch (Exception e){
                    key = pair[0];
                }
                String value = "";
                if (pair.length > 1) {
                    try {
                        value = URLDecoder.decode(pair[1], "UTF-8");
                    }catch (Exception e){
                        value = pair[1];
                    }
                }
                List<String> values = isKeyAssociative(key)? params.get(key):null;
                if (values == null) {
                    values = new ArrayList<String>();
                    params.put(key, values);
                }
                values.add(value);
            }
        }
        return params;
    }

    public static boolean isKeyAssociative(String key){
        return key.endsWith("[]");
    }

    public static Hashtable<String, String[]> getParameterHashTable(String queryString) throws UnsupportedEncodingException {
        Map<String, List<String>> mapOfLists = getUrlParameters(queryString);

        Hashtable<String,String[]> mapOfArrays = new Hashtable<String,String[]>();
        for (String key : mapOfLists.keySet()) {
            mapOfArrays.put(key, mapOfLists.get(key).toArray(new String[] {}));
        }
        return mapOfArrays;
    }


}

