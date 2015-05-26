package assignment3;

import static edu.princeton.cs.introcs.StdOut.*;
import static edu.princeton.cs.introcs.StdRandom.*;
import static java.lang.Math.*;

public class BirthdayProblem {
	static final int NUM_TESTS = 10000;
	static final int NUM_META_TESTS = 30;
	public static double birthdayTest(int n) {
		int i;
		//for (int j=0; j<NUM_TESTS; j)
		boolean[] array = new boolean[n];
		//printf(" 0  ");
		//println(Arrays.toString(array));
		for (i=1; true; i++) {
			//printf("%2d  ",i);
			int rnd = uniform(n);
			if (array[rnd] == true) {
				//printf("Break at %d\n\n",rnd,i,sqrt(PI*(n/2.0)));
				break;
			}
			else array[rnd] = true;

			//println(Arrays.toString(array));
			
		}
		return i;
	}
	
	public static void main(String[] args) {
		double[] results = new double[NUM_META_TESTS];
		for (int i=2; i-1<=NUM_META_TESTS; i++) {
			int testValue = (int) pow(2, i-2);
			//printf("Meta test %5d\n",i-1);
			for (int j=0 ; j<NUM_TESTS ; j++) {
				results[i-2] += birthdayTest(testValue);
				//printf("%2d %5.3f\n", j, results[i-2]);
				//println();
				//println();
			}
			results[i-2] /= NUM_TESTS;
			results[i-2] /= sqrt(PI*(testValue/2.0));
			printf("%2d   %10d      %1.5f\n",i-2,(int)pow(2,i-2),results[i-2]);
			
		}		
		for (int i=0 ; i<NUM_META_TESTS ; i++) {
		}
	}
}