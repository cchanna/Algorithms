package assignment1;

import static edu.princeton.cs.introcs.StdOut.*;
import static edu.princeton.cs.introcs.StdRandom.*;

import java.util.Arrays;

public class Shuffle {

	// From p. 32
	public static void shuffle(double[] a) {
		int n = a.length;
		for (int i = 0; i < n; i++) {
			int r = i + uniform(n - i);
			double temp = a[i];
			a[i] = a[r];
			a[r] = temp;
		}
	}

	public static void badShuffle(double[] a) {
		int n = a.length;
		for (int i = 0; i < n; i++) {
			int r = uniform(n);
			double temp = a[i];
			a[i] = a[r];
			a[r] = temp;
		}
	}

	public static void testShuffle(int m, int n) {
		int output[][] = new int[m][m];
		for (int i = 0; i < n; i++) {
			double array[] = new double[m];
			for (int j = 0; j < array.length; j++) {
				array[j] = j;
			}
			shuffle(array);
			for (int j = 0; j < array.length; j++) {
				output[(int)array[j]][j]++;
			}
		}
		for (int i = 0; i < m; i++) {
			println(Arrays.toString(output[i]));
		}
	}

	public static void testBadShuffle(int m, int n) {
		int output[][] = new int[m][m];
		for (int i = 0; i < n; i++) {
			double array[] = new double[m];
			for (int j = 0; j < array.length; j++) {
				array[j] = j;
			}
			badShuffle(array);
			for (int j = 0; j < array.length; j++) {
				output[(int)array[j]][j]++;
			}
		}
		for (int i = 0; i < m; i++) {
			println(Arrays.toString(output[i]));
		}
	}

}
