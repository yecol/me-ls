package act.mashup.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.Result;
import act.mashup.util.Log;

public abstract class AbstractListModule {

	protected Map<Integer, Result> results;
	protected EngineNode en;
	protected Date timeStamp,debugTime;
	protected List<Item> items;
	protected Result rlt;
	protected ArrayList<Integer> dynamicInputs;

	/**
	 * Default constructor.
	 */
	public AbstractListModule() {
		timeStamp = new Date();
		rlt = new Result(Result.TYPE_LIST);
		items = new ArrayList<Item>();
		dynamicInputs=new ArrayList<Integer>();
	}

	// 供Engine调用的方法
	public void run(EngineNode en, Map<Integer, Result> results) {
		ArrayList<Integer> outputs;
		Iterator<Integer> iterator;
		this.en = en;
		this.results = results;
		try {
			Prepare();
			Execute();
			debugTime=new Date();
			Log.logger.info(this.en.getClassId()+"\t"+(debugTime.getTime()-timeStamp.getTime()));
		} catch (Exception e) {
			Log.logger.fatal(e);
			rlt.ErrorOccur(e.getMessage());
		} finally {
			outputs = en.getOutputs();
			iterator = outputs.iterator();
			while (iterator.hasNext()) {
				results.put(iterator.next(), rlt);
			}
		}

	}

	// 准备阶段
	protected abstract void Prepare() throws Exception;

	// 运行阶段
	protected abstract void Execute() throws Exception;

}
