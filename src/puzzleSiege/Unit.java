package puzzleSiege;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * An abstract class that represents a <code>Unit Tile</code>. Extended to
 * create specific types of <code>Unit</code>s.
 * 
 * @author Cerisa
 * @version 1.2 12/10/2013
 */
public abstract class Unit extends Tile {
	public static final int KNIGHT = 100, GOBLIN = 101, WALL = 102;
	private static BufferedImage[] unitArt;
	private static BufferedImage[] colors;
	private static BufferedImage[] levelArt;

	protected boolean allied;
	protected int damage, level;

	/**
	 * Sets the <code>Unit</code>'s art to the correct value.
	 * 
	 * @param id
	 */
	public Unit(int id) {
		if (Unit.unitArt != null) {
			art = Unit.unitArt[id - 100];
		}
	}

	/**
	 * All units deal their damage to the next tile in line when activated.
	 * 
	 * @param r
	 * @param c
	 * @param board
	 */
	public void onActivate(int r, int c, Board board) {
	}

	/**
	 * Attempts to move the unit to the right, forcing it to attack if an enemy
	 * is in the way.
	 * 
	 * @param r
	 * @param c
	 * @param board
	 */
	protected void moveRight(int r, int c, Board board) {
		Tile tile = board.getTile(r, c + 1);
		if (c + 1 >= Board.SIZE)
			board.damage(r, c + 1, damage, allied);
		else if (tile != null && (tile.isUnit() || tile.getId() == WALL)) {
			if (((Unit) tile).alliance() != allied) {
				board.damage(r, c + 1, damage, allied);
			} else
				rotateRight(r, c, board);
		} else if (tile != null) {
			rotateRight(r, c, board);
		}
	}

	/**
	 * Attempts to move the unit to the left, forcing it to attack if an enemy
	 * is in the way.
	 * 
	 * @param r
	 * @param c
	 * @param board
	 */
	protected void moveLeft(int r, int c, Board board) {
		Tile tile = board.getTile(r, c - 1);
		if (c - 1 < 0)
			board.damage(r, c - 1, damage, allied);
		else if (tile != null && (tile.isUnit() || tile.getId() == WALL)) {
			if (((Unit) tile).alliance() != allied) {
				board.damage(r, c - 1, damage, allied);
			} else
				rotateLeft(r, c, board);
		} else if (tile != null) {
			rotateLeft(r, c, board);
		}
	}

	/**
	 * Pushes the tile to the right of the unit to the left until it stops
	 * moving past units.
	 * 
	 * @param r
	 * @param c
	 * @param board
	 */
	protected void rotateRight(int r, int c, Board board) {
		int i = 1;
		Tile tile;
		tile = board.getTile(r, c - i);
		while (tile != null && tile.isUnit()
				&& ((Unit) tile).alliance() == allied) {
			i++;
			tile = board.getTile(r, c - i);
		}
		board.rotate(r, c + 1 - i, i, true);
	}

	/**
	 * Pushes the tile to the right of the unit to the left until it stops
	 * moving past units.
	 * 
	 * @param r
	 * @param c
	 * @param board
	 */
	protected void rotateLeft(int r, int c, Board board) {
		int length = 1;
		Tile tile;
		tile = board.getTile(r, c + length);
		while (tile != null && tile.isUnit()
				&& ((Unit) tile).alliance() == allied) {
			length++;
			tile = board.getTile(r, c + length);
		}
		board.rotate(r, c - 1, length, false);
	}

	/**
	 * Extended version of the <code>Tile</code> method. The damage is only
	 * dealt if the <code>Unit</code> is not allied with the damage.
	 */
	public boolean takeDamage(int damage, boolean allied) {
		if (allied != this.allied) {
			return super.takeDamage(damage);
		} else
			return false;
	}

	/**
	 * Returns the unit's damage.
	 * @return Returns the unit's damage.
	 */
	public int dealDamage() {
		return damage;
	}

	/**
	 * Returns the unit's alliance.
	 * @return Returns the unit's alliance.
	 */
	public boolean alliance() {
		return allied;
	}

	public void draw(Graphics2D pen, int x, int y) {
		if (allied)
			pen.drawImage(colors[0], null, x, y);
		else
			pen.drawImage(colors[1], null, x, y);
		super.draw(pen, x, y);
		if (level == 1) {
			pen.drawImage(levelArt[0], null, x, y);
		} else if (level > 1) {
			pen.drawImage(levelArt[1], null, x, y);
		}
	}

	public static BufferedImage getColor(boolean allied) {
		if (allied)
			return colors[0];
		else
			return colors[1];
	}

	public static Unit createUnit(int id, boolean allied) {
		if (id == 100)
			return new Knight();
		if (id == 101)
			return new Goblin();
		if (id == 102)
			return new Wall(allied);
		else
			return null;
	}

	public static void loadUnitArt(String path) {
		unitArt = GameWindow.loadImageAsTiles(path + "units.png", Tile.SIZE);
		colors = GameWindow.loadImageAsTiles(path + "colors.png", Tile.SIZE);
		levelArt = GameWindow.loadImageAsTiles(path + "level.png", Tile.SIZE);
	}

	public String toString() {
		return (id % 100) + "";
	}
}
