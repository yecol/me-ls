package act.mashup.module;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.jdom.Element;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Position;
import act.mashup.global.Result;
import act.mashup.util.LocalPlacesSearch;

public class LocalSearch extends AbstractListModule {

	/**
	 * @param args
	 */
	Position positionPara;
	LocalPlacesSearch localPlacesSearch;
	String typePara;
	ResultSet rs;

	Element el;
	Integer istream;

	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	@Override
	protected void Prepare() throws Exception {
		// TODO Auto-generated method stub
		localPlacesSearch = new LocalPlacesSearch();
		double lat = Double.parseDouble(en.getParas().getChildTextTrim("latitude", KV.gf));
		double lng = Double.parseDouble(en.getParas().getChildTextTrim("longitude", KV.gf));
		String type = en.getParas().getChildTextTrim("type", KV.gf);
		if (type.equals("0")) {
			// 0=¾Æµê
			typePara = LocalPlacesSearch.TYPE_HOTEL;
		} else if (type.equals("1")) {
			// 1=¾°µã
			typePara = LocalPlacesSearch.TYPE_RESORT;
		} else if (type.equals("2")) {
			// 2=·¹µê
			typePara = LocalPlacesSearch.TYPE_RESTAURANT;
		}

		// el = en.getParas().getChild("latitude", KV.gf);
		// istream= Integer.parseInt(el.getAttributeValue("istream"));
		// //System.out.println(istream);
		// String
		// latString=results.get(istream).GetResultMap().get(istream.toString()).toString();
		// double lat=Double.parseDouble(latString);
		//
		// el = en.getParas().getChild("longitude", KV.gf);
		// istream= Integer.parseInt(el.getAttributeValue("istream"));
		// String
		// lngString=results.get(istream).GetResultMap().get(istream.toString()).toString();
		// double lng=Double.parseDouble(lngString);

		positionPara = new Position(lat, lng);

		// System.out.println("lat="+lat+" long="+lng);

	}

	@Override
	protected void Execute() throws Exception {
		// TODO Auto-generated method stub
		rs = localPlacesSearch.SearchByPosition(positionPara, typePara);
		if (typePara == LocalPlacesSearch.TYPE_HOTEL) {
			while (rs.next()) {
				Item item = new Item();
				item.setValue(KV.TITLE, rs.getString("name"));
				item.setValue(KV.PLACE, rs.getString("address"));
				item.setValue(KV.LATITUDE, rs.getString("latitude"));
				item.setValue(KV.LONGITUDE, rs.getString("longitude"));
				item.setValue(KV.TYPE, typePara);

				items.add(item);
			}
		} else if (typePara == LocalPlacesSearch.TYPE_RESORT) {
			while (rs.next()) {
				Item item = new Item();
				item.setValue(KV.TITLE, rs.getString("name"));
				item.setValue(KV.PLACE, rs.getString("address"));
				item.setValue(KV.COST, rs.getString("ticket"));
				item.setValue(KV.LATITUDE, rs.getString("latitude"));
				item.setValue(KV.LONGITUDE, rs.getString("longitude"));
				item.setValue(KV.TYPE, typePara);
				items.add(item);
			}
		} else if (typePara == LocalPlacesSearch.TYPE_RESTAURANT) {
			while (rs.next()) {
				Item item = new Item();
				item.setValue(KV.TITLE, rs.getString("name"));
				item.setValue(KV.PLACE, rs.getString("address"));
				item.setValue(KV.COST, rs.getString("averagecost"));
				item.setValue(KV.LATITUDE, rs.getString("latitude"));
				item.setValue(KV.LONGITUDE, rs.getString("longitude"));
				item.setValue(KV.TYPE, typePara);
				items.add(item);
			}
		}

		rlt.SetResultList(items);

		localPlacesSearch.RemoveResouce();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		LocalPlacesSearch locale = new LocalPlacesSearch();
		ResultSet rs = locale.SearchByPosition(new Position(39.28, 116.0), LocalPlacesSearch.TYPE_HOTEL);
		try {
			System.out.println(rs.getFetchSize());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
