package a7;

public class GridPath {
	public final static boolean DOWN = false, RIGHT = true;

	int[][] grid;
	int rows, cols;
	boolean[][] path;
	int[][] distanceFrom;
	boolean[] solution;

	public GridPath(int[][] grid) {
		rows = grid.length;
		cols = grid[0].length;
		this.grid = grid;
		path = new boolean[rows][cols];
		distanceFrom = new int[rows][cols];
		solution = new boolean[rows + cols - 2];
		for (int r = rows-1; r>= 0; r--) {
			for (int c = cols-1; c>= 0; c--) {
				solve(r,c);
			}
		}
		for (int i=0,r=0,c=0; i < rows + cols - 2; i++) {
			solution[i] = path[r][c];
			if (solution[i] == RIGHT) c++;
			else r++;
		}
	}

	private void solve(int r, int c) {
		if (r == rows-1 && c==cols-1 )
			return;
		if (r == rows-1) {
			distanceFrom[r][c] = distanceFrom[r][c+1] + grid[r][c];
			path[r][c] = RIGHT;
		}
		else if (c == cols-1) {
			distanceFrom[r][c] = distanceFrom[r+1][c] + grid[r][c];
			path[r][c] = DOWN;
		}
		else if (distanceFrom[r+1][c] <= distanceFrom[r][c+1]) {
			distanceFrom[r][c] = distanceFrom[r+1][c] + grid[r][c];
			path[r][c] = DOWN;
		}
		else {
			distanceFrom[r][c] = distanceFrom[r][c+1] + grid[r][c];
			path[r][c] = RIGHT;
		}
	}

	public boolean path(int i) {
		return solution[i];
	}
}
