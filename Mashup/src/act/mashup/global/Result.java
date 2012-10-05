package act.mashup.global;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Result {
	//结果数据结构

	//静态值，表明结果类型
	static public final Integer TYPE_LIST = 1;
	static public final Integer TYPE_MAP = 2;

	private Integer status;
	private Date timeStamp;
	private Integer type;
	private String errorMsg;
	private List resultsList;
	private Map resultsMap;

	public Result(Integer type) {
		this.status = 1;
		this.timeStamp = new Date();
		this.type = type;
	}
	
	public Integer GetStatus(){
		return this.status;
	}
	
	public String GetTimeStamp(){
		return this.timeStamp.toString();
	}
	
	public String GetErrorMsg(){
		if(this.errorMsg==null){
			return "发生未知错误！";
		}
		else return this.errorMsg;
	}

	public void ErrorOccur(String errorMsg) {
		this.status = 2;
		this.errorMsg = errorMsg;
	}

	public List GetResultList() {
		return this.resultsList;
	}

	public Map GetResultMap() {
		return this.resultsMap;
	}

	public void SetResultList(List l) {
		this.resultsList = l;
	}

	public void SetResultMap(Map m) {
		this.resultsMap = m;
	}

	public Integer GetType() {
		return this.type;
	}
	
	public String toString(){
		return String.valueOf(this.GetStatus());
	}

}
