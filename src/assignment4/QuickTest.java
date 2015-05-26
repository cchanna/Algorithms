package assignment4;

import static java.lang.Math.*;
import static edu.princeton.cs.introcs.StdRandom.*;
//import static edu.princeton.cs.introcs.StdDraw.*;
import edu.princeton.cs.algs4.Stopwatch;
import static edu.princeton.cs.introcs.StdOut.*;

public class QuickTest {

	private static final int POWER = 6;

	private static final int NUM_TESTS = 100;

	public static void main(String[] args) {
//		setPenRadius(.01);
//		show(100);
		printf("N = %d\n", (int) pow(10, POWER));
		printf("M   Average\n");
		double[] average = new double[31];
		int n = (int) pow(10, POWER);
		Double[] array = new Double[n];
		for (int i = 0; i < array.length; i++) {
			array[i] = uniform(0.0, 1.0);
		}
		for (int m = 0; m <= 30; m++) {
			average[m] = 0;
			Stopwatch timer = new Stopwatch();
			for (int j = 0; j < NUM_TESTS; j++) {
				Quick.sort(array, m);
			}
			average[m] = timer.elapsedTime();
			average[m] /= NUM_TESTS;
			printf("%3.5f\n", average[m]);
		}
		// point(m / 30.0, average[m]);

		System.out.println();
//		double min = average[0], max = average[0];
//		for (int i = 1; i < average.length; i++) {
//			if (average[i] < min)
//				min = average[i];
//			if (average[i] > max)
//				max = average[i];
//		}
//		setYscale(min, max);
//		for (int i = 0; i < average.length; i++) {
//			point(i / 30.0, average[i]);
//		}
//		show();

	}
}
