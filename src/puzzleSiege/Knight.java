package puzzleSiege;

/**
 * A generic allied <code>Unit</code>.
 * @author Cerisa
 * @version 1.1 11/18/2013
 */
public class Knight extends Unit {
//	protected Actor owner;
//	protected int maxHealth;
//	protected int health;
//	protected int damage;
//	protected String name;
//	protected String powers;
	public Knight() {
		super(100);
		id = 100;
		level = 0;
		allied = true;
		maxHealth = 2;
		health = 2;
		damage = 1;
	}
	
	public Knight(int level) {
		this();
		this.level = level;
		if (level >= 1) {
			damage++;
		}
		if (level >= 2) {
			maxHealth++;
			health++;
		}
	}

	public void onActivate(int r, int c, Board board) {
		if (allied) {
			moveRight(r,c,board);
		}
		else {
			moveLeft(r,c,board);
		}
	}
	
	public String toString() {
		return "0";
	}

}
