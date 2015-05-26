package puzzleSiege;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Represents two adjacent tiles swapping positions.
 * 
 * @author Cerisa
 * @version 1.2 12/11/2013
 */
public class Swap implements Action {
	public static final int TIME = 8;
	public static final int SPEED = Tile.SIZE / TIME;

	private int r, c;
	private int frame;
	private boolean horizontal;
	private Tile tile1, tile2;

	

	public static BufferedImage art;

	/**
	 * Constructs the swap by giving the row and column of the top-left
	 * <code>Tile</code>, and whether the swap is a horizontal or vertical one.
	 * 
	 * @param r
	 * @param c
	 * @param horizontal
	 */
	public Swap(int r, int c, boolean horizontal) {
		this.r = r;
		this.c = c;
		this.horizontal = horizontal;
	}

	public boolean onTick() {
		frame++;
		if (frame >= TIME)
			return true;
		return false;
	}

	/**
	 * Sets the two <code>Tile</code>s as unstable.
	 */
	public void onAdd(Board board) {
		tile1 = board.getTile(r, c);
		board.setUnstable(r, c);
		if (horizontal) {
			tile2 = board.getTile(r, c + 1);
			board.setUnstable(r, c + 1);
		} else {
			tile2 = board.getTile(r + 1, c);
			board.setUnstable(r + 1, c);
		}
	}

	/**
	 * Swaps the <code>Tile</code>s and sets them as stable.
	 */
	public boolean activate(Board board) {
		board.setTile(r, c, tile2);
		board.setStable(r, c);
		if (horizontal) {
			board.setTile(r, c + 1, tile1);
			board.setStable(r, c + 1);
			board.clear(r, c);
			board.clear(r, c + 1);
		} else {
			board.setTile(r + 1, c, tile1);
			board.setStable(r + 1, c);
			board.clear(r, c);
			board.clear(r + 1, c);
		}
		return false;
	}
	
	public int getR() {
		return r;
	}

	public int getC() {
		return c;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public void draw(Graphics2D pen) {
		int x = Board.X + c * Tile.SIZE;
		int y = Board.Y + r * Tile.SIZE;
		pen.drawImage(art, null, x, y);
		if (horizontal) {
			pen.drawImage(art, null, x + Tile.SIZE, y);
			tile1.draw(pen, x + SPEED * frame, y);
			tile2.draw(pen, x + Tile.SIZE - SPEED * frame, y);
		} else {
			pen.drawImage(art, null, x, y + Tile.SIZE);
			tile1.draw(pen, x, y + SPEED * frame);
			tile2.draw(pen, x, y + Tile.SIZE - SPEED * frame);
		}
	}

	public static void loadArt(String path) {
		art = GameWindow.loadImage(path + "blank.png");
	}
}
