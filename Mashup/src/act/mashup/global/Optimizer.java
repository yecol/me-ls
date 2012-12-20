package act.mashup.global;

import java.util.ArrayList;

public class Optimizer {

	/**
	 * @param args
	 */

	static int[][] a = { { 0, 1, 1, 1 },// 二维数组a定义四种节点之间的大小关系，下标0，1，2，3分别代表Filter，Sort，
			{ 0, 0, 1, 1 },// Clip，GeoTag，a[i][j]=1表示i节点>j节点，a[i][j]=0
			{ 0, 0, 0, 1 },// 代表不大于；排序时用到；
			{ 0, 0, 0, 0 } };
	
	static void optMashup(ArrayList<EngineNode> mashup, int root){
		sort(mashup, root);
	}
	

	static void sort(ArrayList<EngineNode> list, int q) {
		int p, i, j, s, t, m = 0; // 排序的思想是递归的找每个分支的
		// p=Find(q);

		EngineNode ep = getEngineNodeByOutputId(list, q);

		// 用数组v记录每个分支中的节点，依次
		// v[m++]=p;
		ArrayList<EngineNode> v = new ArrayList<EngineNode>();
		v.add(ep);
		// 遍历这些节点进行冒泡排序；
		while ((!ep.getClassId().contains("Merge")) && (!ep.getClassId().contains("Fetch")))

		// !strstr(node[p].cid,"Merge")&&!strstr(node[p].cid,"Fetch"))
		// //查看分支的终点是否为Merge，是则以Merge
		{ // 的每一个输入节点为起点继续递归过程；不是
			// v[m++]=p; //说明此分支已到达叶子节点，递归终止；
			v.add(ep);
			// ep=Find(node[p].input[1]);
			ep = getEngineNodeByOutputId(list, ep.getInputs().get(0));
		}
		for (i = 0; i < v.size() - 1; i++)
			for (j = 0; j < v.size() - i - 1; j++) {
				s = Fun(list.get(j).getClassId());
				t = Fun(list.get(j + 1).getClassId());
				if (a[s][t] == 1)
					Swap(list, j, j + 1);
			}
		if (ep.getClassId().contains("Merge"))
			for (i = 1; i <= ep.getInputs().size(); i++)
				sort(list, ep.getInputs().get(i));
	}

	// *******************************************************
	static int Fun(String ch)// 将四种节点类型转换成相应的编号
	{
		if (ch.contains("Filter"))
			return 0;
		if (ch.contains("Sort"))
			return 1;
		if (ch.contains("Clip"))
			return 2;
		return 3;
	}

	// *******************************************************
	static void Swap(ArrayList<EngineNode> list, int s, int t)
	// 冒泡排序中的交换过程，需注意的是仅需要交换两个节点的input[]和output即可，id和ClassId不用交换
	{
		ArrayList<Integer> temp;

		temp = list.get(s).getInputs();
		list.get(s).setInputs(list.get(t).getInputs());
		list.get(t).setInputs(temp);

		temp = list.get(s).getOutputs();
		list.get(s).setOutputs(list.get(s).getOutputs());
		list.get(t).setOutputs(temp);

		// TODO:root更换
		return;
	}

	// *******************************************************
	static EngineNode getEngineNodeByOutputId(ArrayList<EngineNode> list, int id) {
		// 按输出取节点
		for (EngineNode e : list) {
			if (e.getOutputs().get(0) == id) {
				return e;
			}
		}
		return null;
	}

}
