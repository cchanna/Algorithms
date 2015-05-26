package assignment4;

import java.util.Arrays;

public class Triplicates {
	public static String find(String[] a, String[] b, String[] c) {
		Arrays.sort(a);
		Arrays.sort(b);
		Arrays.sort(c);
		int i=0, j=0, k=0;
		int counter=0;
		while (i<a.length) {
			System.out.println(++counter);
			while (a[i].compareTo(b[j]) > 0) {
				System.out.println(++counter);
				j++;
				if (j >= b.length) return null;
			}
			System.out.println(++counter);
			if (a[i].compareTo(b[j]) == 0) {
				System.out.println(++counter);
				while (a[i].compareTo(c[k]) > 0) {
					System.out.println(++counter);
					k++;
					if (k >= c.length) return null;
				}
				System.out.println(++counter);
				if (a[i].compareTo(c[k]) == 0) {
					return a[i];
				}
				else {
					i++;
					j++;
					if (j >= b.length) return null; 
				}
			}
			else i++;
		}
		return null;
	}
	public static void main(String[] args) {
		
	}
}
			
		/*
		while (i<a.length) {
			double compareAB = a[i].compareTo(b[j]);
			while ( > 0) {
				j++;
			}
			else if (compareAB < 0) {
				i++;
			}
			else {
				double compareBC = b[j].compareTo(c[k]);
				if (compareBC > 0) {
					k++;
				}
				
			}
			
			/*
			double compareBC = b[j].compareTo(c[k]);
			if (compareAB == 0) {
				if (compareBC == 0) {
					return a[i];
				}
				else if (compareBC > 0) {
					k++;
				}
				else {
					i++;
					j++;
				}
			}
			else if (compareAB > 0) { 
				j++;
			}
			*/