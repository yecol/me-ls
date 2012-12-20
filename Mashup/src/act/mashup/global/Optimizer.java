package act.mashup.global;

import java.util.ArrayList;

public class Optimizer {

	/**
	 * @param args
	 */

	static int[][] a = { { 0, 1, 1, 1 },// ��ά����a�������ֽڵ�֮��Ĵ�С��ϵ���±�0��1��2��3�ֱ����Filter��Sort��
			{ 0, 0, 1, 1 },// Clip��GeoTag��a[i][j]=1��ʾi�ڵ�>j�ڵ㣬a[i][j]=0
			{ 0, 0, 0, 1 },// �������ڣ�����ʱ�õ���
			{ 0, 0, 0, 0 } };
	
	static void optMashup(ArrayList<EngineNode> mashup, int root){
		sort(mashup, root);
	}
	

	static void sort(ArrayList<EngineNode> list, int q) {
		int p, i, j, s, t, m = 0; // �����˼���ǵݹ����ÿ����֧��
		// p=Find(q);

		EngineNode ep = getEngineNodeByOutputId(list, q);

		// ������v��¼ÿ����֧�еĽڵ㣬����
		// v[m++]=p;
		ArrayList<EngineNode> v = new ArrayList<EngineNode>();
		v.add(ep);
		// ������Щ�ڵ����ð������
		while ((!ep.getClassId().contains("Merge")) && (!ep.getClassId().contains("Fetch")))

		// !strstr(node[p].cid,"Merge")&&!strstr(node[p].cid,"Fetch"))
		// //�鿴��֧���յ��Ƿ�ΪMerge��������Merge
		{ // ��ÿһ������ڵ�Ϊ�������ݹ���̣�����
			// v[m++]=p; //˵���˷�֧�ѵ���Ҷ�ӽڵ㣬�ݹ���ֹ��
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
	static int Fun(String ch)// �����ֽڵ�����ת������Ӧ�ı��
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
	// ð�������еĽ������̣���ע����ǽ���Ҫ���������ڵ��input[]��output���ɣ�id��ClassId���ý���
	{
		ArrayList<Integer> temp;

		temp = list.get(s).getInputs();
		list.get(s).setInputs(list.get(t).getInputs());
		list.get(t).setInputs(temp);

		temp = list.get(s).getOutputs();
		list.get(s).setOutputs(list.get(s).getOutputs());
		list.get(t).setOutputs(temp);

		// TODO:root����
		return;
	}

	// *******************************************************
	static EngineNode getEngineNodeByOutputId(ArrayList<EngineNode> list, int id) {
		// �����ȡ�ڵ�
		for (EngineNode e : list) {
			if (e.getOutputs().get(0) == id) {
				return e;
			}
		}
		return null;
	}

}
