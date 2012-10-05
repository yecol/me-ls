package act.mashup.global;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import act.mashup.util.Log;

/**
 * Session Bean implementation class EngineManager
 */

public class EngineManager {

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	public Map<Integer, Integer> satisfyStatus;
	public Map<Integer, Boolean> doneStatus;
	private ArrayList<EngineNode> engineNodes;
	public Map<Integer, Result> results;
	public ArrayList<Integer> dataFlows;

	// 私有临时变量
	private ArrayList<EngineNode> satisfyingNodes;
	private Method prepareMethod;
	private Method runMethod;
	private String outputid;

	/**
	 * Default constructor.
	 */
	public EngineManager() {
		// TODO Auto-generated constructor stub
		satisfyStatus = new HashMap<Integer, Integer>();
		doneStatus = new HashMap<Integer, Boolean>();
		satisfyStatus.put(0, 1);// 初始条件
		engineNodes = new ArrayList<EngineNode>();
		satisfyingNodes = new ArrayList<EngineNode>();
		results = new HashMap<Integer, Result>();
		dataFlows = new ArrayList<Integer>();
	}

	// 从XML格式参数构造执行数组
	public void BuildEngine(String xmlString) {
		// TODO Auto-generated method stub

		// 临时变量
		Element figure;
		Element ioput;
		List ioputs;
		String classId;
		Integer id;
		boolean dynamic;
		Element paras;
		ArrayList attrIns;
		ArrayList inputs;
		ArrayList outputs;
		

		// 从字符串开始解析XML文档
		StringReader read = new StringReader(xmlString);
		//Log.logger.debug(xmlString.length()+xmlString);
		InputSource source = new InputSource(read);
		SAXBuilder sb = new SAXBuilder();

		try {
			Document doc = sb.build(source);
			Element rootElement = doc.getRootElement();
			outputid=rootElement.getAttributeValue("output").toString().trim();
			//Log.logger.debug("Parse begin");
			//Log.logger.debug("Root Element is " + rootElement.toString());
			List figures = rootElement.getChildren("figure", KV.em);
			// 对每一个figure进行对象化操作
			for (Iterator iter = figures.iterator(); iter.hasNext();) {
				figure = (Element) iter.next();
				//Log.logger.debug("Figure: " + figure.toString());

				// 获得属性
				classId = figure.getAttributeValue("classid", KV.gf);
				dynamic = figure.getAttributeValue("dynamic", KV.gf).trim().equals("1") ? true : false;
				id = Integer.parseInt(figure.getAttributeValue("id", KV.gf));
				doneStatus.put(id, false);

				// 获得属性输入
				attrIns = new ArrayList<Integer>();
				if (dynamic == true) {
					ioputs = figure.getChild("AttributeInput", KV.gf).getChildren("istream", KV.gf);
					for (Iterator it = ioputs.iterator(); it.hasNext();) {
						ioput = (Element) it.next();
						attrIns.add(Integer.parseInt(ioput.getValue().trim()));
					}
				}

				// 获得参数
				paras = figure.getChild("LogicalAttribute", KV.gf);

				// 获得输入
				ioputs = figure.getChild("interfaces", KV.gf).getChild("inputs", KV.gf).getChildren("input", KV.gf);
				inputs = new ArrayList<Integer>();
				for (Iterator it = ioputs.iterator(); it.hasNext();) {
					ioput = (Element) it.next();
					inputs.add(Integer.parseInt(ioput.getText()));
				}

				// 获得输出
				ioputs = figure.getChild("interfaces", KV.gf).getChild("outputs", KV.gf).getChildren("output", KV.gf);
				outputs = new ArrayList<Integer>();
				for (Iterator it = ioputs.iterator(); it.hasNext();) {
					ioput = (Element) it.next();
					outputs.add(Integer.parseInt(ioput.getText()));
					dataFlows.add(Integer.parseInt(ioput.getText()));
					satisfyStatus.put(Integer.parseInt(ioput.getText()), 0);
				}

				// 对象化
				engineNodes.add(new EngineNode(id, classId, paras, attrIns, inputs, outputs, dynamic));

			}

		} catch (JDOMException e) {
			Log.logger.fatal(e);
		} catch (IOException e) {
			Log.logger.fatal(e);
		}

		RunSequence();

	}

	// 运行可执行序列
	public void RunSequence() {
		while (doneStatus.containsValue(false) == true) {
			// 更新可执行数组
			QueryRunnable();

			// 线程级地运行组件
			ArrayList<EngineThread> threads = new ArrayList<EngineThread>();
			for (EngineNode en : satisfyingNodes) {

				EngineThread t = new EngineThread(en, results);
				threads.add(t);
				t.start();
			}

			// 将线程运行后的状态更新
			for (EngineThread t : threads) {
				try {
					t.join();
					t.updateStatus(satisfyStatus, doneStatus);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		//Log.logger.info("ALL MODULES EXECUTE OVER");
	}
	

	//获得XML文档
	public Document GetRlt(){
		Integer i=Integer.parseInt(outputid);
		if(i==0)
			return GetResult();
		else
			return GetResult(i);
	}
	
	
	private Document GetResult() {
		Document outDoc = new Document();
		Element rootsElement = new Element("roots");
		Iterator<Integer> iterator = dataFlows.iterator();
		while (iterator.hasNext()) {
			rootsElement.addContent(GetResult(iterator.next()).detachRootElement());
		}
		outDoc.setRootElement(rootsElement);
		return outDoc;
	}
	

	// 从result中生成XML
	private Document GetResult(Integer i) {
		
		Document outDoc = new Document();
		Element rootElement = new Element("root");
		rootElement.setAttribute("figureid", String.valueOf(i));
		rootElement.setAttribute("timestamp", this.results.get(i).GetTimeStamp());
		// 有错误
		if (this.results.get(i).GetStatus() == 0) {
			rootElement.setAttribute("status", "false");
			Element errormsg = new Element("errormsg");
			errormsg.setText(this.results.get(i).GetErrorMsg());
			rootElement.addContent(errormsg);
		}
		// 局部有错
		else if (this.results.get(i).GetStatus() == 2) {
			rootElement.setAttribute("status", "false");
			Element errormsg = new Element("errormsg");
			errormsg.setText(this.results.get(i).GetErrorMsg());
			rootElement.addContent(errormsg);
		}
		// 完全正确
		else {
			rootElement.setAttribute("status", "true");
			if (this.results.get(i).GetType() == Result.TYPE_LIST) {
				List<Item> _itemList = this.results.get(i).GetResultList();
				Element _el = null;
				for (Item _item : _itemList) {
					_el = new Element("item");
					for (Iterator<String> it = _item.getKeys().iterator(); it.hasNext();) {
						String _name = it.next();
						Element _ele = new Element(_name);
						Object obj = _item.getValue(_name);
						if(obj instanceof String) {
							Log.logger.debug("this.is.string");
							_ele.setText(_item.getValue(_name).toString());
						}
						else if(obj instanceof List){
							Log.logger.debug("this.is.List");
							for(Object o:(List)obj){
								if(o instanceof ImageItem){
									ImageItem ii=(ImageItem)o;
									Element _elem = ii.toElement();
									_ele.addContent(_elem);
								}else if(o instanceof VideoItem){
									Log.logger.debug("this.is.video");
									VideoItem vi=(VideoItem)o;
									Element _elem = vi.toElement();
									_ele.addContent(_elem);
								}
							}
						}
						_el.addContent(_ele);
					}
					rootElement.addContent(_el);
				}
			}
			else if(this.results.get(i).GetType() == Result.TYPE_MAP){
				Map<String,String> _itemMap = this.results.get(i).GetResultMap();
				Element _el = null;
				for (String key : _itemMap.keySet()) {
					_el=new Element("no"+key);
					_el.setText(_itemMap.get(key));
					rootElement.addContent(_el);
				}
			}
		}
		outDoc.setRootElement(rootElement);
		return outDoc;
	}

	// 查询可运行的节点
	private ArrayList QueryRunnable() {
		this.satisfyingNodes.clear();
		for (EngineNode node : engineNodes) {
			if (node.isSatisfy(satisfyStatus, doneStatus))
				this.satisfyingNodes.add(node);
		}
		return this.satisfyingNodes;
	}

}
