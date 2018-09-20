package com.mf.smax.alexa.request;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class JSONParserUtil {

    public static void main(String[] args) {
        String s = "{\"Results\":[{\"Properties\":{\"Priority\":\"LowPriority\"},\"RelatedProperties\":{},\"Aggregations\":{\"Count\":{\"Id\":10}}},{\"Properties\":{\"Priority\":\"MediumPriority\"},\"RelatedProperties\":{},\"Aggregations\":{\"Count\":{\"Id\":10}}},{\"Properties\":{\"Priority\":\"CriticalPriority\"},\"RelatedProperties\":{},\"Aggregations\":{\"Count\":{\"Id\":2}}},{\"Properties\":{\"Priority\":\"HighPriority\"},\"RelatedProperties\":{},\"Aggregations\":{\"Count\":{\"Id\":71}}}],\"meta\":{\"completion_status\":\"OK\",\"errorDetails\":null,\"total_count\":93,\"errorDetailsList\":[],\"errorDetailsMetaList\":[],\"query_time\":1537172532520293,\"correlation_id\":null}}";
        JSONObject obj = new JSONObject(s);
        JSONArray jsonArray = new JSONArray();
        jsonArray = obj.getJSONArray("Results");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject o = jsonArray.getJSONObject(i);
            //System.out.println(o);
            JSONObject property = o.getJSONObject("Properties");
            JSONObject count = o.getJSONObject("Aggregations").getJSONObject("Count");
            System.out.print(property.get("Priority"));
            System.out.println(count.get("Id"));

        }


    }

    public static HashMap<String, String> getPrirityAndCount(String str) {
        HashMap<String, String> list = new HashMap<String, String>();
        JSONObject obj = new JSONObject(str);
        JSONArray jsonArray = new JSONArray();
        jsonArray = obj.getJSONArray("Results");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject o = jsonArray.getJSONObject(i);
            //System.out.println(o);
            JSONObject property = o.getJSONObject("Properties");
            JSONObject count = o.getJSONObject("Aggregations").getJSONObject("Count");
            list.put(property.get("Priority").toString(), count.get("Id").toString());
            //System.out.print(property.get("Priority"));
            //System.out.println(count.get("Id"));
        }
        return list;

    }

    public static HashMap<String, String> getRecordListDescription(String str) {
        HashMap<String, String> list = new HashMap<String, String>();
        JSONObject obj = new JSONObject(str);
        JSONArray jsonArray = new JSONArray();
        jsonArray = obj.getJSONArray("entities");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject o = jsonArray.getJSONObject(i);
            //System.out.println(o);
            JSONObject property = o.getJSONObject("properties");

            list.put(property.get("Id").toString(), property.get("DisplayLabel").toString());
            //System.out.print(property.get("Priority"));
            //System.out.println(count.get("Id"));
        }
        return list;

    }
}
