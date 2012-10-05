package act.mashup.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.Result;
import act.mashup.util.Log;

public abstract class AbstractMapModule {

	protected Map<Integer, Result> results;
	protected EngineNode en;
	protected Date timeStamp,debugTime;
	protected Item item;
	protected Result rlt;
	protected ArrayList<Integer> dynamicInputs;

	/**
	 * Default constructor.
	 */
	public AbstractMapModule() {
		timeStamp = new Date();
		rlt = new Result(Result.TYPE_MAP);
		item = new Item();
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
			Log.logger.info("This module has runned "+(debugTime.getTime()-timeStamp.getTime())+"ms.");
		} catch (Exception e) {
			Log.logger.fatal(e.getStackTrace().toString());
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
