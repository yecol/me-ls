package com.google.api;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.util.Log;

public class Yql extends GoogleAPI {
	/**
	 * Constants.
	 */
	private static final String URL = "http://query.yahooapis.com/v1/public/yql?format=json&q=";
	// private static final String PARA =
	// "select * from search.web where query='#KEYWORD#' and appid='#APPID#'";
	private static final String PARA = "select%20*%20from%20search.web%20where%20query='#KEYWORD#'%20and%20appid='#APPID#'";

	public static ArrayList<Item> execute(final String text) throws Exception {
		// validateReferrer();
		String textEncode = URLEncoder.encode(text,"UTF-8");

		String para = PARA.replaceAll("#KEYWORD#", textEncode).replaceAll("#APPID#", KV.yahooAPIKey);
		String wrapedURL = URL + para;
		
		final URL url = new URL(wrapedURL);

		final JSONObject json = retrieveJSON(url);
		//System.out.println(json.toString());
		return getJSONResponse(json);
		//return null;
	}

	private static ArrayList<Item> getJSONResponse(final JSONObject json) throws Exception {
		ArrayList<String> returnText = new ArrayList<String>();
		JSONArray jsonArray = json.getJSONObject("query").getJSONObject("results").getJSONArray("result");
		//System.out.println(jsonArray.toString());
		//TODO
		ArrayList<Item> items=new ArrayList<Item>();
		//String[] names=json.getNames(jo)
		for (int i = 0; i < jsonArray.length(); i++){
			Item item=new Item();
			item.setValue("url", jsonArray.getJSONObject(i).getString("url"));
			item.setValue("title", jsonArray.getJSONObject(i).getString("title"));
			item.setValue("description", jsonArray.getJSONObject(i).get("abstract").toString());
			items.add(item);
		}
		//System.out.println(items.toString());
		return items;

	}

	public static void main(String[] args) throws Exception {
		Yql.execute("หับü");
	}

}
