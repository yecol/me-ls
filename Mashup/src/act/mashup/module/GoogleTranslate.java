package act.mashup.module;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.google.api.Language;
import com.google.api.Translate;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;
import act.mashup.util.Log;

public class GoogleTranslate extends AbstractListModule {

	private Integer in;
	private String targetLanguage;
	private ArrayList<String> keys;

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	@Override
	protected void Prepare() throws Exception {

		targetLanguage = en.getParas().getChildTextTrim("target", KV.gf);

		in = en.getInputs().get(0);
		items.addAll(results.get(in).GetResultList());
		Object[] os = items.get(0).getKeys().toArray();
		keys = new ArrayList<String>();
		for (int i = 0; i < os.length; i++) {
			String s = (String) os[i];
			if (s.equalsIgnoreCase("publishDate") == false && s.equalsIgnoreCase("link")==false) {
				keys.add((String) os[i]);
			}
		}
		//Log.logger.info(keys.toString());
	}

	@Override
	protected void Execute() throws Exception {
		try {
			for (Iterator it = items.iterator(); it.hasNext();) {
				Item item = (Item) it.next();
				String text = "";
				for (int j = 0; j < keys.size(); j++) {
					text = text + "&q=" + URLEncoder.encode(item.getValue(keys.get(j)).toString(), KV.ENCODING);
				}
				ArrayList<String> resultText = Translate.execute(text, targetLanguage);
				for (int j = 0; j < keys.size(); j++) {
					item.setValue(keys.get(j), resultText.get(j));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rlt.SetResultList(items);
	}

}
