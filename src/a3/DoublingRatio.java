package a3;


import edu.princeton.cs.algs4.ResizingArrayStack;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Stopwatch;
import static edu.princeton.cs.introcs.StdOut.*;



public class DoublingRatio {
	private static final int numTests = 1000;
	public static void resizingTest(int N) {
		for (int j=0 ; j<numTests ; j++) {
			ResizingArrayStack<Integer> stack = new ResizingArrayStack<Integer>();
			for (int i=0 ; i<N ; i++) {
				//Integer rnd = StdRandom.uniform(100);
				stack.push(1);			
			}
		}
	}
	
	public static void linkedTest(int N) {
		for (int j=0 ; j<numTests ; j++) {
			Stack<Integer> stack = new Stack<Integer>();
			for (int i=0 ; i<N ; i++) {
				//Integer rnd = StdRandom.uniform(100);
				stack.push(1);			
			}
		}
	}
	
	public static double resizingTimeTrial(int N) {
		Stopwatch timer = new Stopwatch();
		resizingTest(N);
		return timer.elapsedTime();
	}
	
	public static double linkedTimeTrial(int N) {
		Stopwatch timer = new Stopwatch();
		linkedTest(N);
		return timer.elapsedTime();
	}
	
	public static void main(String[] args) {
		int counter = 0;
		double linkedPrev = linkedTimeTrial(50);
		double resizingPrev = resizingTimeTrial(50);
		for (int N = 100; counter<17; N += N) {
			counter++;
			double linkedTime = linkedTimeTrial(N);
			double resizingTime = resizingTimeTrial(N);
			printf("%2d  ",counter);
			printf("%8d   %7.3f  %7.3f     ", N, linkedTime, resizingTime);
			printf("%5.1f %5.1f\n", ((double)linkedTime)/linkedPrev, ((double)resizingTime)/resizingPrev);
			linkedPrev = linkedTime;
			resizingPrev = resizingTime;
		}
	}
}
