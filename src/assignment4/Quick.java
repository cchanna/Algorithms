package assignment4;

import static edu.princeton.cs.introcs.StdDraw.*;
import edu.princeton.cs.introcs.*;

/*************************************************************************
 *  Compilation:  javac Quick.java
 *  Execution:    java Quick < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   http://algs4.cs.princeton.edu/23quicksort/tiny.txt
 *                http://algs4.cs.princeton.edu/23quicksort/words3.txt
 *
 *  Sorts a sequence of strings from standard input using quicksort.
 *   
 *  % more tiny.txt
 *  S O R T E X A M P L E
 *
 *  % java Quick < tiny.txt
 *  A E E L M O P R S T X                 [ one string per line ]
 *
 *  % more words3.txt
 *  bed bug dad yes zoo ... all bad yet
 *       
 *  % java Quick < words3.txt
 *  all bad bed bug dad ... yes yet zoo    [ one string per line ]
 *
 *
 *  Remark: For a type-safe version that uses static generics, see
 *
 *    http://algs4.cs.princeton.edu/23quicksort/QuickPedantic.java
 *
 *************************************************************************/

/**
 *  The <tt>Quick</tt> class provides static methods for sorting an
 *  array and selecting the ith smallest element in an array using quicksort.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/21elementary">Section 2.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */

@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class Quick {

    // This class should not be instantiated.
    private Quick() { }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
	public static void sort(Comparable[] a, int cutoff) {
		//setCanvasSize(1500,256);
		//draw(a);
		//show(100);
    	StdRandom.shuffle(a);
    	sort(a, 0, a.length, cutoff);
    	//draw(a);
    	//show(500);
    }
	
	public static void sort(Comparable[] a) {
		edu.princeton.cs.algs4.Quick.sort(a);
	}
	
	private static void sort(Comparable[] a, int lo, int hi, int cutoff) {
		//draw(a,lo,hi);
		//show(500);
		if (hi - lo <= cutoff + 1) {
			insertionSort(a,lo,hi);
			return;
		}
        int j = partition(a, lo, hi);
        sort(a, lo, j,cutoff);
        sort(a, j+1, hi,cutoff);
        assert isSorted(a, lo, hi);
	}
	
    public static void insertionSort(Comparable[] a, int lo, int hi) {
        for (int i = lo; i < hi; i++) {
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--) {
                exch(a, j, j-1);
                //draw(a,lo,hi);
                //show(100);
            }
        }
    }

    // partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
    // and return the index j.
	private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi;
        Comparable v = a[lo];
        while (true) { 

            // find item on lo to swap
            while (less(a[++i], v))
                if (i >= hi-1) break;

            // find item on hi to swap
            while (less(v, a[--j]))
                if (j <= lo) break;      // redundant since a[lo] acts as sentinel

            // check if pointers cross
            if (i >= j) break;

            exch(a, i, j);
            //draw(a,lo,hi);
            //show(100);
        }

        // put partitioning item v at a[j]
        exch(a, lo, j);

        // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }

    public static void draw(Comparable[] array) {
    	Double[] a = (Double[]) array;
    	clear();
    	int n = a.length;
    	for (int i=0 ; i<n ; i++) {
    		filledRectangle((i*1.0 +1)/(n+2.0),a[i]/2.0, (1/(n+2.0))*0.45, a[i]/2.0);
    	}
    }
    
    public static void draw(Comparable[] array, int lo, int hi) {
    	Double[] a = (Double[]) array;
    	clear();
    	int n = a.length;
    	setPenColor(BLACK);
    	for (int i=0 ; i<lo ; i++) {
    		filledRectangle((i*1.0 +1)/(n+2.0),a[i]/2.0, (1/(n+2.0))*0.45, a[i]/2.0);
    	}
    	setPenColor(BLUE);
    	for (int i=lo ; i<hi ; i++) {
    		filledRectangle((i*1.0 +1)/(n+2.0),a[i]/2.0, (1/(n+2.0))*0.45, a[i]/2.0);
    	}
    	setPenColor(GRAY);
    	for (int i=hi ; i<n ; i++) {
    		filledRectangle((i*1.0 +1)/(n+2.0),a[i]/2.0, (1/(n+2.0))*0.45, a[i]/2.0);
    	}
    }
    /**
     * Rearranges the array so that a[k] contains the kth smallest key;
     * a[0] through a[k-1] are less than (or equal to) a[k]; and
     * a[k+1] through a[N-1] are greater than (or equal to) a[k].
     * @param a the array
     * @param k find the kth smallest
     */
    public static Comparable select(Comparable[] a, int k) {
        if (k < 0 || k >= a.length) {
            throw new IndexOutOfBoundsException("Selected element out of bounds");
        }
        StdRandom.shuffle(a);
        int lo = 0, hi = a.length - 1;
        while (hi > lo) {
            int i = partition(a, lo, hi);
            if      (i > k) hi = i - 1;
            else if (i < k) lo = i + 1;
            else return a[i];
        }
        return a[lo];
    }



   /***********************************************************************
    *  Helper sorting functions
    ***********************************************************************/
    
    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }
        
    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


   /***********************************************************************
    *  Check if array is sorted - useful for debugging
    ***********************************************************************/
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }


    // print array to standard output
    private static void print(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }
}
