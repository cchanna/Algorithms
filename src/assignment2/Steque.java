package assignment2;

import java.util.Iterator;

public class Steque<T> implements Iterable<T> {
	private Node head, tail;
	
	private class Node
	{
		T item;
		Node next;
	}
	
	public void push(T item) {
		Node add = new Node();
		add.item = item;
		if (head == null) {
			head = add;
			tail = add;
			return;
		}
		add.next = head;
		head = add;
	}

	public T pop() {
		Node victim = head;
		head = victim.next;
		return victim.item;
	}

	public void enqueue(T item) {
		Node add = new Node();
		add.item = item;
		if (head == null) {
			head = add;
			tail = add;
		}
		tail.next = add;
		tail = add;
	}
	
	public boolean isEmpty() {
		return (head==null);
	}
	
	public String toString() {
		String output = "";
		for (Node node=head;node!=null;node=node.next) {
			output += node.item + " ";
		}
		return output;
	}
	
	public Steque<T> catenate(Steque<T> input) {
		tail.next = input.head;
		tail = input.tail;
		return input;
	}
	
	public Iterator<T> iterator() {
		return null;
	}
	/*
	private class ListIterator implements Iterator<T>
	{
		private Node current = head;
		
		public boolean hasNext() {
			return current != null;
		}
		
		public void remove() { }
		
		public T next() {
			T item = current.item;
			current = current.next;
			return item;
		}
	}
	*/
}