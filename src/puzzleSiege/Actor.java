package puzzleSiege;

/**
 * An interface describing a player of the game. Is implemented by
 * <code>Player</code> and <code>Computer</code>.
 * 
 * @author Cerisa
 * @version 1.1 11/17/2013
 */
public interface Actor {

	/**
	 * What to do when your turn starts.
	 */
	public void beginTurn();

	/**
	 * What happens when you are dealt damage.
	 * @param damage
	 */
	public void damage(int damage);

	/**
	 * Check to see if this actor has the lost the game.
	 * @return True if the actor has lost.
	 */
	public boolean isDead();
	
	/**
	 * Adds a unit to the actor's collection of incoming units.
	 */
	public void addUnit(int level, int row);
}
