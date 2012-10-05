package act.mashup.global;

import org.jdom.Namespace;

public final class KV {

	// Key-Values

	public static final boolean useBoss = true;

	// Namespace
	public static final Namespace em = Namespace.getNamespace("em", "http://www.example.org/EngineModel");
	public static final Namespace gf = Namespace.getNamespace("gf", "http://www.example.org/GeneralFigure");

	// Item Keys
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";
	public static final String AUTHOR = "author";
	public static final String LINK = "link";
	public static final String PLACE = "place";
	public static final String COST = "cost";
	public static final String RELATIVE_IMAGE = "relateImage";
	public static final String PUBLISHDATE = "publishDate";
	public static final String LATITUDE = "lat";
	public static final String LONGITUDE = "lon";
	public static final String TYPE="type";

	// Url address for geo-tag news
	public static final String geoUrlPrefixWithUngeo = "http://ws.geonames.org/rssToGeoRSS?geoRSS=simple&addUngeocodedItems=true&feedUrl=";
	public static final String geoUrlPrefixWithoutUngeo = "http://ws.geonames.org/rssToGeoRSS?geoRSS=simple&addUngeocodedItems=false&feedUrl=";

	// Sogou Dictionary
	public static final String sogouDictPath = "/SogoulabDic.dic";

	// Places Dictionary
	public static final String placesDictPath = "/PlacesDic.dic";

	public static final String log4JPropertiesFile = "log4j.properties";

	// Similarity Thrashhold
	public static final double similarityThrashhold = 0.96;

	// Google API Key
	public static final String ENCODING = "UTF-8";
	public static final String googleAPIKey = "AIzaSyDvQkoudmKjqjSMWp81U4s30ki5yQU307A";

	// Yahoo Key
	public static final String yahooAPIKey = "YAHOO_KEY";

	// Weibo Key
	public static final String weiboConsumerKey = "3669719157";
	public static final String weiboConsumerSecret = "930d245d7ed96b95201862cbe27ca5b4";

}
