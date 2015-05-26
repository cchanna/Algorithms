package puzzleSiege;

import java.awt.Graphics2D;

/**
 * An advanced swap that moves a single tile past an entire row of units.
 * @author Cerisa
 * @version 1.1 12/11/2013
 */
public class Rotate implements Action {
	public static final int TIME = 8;
	public static final int SPEED = Tile.SIZE / TIME;

	protected int r, c, length;
	protected int frame;
	protected boolean right;
	protected Tile back;
	protected Tile[] front;

	public Rotate(int r, int c, int length, boolean right) {
		this.r = r;
		this.c = c;
		this.length = length;
		this.right = right;
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
		if (right) {
			front = new Tile[length];
			int i;
			for (i = 0; i < length; i++) {
				front[i] = board.getTile(r, c + i);
				board.setUnstable(r, c + i);
				board.delete(r, c + i);
			}
			back = board.getTile(r, c + i);
			board.setUnstable(r, c + i);
			board.delete(r, c + i);
		} else {
			front = new Tile[length];
			int i;
			back = board.getTile(r, c);
			board.setUnstable(r, c);
			board.delete(r, c);
			for (i = 1; i <= length; i++) {
				front[i-1] = board.getTile(r, c + i);
				board.setUnstable(r, c + i);
				board.delete(r, c + i);
			}
		}
	}

	@Override
	public boolean activate(Board board) {
		if (right) {
			board.setTile(r, c, back);
			board.setStable(r,c);
			board.insertRow(front, r, c+1, c + length+1);
		} else {
			board.setTile(r, c+length, back);
			board.setStable(r, c + length);
			board.insertRow(front, r, c, c + length);
		}
		return false;
	}

	@Override
	public void draw(Graphics2D pen) {
		int x = Board.X + c * Tile.SIZE;
		int y = Board.Y + r * Tile.SIZE;
		int speed = SPEED*frame;
		if (right) {
			back.draw(pen, x + Tile.SIZE * (length) - speed*length,
					y);
			for (int i = 0; i < length; i++) {
				front[i].draw(pen, x + speed + Tile.SIZE * i, y);
			}
		} else {
			back.draw(pen, x + speed*length, y);
			for (int i = 0; i < length; i++) {
				front[i].draw(pen, x + Tile.SIZE * (i + 1)
						- (speed), y);
			}
		}
	}
}
