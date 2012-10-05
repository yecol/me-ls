package act.mashup.module;

import java.util.ArrayList;
import java.util.Map;

import org.jdom.Element;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;

public class Join extends AbstractListModule {
	// Join模块。将两个List结果通过关键字链接起来。分为左连接和右连接

	String linkByLeft; // 连接关键字
	String linkByRight;
	String left;// 是否是左连接。1为左连接，否则为右连接
	ArrayList<Integer> ins;// 输入流应该为2个

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	@Override
	protected void Prepare() throws Exception {
		ins = en.getInputs();
			
		linkByLeft = en.getParas().getChildTextTrim("linkByLeft", KV.gf);
		linkByRight = en.getParas().getChildTextTrim("linkByRight", KV.gf);
		left = en.getParas().getChildTextTrim("left", KV.gf);
		if (ins.size() != 2 || results.get(ins.get(0)).GetType() != Result.TYPE_LIST || results.get(ins.get(1)).GetType() != Result.TYPE_LIST)
			throw new Exception();
		if (left.equals("0")) {
			Integer i = ins.get(0);
			ins.set(0, ins.get(1));
			ins.set(1, i);
			String s=linkByLeft;
			linkByLeft=linkByRight;
			linkByRight=s;
		}
	}

	@Override
	protected void Execute() throws Exception {
		for (Object o1 : results.get(ins.get(0)).GetResultList()) {
			Item _item1 = (Item) o1;
			for (Object o2 : results.get(ins.get(1)).GetResultList()) {
				Item _item2 = (Item) o2;
				if (_item1.getValue(linkByLeft).equals(_item2.getValue(linkByRight))) {
					Item _item = new Item();
					for (String key : _item1.getKeys())
						_item.setValue(key, _item1.getValue(key));
					for (String key : _item2.getKeys()) {
						if (_item.getKeys().contains(key) == false)
							_item.setValue(key, _item2.getValue(key));
					}
					items.add(_item);
				}
			}
		}

		rlt.SetResultList(items);
	}

}
