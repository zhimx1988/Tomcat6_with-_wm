package com.onceas.wm.console.kalman;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CPUFilter {

	private static Log innerLog = LogFactory.getLog("inner_log");

	private static Log kalmanLog = LogFactory.getLog(CPUFilter.class);

	private static CPUFilter instance = new CPUFilter();

	private static int period = 15;

	private static int count = 0;

	private double[] throughput;
	private double[] utilizations;
	private double totalUtilization;

	private double[] meanX;

	private Random random = new Random();

	private CPUFilter() {
	}

	public static CPUFilter getInstance() {
		return instance;
	}

	
	private KalmanCalculate calculate;

	public double[] filter(double[] throughput, double[] utilizations, double totalUtilization) {
		if (kalmanLog.isDebugEnabled()) {
			StringBuilder builder = new StringBuilder();
			builder.append("totalUtilization=").append(totalUtilization).append("\n");
			print(builder.append("throughts="), throughput).append("\n");
			print(builder.append("utilizations="), utilizations).append("\n");
			builder.append("=====================").append("\n");

			kalmanLog.debug(builder.toString());
		}

		// 不统计没有任务执行的数据
		if (totalUtilization <= 0 || isUnprocessed(throughput)) {
			return utilizations;
		}

		count++;

		// 统计数据
		if (this.throughput == null) {
			this.throughput = new double[throughput.length];
			this.utilizations = new double[utilizations.length];
		}

		for (int i = 0; i < throughput.length; i++) {
			this.throughput[i] += throughput[i];
			this.utilizations[i] += utilizations[i];
		}
		this.totalUtilization += totalUtilization;

		if (count >= period) {
			// 数据平滑
			double[] t = new double[throughput.length];
			double[] u = new double[utilizations.length];
			double tu = totalUtilization;
			for (int i = 0; i < throughput.length; i++) {
				t[i] = this.throughput[i] / count;
				u[i] = this.utilizations[i] / count;
			}
			tu = this.totalUtilization / count;

			if (innerLog.isDebugEnabled()) {
				final StringBuilder mBuf = new StringBuilder();

				PrintStream print = new PrintStream(new OutputStream() {
					public void write(int i) throws IOException {
						mBuf.append((char) i);
					}
				});

				print.println("Mean value:");
				print.println("total " + tu + "; ");
				print.print("measured :\t");
				for (int i = 0; i < u.length; i++) {
					print.print(String.format("%1.2f ", u[i]));
				}
				print.println("; ");

				print.print("throughput :\t");
				for (int i = 0; i < t.length; i++) {
					print.print(String.format("%1.2f ", t[i]));
				}
				print.print("; ");

				innerLog.debug(mBuf.toString());

			}

			// 创建Kalman计算器
			if (calculate == null) {
				calculate = new KalmanCalculate(new double[u.length]);
				for (int i = 0; i < u.length; i++) {
					calculate.setX(i, KalmanCalculate.DEFAULT_VALUE);
				}
			}
			if (this.meanX == null) {
				this.meanX = new double[utilizations.length];
				Arrays.fill(this.meanX, KalmanCalculate.DEFAULT_VALUE);
			}

			// 更新 x = Ax+c，带有噪音
//			double[] tempX = new double[u.length];
//			int maxPos = 0;
//			double maxValue = 0;
			for (int i = 0; i < u.length; i++) {
				double measured = getMeasuredX(u[i], t[i]);
				double estimated = calculate.getX(i);
				// double x = Math.max(estimated, measured);
				double x = measured > estimated * 2 ? measured : estimated;
				// double x = calculate.getX(i);

				// 噪音
				 x = x + (random.nextDouble() / 10 - 0.05) * x;
				calculate.setX(i, x);

//				tempX[i] = x;
//
//				if (x > maxValue) {
//					maxValue = x;
//					maxPos = i;
//				}
			}
//			for (int i = 0; i < u.length; i++) {
//				int plus = 1;
//				if (i == 0) {
//					plus = -1;
//				}
//
//				double x = tempX[i];
//				x = x + plus * (random.nextDouble() / 10 - 0.05) * x;
//				calculate.setX(i, x);
//			}

			// 计算Q
			double[][] Q = new double[u.length][t.length];
			for (int i = 0; i < utilizations.length; i++) {
				double d = meanX[i] - calculate.getX(i);

				Q[i][i] = d * d;
			}

			// 迭代
			calculate.recurse(Q, 0, tu, t);

			boolean valide = true;

			// 更新均值（连续三次统计的均值）
			for (int i = 0; i < u.length; i++) {
				if (t[i] > 0) {
					double x = meanX[i];
					double y = calculate.getX(i);
					if (x == KalmanCalculate.DEFAULT_VALUE) {
						meanX[i] = y;
					} else {
						meanX[i] = (x * 2 + y) / 3;
					}

					if (y < 0) {
						valide = false;
					}
				}
			}

			if (valide) {
				// 清空已收集数据
				count = 0;
				Arrays.fill(this.throughput, 0);
				Arrays.fill(this.utilizations, 0);
				this.totalUtilization = 0;
				if (innerLog.isDebugEnabled()) {
					StringBuilder builder = new StringBuilder("X:\n");
					print(builder.append("calculated:\t"), calculate.getX());
					builder.append("\n");
					print(builder.append("mean:\t\t"), meanX);
					builder.append("\n");
					double[] measured = new double[u.length];
					for (int i = 0; i < measured.length; i++) {
						if (t[i] > 0) {
							measured[i] = u[i] / t[i];
						}
					}
					print(builder.append("measured:\t"), measured);
					innerLog.debug(builder.toString());
				}
			} else {
				if (innerLog.isDebugEnabled()) {
					StringBuilder builder = new StringBuilder("INVALIDE X:\n");
					print(builder, calculate.getX());
					innerLog.debug(builder.toString());
				}
				// 重置
				for (int i = 0; i < utilizations.length; i++) {
					double x = meanX[i];
					double y = calculate.getX(i);

					meanX[i] = (3 * x - y) / 2;

					calculate.setX(i, getMeasuredX(u[i], t[i]));
					// calculate.setX(i, y > 0 ? meanX[i] :
					// KalmanCalculate.DEFAULT_VALUE);
				}
				calculate.setP(QCalculate.caculate(calculate.getX()));
				if (innerLog.isDebugEnabled()) {
					StringBuilder builder = new StringBuilder("Reset X:\n");
					print(builder, calculate.getX());
					innerLog.debug(builder.toString());
				}
			}

			if (innerLog.isDebugEnabled()) {
				innerLog.debug("************************************");
			}
		}

		// 第一个周期之前直接返回
		if (calculate == null) {
			return utilizations;
		}

		// 根据当前监控到的CPU做缩放，使总值不变（解决抖动问题）
		double total = 0;
		for (int i = 0; i < utilizations.length; i++) {
			utilizations[i] = meanX[i] * throughput[i];
			total += utilizations[i];
		}
		double p = totalUtilization / total;
		for (int i = 0; i < utilizations.length; i++) {
			utilizations[i] *= p;
		}

		return utilizations;
	}

	private boolean isUnprocessed(double[] throughput) {
		for (int i = 0; i < throughput.length; i++) {
			if (throughput[i] > 0) {
				return false;
			}
		}
		return true;
	}

	public double getMeasuredX(double u, double t) {
		double x = 0;
		if (u > 0 && t > 0) {
			x = u / t;
		} else {
			x = KalmanCalculate.DEFAULT_VALUE;
		}
		return x;
	}

	private StringBuilder print(StringBuilder builder, double[] data) {

		builder.append("[");

		for (int i = 0; i < data.length; i++) {
			if (i != 0) {
				builder.append(",");
			}
			builder.append(String.format("%.8f", data[i]));
		}

		builder.append("]");

		return builder;
	}

	public double[] getX() {
		return meanX;
	}

}
