package com.onceas.wm.console.kalman;

public class QCalculate {

	public static double[][] caculate(double[] x) {

		double[][] q = new double[x.length][x.length];

		for (int j = 0; j < x.length; j++) {
			double std = x[j] ;
			q[j][j] = std * std;
		}

		return q;

	}

}
