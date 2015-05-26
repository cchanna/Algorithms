package puzzleSiege;

/**
 * A <code>Wall Unit</code>. Created via matching <code>DEFENSE</code> tiles.
 * Does not actually count as a unit for most purposes.
 * 
 * @author Cerisa
 * @version 1.2 12/11/2013
 */
public class Wall extends Unit {

	public Wall(boolean allied) {
		super(102);
		this.id = 102;
		level = 0;
		this.allied = allied;
		maxHealth = 3;
		health = 2;
		damage = 0;
	}

	public Wall(int level, boolean allied) {
		this(allied);
		this.level = level;
		maxHealth += level;
		health += level;
	}

	/**
	 * Walls don't do the things that most units do.
	 */
	public boolean isUnit() {
		return false;
	}
}
