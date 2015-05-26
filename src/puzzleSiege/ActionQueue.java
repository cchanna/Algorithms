package puzzleSiege;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A queue of <code>actions</code>.
 * 
 * @author Cerisa
 * @version 1.1 11/17/2013
 * @param <A>
 */
public class ActionQueue<A extends Action> {

	private Queue<A> queue;
	private Board board;

	/**
	 * When the board constructs the <code>ActionQueue</code>, it passes itself
	 * to the constructor to enable it to interact with the tiles.
	 * 
	 * @param board
	 */
	public ActionQueue(Board board) {
		this.board = board;
		queue = new LinkedList<A>();
	}

	/**
	 * Iterate through every action, calling <code>onTick</code>, and calling
	 * <code>activate</code> if the return value is <code>true</code>. If
	 * <code>activate</code> returns <code>true</code>, then it gets returned to
	 * the queue.
	 * 
	 * @return Returns true if there are no actions to iterate on.
	 */
	public boolean onTick() {
		if (queue.isEmpty())
			return true;
		Queue<A> temp = new LinkedList<A>();
		while (!queue.isEmpty()) {
			A action = queue.remove();
			if (!action.onTick() || action.activate(board))
				temp.add(action);
		}
		queue.addAll(temp);
		return false;
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}

	/**
	 * Draws each action.
	 * 
	 * @param pen
	 */
	public void draw(Graphics2D pen) {
		for (A action : queue) {
			action.draw(pen);
		}
	}

	public void add(A action) {
		queue.add(action);
		action.onAdd(board);
	}
}
