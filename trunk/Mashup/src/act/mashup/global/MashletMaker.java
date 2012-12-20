package act.mashup.global;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.AsyncContext;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import act.mashup.util.Log;

public class MashletMaker implements Runnable {

	private AsyncContext ctx;

	public static ArrayList<Mashlet> mashletQueue = null;
	public static HashMap<Integer, Integer> queueMap = null;
	public static HashMap<Integer, Date> startTimes = null;

	private static int globalMashupId = 1;
	private static int mashletId = 1;

	private int mashupId = 0;

	ArrayList<EngineNode> engineNodes = new ArrayList<EngineNode>();

	public MashletMaker(AsyncContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public void run() {
		// 临时变量
		Element figure;
		Element ioput;
		List ioputs;
		String classId;
		Integer id;
		Element paras;
		ArrayList inputs;
		ArrayList outputs;
		int rootNodeId = 0, rootLocation = 0;

		// HashMap<Integer, Integer> nodeMap = new HashMap<Integer, Integer>();

		// 获取请求串
		String xmlString = ctx.getRequest().getParameter("xml").toString();

		// 从字符串开始解析XML文档
		StringReader read = new StringReader(xmlString);
		InputSource source = new InputSource(read);
		SAXBuilder sb = new SAXBuilder();

		try {
			Document doc = sb.build(source);
			Element rootElement = doc.getRootElement();
			List figures = rootElement.getChildren("figure", KV.em);

			// 对每一个figure进行对象化操作
			for (Iterator iter = figures.iterator(); iter.hasNext();) {
				figure = (Element) iter.next();

				// 获得属性
				classId = figure.getAttributeValue("classid", KV.gf);

				int averageTime = HistoricTimer.getInstance().getTime(classId);

				id = Integer.parseInt(figure.getAttributeValue("id", KV.gf));

				if (figure.getAttributeValue(KV.EF_ROOT, KV.gf) != null && figure.getAttributeValue(KV.EF_ROOT, KV.gf).equals("1")) {
					rootNodeId = id;
					rootLocation = engineNodes.size();
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
				}

				// 对象化
				// nodeMap.put(id, engineNodes.size());
				engineNodes.add(new EngineNode(id, classId, paras, inputs, outputs, averageTime));

			}
		} catch (JDOMException e) {
			Log.logger.fatal(e);
		} catch (IOException e) {
			Log.logger.fatal(e);
		}

		ArrayList<EngineNode> curMashup = engineNodes;

		// System.out.println("yes parsed rootNodeID=" + rootNodeId);
		// Optimizer.sort(curMashup, rootLocation);

		EngineNode root = getEngineNodeById(rootNodeId);
		Mashlet mashupLetRoot = dfs(root, -1, mashletQueue, queueMap);
		mashupLetRoot.setBrothers(null);
		mashupLetRoot.setFinishInit();
		mashupLetRoot.setAsynContext(ctx);

		// mashupLetRoot finish
		setCompleteTime(mashupLetRoot, mashletQueue, queueMap);
		setDelayTime(mashupLetRoot, mashupLetRoot.getCompleteTime(), mashletQueue, queueMap);

		Optimizer.sort(curMashup, rootLocation);

		// System.out.println("this is mashlets and size is " +
		// mashletQueue.size());
		for (int j = 0; j < mashletQueue.size(); j++) {
			if (mashletQueue.get(j) != null)
				System.out.println(mashletQueue.get(j));
		}

		startTimes.put(mashupId, new Date());

	}

	private void setCompleteTime(Mashlet root, ArrayList<Mashlet> mashlets, HashMap<Integer, Integer> queueMap) {
		if (root.getPrevs() == null) { // entry mashLet
			root.setCompleteTime(root.getAverageTime());
			return;
		}

		long maxCompleteTime = 0;
		for (Integer prevId : root.getPrevs()) {
			setCompleteTime(mashlets.get(queueMap.get(prevId)), mashlets, queueMap);
			maxCompleteTime = Math.max(maxCompleteTime, mashlets.get(queueMap.get(prevId)).getCompleteTime());
		}

		root.setCompleteTime(root.getAverageTime() + maxCompleteTime);
	}

	private void setDelayTime(Mashlet root, long completeTime, ArrayList<Mashlet> mashupLets, HashMap<Integer, Integer> queueMap) {

		if (root.getPrevs() == null) { // entry mashLet
			root.setDelayTime(completeTime - root.getAverageTime());
			return;
		}

		for (Integer prevId : root.getPrevs())
			setDelayTime(mashupLets.get(queueMap.get(prevId)), completeTime - root.getAverageTime(), mashupLets, queueMap);

		root.setDelayTime(completeTime - root.getAverageTime());
	}

	private Mashlet dfs(EngineNode root, int nextLetId, ArrayList<Mashlet> mashupLets, HashMap<Integer, Integer> queueMap) {
		Mashlet mashlet = new Mashlet();
		mashlet.setMashupId(mashupId);
		mashlet.setMashletId(mashletId);

		queueMap.put(mashletId, mashupLets.size());
		mashupLets.add(mashlet);

		ArrayList<EngineNode> nodeList = new ArrayList<EngineNode>();
		nodeList.add(root);

		mashletId++;

		mashlet.setNext(nextLetId);

		ArrayList<Integer> inputs = root.getInputs();
		long averageTime = 0;
		while (inputs.size() == 1 && inputs.get(0) != 0) {
			averageTime += root.getAverageTime();
			root = getEngineNodeByOutputId(inputs.get(0));
			nodeList.add(root);
			inputs = root.getInputs();
		}

		mashlet.setNodes(nodeList);

		averageTime += root.getAverageTime();
		mashlet.setAverageTime(averageTime);

		if (inputs.size() == 1 && inputs.get(0) == 0) { // entry engineNode
			// System.out.println(mashletId - 1);
			mashlet.setPrevs(null);
			return mashlet;
		}

		ArrayList<Integer> prevIds = new ArrayList<Integer>();

		ArrayList<Mashlet> prevMashupLets = new ArrayList<Mashlet>();
		for (Integer inputId : inputs) {
			Mashlet prevMashupLet = dfs(getEngineNodeByOutputId(inputId), mashlet.getMashletId(), mashupLets, queueMap);
			prevIds.add(prevMashupLet.getMashletId());
			prevMashupLets.add(prevMashupLet);
		}

		mashlet.setPrevs(prevIds);

		if (prevMashupLets.size() == 1) {
			prevMashupLets.get(0).setBrothers(null);
		} else {
			for (int i = 0; i < prevMashupLets.size(); i++) {
				ArrayList<Integer> brotherIds = new ArrayList<Integer>();
				for (int j = 0; j < prevIds.size(); j++) {
					if (j != i)
						brotherIds.add(prevIds.get(j));
				}
				prevMashupLets.get(i).setBrothers(brotherIds);
				prevMashupLets.get(i).setFinishInit();
			}
		}

		return mashlet;
	}

	public EngineNode getEngineNodeById(int id) {
		// 按ID取节点
		for (EngineNode e : engineNodes) {
			if (e.getId() == id) {
				return e;
			}

		}
		return null;
	}

	public EngineNode getEngineNodeByOutputId(int id) {
		// 按输出取节点
		for (EngineNode e : engineNodes) {
			if (e.getOutputs().get(0) == id) {
				return e;
			}

		}
		return null;
	}
}
