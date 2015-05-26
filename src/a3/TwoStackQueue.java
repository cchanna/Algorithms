package a3;
import edu.princeton.cs.algs4.Stack;

public class TwoStackQueue<Item> {
	Stack<Item> enStack = new Stack<Item>();
	Stack<Item> deStack = new Stack<Item>();
	boolean pushing = true;
	
	public TwoStackQueue() {
		
	}
	
	public void enqueue(Item item) {
		if (pushing) {
			enStack.push(item);
		}
		else {
			swapToEn();
			pushing = true;
			enStack.push(item);
		}
	}
	
	public Item dequeue() {
		if (pushing) {
			swapToDe();
			pushing = false;
			return deStack.pop();
		}
		else {
			return deStack.pop();		
		}
	}
	
	public void swapToDe() {
		while(!enStack.isEmpty()) {
			deStack.push(enStack.pop());
		}
	}
	
	public void swapToEn() {
		while(!deStack.isEmpty()) {
			enStack.push(deStack.pop());
		}
	}
	
	public boolean isEmpty() {
		return (enStack.isEmpty() && deStack.isEmpty());
	}
}