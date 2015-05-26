package a7;

import java.io.File;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.TrieST;
import edu.princeton.cs.introcs.In;

public class Index {

	private TrieST<Queue<Integer>> trie;
	
	public Index(String text) {
		trie = new TrieST<Queue<Integer>>();
		File file = new File("src/assignment7/" + text);
		In in = new In(file);
		for (int l = 0; in.hasNextLine(); l++) {
			String line = in.readLine();
			String[] words = line.split(" ");
			for (int i =0; i<words.length; i++) {
				put(words[i],l);
			}
		}
	}
	
	private void put(String word, int line) {
		if (trie.contains(word)) {
			trie.get(word).enqueue(line);
		}
		else {
			Queue<Integer> queue = new Queue<Integer>();
			queue.enqueue(line);
			trie.put(word, queue);
		}
	}
	
	public Queue<Integer> get(String pattern) {
		return trie.get(pattern);
	}
}
