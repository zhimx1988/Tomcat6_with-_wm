package com.onceas.wm.console.util;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class SystemInfo {

	// private Shell sigarShell;

	private Sigar sigar;

	private static SystemInfo instance;

	private static Object LOCK = new Object();

	private static double currentCpuUsage;

	// private static double p;

	private SystemInfo() {
		// sigarShell = new Shell();
		sigar = new Sigar();
	}

	public static SystemInfo getInstance() {
		if (instance == null) {
			synchronized (LOCK) {
				if (instance == null) {
					instance = new SystemInfo();
					// make sure Sigar is ready
					try {
						instance.sigar.getCpuPercList();
					} catch (SigarException e) {
						
					}
					// try {
					// p = 100d / (double)
					// instance.sigar.getCpuPercList().length;
					// } catch (SigarException e) {
					// throw new RuntimeException(e);
					// }
				}
			}

		}
		return instance;
	}

	public static Sigar getSigar() {
		return getInstance().sigar;
	}

	public int getCPUsage() {
		CpuPerc[] cpuPerc;
		try {
			// return (int) (sigar.getProcCpu(sigar.getPid()).getPercent() * p);
			cpuPerc = sigar.getCpuPercList();
			double avgBusy = 0;
			double tmp;
			// System.out.println("cpu count = " + cpuPerc.length);
			for (int i = 0; i < cpuPerc.length; i++) {
				tmp = 1 - cpuPerc[i].getIdle();
				// System.out.println("cpu " + (i + 1) + " percent = " + tmp);
				// System.out.println("cpu percent = " + cpuPerc.length);
				avgBusy += tmp;
			}
			currentCpuUsage = avgBusy * 100 / cpuPerc.length;
		} catch (SigarException e) {
			throw new RuntimeException(e);
		}
		return (int) currentCpuUsage;
	}

}
