package puzzleSiege;

import static edu.princeton.cs.introcs.StdRandom.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Represents a single <code>Tile</code> on the <code>Board</code>. Also
 * contains helper functions relevant to tiles.
 * 
 * @author Cerisa
 * @version 1.1 11/18/2013
 */
public class Tile {
	public static final int NUM_TILES = 6;
	public static final int SWORD = 0, ARROW = 1, COIN = 2, DEFENSE = 3,
			RALLY = 4, BOMB = 5;
	public static final char[] NAMES = { 'S', 'A', 'C', 'D', 'R', 'B' };

	public static enum State {
		FALL_LEFT, FALL_RIGHT, CLEAR
	}

	// ART
	public static final int SIZE = 32;
	private static BufferedImage[] tileArt;
	private static BufferedImage[] damageArt;

	protected BufferedImage art;

	State state;
	int r, c, frame;
	int maxHealth;
	int health;

	protected int id = -1;

	/**
	 * Constructs a random tile.
	 */
	public Tile() {
		id = randomTile();
		if (tileArt != null) {
			art = tileArt[id];
		}
		maxHealth = 1;
		health = 1;
	}

	/**
	 * Given a Tile id, constructs a tile of that type.
	 * 
	 * @param id
	 */
	public Tile(int id) {
		this.id = id;
		if (tileArt != null) {
			art = tileArt[id];
		}
		health = 1;
	}

	/**
	 * Given a tile id in the form a <code>char</code>, constructs a tile of
	 * that type.
	 * 
	 * @param id
	 */
	public Tile(char id) {
		this();
		if (id == 'S')
			this.id = SWORD;
		if (id == 'A')
			this.id = ARROW;
		if (id == 'C')
			this.id = COIN;
		if (id == 'D')
			this.id = DEFENSE;
		if (id == 'R')
			this.id = RALLY;
		if (id == 'B')
			this.id = BOMB;

	}

	/**
	 * HERE THERE BE ALGORITHMS returns a <code>size</code> by <code>size</code> array of
	 * <code>Tile</code>s that contains no matches.
	 * 
	 * @param size
	 * @return returns a <code>size</code> by <code>size</code> array of
	 *         <code>Tile</code>s that contains no matches.
	 */
	public static Tile[][] stratify(int size) {
		final int SEGMENT = 4;
		Tile[][] output = new Tile[size][size];
		for (int r = 0; r < size; r += SEGMENT) {
			for (int c = 0; c < size; c += SEGMENT) {
				stratify(r, c, SEGMENT, output);
			}
		}

		return output;
	}

	/**
	 * Creates a non-explosive grid of tiles.
	 * 
	 * @param r
	 * @param c
	 * @param size
	 * @param output
	 */
	private static void stratify(int r, int c, int size, Tile[][] output) {
		Tile[][] tiles = latinSquare();
		for (int i = 0; i < size && i + r < output.length; i++) {
			for (int k = 0; k < size && k + c < output[0].length; k++) {
				output[r + i][c + k] = tiles[i][k];
			}
		}
	}

	/**
	 * Returns a latin square of tiles.
	 * 
	 * @return Returns a latin square of tiles.
	 */
	private static Tile[][] latinSquare() {
		Tile[][] square = new Tile[NUM_TILES][NUM_TILES];
		for (int r = 0; r < NUM_TILES; r++) {
			for (int c = 0; c < NUM_TILES; c++) {
				int t = (c + r) % NUM_TILES;
				square[r][c] = new Tile(t);
			}
		}
		for (int r = 0; r < NUM_TILES; r++) {
			int position = r + uniform(NUM_TILES - r);
			Tile[] temp = square[r];
			square[r] = square[position];
			square[position] = temp;
		}
		square = transpose(square);
		for (int r = 0; r < NUM_TILES; r++) {
			int position = r + uniform(NUM_TILES - r);
			Tile[] temp = square[r];
			square[r] = square[position];
			square[position] = temp;
		}
		return square;
	}

	/**
	 * Transposes (the matrix operation) a given array of tiles.
	 * 
	 * @param array
	 * @return Returns the transposed array.
	 */
	private static Tile[][] transpose(Tile[][] array) {
		Tile[][] output = new Tile[array[0].length][array.length];
		for (int r = 0; r < array[0].length; r++) {
			for (int c = 0; c < array.length; c++) {
				output[r][c] = array[c][r];
			}
		}
		return output;
	}

	/**
	 * Given a <code>char</code> representing a <code>Tile</code>, returns the
	 * appropriate id.
	 * 
	 * @param code
	 * @return Returns an <code>int</code> of the appropriate id.
	 */
	public static int charToId(char code) {
		if (code == 'S')
			return SWORD;
		if (code == 'A')
			return ARROW;
		if (code == 'C')
			return COIN;
		if (code == 'D')
			return DEFENSE;
		if (code == 'R')
			return RALLY;
		if (code == 'B')
			return BOMB;
		else
			return code - '0' + 100;
	}

	/**
	 * Reduces the tile's health by the given amount, returning true if it
	 * reduced its health to 0 or less.
	 * 
	 * @param damage
	 * @return Returns true if the damage destroyed the tile.
	 */
	public boolean takeDamage(int damage) {
		health -= damage;
		if (health <= 0)
			return true;
		return false;
	}

	/**
	 * Calls <code>takeDamage(damage)</code>. Exists so <code>Unit</code> tiles
	 * can extend it.
	 * 
	 * @param damage
	 * @param allied
	 * @return Returns true if the damage destroyed the <code>Tile</code>.
	 */
	public boolean takeDamage(int damage, boolean allied) {
		return takeDamage(damage);
	}

	public int getId() {
		return id;
	}

	/**
	 * Returns true if the given <code>Tile</code> is a <code>Unit</code>.
	 * 
	 * @return Returns true if the given <code>Tile</code> is a
	 *         <code>Unit</code>.
	 */
	public boolean isUnit() {
		return id >= 100;
	}

	public void draw(Graphics2D pen, int x, int y) {
		pen.drawImage(art, null, x, y);
		if (maxHealth != 0 && health < maxHealth) {
			if (health < 0)
				health = 0;
			double damageRatio = 1 - ((health * 1.0) / (maxHealth * 1.0));
			int damageFrame = (int) (damageRatio * damageArt.length) - 1;
			pen.drawImage(damageArt[damageFrame], null, x, y);
		}
	}

	/**
	 * Returns a random id from the range of possible <code>Tile</code>s.
	 * 
	 * @return
	 */
	private static int randomTile() {
		return uniform(NUM_TILES);
	}

	/**
	 * Returns a single-character <code>String</code> representing the type of
	 * <code>Tile</code>.
	 */
	public String toString() {
		if (id != -1)
			return "" + NAMES[id];
		else
			return "-";
	}

	/**
	 * Given a <code>String</code>, returns a row of <code>Tile</code>s using
	 * the <code>chars</code> as ids.
	 * 
	 * @param code
	 * @return Returns the constructed row.
	 */
	public static Tile[] tileRow(String code) {
		Tile[] output = new Tile[Board.SIZE];
		for (int i = 0; i < code.length(); i++) {
			int id = charToId(code.charAt(i));
			if (id < 100)
				output[i] = new Tile(id);
			else
				output[i] = Unit.createUnit(id, true);

		}
		return output;
	}

	/**
	 * Returns true if the two tiles have the same id.
	 * 
	 * @param tile
	 * @return Returns true if the two tiles have the same id.
	 */
	public boolean equals(Tile tile) {
		if (tile == null)
			return false;
		if (id < 0 || tile.id < 0)
			return false;
		return (id == tile.id);
	}

	/**
	 * Static function for determining the effects of tile matches.
	 * 
	 * @param board
	 * @param r
	 * @param c
	 * @param id
	 * @param count
	 * @param horizontal
	 */
	public static void match(Board board, int r, int c, int id, int count,
			boolean horizontal) {
		if (id == SWORD)
			swordAction(board, r, c, count, horizontal);
		else if (id == RALLY)
			rallyAction(board, r, c, count, horizontal);
		else if (id == COIN)
			coinAction(board, r, c, count, horizontal);
		else if (id == DEFENSE)
			defenseAction(board, r, c, count, horizontal);
		else if (id == BOMB)
			bombAction(board, r, c, count, horizontal);
		else if (id == ARROW)
			arrowAction(board, r, c, count, horizontal);
		else if (id < 100) {
			if (horizontal) {
				for (int i = 0; i < count; i++) {
					board.shatter(r, c + i);
				}
			} else {
				for (int i = 0; i < count; i++) {
					board.shatter(r + i, c);
				}
			}
		}

	}

	/**
	 * Creates a new <code>SwordAction</code>
	 * 
	 * @param board
	 * @param r
	 * @param c
	 * @param count
	 * @param horizontal
	 */
	private static void swordAction(Board board, int r, int c, int count,
			boolean horizontal) {
		if (horizontal) {
			board.addAction(new SwordAction(r, count, true));
			for (int i = 0; i < count; i++) {
				board.shatter(r, c + i);
			}
		} else {
			board.addAction(new SwordAction(c, count, false));
			for (int i = 0; i < count; i++) {
				board.shatter(r + i, c);
			}
		}
	}

	/**
	 * Adds a new Unit to be created.
	 * 
	 * @param board
	 * @param r
	 * @param c
	 * @param count
	 * @param horizontal
	 */
	private static void rallyAction(Board board, int r, int c, int count,
			boolean horizontal) {
		if (horizontal) {
			board.addUnit(r, count - 3);
			for (int i = 0; i < count; i++) {
				board.shatter(r, c + i);
			}
		} else {
			board.addUnit(uniform(count) + r, count - 3);
			for (int i = 0; i < count; i++) {
				board.shatter(r + i, c);
			}
		}
	}

	/**
	 * Adds to the player's score, or subtracts if it's the computer's turn.
	 * 
	 * @param board
	 * @param r
	 * @param c
	 * @param count
	 * @param horizontal
	 */
	private static void coinAction(Board board, int r, int c, int count,
			boolean horizontal) {
		if (horizontal) {
			for (int i = 0; i < count; i++) {
				board.shatter(r, c + i);
			}
		} else {
			for (int i = 0; i < count; i++) {
				board.shatter(r + i, c);
			}
		}
		if (board.currentTurn())
			board.addScore((count - 2) * 100);
		else
			board.addScore((2 - count) * 25);
	}

	/**
	 * Creates new <code>DefenseAction</code>s to transform all of the matched
	 * tiles into <code>Wall Unit</code>s.
	 * 
	 * @param board
	 * @param r
	 * @param c
	 * @param count
	 * @param horizontal
	 */
	private static void defenseAction(Board board, int r, int c, int count,
			boolean horizontal) {
		if (horizontal) {
			for (int i = 0; i < count; i++) {
				if (board.getTile(r, c + i) != null) {
					board.addAction(new DefenseAction(r, c + i, count - 3,
							board.currentTurn()));
				}
			}
		} else {
			for (int i = 0; i < count; i++) {
				if (board.getTile(r + i, c) != null) {
					board.addAction(new DefenseAction(r + i, c, count - 3,
							board.currentTurn()));
				}
			}
		}
	}

	/**
	 * Damages all tiles adjacent to the matched tiles.
	 * 
	 * @param board
	 * @param r
	 * @param c
	 * @param count
	 * @param horizontal
	 */
	private static void bombAction(Board board, int r, int c, int count,
			boolean horizontal) {
		int damage = count - 2;
		boolean allied = board.currentTurn();
		if (horizontal) {
			for (int i = 0; i < count + 2; i++) {
				board.damageTile(r - 1, c + i - 1, damage, allied);
				board.damageTile(r + 1, c + i - 1, damage, allied);
			}
			board.damageTile(r, c - 1, damage, allied);
			for (int i = 0; i < count; i++) {
				board.shatter(r, c + i);
			}
			board.damageTile(r, c + count, damage, allied);
		} else {
			for (int i = 0; i < count + 2; i++) {
				board.damageTile(r + i - 1, c - 1, damage, allied);
				board.damageTile(r + i - 1, c + 1, damage, allied);
			}
			board.damageTile(r - 1, c, damage, allied);
			for (int i = 0; i < count; i++) {
				board.shatter(r + i, c);
			}
			board.damageTile(r + count, c, damage, allied);
		}
	}

	/**
	 * For each matched <code>Tile</code>, creates an <code>ArrowAction</code>
	 * in that row.
	 * 
	 * @param board
	 * @param r
	 * @param c
	 * @param count
	 * @param horizontal
	 */
	private static void arrowAction(Board board, int r, int c, int count,
			boolean horizontal) {
		// board.addArrows(r, count, horizontal);
		if (horizontal) {
			for (int i = 0; i < count; i++) {
				board.addAction(new ArrowAction(r, i * 2, board.currentTurn()));
			}
		} else
			for (int i = 0; i < count; i++) {
				board.addAction(new ArrowAction(r + i, board.currentTurn()));
			}
		if (horizontal) {
			for (int i = 0; i < count; i++) {
				board.shatter(r, c + i);
			}
		} else {
			for (int i = 0; i < count; i++) {
				board.shatter(r + i, c);
			}
		}
	}

	public static void loadArt(String path) {
		tileArt = GameWindow.loadImageAsTiles(path + "tiles.png", SIZE);
		damageArt = GameWindow.loadImageAsTiles(path + "damage.png", SIZE);
	}

}