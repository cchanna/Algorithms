package assignment1;

public class RelativePrime {
	static boolean[][] generateMatrix(int n) {
		boolean array[][] = new boolean[n][n];
		for (int row = 0; row < array.length; row++) {
			for (int col = 0; col < array.length; col++) {
				array[row][col] = checkRelativePrime(row,col);
			}
		}
		return array;
	}
	
	private static boolean checkRelativePrime(int row, int col) {
		if (gcd(row,col) == 1) return true;
		else return false;
	}
	
	private static int gcd(int p, int q) {
		if (q == 0) return p;
		int r = p % q;
		return gcd(q,r);
	}
}
