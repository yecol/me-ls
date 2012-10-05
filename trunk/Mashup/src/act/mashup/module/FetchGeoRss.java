package act.mashup.module;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;

import com.sun.syndication.feed.module.georss.GeoRSSModule;
import com.sun.syndication.feed.module.georss.GeoRSSUtils;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * <p>
 * FetchGeoRss Module
 * <p>
 * 参数：
 * <ul>
 * <li>0.Rss源地址（必须）</li>
 * <li>1.是否保持没有地理标记的项目（必须）</li>
 * </ul>
 * <p>
 * 返回：内部数据结构Item的列表
 */

public class FetchGeoRss extends AbstractListModule {

	private String rssAddress;
	private List<SyndEntry> entries;

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	// 打印列表
	private void PrintItems() {
		for (Item i : items) {
			System.out.println(i.toString());
		}
	}

	@Override
	protected void Prepare() throws Exception {
		String urlString = en.getParas().getChildTextTrim("url", KV.gf);

		if (urlString.isEmpty() || urlString.length() == 0)
			throw new MalformedURLException();

		// 决定如何再构造Url地址
		String addUngeoItems = en.getParas().getChildTextTrim("addUngeoItems", KV.gf);
		if (addUngeoItems.equals("1")) {
			this.rssAddress = KV.geoUrlPrefixWithUngeo + urlString;
		} else {
			this.rssAddress = KV.geoUrlPrefixWithoutUngeo + urlString;
		}

	}

	@Override
	protected void Execute() throws Exception {
		if (items.isEmpty()) {
			URL _url = new URL(rssAddress);

			// 读取Rss源
			XmlReader _reader = new XmlReader(_url);
			SyndFeedInput _input = new SyndFeedInput();
			SyndFeed _feed = _input.build(_reader);

			// 得到Rss源中的每一个条目
			entries = _feed.getEntries();

			// 将每个条目转化为Engine的内部格式
			Item _item;
			for (SyndEntry entry : entries) {
				GeoRSSModule geoRSSModule = GeoRSSUtils.getGeoRSS(entry);

				_item = new Item();
				_item.setValue("title", entry.getTitle());
				if (!entry.getAuthor().trim().equals(""))
					_item.setValue("author", entry.getAuthor());
				_item.setValue("link", entry.getLink());
				_item.setValue("publishDate", entry.getPublishedDate() == null ? timeStamp.toString() : entry.getPublishedDate().toString());
				_item.setValue("description", entry.getDescription().getValue());
				_item.setValue("lat", Double.toString(geoRSSModule.getPosition().getLatitude()));
				_item.setValue("Lon", Double.toString(geoRSSModule.getPosition().getLongitude()));
				items.add(_item);
			}
		}
		// 将结果放入结果映射集
		rlt.SetResultList(items);

	}
}
