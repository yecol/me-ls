package act.mashup.global;

import java.util.HashMap;

import org.jdom.Namespace;

public final class KV {

	// 常量
	public static final boolean useBoss = true;

	// 命名空间
	public static final Namespace em = Namespace.getNamespace("em", "http://www.example.org/EngineModel");
	public static final Namespace gf = Namespace.getNamespace("gf", "http://www.example.org/GeneralFigure");

	// Keys for engine file
	public static final String EF_OUTPUT = "output";
	public static final String EF_OUTPUTS = "outputs";
	public static final String EF_INPUT = "input";
	public static final String EF_INPUTS = "inputs";
	public static final String EF_CLASS_ID = "classid";
	public static final String EF_ROOT = "root";
	public static final String EF_FIGURE = "figure";
	public static final String EF_DYNAMIC = "dynamic";
	public static final String EF_ID = "id";
	public static final String EF_ISTREAM = "istream";
	public static final String EF_ATTRIBUTE_INPUT = "AttributeInput";
	public static final String EF_LOGICAL_ATTRIBUTE ="LogicalAttribute";
	public static final String EF_INTERFACES ="interfaces";
	
	

	// Item keys
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
	public static final String TYPE = "type";
	public static final String RANK = "rank";
	

	// Url address for geo-tag news
	public static final String geoUrlPrefixWithUngeo = "http://ws.geonames.org/rssToGeoRSS?geoRSS=simple&addUngeocodedItems=true&feedUrl=";
	public static final String geoUrlPrefixWithoutUngeo = "http://ws.geonames.org/rssToGeoRSS?geoRSS=simple&addUngeocodedItems=false&feedUrl=";

	// Sogou Dictionary
	public static final String sogouDictPath = "/SogoulabDic.dic";

	// Places Dictionary
	public static final String placesDictPath = "/PlacesDic.dic";

	public static final String log4JPropertiesFile = "log4j.properties";

	// Similarity Threshold
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
