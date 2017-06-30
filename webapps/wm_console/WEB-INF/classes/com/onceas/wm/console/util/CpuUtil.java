package com.onceas.wm.console.util;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * @author syk
 *
 */
public class CpuUtil {

	private static Sigar sigar = SystemInfo.getSigar();
	public static int getCoreNum(){
		int core = 1;
		CpuPerc[] cpuPerc;
		try {
			cpuPerc = sigar.getCpuPercList();
			core = cpuPerc.length;
			
		} catch (SigarException e) {
			throw new RuntimeException(e);
		}		
		return core;
	}
}
