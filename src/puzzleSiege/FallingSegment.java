package puzzleSiege;

import java.awt.Graphics2D;

/**
 * A segment of tiles that are falling to a neutral position.
 * 
 * @author Cerisa
 * @version 1.3 12/11/2013
 */
public class FallingSegment implements Action {
	public static final int TIME = 2;
	public static final double SPEED = Tile.SIZE / TIME;

	protected boolean turn;

	Tile[] row;
	int r;
	int start, end;
	int frame;

	public FallingSegment(int r, int start, int end, Board board) {
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

	public FallingSegment(int r, int back, Tile[] tiles, boolean turn) {
		this.turn = turn;
		row = tiles;
		if (turn) {
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

	@Override
	public void onAdd(Board board) {
	}

	@Override
	public boolean activate(Board board) {
		if (board.currentTurn()) {
			start++;
			end++;
			if (start <= 0) {
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