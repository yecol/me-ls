package act.mashup.module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;

public class Rename extends AbstractListModule {

	private Integer in;
	private Map<String, String> renames;

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	@Override
	protected void Prepare() throws Exception {
		renames=new HashMap<String,String>();
		in = en.getInputs().get(0);
		items.addAll(results.get(in).GetResultList());
		List renamesElements = en.getParas().getChildren("rename", KV.gf);
		for (Object o : renamesElements) {
			Element el = (Element) o;
			renames.put(el.getChildTextTrim("oldName", KV.gf), el.getChildTextTrim("newName", KV.gf));
		}
		if (in == null || in == 0 || renames.isEmpty() || renames.size() == 0)
			throw new IOException();
	}

	@Override
	protected void Execute() throws Exception {
		// ××××××××××××××××××××××警告：引用操作××××××××××××××××××××××××××××××
		String _oldname;
		for (int i = 0; i < items.size(); i++) {
			for (Iterator<String> it = renames.keySet().iterator(); it.hasNext();) {
				_oldname = it.next();
				if (items.get(i).RenameKey(_oldname, renames.get(_oldname)))
					continue;
				else
					throw new Exception("Rename failed");
			}
		}
		rlt.SetResultList(items);

	}
}
