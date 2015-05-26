package puzzleSiege;

import java.awt.Graphics2D;
import java.util.LinkedList;

/**
 * The <code>Computer</code>'s collection of incoming <code>Unit</code>s.
 * 
 * @author Cerisa
 * @version 1.1 11/18/2013
 */
public class Horde {

//	public static final int X = Board.X + Tile.SIZE * (Board.SIZE + 1);
//	public static final int Y = Board.Y;

	public static final int TIME = 8;
	public static final int DISTANCE = (int) (1.5 * Tile.SIZE);
	public static final int SPEED = Tile.SIZE / TIME;

	private int frame;
	private int offset;

	private LinkedList<Unit> horde;

	// private Board board;

	public Horde() {
		// this.board = board;
		horde = new LinkedList<Unit>();
		this.frame = 0;
		this.offset = 0;
	}

	public void onTick() {
		if (offset < 0) {
			if (++frame >= TIME) {
				frame = 0;
				offset++;
			}
		}
		if (offset > 0) {
			if (++frame >= TIME) {
				frame = 0;
				offset--;
			}
		}
	}

	/**
	 * Adds a new <code>Unit</code> to the top of the <code>Horde</code>, then
	 * signals for it to re-position itself graphically.
	 * 
	 * @param unit
	 */
	public void push(Unit unit) {
		horde.push(unit);
		offset--;
	}

	/**
	 * Adds a new <code>Unit</code> to the bottom of the <code>Horde</code>.
	 * 
	 * @param unit
	 */
	public void add(Unit unit) {
		horde.addLast(unit);
	}

	/**
	 * Removes and returns the top <code>Unit</code> of the <code>Horde</code>,
	 * then signals for it to reposition itself graphically.
	 * 
	 * @return
	 */
	public Unit pop() {
		offset++;
		if (!horde.isEmpty())
			return horde.pop();
		else
			return null;
	}

	public void draw(Graphics2D pen, int x, int y) {
		 y = y + offset * DISTANCE;
		if (offset < 1)
			y += frame * SPEED;
		else
			y -= frame * SPEED;
		for (Unit unit : horde) {
			unit.draw(pen, x, y);
			y += DISTANCE;
		}
	}

	public boolean isEmpty() {
		return horde.isEmpty();
	}

	/**
	 * Damages the top <code>Unit</code> of the Horde, removing it if it it
	 * dies.
	 * 
	 * @param damage
	 */
	public void damage(int damage) {
		if (!horde.isEmpty()) {
			Unit unit = horde.pollFirst();
			if (unit.takeDamage(damage))
				pop();
		}
	}

}