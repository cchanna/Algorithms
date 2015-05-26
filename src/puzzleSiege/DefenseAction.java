package puzzleSiege;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Represents an <code>Action</code> turning a Defense <code>Tile</code> into a
 * <code>Wall Unit</code>.
 * 
 * @author Cerisa
 * @version 1.1 11/17/2013
 */
public class DefenseAction implements Action {

	public static final int TIME = 8;

	protected int r, c, level;

	protected boolean allied;

	private int frame;

	private static BufferedImage[] art;
	private int x, y;

	public DefenseAction(int r, int c, int level, boolean allied) {
		this.r = r;
		this.c = c;
		this.level = level;
		this.allied = allied;
		x = Board.X + Tile.SIZE * c;
		y = Board.Y + Tile.SIZE * r;
		frame = 0;
	}

	@Override
	public boolean onTick() {
		frame++;

		return frame >= TIME;
	}

	@Override
	public void onAdd(Board board) {
		board.setUnstable(r, c);
	}

	@Override
	public boolean activate(Board board) {
		board.setTile(r, c, new Wall(level, allied));
		board.setStable(r, c);
		return false;
	}

	@Override
	public void draw(Graphics2D pen) {
		pen.drawImage(Unit.getColor(allied), null, x, y);
		int index = (frame * art.length) / TIME;
		pen.drawImage(art[index], null, x, y);

	}

	public static void loadArt(String path) {
		art = GameWindow.loadImageAsTiles(path + "defense.png", Tile.SIZE);
	}

}
