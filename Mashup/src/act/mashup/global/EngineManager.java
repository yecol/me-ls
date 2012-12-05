package act.mashup.global;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

import act.mashup.util.Log;

/**
 * Session Bean implementation class EngineManager
 */

public class EngineManager extends TimerTask{

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;

	public static HashMap<Integer, Date> startTimes = new HashMap<Integer, Date>();
	public static ArrayList<Mashlet> mashletsQueue = new ArrayList<Mashlet>();
	public static HashMap<Integer, Integer> queueMap = new HashMap<Integer, Integer>();// 为了方便找点，映射<mashletid,array下标>
	public static PriorityQueue<Mashlet> priorityQueue = new PriorityQueue<Mashlet>();
	public static boolean isChecking = false;
	public static boolean isExecuting = false;

	ExecutorService mashletExePool;

	/**
	 * Default constructor.
	 */

	public EngineManager() {
		Timer timer = new Timer();
		timer.schedule(this, 1000, 5000);

		// mashletExePool = Executors.newFixedThreadPool(30);
		// mashletExePool.
	}

	public void checkRunnableMashlets() {
		isChecking = true;
		// System.out.println("checking ");
		Iterator<Mashlet> it;
		Mashlet mlt;

		while (mashletsQueue.size() != 0) {
			// 在此运行mashlets

			long curTime = (new Date()).getTime();

			for (int i = 0; i < mashletsQueue.size(); i++) {

				mlt = mashletsQueue.get(i);
				if (mlt != null) {
					// it.next();
					if (mlt.getStatus() == Mashlet.STATUE_IS_DEAD) {
						// it.remove();
						queueMap.remove(mlt.getMashletId());
						mashletsQueue.set(i, null);
						continue;
					}
					if (mlt.needCheck() == true && mlt.checkRunable() == true) {
						// 该Mashlet满足执行条件，即该Mashlet的前驱节点均已执行完成

						// /*******************************************************************/
						// /***************if is the expt set
						// 2**************************/
						// /*******************************************************************/
						//if (curTime > mlt.getPredictStartTime()) {
							mlt.calculatePMT();
							mlt.setStatus(Mashlet.STATUE_READY_TO_RUN);
							//readyQueue.add(mlt);
							priorityQueue.add(mlt);
						//}
					}
				}
			}
			if (isExecuting == false && priorityQueue.size() != 0) {
				executeMashlets();
			}
		}
		isChecking = false;
	}

	public void executeMashlets() {
		isExecuting = true;
		Iterator<Mashlet> it;

//		for (it = readyQueue.iterator(); it.hasNext();) {
//			it.next().start();
//			it.remove();
//		}
		while(!priorityQueue.isEmpty()){
			priorityQueue.poll().start();
		}
		
		
		isExecuting = false;
	}

	@Override
	public void run() {

		if (isChecking == false) {
			if (mashletsQueue.size() != 0) {
				checkRunnableMashlets();
			}
		}

		Log.logger.debug("cur Size of mashletsQueue is " + mashletsQueue.size());

	}
}