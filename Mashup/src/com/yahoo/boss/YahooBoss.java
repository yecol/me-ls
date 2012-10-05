/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yahoo.boss;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import act.mashup.global.ImageItem;

/**
 * Sample code to use Yahoo! Search BOSS
 * 
 * Please include the following libraries 1. Apache Log4j 2. oAuth Signpost
 * 
 * @author xyz
 */
public class YahooBoss {

	private static final Logger log = Logger.getLogger(YahooBoss.class);
	protected static String yahooServer = "http://yboss.yahooapis.com/ysearch/";
	// FIXME Please provide your consumer key here
	private static String consumer_key = "dj0yJmk9OFNieWNLVHhtOHBGJmQ9WVdrOVRIazNjbkJJTTJVbWNHbzlNakk0TmpReE9UWXkmcz1jb25zdW1lcnNlY3JldCZ4PTY5";
	// FIXME Please provide your consumer secret here
	private static String consumer_secret = "dd88322ff72774658f074c686d357c12fa464b0e";
	/** The HTTP request object used for the connection */
	private static StHttpRequest httpRequest = new StHttpRequest();
	/** Encode Format */
	private static final String ENCODE_FORMAT = "UTF-8";
	/** Call Type */
	private static final String callType = "images";
	private static final int HTTP_STATUS_OK = 200;

	public enum services {
		web, limitedweb, images, news, blogs, ads
	}

	/**
	 * 
	 * @return
	 */
	public int returnHttpData() throws UnsupportedEncodingException, Exception {

		if (this.isConsumerKeyExists() && this.isConsumerSecretExists()) {

			// Start with call Type
			String params = callType;

			// Add query
			params = params.concat("?q=");

			// Encode Query string before concatenating
			params = params.concat(URLEncoder.encode(this.getSearchString(), ENCODE_FORMAT));

			// Create final URL
			String url = yahooServer + params;

			// Create oAuth Consumer
			OAuthConsumer consumer = new DefaultOAuthConsumer(consumer_key, consumer_secret);

			// Set the HTTP request correctly
			httpRequest.setOAuthConsumer(consumer);

			try {
				log.info("sending get request to" + URLDecoder.decode(url, ENCODE_FORMAT));
				int responseCode = httpRequest.sendGetRequest(url);

				// Send the request
				if (responseCode == HTTP_STATUS_OK) {
					log.info("Response ");
				} else {
					log.error("Error in response due to status code = " + responseCode);
				}
				log.info(httpRequest.getResponseBody());

			} catch (UnsupportedEncodingException e) {
				log.error("Encoding/Decording error");
			} catch (IOException e) {
				log.error("Error with HTTP IO", e);
			} catch (Exception e) {
				log.error(httpRequest.getResponseBody(), e);
				return 0;
			}

		} else {
			log.error("Key/Secret does not exist");
		}
		return 1;
	}

	/**
	 * TODO return actual search String
	 * 
	 * @return
	 */
	public List<ImageItem> SearchImageWithKey(String searchKey, int resultSize) throws UnsupportedEncodingException, Exception {

		if (this.isConsumerKeyExists() && this.isConsumerSecretExists()) {

			List<ImageItem> resultImageItemList;

			// Start with call Type
			String params = callType;

			// Add query
			params = params.concat("?q=");

			// Encode Query string before concatenating
			params = params.concat(URLEncoder.encode(searchKey, ENCODE_FORMAT));

			// Create final URL
			String url = yahooServer + params;

			// Create oAuth Consumer
			OAuthConsumer consumer = new DefaultOAuthConsumer(consumer_key, consumer_secret);

			// Set the HTTP request correctly
			httpRequest.setOAuthConsumer(consumer);

			try {
				log.info("sending get request to" + URLDecoder.decode(url, ENCODE_FORMAT));
				int responseCode = httpRequest.sendGetRequest(url);

				// Send the request
				if (responseCode == HTTP_STATUS_OK) {
					resultImageItemList = new ArrayList<ImageItem>();

					JSONObject httpJsonResult = new JSONObject(httpRequest.getResponseBody());
					JSONArray results = httpJsonResult.getJSONObject("bossresponse").getJSONObject("images").getJSONArray("results");
					for (int i = 0; i < Math.min(resultSize, results.length()); i++) {
						ImageItem imageItem = new ImageItem();
						JSONObject result = results.getJSONObject(i);
						imageItem.setImageTitle(result.getString("title"));
						imageItem.setImageThumbURL(result.getString("thumbnailurl"));
						imageItem.setImageRefererURL(result.getString("refererurl"));
						imageItem.setImageURL(result.getString("url"));
						resultImageItemList.add(imageItem);
					}
					return resultImageItemList;

				} else {
					log.error("Error in response due to status code = " + responseCode);
				}
				log.info(httpRequest.getResponseBody());

			} catch (UnsupportedEncodingException e) {
				log.error("Encoding/Decording error");
			} catch (IOException e) {
				log.error("Error with HTTP IO", e);
			} catch (Exception e) {
				log.error(httpRequest.getResponseBody(), e);
			}
		} else {
			log.error("Key/Secret does not exist");
		}
		return null;
	}

	private String getSearchString() {
		return "ï¿½ï¿½ï¿½ï¿½";
	}

	private boolean isConsumerKeyExists() {
		if (consumer_key.isEmpty()) {
			log.error("Consumer Key is missing. Please provide the key");
			return false;
		}
		return true;
	}

	private boolean isConsumerSecretExists() {
		if (consumer_secret.isEmpty()) {
			log.error("Consumer Secret is missing. Please provide the key");
			return false;
		}
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		BasicConfigurator.configure();

		try {

			YahooBoss signPostTest = new YahooBoss();
			System.out.println(signPostTest.SearchImageWithKey("È«¹ú ¿¼ÑÐ", 2).toString());
		

		} catch (Exception e) {
			log.info("Error", e);
		}
	}
}
