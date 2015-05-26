package puzzleSiege;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Represents the game board itself. This the hub of action for the game, and as
 * such tends to pass itself around to a lot of other classes/methods so that
 * they can interact with it.
 * 
 * @author Cerisa
 * @version 1.4 12/11/2013
 */
public class Board {
	public static final int X = 256, Y = 128;

	private boolean isTurn = false;
	public final static boolean PLAYER = true, COMPUTER = false;
	private boolean turn = PLAYER;
	private boolean unitsDone = false;

	public static final int SIZE = 16;
	private Tile[][] tiles;

	private boolean[][] unstable;

	private ActionQueue<Match> matches;
	private ActionQueue<BreakingTile> breakingTiles;
	private ActionQueue<FallingSegment> segments;
	private ActionQueue<Action> clearActions;
	private ActionQueue<Rotate> rotates;

	private Swap swap;
	private Selection selection;
	private NewTiles newTiles;

	private Player player;
	private Computer computer;

	private static BufferedImage frameArt;

	/**
	 * Makes a new, randomly generated <code>board</code>. Uses the
	 * <code>Tile.stratify</code> method to randomize themselves in such a way
	 * that no match-3s appear in the initial board state.
	 */
	public Board() {
		tiles = Tile.stratify(SIZE);
		unstable = new boolean[SIZE][SIZE];
		matches = new ActionQueue<Match>(this);
		breakingTiles = new ActionQueue<BreakingTile>(this);
		segments = new ActionQueue<FallingSegment>(this);
		clearActions = new ActionQueue<Action>(this);
		newTiles = new NewTiles();
		rotates = new ActionQueue<Rotate>(this);
	}

	/**
	 * Create a non-random <code>board</code> using an array of Tiles.
	 * 
	 * @param tiles
	 */
	public Board(Tile[][] tiles) {
		this();
		this.tiles = tiles;
	}

	/**
	 * Establishes the player 1 (the <code>Player</code>) and player 2 (the
	 * <code>Computer</code>) of the game. Necessary in order for damage to be
	 * dealt to players, but should not break testing clients if not called.
	 * 
	 * @param player
	 * @param computer
	 */
	public void setActors(Player player, Computer computer) {
		this.player = player;
		this.computer = computer;
	}

	/**
	 * Runs through all of the various animations, actions, and effects that can
	 * be happening on the board, and resolves them one by one. If they are all
	 * empty, then it instead checks to see if the units have acted yet, and if
	 * not, activates them, likely beginning a new chain of actions. Once those
	 * have been resolved, it returns <code>true</code>, signifying to the
	 * <code>Game</code> that the turn is now over.
	 * 
	 * @return True if the turn is over.
	 */
	public boolean onTick() {
		// tick = (tick + 1) % 60;
		// if (tick == 0)
		if (!isTurn) {
			boolean ready = true;
			ready = (resolveSwap() && ready);
			ready = (matches.onTick() && ready);
			ready = (breakingTiles.onTick() && ready);
			ready = (segments.onTick() && ready);
			ready = (clearActions.onTick() && ready);
			ready = (rotates.onTick() && ready);
			collapseRows();
			if (ready) {
				if (fixRows()) {
					if (unitsDone) {
						return true;
					} else {
						resolveUnits();
						unitsDone = true;
					}
				}

			}
		}
		return false;
	}

	/**
	 * Because there can only be one <code>Swap</code> at a time (for now),
	 * functions as a mini-<code>ActionQueue</code> that only accepts one
	 * element.
	 * 
	 * @return True if nothing is current being swapped.
	 */
	protected boolean resolveSwap() {
		if (swap == null)
			return true;

		if (swap.onTick()) {
			swap.activate(this);
			swap = null;
		}

		return false;

	}

	/**
	 * Activates each of the units, dealing their damage to the next thing in
	 * line, and any using any other special abilities they might have.
	 */
	protected void resolveUnits() {
		unitsDone = true;
		for (int r = 0; r < SIZE; r++) {
			if (!newTiles.unitsEmpty(r)) {
				if (turn == PLAYER) {
					damage(r, 0, newTiles.peekUnitDamage(r), PLAYER);
				} else {
					damage(r, SIZE - 1, newTiles.peekUnitDamage(r), COMPUTER);
				}
			}
			for (int c = 0; c < SIZE; c++) {
				if (tiles[r][c] != null && !unstable[r][c]
						&& tiles[r][c].id >= 100) {
					Unit unit = (Unit) tiles[r][c];
					if (unit.allied == turn) {
						unit.onActivate(r, c, this);
					}
				}
			}
		}
	}

	/**
	 * Draws all the things.
	 * 
	 * @param pen
	 */
	public void draw(Graphics2D pen) {
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				if (tiles[r][c] != null) {
					tiles[r][c].draw(pen, X + (c * Tile.SIZE), Y
							+ (r * Tile.SIZE));
				}
			}
		}
		breakingTiles.draw(pen);
		matches.draw(pen);
		segments.draw(pen);
		clearActions.draw(pen);
		rotates.draw(pen);
		if (selection != null) {
			selection.draw(pen);
		}
		if (swap != null)
			swap.draw(pen);
		pen.drawImage(frameArt, null, 0, 0);
	}

	/**
	 * Resolves what happens if the board is clicked. If it's the player's turn
	 * to act, allows for a <code>swap</code> to occur.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean click(int x, int y) {
		int c = x / Tile.SIZE, r = y / Tile.SIZE;
		if (tiles[r][c] != null && !unstable[r][c]) {
			if (selection == null) {
				selection = new Selection(r, c);
			} else if (selection.left(r, c)) {
				swap(r, c, true);
				return true;
			} else if (selection.right(r, c)) {
				swap(r, c - 1, true);
				return true;
			} else if (selection.above(r, c)) {
				swap(r, c, false);
				return true;
			} else if (selection.below(r, c)) {
				swap(r - 1, c, false);
				return true;
			} else
				selection = null;
		}
		return false;
	}

	/**
	 * Swaps two tiles, and signifies that this turn is now going into the
	 * action-resolution stage.
	 * 
	 * @param r
	 * @param c
	 * @param horizontal
	 */
	public void swap(int r, int c, boolean horizontal) {
		// if (swap == null) {
		// swap = new Swap(r, c, horizontal);
		// swap.onAdd(this);
		// selection = null;
		// isTurn = false;
		// } else {
		// System.err.println("double swaps are bad");
		// }
		swap(new Swap(r, c, horizontal));
	}

	/**
	 * Given a swap action, sets it to work on the board.
	 * 
	 * @param swap
	 */
	public void swap(Swap swap) {
		if (this.swap == null) {
			this.swap = swap;
			swap.onAdd(this);
			selection = null;
			isTurn = false;
		} else {
			System.err.println("double swaps are bad");
		}
	}

	/** 
	 * Puts a rotate in the rotate queue.
	 * @param r
	 * @param c
	 * @param length
	 * @param right
	 */
	public void rotate(int r, int c, int length, boolean right) {
		rotates.add(new Rotate(r, c, length, right));
	}

	/**
	 * Destroys a tile. Creates a <code>BreakingTile</code> at
	 * <code>[r],[c]</code>that will remove the tile and replace it with a new
	 * one.
	 * 
	 * @param r
	 * @param c
	 */
	public void shatter(int r, int c) {
		if (tiles[r][c] != null) {
			breakingTiles.add(new BreakingTile(r, c));
		}
	}

	/**
	 * Delete a tile, and queue a new tile to be added in that row.
	 * 
	 * @param r
	 * @param c
	 */
	public void replace(int r, int c) {
		if (tiles[r][c] != null) {
			tiles[r][c] = null;
			newTiles.add(r);
		}
		setStable(r, c);
	}

	/**
	 * Remove a tile without replacing it.
	 * 
	 * @param r
	 * @param c
	 */
	public void delete(int r, int c) {
		tiles[r][c] = null;
	}

	/**
	 * Deal damage to a tile, or an actor if that damage is off the side of the
	 * board.
	 * 
	 * @param r
	 * @param c
	 * @param damage
	 */
	public void damage(int r, int c, int damage) {
		if (c < 0) {
			player.damage(damage);
		} else if (c >= SIZE) {
			computer.damage(damage);
		} else if (r >= 0 && r < SIZE && tiles[r][c] != null && !unstable[r][c]
				&& tiles[r][c].takeDamage(damage)) {
			shatter(r, c);
		}
	}

	/**
	 * Deal "allied damage" to a tile or actor. Allied damage only gets resolved
	 * if it is being dealt to something that is not allied with the damage.
	 * 
	 * @param r
	 * @param c
	 * @param damage
	 * @param allied
	 */
	public void damage(int r, int c, int damage, boolean allied) {
		if (c < 0) {
			if (player != null && !allied)
				player.damage(damage);
		} else if (c >= SIZE) {
			if (computer != null && allied != COMPUTER)
				computer.damage(damage);
		} else if (r >= 0 && r < SIZE && tiles[r][c] != null && !unstable[r][c]
				&& tiles[r][c].takeDamage(damage, allied)) {
			shatter(r, c);
		}
	}

	/**
	 * Calls damage, but only if it's actually a tile (so it can't effect
	 * players).
	 * 
	 * @param r
	 * @param c
	 * @param damage
	 * @param allied
	 */
	public void damageTile(int r, int c, int damage, boolean allied) {
		if (c >= 0 && c < SIZE)
			damage(r, c, damage, allied);
	}

	/**
	 * Adds a generic, level-0 unit to the side of whoever's turn it is.
	 * 
	 * @param r
	 */
	public void addUnit(int r) {
		Unit unit;
		if (turn == PLAYER)
			unit = new Knight();
		else
			unit = new Goblin();
		addUnit(r, unit);
	}

	/**
	 * Given a level, adds a unit of that level to the side of whoever's turn it
	 * is.
	 * 
	 * @param r
	 * @param level
	 */
	public void addUnit(int r, int level) {
		// Unit unit;
		// if (turn == PLAYER)
		// unit = new Knight(level);
		// else
		// unit = new Goblin(level);
		// addUnit(r, unit);
		if (player != null && computer != null) {
			if (turn == PLAYER) {
				player.addUnit(level, r);
			} else
				computer.addUnit(level, r);
		} else {
			if (turn == PLAYER) {
				addUnit(r, new Knight(level));
			} else {
				addUnit(r, new Goblin(level));
			}
		}
	}

	/**
	 * Given a <code>Unit</code>, it adds it to the queue of new tiles in that
	 * row.
	 * 
	 * @param r
	 * @param unit
	 */
	public void addUnit(int r, Unit unit) {
		newTiles.add(r, unit);
	}

	/**
	 * Add an <code>Action</code> the cue of events that clearing tiles can
	 * cause.
	 * 
	 * @param action
	 */
	public void addAction(Action action) {
		clearActions.add(action);
	}

	/**
	 * Returns the tile from the given row and column.
	 * 
	 * @param r
	 * @param c
	 * @return Returns the tile from the given row and column.
	 */
	public Tile getTile(int r, int c) {
		if (r < 0 || c < 0 || r >= SIZE || c >= SIZE)
			return null;
		return tiles[r][c];
	}

	/**
	 * Returns the id of the tile from the given row and column, or -1 if there
	 * is no tile at that location.
	 * 
	 * @param r
	 * @param c
	 * @return Returns the id of the tile from the given row and column, or -1
	 *         if there is no tile at that location.
	 */
	public int getId(int r, int c) {
		if (r < 0 || r >= SIZE || c < 0 || c >= SIZE)
			return -1;
		if (tiles[r][c] != null)
			return tiles[r][c].getId();
		else
			return -1;
	}

	/**
	 * Sets a <code>Tile</code> as the input <code>Tile</code>.
	 * 
	 * @param r
	 * @param c
	 * @param tile
	 */
	public void setTile(int r, int c, Tile tile) {
		tiles[r][c] = tile;
		clear(r, c);
	}

	/**
	 * Returns whether a specific spot on the board is "unstable", or currently
	 * being acted upon.
	 * 
	 * @param r
	 * @param c
	 * @return Returns whether a specific spot on the board is "unstable", or
	 *         currently being acted upon.
	 */
	public boolean unstable(int r, int c) {
		if (c < 0 || c >= SIZE)
			return false;
		if (r < 0 || r >= SIZE)
			return false;
		return unstable[r][c];
	}

	/**
	 * Sets a location as unstable.
	 * 
	 * @param r
	 * @param c
	 */
	public void setUnstable(int r, int c) {
		unstable[r][c] = true;
	}

	/**
	 * Sets a location as stable.
	 * 
	 * @param r
	 * @param c
	 */
	public void setStable(int r, int c) {
		unstable[r][c] = false;
	}

	/**
	 * Checks to see if a given row has any new incoming tiles.
	 * 
	 * @param r
	 *            - the row to be checked.
	 * @return
	 */
	public int getIncoming(int r) {
		return newTiles.incoming(r);
	}

	/**
	 * Informs the board whether or not a tile is incoming in a given row.
	 * 
	 * @param r
	 * @param b
	 */
	public void reduceIncoming(int r) {
		newTiles.reduceIncoming(r);
	}

	/**
	 * Tells the newTiles that there shouldn't be any of them.
	 * @param r
	 */
	public void clearNewTile(int r) {
		newTiles.clearIncoming(r);
	}

	/**
	 * Modifies the player's coins by the given amount. Can be negative.
	 * 
	 * @param amount
	 */
	public void addScore(int amount) {
		if (player != null) {
			player.addScore(amount);
		}
	}

	/**
	 * Returns the value for the current turn.
	 * 
	 * @return
	 */
	public boolean currentTurn() {
		return turn;
	}

	/**
	 * Sets it to be the given player's turn.
	 * 
	 * @param playersTurn
	 */
	public void callTurn(boolean playersTurn) {
		isTurn = true;
		turn = playersTurn;
		unitsDone = false;
		newTiles.clearUnits();
	}

	/**
	 * Checks to see if there are any units of the board of the given alliance.
	 * 
	 * @param allied
	 * @return Returns true if any such units exist, and false otherwise.
	 */
	public boolean hasUnits(boolean allied) {
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				if (tiles[r][c] != null && tiles[r][c].isUnit()
						&& ((Unit) tiles[r][c]).allied == allied)
					return true;
			}
		}
		return false;
	}

	/**
	 * Collapses all of the rows.
	 */
	protected void collapseRows() {
		for (int r = 0; r < SIZE; r++) {
			collapseRow(r);
		}
	}

	/**
	 * Collapses a given row by scanning for segments that should be falling,
	 * and setting them to do so, then adding any incoming tiles on the end.
	 * 
	 * @param r
	 */
	protected void collapseRow(int r) {
		int start, end;
		if (turn == PLAYER) {
			int c = SIZE;
			while (c > 0 && (tiles[r][c - 1] != null || unstable[r][c - 1])) {
				c--;
			}
			while (c > 0) {
				while (c > 0 && (tiles[r][c - 1] == null || unstable[r][c - 1])) {
					c--;
				}
				end = c;
				while (c > 0 && tiles[r][c - 1] != null && !unstable[r][c - 1]) {
					c--;
				}
				start = c;
				if (end - start > 0) {
					segments.add(new FallingSegment(r, start, end, this));
				}
			}
		} else {
			int c = 0;
			while (c < SIZE && (tiles[r][c] != null || unstable[r][c])) {
				c++;
			}
			while (c < SIZE) {
				while (c < SIZE && (tiles[r][c] == null || unstable[r][c])) {
					c++;
				}
				start = c;
				while (c < SIZE && (tiles[r][c] != null || unstable[r][c])) {
					c++;
				}
				end = c;
				if (end - start > 0) {
					segments.add(new FallingSegment(r, start, end, this));
				}
			}
		}

		Tile[] row = newTiles.get(r);
		if (row != null) {
			segments.add(new FallingSegment(r, newTiles.incoming(r), row, turn));
		}
		// }
	}

	/**
	 * Given a row of tiles and a position, inserts those tiles into the board.
	 * 
	 * @param row
	 * @param r
	 * @param start
	 * @param end
	 */
	public void insertRow(Tile[] row, int r, int start, int end) {
		for (int c = start; c < end; c++) {
			tiles[r][c] = row[c - start];
			setStable(r, c);
		}
		for (int c = start; c < end; c++) {
			clear(r, c);
		}
	}

	/**
	 * Shouldn't actually be necessary, but sometimes weird bugs happen and this
	 * will patch them up. If there are any empty spots on the board, creates
	 * new tiles to fill them.
	 * 
	 * @return Returns true if the board didn't need any fixing, and false
	 *         otherwise.
	 */
	public boolean fixRows() {
		boolean out = true;
		for (int r = 0; r < SIZE; r++) {
			if (turn == PLAYER) {
				int c;
				for (c = 0; c < SIZE && tiles[r][c] == null; c++) {
					newTiles.add(r);
					setStable(r, c);
				}
				if (c > 0)
					out = false;
			} else {
				int c;
				for (c = SIZE - 1; c >= 0 && tiles[r][c] == null; c--) {
					newTiles.add(r);
					setStable(r, c);
				}
				if (c < SIZE - 1)
					out = false;

			}
		}
		return out;
	}

	/**
	 * Checks the entire board for any matches. Should not ever need to be
	 * called.
	 */
	protected void clear() {
		int id, start, count;
		// Check horizontal
		for (int r = 0; r < SIZE; r++) {
			id = -1;
			start = 0;
			count = 1;
			for (int c = 0; c < SIZE; c++) {
				if (tiles[r][c] == null || unstable(r, c)) {
					id = -1;
					start = c;
					count = 0;
				} else if (tiles[r][c].getId() == id)
					count++;
				else {
					if (count >= 3) {
						matches.add(new Match(r, start, id, count, true));
					}
					id = tiles[r][c].getId();
					start = c;
					count = 1;
				}
			}
		}
		// Check vertical
		for (int c = 0; c < SIZE; c++) {
			id = -1;
			start = 0;
			count = 1;
			for (int r = 0; r < SIZE; r++) {
				if (tiles[r][c] == null || unstable(r, c)) {
					id = -1;
					start = r;
					count = 0;
				} else if (tiles[r][c].getId() == id)
					count++;
				else {
					if (count >= 3) {
						matches.add(new Match(start, c, id, count, false));
					}
					id = tiles[r][c].getId();
					start = r;
					count = 1;
				}
			}
		}

	}

	/**
	 * HERE THERE BE ALGORITHMS Checks to see if there are any matches that the
	 * given tile has caused. Called when a new tile is inserted into the board.
	 * 
	 * @param r
	 * @param c
	 */
	protected void clear(int r, int c) {
		int hStart, hEnd, vStart, vEnd;
		Tile root = tiles[r][c];
		hStart = c;
		hEnd = c + 1;
		vStart = r;
		vEnd = r + 1;
		if (root == null)
			return;
		while (hStart > 0 && root.equals(tiles[r][hStart - 1])
				&& !unstable(r, hStart - 1)) {
			hStart--;
		}
		while (hEnd < SIZE && root.equals(tiles[r][hEnd]) && !unstable(r, hEnd)) {
			hEnd++;
		}
		while (vStart > 0 && root.equals(tiles[vStart - 1][c])
				&& !unstable(vStart - 1, c)) {
			vStart--;
		}
		while (vEnd < SIZE && root.equals(tiles[vEnd][c]) && !unstable(vEnd, c)) {
			vEnd++;
		}
		if (hEnd - hStart >= 3) {
			matches.add(new Match(r, hStart, root.id, hEnd - hStart, true));
		}
		if (vEnd - vStart >= 3) {
			matches.add(new Match(vStart, c, root.id, vEnd - vStart, false));
		}
	}

	/**
	 * Prints the board to the console using shorthand notation.
	 */
	public void print() {
		System.out.println();
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				if (tiles[r][c] == null)
					System.out.print("- ");
				else
					System.out.print(tiles[r][c] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Prints the board, replacing unstable locations with a '!'.
	 */
	public void printUnstable() {
		System.out.println();
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				if (unstable[r][c])
					System.out.print("!");
				else if (tiles[r][c] == null)
					System.out.print("- ");
				else
					System.out.print(tiles[r][c] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Loads the art for the background.
	 * 
	 * @param path
	 */
	public static void loadArt(String path) {
		frameArt = GameWindow.loadImage(path + "frame.png");
	}
}
