package puzzleSiege;

/**
 * Generic enemy <code>unit</code>.
 * @author Cerisa
 * @version 1.1 11/18/2013
 */
public class Goblin extends Unit {

	public Goblin() {
		super(101);
		level = 0;
		id = 101;
		allied = false;
		maxHealth = 1;
		health = 1;
		damage = 1;
	}
	
	public Goblin(int level) {
		this();
		this.level = level;
		if (level >= 1) {
			maxHealth++;
			health++;
		}
		if (level >= 2) {
			damage++;
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
		return "1";
	}
}

