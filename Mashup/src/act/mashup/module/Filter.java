package act.mashup.module;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;
import act.mashup.util.Log;

public class Filter extends AbstractListModule {

	private Integer in;
	private String blockOrPermit;// 1:block,0:permit
	private String anyOrAll;// 1:any,0:all
	private ArrayList<Condition> conditions;

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	@Override
	protected void Prepare(){
		try{
		conditions = new ArrayList<Condition>();

		in = en.getInputs().get(0);
		// 获得参数
		blockOrPermit = en.getParas().getChildTextTrim("blockOrPermit", KV.gf);
		anyOrAll = en.getParas().getChildTextTrim("anyOrAll", KV.gf);
		List es = en.getParas().getChild("conditions", KV.gf).getChildren("condition", KV.gf);
		if (en.isDynamic() == false) {
			System.out.println("static");
			for (Object o : es) {
				Element e = (Element) o;
				Condition c = new Condition(e.getChildTextTrim("field", KV.gf), e.getChildTextTrim("relation", KV.gf), e.getChildTextTrim("key", KV.gf));
				conditions.add(c);
			}
		}
		else{
			//System.out.println("dynamic");
			for(Object o:es){
				Element e=(Element)o;
				Integer istream;
				Condition c;
				try {
					istream = Integer.parseInt(e.getChild("key", KV.gf).getAttributeValue("istream"));
				} catch (NumberFormatException nfe) {
					istream = 0;
				}
				System.out.println(istream);
				if(istream!=0){
					System.out.println("istream="+istream);
					System.out.println(results.get(istream).GetResultMap().toString());
					
					String dynamicInput=results.get(istream).GetResultMap().get(istream.toString()).toString();
					c=new Condition(e.getChildTextTrim("field", KV.gf), e.getChildTextTrim("relation", KV.gf), dynamicInput);	
				}
				else
					c=new Condition(e.getChildTextTrim("field", KV.gf), e.getChildTextTrim("relation", KV.gf), e.getChildTextTrim("key", KV.gf));
				conditions.add(c);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	protected void Execute(){
		// permit case
		try{
		List temp = results.get(in).GetResultList();
		for (Object o : temp) {
			Item _item = (Item) o;
			if (anyOrAll.equals("0") && (blockOrPermit.equals("1"))) {
				// any case, permit
				boolean flag = false;
				for (Condition con : conditions) {
					if (con.validate(_item) == true) {
						flag = true;
						break;
					}
				}
				if (flag == true)
					items.add(_item);
			}

			else if (anyOrAll.equals("0") && (blockOrPermit.equals("0"))) {
				// any case, block
				int falses = conditions.size();
				int count = 0;
				for (Condition con : conditions) {
					if (con.validate(_item) == false) {
						count++;
						continue;
					} else
						break;
				}
				if (count == falses)
					items.add(_item);
			}

			else if (anyOrAll.equals("1") && (blockOrPermit.equals("0"))) {
				// all case,block
				boolean flag = false;
				for (Condition con : conditions) {
					if (con.validate(_item) == false) {
						flag = true;
						break;
					}
				}
				if (flag == true)
					items.add(_item);
			}

			else if (anyOrAll.equals("1") && (blockOrPermit.equals("1"))) {
				// all case permit
				int trues = conditions.size();
				int count = 0;
				for (Condition con : conditions) {
					System.out.println("hello4");
					if (con.validate(_item) == true) {
						count++;
						continue;
					} else
						break;
				}
				if (count == trues)
					items.add(_item);

			}

		}
		rlt.SetResultList(items);
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	class Condition {
		String field;
		String relation;
		String keyword;

		Condition(String s1, String s2, String s3) {
			this.field = s1;
			this.relation = s2;
			this.keyword = s3;
		}

		public boolean validate(Item item) {
			if (relation.equals("0"))
				return this.isContains(item);
			else if (relation.equals("1"))
				return this.isNotContains(item);
			return false;
		}

		private boolean isContains(Item item) {
			return item.getValue(field).toString().contains(keyword);
		}

		private boolean isNotContains(Item item) {
			return !item.getValue(field).toString().contains(keyword);
		}
	}

}
