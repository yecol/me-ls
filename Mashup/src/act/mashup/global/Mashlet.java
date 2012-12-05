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
 * 调度支
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

	private int mashletId;// 唯一序列号
	private int mashupId;// 源mashup序列号

	private ArrayList<EngineNode> nodes;// 包含的操作子
	private ArrayList<Integer> brothers; // 记录拥有同一个输出的兄弟调度支， size=5
	private ArrayList<Integer> prevs; // 记录前导和后续调度支的id，size=5

	private int next;// 后续调度支只有一个

	private int status; // 记录状态：就绪，等待前导完成，自身运行完成
	private Result result;
	private int pmt;

	private long delayTime;// 延时启动毫秒
	private long averageTime;// 预测运行时间
	private long completeTime;// 预测完成时间

	private long prStartTime;// 预测开始时间
	private long rlStartTime;// 实际开始时间

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
			// 在被执行前先检查是不是满足之前的条件
			if (this.prevs == null) {
				return true;
			} else {
				for (int prev : this.prevs) {
					// 前导未完成
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

		// 接受数据流和清理
		

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
			// 数据流在此合并，将i再减1，不需再运行合并节点
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
				// 接受数据流后清理之前的那些mashlet
				// removeMashlet(prev);
				// tMashlet = null;
			}
		}

		// 逆序执行nodes
		for (; j >= 0; j--) {

			EngineNode en = nodes.get(j);

			String interfaceName = "act.mashup.module." + en.getClassId();
			// Log.logger.debug(interfaceName);

			try {
				// 反射构造类的实例
				Class c = Class.forName(interfaceName);
				Object obj = c.newInstance();
				// 运行
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

		// 执行完后的状态变更和记录
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
		// 只计算了 DelayPMT 部分

		Date cur = new Date();
		long currentTime = cur.getTime();
		long delayTime = currentTime - this.prStartTime;
		Log.logger.debug("delay = \t " + delayTime);

		// 计算delayTime
		Mashlet mlt;
		int delayMemorySize = 0;
		if (prevs != null) {
			for (int prev : prevs) {
				// 前驱结果的驻留
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
				// 兄弟节点的结果驻留
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
		// 得到内存尺寸
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
