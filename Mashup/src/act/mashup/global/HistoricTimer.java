package act.mashup.global;

import java.util.HashMap;
import java.util.Map;

public class HistoricTimer {

	/**
	 * @param args
	 */
	private static final HistoricTimer instance = new HistoricTimer();

	final int DEFAULT_TIME = 200;
	final HashMap<String, Integer> predictTimes;

	private HistoricTimer() {
		predictTimes = new HashMap<String, Integer>();
		predictTimes.put("Crop", 60);
		predictTimes.put("ExtractGeo", 500);
		predictTimes.put("ExtractOne", 60);
		predictTimes.put("FetchGeoRss", 1000);
		predictTimes.put("FetchRss", 1000);
		predictTimes.put("FetchHtml", 1000);
		predictTimes.put("FetchXml", 1000);
		predictTimes.put("Filter", 300);
		predictTimes.put("Merge", 100);
		predictTimes.put("Rename", 100);
		predictTimes.put("Sort", 100);
	}

	static public HistoricTimer getInstance() {
		return instance;
	}

	public Integer getTime(String key) {
		int time = 0;
		try {
			time = this.predictTimes.get(key);
		} catch (Exception e) {
			time = DEFAULT_TIME;
		}
		return time;
	}
}
