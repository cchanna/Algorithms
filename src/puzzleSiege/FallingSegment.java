package puzzleSiege;

import java.awt.Graphics2D;

/**
 * A segment of tiles that are falling to a neutral position.
 * 
 * @author Cerisa
 * @version 1.3 12/11/2013
 */
// TODO Restructure how new tiles work
public class FallingSegment implements Action {
	public static final int TIME = 2;
	public static final double SPEED = Tile.SIZE / TIME;

	// protected static boolean[] newTile = new boolean[Board.SIZE];

	protected boolean turn;

	Tile[] row;
	int r;
	int start, end;
	int frame;

	public FallingSegment(int r, int start, int end, Board board) {
		// System.out.println("new segment " + r + " " + start + " " + end +
		// " ");
		turn = board.currentTurn();
		row = new Tile[end - start];
		for (int i = 0; i < end - start; i++) {
			row[i] = board.getTile(r, start + i);
			board.delete(r, start + i);
		}
		this.start = start;
		this.end = end;
		this.r = r;
		this.frame = 0;
	}

	/*
	 * public FallingSegment(int r, NewTiles newTiles, Board board) { turn =
	 * board.currentTurn(); // newTile[r] = true; board.setNewTile(r, true); row
	 * = new Tile[newTiles.size(r)]; for (int i = 0; i < row.length; i++) {
	 * row[i] = newTiles.get(r); } if (turn) { start = 0 - row.length; end = 0;
	 * } else { start = Board.SIZE; end = Board.SIZE + row.length; } this.r = r;
	 * this.frame = 0; }
	 */

	public FallingSegment(int r, int back, Tile[] tiles, boolean turn) {
		this.turn = turn;
		// board.setNewTile(r,true);
		row = tiles;
		if (turn) {
//			start = 0 - row.length - back;
			start = 0 - back;
			end = 0 - back + row.length;
		} else {
			start = Board.SIZE + back - row.length;
			end = Board.SIZE + back;
		}
		this.r = r;
		frame = 0;
	}

	public FallingSegment(int r, Unit unit, Board board) {
		turn = board.currentTurn();
		// board.setNewTile(r,true);
		row = new Tile[1];
		row[0] = unit;
		if (turn) {
			start = -1;
			end = 0;
		} else {
			start = Board.SIZE;
			end = Board.SIZE + 1;
		}
		this.r = r;
		frame = 0;
	}

	@Override
	public boolean onTick() {
		frame++;
		if (frame >= TIME)
			return true;
		return false;
	}

	// public static boolean isFalling(int r) {
	// return newTile[r];
	// }

	@Override
	public void onAdd(Board board) {
	}

	@Override
	public boolean activate(Board board) {
//		System.out.print("[" + start + "," + end + "] ");
		if (board.currentTurn()) {
			start++;
			end++;
			if (start <= 0) {
				// newTile[r] = false;
				// board.setNewTile(r, false);
				board.reduceIncoming(r);
				if (start < 0) {
					frame = 0;
					return true;
				}
			}
			if (end < Board.SIZE && board.getTile(r, end) == null
					&& !board.unstable(r, end)) {
				frame = 0;
				return true;
			} else {
				board.insertRow(row, r, start, end);
				return false;
			}
		} else {
			start--;
			end--;
			if (end >= Board.SIZE) {
				// newTile[r] = false;
				// board.setNewTile(r, false);
				board.reduceIncoming(r);
				if (end > Board.SIZE) {
					frame = 0;
					return true;
				}
			}
			if (start > 0 && board.getTile(r, start - 1) == null) {
				frame = 0;
				return true;
			} else {
				board.insertRow(row, r, start, end);
				return false;
			}
		}
	}

	@Override
	public void draw(Graphics2D pen) {
		int size = Tile.SIZE;
		int distance = (int) SPEED * frame;
		int x = Board.X;
		int y = Board.Y;
		if (turn) {
			for (int i = 0; i < end - start; i++) {
				row[i].draw(pen, x + (start + i) * size + distance, y
						+ (r * size));
			}
		} else
			for (int i = 0; i < end - start; i++) {
				row[i].draw(pen, x + (start + i) * size - distance, y
						+ (r * size));
			}
	}

}
/*
 * protected class FallingRow { Tile[] row; int r, start, end; int frame;
 * 
 * protected FallingRow(int r, int start, int end) { int buffer = 0; if
 * (board.fallRight) { // if (start == 0) { // buffer = board.getNumNewTiles(r);
 * // board.resetNumNewTiles(r); // } row = new Tile[end - start + buffer]; for
 * (int i = 0; i < buffer; i++) { row[i] = new Tile(); } for (int i = 0; i < end
 * - start; i++) { row[buffer + i] = board.getTile(r,start + i); //
 * tiles[r][start + i] = null; board.delete(r, start+i); } this.start = start -
 * buffer; this.end = end; } else { // if (end == Board.SIZE) { // buffer =
 * board.getNumNewTiles(r); // board.resetNumNewTiles(r); // } row = new
 * Tile[end - start + buffer]; for (int i = 0; i < end - start; i++) { row[i] =
 * board.getTile(r,start+i); board.delete(r,start + i); } for (int i = 0; i <
 * buffer; i++) { row[end - start + i] = new Tile(); } this.start = start;
 * this.end = end + buffer; } this.r = r; this.frame = 0; //
 * System.out.println(buffer); }
 * 
 * protected FallingRow(int r, int numNewTiles) { if (board.fallRight) {
 * this.row = new Tile[numNewTiles]; for (int i=0; i<numNewTiles; i++) { row[i]
 * = new Tile(); } this.start = 0 - numNewTiles; this.end = 0; } else { this.row
 * = new Tile[numNewTiles]; for (int i=0; i<numNewTiles; i++) { row[i] = new
 * Tile(); } this.start = Board.SIZE; this.end = Board.SIZE + numNewTiles; }
 * this.r = r; this.frame = 0; }
 * 
 * protected FallingRow(int r, NewTiles newTiles) { this.row = new
 * Tile[newTiles.size(r)]; for (int i=0; i<length; i++) { row[i] =
 * newTiles.get(r); } if (board.fallRight) { start = 0 - length; end = 0; } else
 * { start = Board.SIZE; end = Board.SIZE + length; } this.r = r; this.frame =
 * 0; System.out.println(start + " " + end); } }
 */
