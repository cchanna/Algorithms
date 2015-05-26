package assignment5;

public class BloomFilter<T>  {
	Boolean[] array;
	
	public BloomFilter() {
		array = new Boolean[256];
		for (int i = 0; i < array.length; i++) {
			array[i] = false;
		}
	}
	
	public int hash(T in, int i) {
		int output = in.hashCode();
		
		for (;i>0;i--) {
			output = output >>> 8;
		}
		output = output & 0x00FF;
		return output;
	}
	
	public boolean contains(T key) {
		return array[hash(key,0)] && array[hash(key,1)] && array[hash(key,2)] && array[hash(key,3)];
	}
	
	public void add(T key) {
		for (int i=0; i<4; i++) {
			array[hash(key,i)] = true;
		}
	}
}
