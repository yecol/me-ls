package act.mashup.module;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.jdom.Element;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;
import act.mashup.util.ChinesePlaces;
import act.mashup.util.ChineseSegment;

public class ExtractGeo extends AbstractListModule {
	
	//支持动态配置

	private Integer in;
	private ChineseSegment cs;
	private ChinesePlaces cp;
	private String addUngeoItems;

	private String string;
	private ArrayList<String> segments;

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	@Override
	protected void Prepare() throws Exception {
		Element e = en.getParas().getChild("addUngeoItems", KV.gf);

		Integer istream;
		try {
			istream = Integer.parseInt(e.getAttributeValue("istream"));
		} catch (NumberFormatException nfe) {
			istream = 0;
		}
		if (en.isDynamic() == true && istream != 0)
			addUngeoItems = results.get(istream).GetResultMap().get("addUngeoItems").toString();
		else
			addUngeoItems = e.getValue().trim();

		cs = ChineseSegment.getInstance();
		cp = ChinesePlaces.getInstance();
		in = en.getInputs().get(0);
		items.addAll(results.get(in).GetResultList());
	}

	@Override
	protected void Execute() throws Exception {
		for (Iterator it = items.iterator(); it.hasNext();) {
			Item item = (Item) it.next();
			string = item.getValue(KV.TITLE).toString();
			segments = cs.getSegments(string);
			String place = ExtractGeoInformation(segments);
			if (place == null&&item.getKeys().contains(KV.DESCRIPTION)) {
				string = item.getValue(KV.DESCRIPTION).toString();
				segments = cs.getSegments(string);
				place = ExtractGeoInformation(segments);
			}
			if (place != null && place.length() != 0) {
				item.setValue(KV.PLACE, place);
			} else if (addUngeoItems.equals("0")) {
				it.remove();
			}
		}
		rlt.SetResultList(items);

	}

	private String ExtractGeoInformation(ArrayList<String> segments) {
		for (String segment : segments) {
			if (cp.FindPlace(segment) == true)
				return segment;
		}
		return null;
	}

}
