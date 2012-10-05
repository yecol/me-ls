package act.mashup.module;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPather;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;
import act.mashup.util.Log;

public class FetchHtml extends AbstractListModule {

	private HtmlCleaner cleaner;
	private String inUrl;
	private String url;
	private String xPath;
	private DateFormat dateFormat;
	private String baseUrl;

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	@Override
	protected void Prepare() throws Exception {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		inUrl = en.getParas().getChildTextTrim("url", KV.gf);
		if (inUrl.substring(inUrl.lastIndexOf("/")).contains(".")) {
			// a page url
			url = inUrl.substring(0, inUrl.lastIndexOf("/") + 1);
		} else {
			// a site url
			url = inUrl.endsWith("/") ? inUrl : inUrl.concat("/");
		}
		xPath = en.getParas().getChildTextTrim("xPath", KV.gf);
		if (xPath.endsWith("//a") == false) {
			xPath += "//a";
		}
		System.out.println(xPath);
		Log.logger.debug(url + xPath);
		cleaner = new HtmlCleaner();
		baseUrl = url.substring(0, url.indexOf('/', 7) + 1);
		System.out.println(url + " " + baseUrl);

	}

	@Override
	protected void Execute() throws Exception {
		TagNode htmlNode = cleaner.clean(new URL(inUrl));
		Date debugTime1 = new Date();
		Log.logger.info(this.en.getClassId() + "\t" + (debugTime1.getTime() - timeStamp.getTime()));

		XPather xPather = new XPather(xPath);
		Object[] fitNodes = xPather.evaluateAgainstNode(htmlNode);
		Date debugTime2 = new Date();
		Log.logger.info(this.en.getClassId() + "\t" + (debugTime2.getTime() - debugTime1.getTime()));
		for (Object o : fitNodes) {
			TagNode tn = (TagNode) o;
			Item _item = new Item();
			_item.setValue(KV.TITLE, tn.getText().toString());
			String href = tn.getAttributeByName("href");

			// Log.logger.debug("before\t"+href);

			if (href.startsWith("mailto") || href.startsWith("javascript:"))
				continue;

			if (!href.startsWith("http://")) {

				// relative path
				String _url = url;
				int slashs = 0;
				if (href.indexOf("./") != -1) {
					_url = _url.substring(0, _url.lastIndexOf("/"));
					slashs = href.indexOf("./");
					while (slashs != 0) {
						_url = _url.substring(0, _url.lastIndexOf("/"));
						slashs--;
					}
					href = _url.concat(href.substring(href.indexOf("./") + 1));
				} else if (href.startsWith("/"))
					href = baseUrl.concat(href.substring(1));
				else
					href = _url.concat(href);

			}
			// Log.logger.debug("after\t"+href);
			_item.setValue(KV.LINK, href);

			_item.setValue(KV.PUBLISHDATE, dateFormat.format(timeStamp));
			items.add(_item);
		}
		Date debugTime3 = new Date();
		Log.logger.info(debugTime3.getTime() - debugTime2.getTime());
		rlt.SetResultList(items);

	}

}
