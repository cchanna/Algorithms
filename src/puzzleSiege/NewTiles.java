package puzzleSiege;

//import java.util.LinkedList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Hashtable;

/**
 * New tile purgatory, for while they wait to be placed on the board.
 * 
 * @author Cerisa
 * @version 1.2 11/18/2013
 */
public class NewTiles {

	// private Hashtable<Integer, Queue<Tile>> tiles;
	private Hashtable<Integer, Queue<Unit>> units;
	private int[] newTiles;
	private int[] incoming;

	public NewTiles() {
		units = new Hashtable<Integer, Queue<Unit>>();
		newTiles = new int[Board.SIZE];
		incoming = new int[Board.SIZE];
	}

	/**
	 * Adds a new unit to be added to a row.
	 * 
	 * @param r
	 * @param unit
	 */
	public void add(int r, Unit unit) {
		Queue<Unit> row = units.get(r);
		if (row == null) {
			row = new LinkedList<Unit>();
			units.put(r, row);
		}
		row.add(unit);
	}

	/**
	 * Signals that a new tile should be arriving in the given row.
	 * 
	 * @param r
	 */
	public void add(int r) {
		if (r >= 0 && r < Board.SIZE)
			newTiles[r]++;
		else
			System.err.println();
	}

	/**
	 * Are there any new tiles?
	 * 
	 * @return Returns true if there aren't any new tiles.
	 */
	public boolean isEmpty() {
		for (int i = 0; i < Board.SIZE; i++) {
			if (newTiles[i] != 0)
				return false;
		}
		return true;
	}

	/**
	 * Are there any new tiles for a given row?
	 * 
	 * @param r
	 * @return Returns true if there aren't any new tiles.
	 */
	public boolean isEmpty(int r) {
		return (newTiles[r] == 0);
	}

	/**
	 * How many new tiles are coming in this row?
	 * 
	 * @param r
	 * @return Returns the number of new tiles.
	 */
	public int incoming(int r) {
		return incoming[r];
	}

	/**
	 * Sets the number of incoming tiles for this row to 0.
	 * 
	 * @param r
	 */
	public void clearIncoming(int r) {
		incoming[r] = 0;
	}

	/**
	 * Empties the collection of units. A sloppy bug patch, but far too
	 * complicated of a bug to hunt down in such a short time.
	 */
	public void clearUnits() {
		units = new Hashtable<Integer, Queue<Unit>>();
	}

	/**
	 * Are there any units?
	 * @param r
	 * @return Returns true if there aren't any units.
	 */
	public boolean unitsEmpty(int r) {
		Queue<Unit> row = units.get(r);
		if (row == null || row.isEmpty())
			return true;
		return false;

	}

	/**
	 * Creates a row of tiles/units to be turned into a FallingSegement.
	 * @param r
	 * @return returns an array of tiles.
	 */
	public Tile[] get(int r) {
		if (r < 0 || r >= Board.SIZE)
			return null;
		int n = newTiles[r];
		if (n <= 0)
			return null;
		Queue<Unit> row = units.get(r);
		newTiles[r] = 0;
		Tile[] output = new Tile[n];
		for (int i = 0; i < n; i++) {
			if (row != null && !row.isEmpty()) {
				output[i] = row.remove();
				if (row.isEmpty())
					units.remove(r);
			} else {
				output[i] = new Tile();
			}
		}
		incoming[r] = n;
		return output;
	}

	/**
	 * Returns the damage of the first unit incoming in that row.
	 * @param r
	 * @return
	 */
	public int peekUnitDamage(int r) {
		Queue<Unit> row = units.get(r);
		if (row == null || row.isEmpty())
			return 0;
		return row.peek().dealDamage();
	}

	/**
	 * Lowers the number incoming in this row by 1.
	 * @param r
	 */
	public void reduceIncoming(int r) {
		incoming[r]--;
	}
}
