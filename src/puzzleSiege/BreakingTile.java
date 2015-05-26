package puzzleSiege;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * An <code>Action</code> representing a tile in the process of getting destroyed.
 * @author Cerisa
 * @version 1.1 11/17/2013
 */
public class BreakingTile implements Action {
	public static final int TIME = 10;
	public static final String filename = "shatter.png";

	public static BufferedImage[] art;

	public int frame;
	int r, c;

	public BreakingTile(int r, int c) {
		super();
		this.r = r;
		this.c = c;
	}

	public boolean onTick() {
		frame++;
		if (frame >= TIME)
			return true;
		return false;
	}

	public void onAdd(Board board) {
		board.setUnstable(r, c);
	}

	@Override
	public boolean activate(Board board) {
		board.replace(r, c);
		board.setStable(r, c);
		return false;
	}

	@Override
	public void draw(Graphics2D pen) {
		int artFrame = (art.length / TIME) * frame;
		int x = Board.X + c * Tile.SIZE;
		int y = Board.Y + r * Tile.SIZE;
		pen.drawImage(art[artFrame], null, x, y);
	}

	public static void loadArt(String path) {
		art = GameWindow.loadImageAsTiles(path + filename, Tile.SIZE);
	}

}
