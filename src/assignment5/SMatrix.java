package assignment5;

import edu.princeton.cs.algs4.ST;

public class SMatrix {
	protected ST<Integer, ST<Integer,Double>> st;
	
	public SMatrix() {
		st = new ST<Integer,ST<Integer,Double>>();
	}
	
	public void put(int r, int c, double x) {
		ST<Integer,Double> row = st.get(r);
		if (row != null) row.put(c, x);
		else {
			row = new ST<Integer,Double>();
			row.put(c, x);
			st.put(r, row);
		}
	}
	
	public double get(int r, int c) {
		ST<Integer,Double> row = st.get(r);
		if (row!=null) {
			Double val = row.get(c);
			if (val != null) return val;
		}
		return 0.0;
	}
	
	public SMatrix transpose() {
		SMatrix output = new SMatrix();
		for (int r : st.keys()) {
			for (int c : st.get(r).keys()) {
				output.put(c, r, get(r,c));
			}
		}
		return output;
	}
	
	public SMatrix plus(SMatrix add) {
		SMatrix output = new SMatrix();
		for (int i : st.keys()) {
			for (int j : st.get(i).keys()) {
				output.put(i, j, get(i,j));
			}
		}
		for (int i : add.st.keys()) {
			for (int j : add.st.get(i).keys()) {
				double sum = add.get(i,j) + output.get(i,j);
				if (sum != 0.0) output.put(i,j,add.get(i, j) + output.get(i,j));
			}
		}
		return output;
	}
	
	public SMatrix times(SMatrix mult) {
		SMatrix output = new SMatrix();
		mult = mult.transpose();
		for (int r : st.keys()) {
			for (int c : mult.st.keys()) {
				double sum = 0.0;
				for (int e : st.get(r).keys()) {
					sum += get(r,e) * mult.get(c,e);
				}
				if (sum != 0.0) output.put(r, c, sum);
			}
		}
		return output;
	}
}
