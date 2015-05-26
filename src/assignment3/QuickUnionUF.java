package assignment3;

import static edu.princeton.cs.introcs.StdOut.*;

import java.util.Arrays;

public class QuickUnionUF {
    private int[] id;    // id[i] = parent of i
    private int count;   // number of components

    // instantiate N isolated components 0 through N-1
    public QuickUnionUF(int N) {
        id = new int[N];
        count = N;
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    // return number of connected components
    public int count() {
        return count;
    }

    // Modified for path compression
    // return root of component corresponding to element p
    public int find(int p) {
    	int q = p;
        while (p != id[p])
            p = id[p];
        while (q != p) {
        	int r = q;
        	q = id[q];
        	id[r] = p;
        }
        return p;
        
    }

    // are elements p and q in the same component?
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // merge components containing p and q
    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) return;
        id[i] = j; 
        count--;
    }

    public static void main(String[] args) {
        QuickUnionUF uf = new QuickUnionUF(16);
        // read in a sequence of pairs of integers (each in the range 0 to N-1),
        // calling find() for each pair: If the members of the pair are not already
        // call union() and print the pair.
        uf.union(0,1);
        uf.union(2,3);
        uf.union(0,2);
        
        uf.union(4,5);
        uf.union(6,7);
        uf.union(4, 6);
        
        uf.union(3, 7);
        
        uf.union(8, 9);
        uf.union(10, 11);
        uf.union(8, 10);
        
        uf.union(12, 13);
        uf.union(14, 15);
        uf.union(12, 14);
        
        uf.union(11, 15);
        
        uf.union(7, 15);
        
        println("# components: " + uf.count());
        uf.printTree();
    }

    // These getters added by Peter Drake for purposes of JUnit testing
	protected int[] getId() {
		return id;
	}

	protected int getCount() {
		return count;
	}
	
	protected void printTree() {
		println(Arrays.toString(id));
		for (int i=0; i<id.length; i++) {
			if (i == id[i]) {
				println(i);
				printPath(i,"","|   ");
				println();
			}
		}
	}
	
	private void printPath(int n, String depth, String add) {
		for (int i=0; i<id.length; i++) {
			if (id[i] == n && i != n) {
				println(depth + "^---" + i);
				printPath(i,add + depth,"|   ");
			}
		}
	}

}
