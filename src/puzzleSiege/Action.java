package puzzleSiege;

import java.awt.Graphics2D;

/**
 * An abstract class serving as a model for subclasses. Actions are usually
 * animated effects that interact with the board in some way. Actions are
 * usually contained in an <code>ActionQueue</code>.
 * 
 * @author Cerisa
 * @version 1.1 11/17/2013
 */
public interface Action {

	/**
	 * What happens every tick of the game cycle.
	 * 
	 * @return Return true if you want the ActionQueue to call
	 *         <code>activate</code>.
	 */
	public boolean onTick();

	/**
	 * What happens when the action is first created.
	 * 
	 * @param board
	 */
	public void onAdd(Board board);

	/**
	 * Is triggered by an <code>ActionQueue</code> if <code>onTick</code>
	 * returns <code>true</code>.
	 * 
	 * @param board
	 * @return Return false if the action is to be removed and
	 *         garbage-collected. Return true if the action should be returned
	 *         to the queue.
	 */
	public boolean activate(Board board);

	/**
	 * Draw the action.
	 * @param pen
	 */
	public void draw(Graphics2D pen);

}
