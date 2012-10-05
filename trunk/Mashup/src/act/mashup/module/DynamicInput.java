package act.mashup.module;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import act.mashup.global.EngineNode;
import act.mashup.global.Result;
import act.mashup.util.Log;

public class DynamicInput extends AbstractMapModule {
	
	Map<String,String> inputsMap;
	List el;

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}


	@Override
	protected void Prepare() throws Exception {
		inputsMap=new HashMap<String, String>();
		el=en.getParas().getChildren();
	}

	
	@Override
	protected void Execute() throws Exception {
		//获得参数
		
		Element _element;
		for(Object o:el){
			_element=(Element)o;
			Iterator iterator = en.getOutputs().iterator();
			while (iterator.hasNext()) {
				inputsMap.put(iterator.next().toString(), _element.getValue().trim());
			}
		    
		    //Log.logger.debug(inputsMap.toString());
			rlt.SetResultMap(inputsMap);
		}	
	}
}
