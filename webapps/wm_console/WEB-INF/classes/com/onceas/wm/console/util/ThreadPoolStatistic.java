package com.onceas.wm.console.util;

import java.util.ArrayList;
import java.util.Date;

public class ThreadPoolStatistic {
	public static int MAX = 240;

	public static ArrayList<Integer> throughPut = new ArrayList<Integer>(MAX);

	public static Date threadPoolSampleStartTime;

	public static int threadPoolSampleTimeInterval = 3;

	public static boolean startFlag = false;

	public static boolean isFull() {
		if (throughPut.size() == MAX) {
			return true;
		} else {
			return false;
		}
	}

	public static void setThroughPut(Integer sta) {
		if (isFull()) {
			throughPut.remove(0);
			threadPoolSampleStartTime = new Date(threadPoolSampleStartTime
					.getTime()
					+ threadPoolSampleTimeInterval * 1000L);
		}
		throughPut.add(sta);
	}

	public static void startSample(Integer sta) {
		if (throughPut.size() == 0) {
			threadPoolSampleStartTime = new Date();
			throughPut.add(sta);
		}
		startFlag = true;
	}

}
