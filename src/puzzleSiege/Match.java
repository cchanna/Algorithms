package puzzleSiege;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * A match on the board that has been located. Calls <code>Tile.match</code>.
 * 
 * @author Cerisa
 * @version 1.1
 */
public class Match implements Action {
	public static final int TIME = 4;

	private int r, c;
	private int id;
	private int count;
	private int frame;
	private boolean horizontal;

	private static BufferedImage[] art;

	public Match(int r, int c, int id, int count, boolean horizontal) {
		this.r = r;
		this.c = c;
		this.horizontal = horizontal;
		this.id = id;
		this.count = count;
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
		if (horizontal) {
			for (int i = 0; i < count; i++) {
				board.setUnstable(r, c + i);
			}
		} else {
			for (int i = 0; i < count; i++) {
				board.setUnstable(r + i, c);
			}
		}
	}

	@Override
	public boolean activate(Board board) {
		if (horizontal) {
			for (int i = 0; i < count; i++) {
				board.setStable(r, c + i);
			}
		} else {
			for (int i = 0; i < count; i++) {
				board.setStable(r + i, c);
			}
		}
		Tile.match(board, r, c, id, count, horizontal);
		return false;
	}

	@Override
	public void draw(Graphics2D pen) {
		int x = Board.X;
		int y = Board.Y;
		if (horizontal) {
			for (int i = 0; i < count; i++) {
				pen.drawImage(art[0], null, x + ((c + i) * Tile.SIZE), y
						+ (r * Tile.SIZE));
			}
		} else {
			for (int i = 0; i < count; i++) {
				pen.drawImage(art[0], null, x + (c * Tile.SIZE), y
						+ ((r + i) * Tile.SIZE));
			}
		}
	}

	public static void loadArt(String path) {
		art = GameWindow.loadImageAsTiles(path + "match.png", Tile.SIZE);
	}

}
