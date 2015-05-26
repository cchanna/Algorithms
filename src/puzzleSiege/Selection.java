package puzzleSiege;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Represents a single tile that has been clicked on.
 * 
 * @author Cerisa
 * @version 1.1 11/18/2013
 */
public class Selection {
	private static BufferedImage[] art;

	protected int r, c;
	protected int x, y;

	public Selection(int r, int c) {
		this.r = r;
		this.c = c;
		x = Board.X + c * Tile.SIZE;
		y = Board.Y + r * Tile.SIZE;
	}

	public void draw(Graphics2D pen) {
		pen.drawImage(art[0], null, x, y);
	}

	/**
	 * Returns true if the given coordinates are immediately to the left of the
	 * selection.
	 * 
	 * @param r
	 * @param c
	 * @return Returns true if the given coordinates are immediately to the left
	 *         of the selection.
	 */
	public boolean left(int r, int c) {
		return (this.r == r && c + 1 == this.c);
	}

	/**
	 * Returns true if the given coordinates are immediately to the right of the
	 * selection.
	 * 
	 * @param r
	 * @param c
	 * @return Returns true if the given coordinates are immediately to the
	 *         right of the selection.
	 */
	public boolean right(int r, int c) {
		return (r == this.r && c - 1 == this.c);
	}

	/**
	 * Returns true if the given coordinates are immediately above the
	 * selection.
	 * 
	 * @param r
	 * @param c
	 * @return Returns true if the given coordinates are immediately above the
	 *         selection.
	 */
	public boolean above(int r, int c) {
		return (r + 1 == this.r && c == this.c);
	}

	/**
	 * Returns true if the given coordinates are immediately below the given
	 * selection.
	 * 
	 * @param r
	 * @param c
	 * @return Returns true if the given coordinates are immediately below the
	 *         given selection.
	 */
	public boolean below(int r, int c) {
		return (r - 1 == this.r && c == this.c);
	}

	public static void loadArt(String path) {
		art = GameWindow.loadImageAsTiles(path + "selection.png", Tile.SIZE);
	}

}
