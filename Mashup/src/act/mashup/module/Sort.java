package act.mashup.module;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jdom.Element;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;

public class Sort extends AbstractListModule {

	private Comparator comparator;
	private Integer sortLength;
	private Integer in;
	private ArrayList<String> keys;
	private ArrayList<String> kinds;

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	// 获得参数，新建比较器
	@Override
	protected void Prepare() throws Exception {

		keys = new ArrayList<String>();
		kinds = new ArrayList<String>();

		List sorts = en.getParas().getChildren("sort", KV.gf);
		sortLength = sorts.size();
		for (int i = 0; i < sortLength; i++) {
			Element e = (Element) sorts.get(i);			
			keys.add(e.getChildTextTrim("sortKey", KV.gf));
			kinds.add(e.getChildTextTrim("sortKind", KV.gf));
		}

		comparator = new ComboComparator(keys, kinds);

	}

	// 进行排序
	@Override
	protected void Execute() throws Exception {
		in = en.getInputs().get(0);
		List temp = results.get(in).GetResultList();
		if (sortLength != 0)
			Collections.sort(temp, comparator);
		rlt.SetResultList(temp);
	}

	private class ComboComparator implements Comparator {

		private ArrayList<String> keys;
		private ArrayList<String> kinds;
		private Integer sortlength;

		public ComboComparator(ArrayList<String> keys, ArrayList<String> kinds) {
			this.keys = keys;
			this.kinds = kinds;
			sortLength = keys.size();

		}

		@Override
		public int compare(Object o1, Object o2) {
			// TODO Auto-generated method stub
			Item i1 = (Item) o1;
			Item i2 = (Item) o2;

			int i = 0;
			Collator collator = Collator.getInstance(Locale.CHINA);
			while (collator.compare(i1.getValue(keys.get(i)), i2.getValue(keys.get(i))) == 0 && i < sortLength - 1) {
				i++;
			}
			if (kinds.get(i).equals("1")) {
				// 正序。
				return collator.compare(i1.getValue(keys.get(i)), i2.getValue(keys.get(i)));
			} else
				// 逆序
				return -collator.compare(i1.getValue(keys.get(i)), i2.getValue(keys.get(i)));
		}
	}

}
