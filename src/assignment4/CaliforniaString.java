package assignment4;

public class CaliforniaString implements Comparable<CaliforniaString> {
	private String string;
	private int[] sorting = {7,9,16,22,18,24,11,8,17,4,19,25,5,14,3,21,2,0,10,15,20,6,1,13,23,12};
			
//	172216149 12210 7 1 186 252313192 8 4 1020153 245 11
//	R W Q O J M V A H B S G Z X N T C I E K U P D Y F L
//	                    1 1 1 1 1 1 1 1 1 1 2 2 2 2 2 2	
//	0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5

	public CaliforniaString(String string) {
		this.string=string;
	}
	
	public int compareTo(CaliforniaString compare) {
		for (int i=0; true; i++) {
			if (i >= string.length()) {
				if (compare.string.length() == string.length()) return 0;
				else return -1;
			}
			if (i >= compare.string.length()) return 1;
			int value1 = (int) string.charAt(i) - 'A';
			int value2 = (int) compare.string.charAt(i) - 'A';
			if (value1 != value2) return sorting[value1] - sorting[value2];
		}
	}
	
	public String toString() {
		return string;
	}
}
