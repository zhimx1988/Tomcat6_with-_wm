package com.onceas.wm.console.kalman;

import Jama.Matrix;

public class KalmanCalculate {

	public static final double DEFAULT_VALUE = 	0.00000001d;

	private Matrix X;
	private Matrix P;

	private Matrix Q;
	private Matrix K;
	private Matrix H;
	private double Z;
	private double R;
	private int n;

	public KalmanCalculate(double[] x) {
		this(x, QCalculate.caculate(x));
	}

	public KalmanCalculate(double[] x, double[][] p) {
		super();
		this.n = x.length;
		this.X = new Matrix(x, x.length); // x.length == rows
		this.P = new Matrix(p);

	}

	public void recurse(double[][] q, double r, double z, double[] h) {

		this.Q = new Matrix(q);
		this.R = r;
		this.Z = z;
		this.H = new Matrix(h, 1);

//		P.plusEquals(Q);
		P = Q;

		calculateK();

		refreshX();

//		refreshP();
	}

	// K
	public void calculateK() {
		Matrix hph = H.times(P).times(H.transpose());
		double[][] temp = hph.getArray();

		// R = (Z * Z / 100);

		double hphr = 1d / (temp[0][0] + R);
		K = P.times(H.transpose());
		K = K.times(hphr);

		/*
		 * double[][] tempk = K.getArray(); for(int i = 0; i < n; i++){
		 * System.out.println(" k[" + i + "]= " + tempk[i][0]); }
		 * 
		 */
	}

	// X
	public void refreshX() {
		Matrix hx = H.times(X);
		double[][] tempHX = hx.getArray();
		double zhx = Z - tempHX[0][0];

		// System.out.println("Z: " + Z + " | " + tempHX[0][0]);

		X = X.plus(K.times(zhx));

		// ///// add by huangxiang //////////

		// for (int i = 0; i < meanX.length; i++) {
		// double x = meanX[i];
		// // count ++;
		//
		// double newX = X.get(i, 0);
		// // if (newX < 0) {
		// // newX = meanX[i];
		// // X.set(i,0, DEFAULT_VALUE);
		// // }
		//
		// // x = (x * count + newX) / (count + 1);
		// x = (x * 4 + newX) / 5;
		// meanX[i] = Math.abs(x);
		// // meanX[i] = x;
		//
		// }

		// System.out.print("->mean:");
		// for (int i = 0; i < meanX.length; i++) {
		// System.out.print(String.format("%1.2f ", meanX[i]));
		// }
		// System.out.println();
		// System.out.print("->Xval:");
		// for (int i = 0; i < meanX.length; i++) {
		// System.out.print(String.format("%1.2f ", X.get(i, 0)));
		// }
		// System.out.println();

		// ////// add over ////////////
	}

	// P
	public void refreshP() {
		Matrix kh = K.times(H);
		Matrix ikh = Matrix.identity(n, n).minus(kh);
		P = ikh.times(P);

		/*
		 * double[][] tempP = P.getArray(); System.out.println("P: "); for(int i =
		 * 0; i < n; i++){ for(int j = 0; j < n; j++){
		 * System.out.print(tempP[i][j] + " "); } System.out.println(); }
		 * System.out.println();
		 * 
		 */}

	public double[] getX() {
		double[][] x1 = X.getArray();
		double[] x2 = new double[n];
		for (int i = 0; i < n; i++) {
			x2[i] = x1[i][0];
		}
		return x2;
	}

	public void setX(int pos, double value) {
		X.set(pos, 0, value);
	}

	public double getX(int pos) {
		return X.get(pos, 0);
	}

	public double[][] getP() {
		return P.getArray();
	}

	public void setP(double[][] p) {
		this.P = new Matrix(p);
	}

}
