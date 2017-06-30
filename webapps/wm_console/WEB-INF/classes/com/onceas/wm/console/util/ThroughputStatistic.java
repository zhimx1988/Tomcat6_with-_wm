package com.onceas.wm.console.util;

import java.util.ArrayList;
import java.util.Date;

public class ThroughputStatistic {
	public static int MAX = 240;

	public static ArrayList<Long> throughPut = new ArrayList<Long>(MAX);

	public static Date throughputSampleStartTime;

	public static int throughputSampleTimeInterval = 3;

	public static boolean startFlag = false;

	public static boolean isFull() {
		if (throughPut.size() == MAX) {
			return true;
		} else {
			return false;
		}
	}

	public static void setThroughPut(Long sta) {
		if (isFull()) {
			throughPut.remove(0);
			throughputSampleStartTime = new Date(throughputSampleStartTime
					.getTime()
					+ throughputSampleTimeInterval * 1000L);
		}
		throughPut.add(sta);
	}

	public static void startSample(Long sta) {
		if (throughPut.size() == 0) {
			throughputSampleStartTime = new Date();
			throughPut.add(sta);
		}
		startFlag = true;
	}

}
