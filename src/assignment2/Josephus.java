package assignment2;
import edu.princeton.cs.algs4.Queue;

public class Josephus {
	public static String run(int N, int M) {
		String output = "";
		int size = N;
		int counter = 1;
		
		Queue<Integer> queue = new Queue<Integer>();
		for (int i=0; i<N; i++) {
			queue.enqueue(i);
		}
		while (size > 0) {
			if (counter < M) {
				queue.enqueue(queue.dequeue());
				counter++;
				System.out.println("Duck");
			}
			else {
				output += queue.dequeue() + " ";
				counter = 1;
				System.out.println("Goose");
				size--;
			}
		}
		return output;
	}
	
}
