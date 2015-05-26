package assignment7;

public class ArraySort {

	public static void sort(int[][] rows) {
		sort(rows,0,rows.length,0);
	}
	
	private static void sort(int[][] rows, int lo, int hi, int d) {
		if (hi <= lo) return;
		int lt = lo, gt = hi;
		int v;
		if (d == rows[lo].length) v = -1;
		else v = rows[lo][d];
		int i = lo + 1;
		while (i < gt) {
			int t;
			if (d == rows[i].length) t = -1;
			else t = rows[i][d];
			if (t < v) exch(rows,lt++,i++);
			else if (t > v) exch(rows,i,--gt);
			else i++;
		}
		
		sort (rows,lo,lt,d);
		if (v >= 0) sort(rows,lt,gt,d+1);
		sort(rows,gt,hi,d);
	}
	
	private static void exch(int[][] rows, int a, int b) {
		int[] temp = rows[a];
		rows[a] = rows[b];
		rows[b] = temp;
	}
}
