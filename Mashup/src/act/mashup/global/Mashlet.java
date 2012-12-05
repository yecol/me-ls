package act.mashup.global;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import act.mashup.util.Log;

/*
 * ����֧
 * Created by Yecol Hsu on 2012.3.17
 * Implemented by 
 *  
 * */
public class Mashlet extends Thread implements Comparable {

	public static ArrayList<Mashlet> mashletQueue = null;
	public static HashMap<Integer, Integer> queueMap = null;
	public static HashMap<Integer, Date> startTimes = null;

	public static final int STATUE_READY_TO_RUN = 0;
	public static final int STATUE_WAIT_FOR_PREVS = 1;
	public static final int STATUE_FINISH_RUN = 2;
	public static final int STATUE_IS_DEAD = 3;
	public static final int STATUE_INITING = 4;

	private int mashletId;// Ψһ���к�
	private int mashupId;// Դmashup���к�

	private ArrayList<EngineNode> nodes;// �����Ĳ�����
	private ArrayList<Integer> brothers; // ��¼ӵ��ͬһ��������ֵܵ���֧�� size=5
	private ArrayList<Integer> prevs; // ��¼ǰ���ͺ�������֧��id��size=5

	private int next;// ��������ֻ֧��һ��

	private int status; // ��¼״̬���������ȴ�ǰ����ɣ������������
	private Result result;
	private int pmt;

	private long delayTime;// ��ʱ��������
	private long averageTime;// Ԥ������ʱ��
	private long completeTime;// Ԥ�����ʱ��

	private long prStartTime;// Ԥ�⿪ʼʱ��
	private long rlStartTime;// ʵ�ʿ�ʼʱ��

	public Mashlet() {
		super();
		this.status = STATUE_INITING;
		this.prStartTime = (new Date()).getTime() + (long) 1000000000000.0;
		this.setPriority(Thread.MIN_PRIORITY);
	}

	public int getMashletId() {
		return mashletId;
	}

	public void setMashletId(int mashletId) {
		this.mashletId = mashletId;
	}

	public int getMashupId() {
		return mashupId;
	}

	public void setMashupId(int mashupId) {
		this.mashupId = mashupId;
	}

	public ArrayList<EngineNode> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<EngineNode> nodes) {
		this.nodes = nodes;
	}

	public ArrayList<Integer> getBrothers() {
		return brothers;
	}

	public void setBrothers(ArrayList<Integer> brothers) {
		this.brothers = brothers;
	}

	public ArrayList<Integer> getPrevs() {
		return prevs;
	}

	public void setPrevs(ArrayList<Integer> prevs) {
		this.prevs = prevs;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public int getPmt() {
		return pmt;
	}

	public void setPmt(int pmt) {
		this.pmt = pmt;
	}

	public long getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(long delayTime) {
		this.delayTime = delayTime;
	}

	public long getAverageTime() {
		return averageTime;
	}

	public void setAverageTime(long averageTime) {
		this.averageTime = averageTime;
	}

	public long getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(long completeTime) {
		this.completeTime = completeTime;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return this.status;
	}

	public long getRealStartTime() {
		return this.rlStartTime;
	}

	public long getPredictStartTime() {
		return this.prStartTime;
	}

	public void setFinishInit() {
		this.status = STATUE_WAIT_FOR_PREVS;
		this.prStartTime = (new Date()).getTime() + delayTime;
	}

	public boolean needCheck() {
		if (this.status == STATUE_WAIT_FOR_PREVS)
			return true;
		else
			return false;
	}

	public boolean checkRunable() {
		int temp = 0;
		try {
			// �ڱ�ִ��ǰ�ȼ���ǲ�������֮ǰ������
			if (this.prevs == null) {
				return true;
			} else {
				for (int prev : this.prevs) {
					// ǰ��δ���
					temp = prev;
					if (mashletQueue.get(queueMap.get(prev)).getStatus() != STATUE_FINISH_RUN) {
						return false;
					}
				}

				// readyTime = new Date().getTime();
				// Log.logger.info("Mashlet waits \t Mashlet" + mashletId + "\t"
				// + String.valueOf(readyTime - initTime));
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("check runnable of " + mashletId + " prv= " +
			// temp + " mapv= " + queueMap.get(temp) + " letId in queue= "
			// + mashletQueue.get(queueMap.get(temp)).getMashletId());

		}
		return false;
	}

	public void run() {
		
		this.rlStartTime = (new Date()).getTime();
		Log.logger.info("Mashlet Time mltid_mpid\t " + this.getMashletId() + "\t" +this.getMashupId()+"\t"+this.rlStartTime);

		// ����������������
		

		// Log.logger.debug("mashlet" + mashletId + " of mashup" + mashupId +
		// " begin to run");
		// long beginRun = (new Date()).getTime();
		// Log.logger.info("Mashlet scheduling \t Mashlet" + mashletId + "\t" +
		// String.valueOf(beginRun - readyTime));

		// System.out.println("mashlet" + mashletId + " of mashup" + mashupId +
		// " begin to run");

		result = new Result(Result.TYPE_LIST);

		int j = nodes.size() - 1;

		if (this.prevs != null) {
			// �������ڴ˺ϲ�����i�ټ�1�����������кϲ��ڵ�
			// System.out.println("have prevs");
			j--;
			for (int prev : this.prevs) {

				Mashlet tMashlet = mashletQueue.get(queueMap.get(prev));
				long prevPmt = (long) ((this.rlStartTime - tMashlet.getRealStartTime()) * tMashlet.getMemorySize());

				if (tMashlet.getResult().GetResultList() != null && tMashlet.getResult().GetResultList().size() != 0) {
					result.GetResultList().addAll(tMashlet.getResult().GetResultList());
				}

				Log.logger.info("Mashlet PMT \t Mashlet" + tMashlet.getMashletId() + "\t" + String.valueOf(prevPmt));

				tMashlet.setStatus(STATUE_IS_DEAD);
				// ����������������֮ǰ����Щmashlet
				// removeMashlet(prev);
				// tMashlet = null;
			}
		}

		// ����ִ��nodes
		for (; j >= 0; j--) {

			EngineNode en = nodes.get(j);

			String interfaceName = "act.mashup.module." + en.getClassId();
			// Log.logger.debug(interfaceName);

			try {
				// ���乹�����ʵ��
				Class c = Class.forName(interfaceName);
				Object obj = c.newInstance();
				// ����
				Method runMethod = c.getDeclaredMethod("run", EngineNode.class, Result.class);
				runMethod.invoke(obj, en, this.result);

			} catch (ClassNotFoundException e) {
				Log.logger.fatal(e);
			} catch (SecurityException e) {
				Log.logger.fatal(e);
			} catch (NoSuchMethodException e) {
				Log.logger.fatal(e);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				Log.logger.fatal(e);
			} catch (IllegalArgumentException e) {
				Log.logger.fatal(e);
			} catch (InstantiationException e) {
				Log.logger.fatal(e);
			} catch (InvocationTargetException e) {
				Log.logger.fatal(e);
			} catch (Exception e) {
				Log.logger.fatal(e);
			}
		}

		// ִ������״̬����ͼ�¼
		this.status = STATUE_FINISH_RUN;

		// long runningTime = (new Date()).getTime() - beginRun;
		// Log.logger.info("Mashlet runs \t Mashlet" + mashletId + "\t" +
		// String.valueOf(runningTime));

		if (this.next == -1) {
			// System.out.println("Ending exe of mashup" + mashupId);

			// Log.logger.info("Mashup \t i \t Mashup" + mashupId +
			// " ends execution");
			Date end = new Date();
			Log.logger.info("Mashup ends \t Mashup" + mashupId + "\t" + String.valueOf(((end.getTime() - startTimes.get(mashupId).getTime()))));
			setStatus(STATUE_IS_DEAD);

		}
	}

	public void calculatePMT() {
		// ֻ������ DelayPMT ����

		Date cur = new Date();
		long currentTime = cur.getTime();
		long delayTime = currentTime - this.prStartTime;
		Log.logger.debug("delay = \t " + delayTime);

		// ����delayTime
		Mashlet mlt;
		int delayMemorySize = 0;
		if (prevs != null) {
			for (int prev : prevs) {
				// ǰ�������פ��
				try {
					mlt = mashletQueue.get(queueMap.get(prev));
					if (mlt.getStatus() == STATUE_FINISH_RUN) {
						delayMemorySize += mlt.getMemorySize();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (brothers != null) {
			for (int bro : brothers) {
				// �ֵܽڵ�Ľ��פ��
				try {
					mlt = mashletQueue.get(queueMap.get(bro));
					if (mlt.getStatus() == STATUE_FINISH_RUN) {
						delayMemorySize += mlt.getMemorySize();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// System.out.println("memory size = " + delayMemorySize);

		this.pmt = (int) (delayMemorySize * delayTime);
//		Log.logger.debug("pmt = \t " + pmt);
//		if (this.pmt == 0) {
//			this.setPriority(Thread.MIN_PRIORITY);
//		}
//		if (this.pmt > 100000000) {
//			this.setPriority(3);
//		}
//		if (this.pmt > 150000000) {
//			this.setPriority(5);
//		}
//		if (this.pmt > 200000000) {
//			this.setPriority(7);
//		}
//		if (this.pmt > 250000000) {
//			this.setPriority(9);
//		}
//		if (this.pmt > 300000000) {
//			this.setPriority(Thread.MAX_PRIORITY);
//		}
	}

	// private synchronized void removeMashlet(int mashletId) {
	// System.out.println("delete"+mashletId);
	// System.out.println("sizebefore"+mashletQueue.size()+"and index="+queueMap.get(mashletId));
	//
	//
	// Mashlet tMashlet = mashletQueue.get(queueMap.get(mashletId));
	// mashletQueue.remove(tMashlet);
	// //mashletQueue.
	//
	// System.out.println(queueMap);
	// queueMap.remove(mashletId);
	//
	// System.out.println("sizeafter"+mashletQueue.size());
	// tMashlet = null;
	// }

	public int getMemorySize() {
		// �õ��ڴ�ߴ�
		//TODO:
		return 0;
		//result.getResultSize();
	}

	@Override
	public String toString() {
		return "Mashlet [mashletId=" + mashletId + ", mashupId=" + mashupId + ", nodes=" + nodes + ", brothers=" + brothers + ", prevs=" + prevs + ", next=" + next + "]";
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Mashlet other = (Mashlet) o;
		return other.getPriority() - this.getPriority();
	}

}
