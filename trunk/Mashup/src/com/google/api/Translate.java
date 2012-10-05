package com.google.api;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import act.mashup.global.KV;
import act.mashup.util.Log;

import com.google.api.GoogleAPI;
import com.google.api.Language;


public final class Translate extends GoogleAPI {
	
	/**
	 * Constants.
	 */
    private static final String
    
   
    URL = "https://www.googleapis.com/language/translate/v2?key=#KEY#&target=#TO#";
    		

    public static ArrayList<String> execute(final String text, final String to) throws Exception {
    	//validateReferrer();
    	
    	String wrapedURL=URL.replaceAll("#KEY#", KV.googleAPIKey).replaceAll("#TO#", to)+text;
    	Log.logger.info(wrapedURL);
    	final URL url = new URL(wrapedURL);
    	
    	final JSONObject json = retrieveJSON(url);
    	
    	return getJSONResponse(json);
    }
 
    

    
    
    private static ArrayList<String> getJSONResponse(final JSONObject json) throws Exception {
    	ArrayList<String> returnText=new ArrayList<String>();	
    	JSONArray jsonArray=json.getJSONObject("data").getJSONArray("translations");
    	for(int i=0;i<jsonArray.length();i++)
    		returnText.add(jsonArray.getJSONObject(i).getString("translatedText"));
    	return returnText;
    		
    }
    
    public static void main(String[] args) throws Exception{
    	//System.out.println(Translate.execute("yes great",Language.CHINESE_SIMPLIFIED));
    }
    
    
}

