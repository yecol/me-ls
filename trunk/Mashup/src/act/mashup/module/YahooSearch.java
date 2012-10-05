package act.mashup.module;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.google.api.Yql;

import act.mashup.global.EngineNode;
import act.mashup.global.KV;
import act.mashup.global.Result;

public class YahooSearch extends AbstractListModule {

	String keyword;

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	@Override
	protected void Execute() throws Exception {
		try {
			items = Yql.execute(keyword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		rlt.SetResultList(items);

	}

	@Override
	protected void Prepare() throws Exception {
		keyword = en.getParas().getChildTextTrim("keyword", KV.gf);
		System.out.println(keyword);

	}

}
