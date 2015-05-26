package a8;

/** Las Vegas variant of 2-dimensional pattern matching, using a generalization of the Rabin-Karp algorithm. Based on the implementation provided in Sedgewick & Wayne, Algorithms (Fourth Edition). */
public class RabinKarp2D {

	/** A large number that happens to be prime. */
	public static final long MODULUS = Integer.MAX_VALUE;
	
	/** Radix of the alphabet. Assumes ASCII characters. */
	public static final int RADIX = 256;
	
	/** Powers of RADIX. Used to find the hash when the pattern window is shifted. Analogous to RM in S&W's code. */
	private long[] factors;
	
	/** Height of pattern. */
	private int height;
	
	/** The pattern itself. */
	private char[][] pattern;
	
	/** Hash code of the pattern. */
	private long patternHash;
	
	/** Width of pattern. */
	private int width;
	
	public RabinKarp2D(char[][] pattern) {
		this.pattern = pattern;
		height = pattern.length;
		width = pattern[0].length;
		factors = new long[(height - 1) + (width - 1) + 1];
		factors[0] = 1;
		for (int i = 1; i < factors.length; i++) {
			factors[i] = (RADIX * factors[i - 1]) % MODULUS;
		}
		patternHash = hash(pattern);
	}
	
	/** Returns true if pattern matches text at position i, j. */
	public boolean check(char[][] text, int i, int j) {
		int x = i;
		int y = j;
		for (int a = 0; a < height; a++) {
			for (int b = 0; b < width; b++) {
				if (text[x][y] != pattern[a][b]) {
					return false;
				}
				y++;
			}
			x++;
			y = j;
		}
		return true;
	}

	/** Returns powers of RADIX, modulo MODULUS, up to (height - 1) * (width - 1). */
	protected long[] getFactors() {
		return factors;
	}
	
	/** Computes (from scratch) and returns the hash of the upper left height * width block of data. */
	protected long hash(char[][] data) {
		long result = 0;
		for (int i = 0; i < height; i++) {
			long rowHash = 0;
			for (int j = 0; j < width; j++) {
				rowHash = (RADIX * rowHash + data[i][j]) % MODULUS;
			}
			result = (RADIX * result + rowHash) % MODULUS;
		}
		return result;
	}
	
	/** Returns an array [i, j], where i and j are the coordinates of a match of pattern in text. */
	public int[] search(char[][] text) {
		long rowStartHash = hash(text);
		long hash = rowStartHash;
		for (int i = 0; i <= text.length - height; i++) {
//			System.out.println("[" + i + ",0] hash=" + hash);
			if ((hash == patternHash) && check(text, i, 0)) {
//				System.out.println("\n\n");
				return new int[] {i, 0};
			}
			for (int j = 0; j < text[0].length - width; j++) {
				hash = shiftRight(hash, text, i, j);
//				System.out.println("[" + i + "," + (j+1) + "] hash=" + hash);
				if ((hash == patternHash) && check(text, i, j + 1)) {
//					System.out.println("\n\n");
					return new int[] {i, j + 1};
				}
			}
			if (i != text.length - height) rowStartHash = shiftDown(rowStartHash, text, i);
			hash = rowStartHash;
		}
		return null;
	}
	
	/** Given the hash of the block at i, j, returns the hash of the block at i + 1, j. */
	protected long shiftDown(long hash, char[][] text, int i) {
		long top = 0;
		for (int k=0; k < width; k++) {
			top = (RADIX * top + text[i][k]) % MODULUS;
		}
		for (int k=0; k < height - 1; k++) {
			top = (RADIX * top) % MODULUS;
		}
		long bottom = 0;
		for (int k=0; k < width; k++) {
			bottom = (RADIX * bottom + text[i+height][k]) % MODULUS;
		}
		hash = (((hash - top) * RADIX) + bottom) % MODULUS;
		hash = (hash + MODULUS) % MODULUS;
		return hash;
	}
	
	/** Given the hash of the block at i, j, returns the hash of the block at i, j + 1. */
	protected long shiftRight(long hash, char[][] text, int i, int j) {
		long left = 0;
		for (int k = 0; k < height; k++) {
			left = (RADIX * left + text[i + k][j]) % MODULUS;
		}
		for (int k = 0; k < width - 1; k++) {
			left = (RADIX * left) % MODULUS;
		}
		long right = 0;
		for (int k = 0; k < height; k++) {
			right = (RADIX * right + text[i + k][j + width]) % MODULUS;
		}
		hash = (((hash - left) * RADIX) + right) % MODULUS;

		hash = (hash + MODULUS) % MODULUS;
		return hash;
	}

}
