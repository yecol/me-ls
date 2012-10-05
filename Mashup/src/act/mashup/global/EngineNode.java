package act.mashup.global;

import java.util.ArrayList;
import java.util.Map;

import org.jdom.Element;

import act.mashup.util.Log;

public class EngineNode {
	private Integer id;
	private String classId;
	private Element paras;
	private final ArrayList<Integer> attrIns;
	private final ArrayList<Integer> inputs;
	private final ArrayList<Integer> outputs;
	private boolean isDynamic;
	private boolean satisfy;

	// 构造方法
	public EngineNode(Integer id, String classId, Element paras, ArrayList<Integer> attrIns,ArrayList<Integer> inputs, ArrayList<Integer> outputs, boolean isDynamic) {
		super();
		this.id = id;
		this.classId = classId;
		this.paras = paras;
		this.attrIns=attrIns;
		this.inputs = inputs;
		this.outputs = outputs;
		this.isDynamic = isDynamic;
		this.satisfy = false;
	}

	// 返回满足与否，并在每次查询满足与否前重新检测
	public boolean isSatisfy(Map<Integer, Integer> status, Map<Integer, Boolean> doneStatus) {
		if (doneStatus.get(this.id) == true)
			return false;
		else {
			CheckSatisfy(status);
			return satisfy;
		}
	}

	public Integer getId() {
		return this.id;
	}
	
	public boolean isDynamic(){
		return this.isDynamic;
	}

	public String getClassId() {
		return this.classId;
	}

	public Element getParas() {
		return this.paras;
	}

	public ArrayList<Integer> getInputs() {
		return this.inputs;
	}

	public ArrayList<Integer> getOutputs() {
		return this.outputs;
	}


	// 重新检测是否满足运行条件
	private void CheckSatisfy(Map<Integer, Integer> stateArray) {
		if (satisfy == true)
			return;
		else {
			//Log.logger.debug("stateArray:"+stateArray.toString());
			
			//Log.logger.debug("attrIns:"+this.attrIns.toString());
			boolean singleSatisfy = true;
			//如果是动态参数输入模块则检测是否准备就绪
			if(this.isDynamic==true&&this.attrIns.size()!=0){
				for(Integer i:this.attrIns){
					if(stateArray.get(i)==0){
						singleSatisfy = false;
						break;
					}
				}
			}	
			for (Integer i : this.inputs) {
				if (stateArray.get(i) == 0) {
					singleSatisfy = false;
					break;
				}
			}// 每一个输入条件都满足才置满足条件的值为真
			if (singleSatisfy == true)
				satisfy = true;
		}
	}

	// 测试
	public String toString() {
		return "id=" + id + " classId=" + classId + " paras=" + paras.toString() + " inputs=" + inputs.toString() + " outputs=" + outputs.toString();
	}

}
