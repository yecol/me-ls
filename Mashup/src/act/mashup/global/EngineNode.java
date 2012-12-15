package act.mashup.global;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.AsyncContext;

import org.jdom.Element;

public class EngineNode {
	private Integer id;
	private String classId;
	private Element paras;
	private ArrayList<Integer> inputs;
	private ArrayList<Integer> outputs;
	private boolean satisfy;
	private boolean dynamic;
	private int averageTime;
	private AsyncContext context;

	// 构造方法
	public EngineNode(Integer id, String classId, Element paras, ArrayList<Integer> inputs, ArrayList<Integer> outputs, int averageTime) {
		super();
		this.id = id;
		this.classId = classId;
		this.paras = paras;
		this.inputs = inputs;
		this.outputs = outputs;
		this.dynamic = false;
		this.satisfy = false;
		this.averageTime = averageTime;
	}
	
	// 构造方法
	public EngineNode(Integer id, String classId, Element paras, ArrayList<Integer> attrIns,ArrayList<Integer> inputs, ArrayList<Integer> outputs, boolean isDynamic) {
		super();
		this.id = id;
		this.classId = classId;
		this.paras = paras;
		this.inputs = inputs;
		this.outputs = outputs;
		this.dynamic = isDynamic;
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
		return this.dynamic;
	}

	public int getAverageTime() {
		return averageTime;
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

	public AsyncContext getContext() {
		return context;
	}

	public void setContext(AsyncContext context) {
		this.context = context;
	}
	

	public void setInputs(ArrayList<Integer> inputs) {
		this.inputs = inputs;
	}

	public void setOutputs(ArrayList<Integer> outputs) {
		this.outputs = outputs;
	}

	// 重新检测是否满足运行条件
	private void CheckSatisfy(Map<Integer, Integer> stateArray) {
		if (satisfy == true)
			return;
		else {
			boolean singleSatisfy = true;
	
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
	@Override
	public String toString() {
		return "EngineNode [id=" + id + ", classId=" + classId + ", inputs=" + inputs + ", outputs=" + outputs + ", satisfy=" + satisfy + ", averageTime=" + averageTime + "]";
	}

	
}
