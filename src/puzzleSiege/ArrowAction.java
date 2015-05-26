package puzzleSiege;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The <code>Action</code> that results from matching arrow tiles.
 * 
 * @author Cerisa
 * @version 1.1 11/17/2013
 */
public class ArrowAction implements Action {
	public static final int TIME = 2;
	public static final int SPEED = Tile.SIZE / TIME;

	private int r, c;
	private boolean allied;
	private int x, y;
	private int frame;

	private static BufferedImage[] art;

	/**
	 * Passes the row the arrow is created on, and which direction it should be
	 * firing.
	 * 
	 * @param r
	 * @param allied
	 */
	public ArrowAction(int r, boolean allied) {
		this.r = r;
		this.y = Board.Y + Tile.SIZE * r;
		frame = 0;
		this.allied = allied;
		if (allied) {
			c = -1;
			this.x = Board.X - (Tile.SIZE);
		} else {
			c = Board.SIZE;
			this.x = Board.X + (Board.SIZE) * (Tile.SIZE);
		}
	}

	/**
	 * Gives a value <code>f</code> that delays the arrow before it appears on
	 * the board. For horizontal matches.
	 * 
	 * @param r
	 * @param f
	 * @param allied
	 */
	public ArrowAction(int r, int f, boolean allied) {
		this(r, allied);
		frame -= f * TIME;
		if (allied)
			x -= Tile.SIZE * f;
		else
			x += Tile.SIZE * f;
	}

	/**
	 * Returns <code>true</code> every time the arrow has moved to the next
	 * tile.
	 */
	@Override
	public boolean onTick() {
		if (allied)
			x += SPEED;
		else
			x -= SPEED;
		return ++frame >= TIME;
	}

	@Override
	public void onAdd(Board board) {
	}

	/**
	 * Checks to see if the arrow has run into a unit. If it has, it damages it,
	 * otherwise, it continues to fly.
	 */
	@Override
	public boolean activate(Board board) {
		if (allied && ++c >= Board.SIZE) return false;
		if (!allied && --c < 0) return false;
		if (!board.unstable(r, c)) {
			Tile tile = board.getTile(r, c);
			if (tile != null && tile.isUnit() && ((Unit) tile).alliance() != allied) {
				board.damage(r, c, 1);
				return false;
			}
		}
		frame = 0;
		return true;
	}

	@Override
	public void draw(Graphics2D pen) {
		if (allied)
			pen.drawImage(art[0], null, x, y);
		else
			pen.drawImage(art[1], null, x, y);
	}

	public static void loadArt(String path) {
		art = GameWindow.loadImageAsTiles(path + "arrow.png", Tile.SIZE);
	}

}
