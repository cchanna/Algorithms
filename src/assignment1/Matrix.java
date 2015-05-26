package assignment1;

import java.util.Arrays;

public class Matrix {
	public static void main(String[] args) {
		double[][] a = {{3,2,1},{0,0,0,},{1,1,2}};
		double[][] b = {{10,2,-1},{3,1,6},{4,2,1}};
		System.out.println(Arrays.deepToString(mult(a,a)));
		System.out.println(Arrays.deepToString(mult(b,a)));
	}
	static double dot(double[] x, double[] y) {
		double solution = 0;
		for (int row = 0; row < x.length; row++) {
			solution += x[row]*y[row];
		}
		return solution;
	}
	
	static double[][] mult(double[][] a, double[][] b) {
		double[][] solution = new double[a.length][b[0].length];
		for (int row = 0; row < a.length; row++) {
			solution[row] = mult(a[row],b);
		}
		return solution;
	}
	
	static double[][] transpose(double[][] a) {
		double[][] solution = new double[a[0].length][a.length];
		for (int row = 0; row < solution.length; row++) {
			for (int col = 0; col < solution[0].length; col++) {
				solution[row][col] = a[col][row];
			}
		}
		return solution;
	}
	
	static double[] mult(double[][] a, double[] x) {
		double[] solution = new double[a.length];
		for (int row = 0; row < a.length; row++) {
			solution[row] = dot(a[row],x);
		}
		return solution;
	}
	
	static double[] mult(double[] y, double[][] a) {
		return mult(transpose(a),y);
		
	}
}
