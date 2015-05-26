package puzzleSiege;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Represents the clear effect of sword tiles. Damages all non-allied
 * <code>Unit</code>s in the same row/column as the matched <code>Tile</code>s.
 * 
 * @author Cerisa
 * @version 1.1 11/18/2013
 */
public class SwordAction implements Action {
	public static final int TIME = 16;

	private int i;
	private int level;
	private boolean horizontal;
	private int frame;

	private static BufferedImage[] art;
	private int x, y;
	private AffineTransform rotate;

	/**
	 * Constructs a <code>SwordAction</code> by giving it the row or column
	 * number, how many tiles were matched, and whether it was a horizontal or
	 * vertical match (and thus whether the first number is a row or column,
	 * respectively).
	 * 
	 * @param i
	 * @param count
	 * @param horizontal
	 */
	public SwordAction(int i, int count, boolean horizontal) {
		this.i = i;
		level = Math.min(count - 3, 2);
		this.horizontal = horizontal;
		frame = 0;
		x = Board.X;
		y = Board.Y;
		rotate = new AffineTransform();
		if (horizontal) {
			y += Tile.SIZE * (i + 1);
		} else
			x += Tile.SIZE * i;
		rotate.translate(x, y);
		if (horizontal)
			rotate.rotate(-Math.PI / 2.0);
	}

	@Override
	public boolean onTick() {
		frame++;
		return frame >= TIME;
	}

	/**
	 * To avoid any weird results of <code>Unit</code>s avoiding damage by
	 * getting put in motion from the destruction of the original Tiles, the
	 * actual event of the <code>SwordAction</code> occurs on the add.
	 */
	@Override
	public void onAdd(Board board) {
		if (horizontal) {
			for (int c = 0; c < Board.SIZE; c++) {
				Tile tile = board.getTile(i, c);
				if (tile != null && !board.unstable(i, c)) {
					if (tile.getId() >= 100)
						board.damage(i, c, level + 1, board.currentTurn());
				}
			}
		} else {
			for (int r = 0; r < Board.SIZE; r++) {
				Tile tile = board.getTile(r, i);
				if (tile != null && !board.unstable(r, i)) {
					if (board.getTile(r, i).getId() >= 100)
						board.damage(r, i, level + 1, board.currentTurn());
				}
			}
		}
	}

	@Override
	public boolean activate(Board board) {
		return false;
	}

	@Override
	public void draw(Graphics2D pen) {
		pen.drawImage(art[0], rotate, null);
	}

	public static void loadArt(String path) {
		art = GameWindow.loadImageAsTiles(path + "swordAction.png", Tile.SIZE);
	}
}
