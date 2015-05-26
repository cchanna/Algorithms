package puzzleSiege;

import java.awt.Graphics2D;

import static edu.princeton.cs.introcs.StdRandom.*;

/**
 * The computer-controlled <code>Actor</code>. Has a <code>Horde</code> of
 * monsters in addition to its ability to take turns.
 * 
 * @author Cerisa
 * @version 1.2 12/11/2013
 */
public class Computer implements Actor {
	public static final int TIME = 16;
	public static final int X = Board.X + Tile.SIZE * (Board.SIZE + 1);
	public static final int Y = Board.Y;

	// protected Queue<Unit> horde; // Consider creating new data type
	private Horde horde;

	private int turn;
	private boolean isTurn;
	private int pause;

	private Board board;

	// private Choice[] choices;
	private Swap[] choices;
	private int numChoices;
	private int bestScore;

	/**
	 * Create a new computer, passing it the board so that it is capable of
	 * acting upon it.
	 * 
	 * @param board
	 */
	public Computer(Board board) {
		horde = new Horde();
		for (int i = 0; i < 30; i++) {
			horde.add(new Goblin(i/10));
		}
		turn = 0;
		pause = 0;
		// TODO Generate horde
		this.board = board;
		// choices = new Choice[100];
	}

	/**
	 * Notify the board that the computer is taking its turn, find and execute a
	 * swap, and, if appropriate, release the next member of the horde.
	 */
	@Override
	public void beginTurn() {
		isTurn = true;
		board.callTurn(Board.COMPUTER);
	}

	/**
	 * Calls onTick for the horde, and, if it's the computers turn, counts up
	 * until it takes its action (if it acts too fast, the player won't be able
	 * to tell anything even happened).
	 */
	public void onTick() {
		horde.onTick();
		if (isTurn && ++pause == TIME) {
			isTurn = false;
			pause = 0;
			findBetterSwap();
			if (!horde.isEmpty() && (turn++ % 3) == 0) {
				board.addUnit(uniform(15), horde.pop());
			}
		}
	}

	/**
	 * HERE THERE BE ALGORITHMS.
	 * <p>
	 * Finds and executes a possible swap.
	 */
	protected void findBetterSwap() {
		choices = new Swap[200];
		numChoices = 1;
		bestScore = -1;
		choices[0] = new Swap(0, 0, true);
		for (int r = 0; r < Board.SIZE; r++) {
			for (int c = 0; c < Board.SIZE; c++) {
				int start, end;
				int id = board.getId(r, c);
				if (id >= 0 && id < 200) {
					if (c < Board.SIZE - 1 && board.getId(r, c + 1) >= 0) {
						int score = 0;
						start = r;
						end = r + 1;
						while (id == board.getId(start - 1, c + 1))
							start--;
						while (id == board.getId(end, c + 1))
							end++;
						if (end - start >= 3)
							score += scoreBetterChoice(start, c + 1, end
									- start, false, id);
						start = c + 1;
						end = c + 2;
						while (id == board.getId(r, end))
							end++;
						if (end - start >= 3)
							score += scoreBetterChoice(r, start, end - start,
									true, id);

						id = board.getId(r, c + 1);
						start = r;
						end = r + 1;
						while (id == board.getId(start - 1, c))
							start--;
						while (id == board.getId(end, c))
							end++;
						if (end - start >= 3)
							score += scoreBetterChoice(start, c, end - start,
									false, id);
						start = c - 1;
						end = c;
						while (id == board.getId(r, start - 1))
							start--;
						if (end - start >= 3)
							score += scoreBetterChoice(r, start, end - start,
									true, id);
						if (score > bestScore) {
							bestScore = score;
							choices = new Swap[200];
							choices[0] = new Swap(r, c, true);
							numChoices = 1;
						} else if (score == bestScore) {
							choices[numChoices] = new Swap(r, c, true);
							numChoices++;
						}
					}

					if (r < Board.SIZE - 1 && board.getId(r + 1, c) >= 0) {
						id = board.getId(r, c);
						int score = 0;
						start = c;
						end = c + 1;
						while (id == board.getId(r + 1, start - 1))
							start--;
						while (id == board.getId(r + 1, end))
							end++;
						if (end - start >= 3)
							score += scoreBetterChoice(r + 1, start, end
									- start, true, id);
						start = r + 1;
						end = r + 2;
						while (id == board.getId(end, c))
							end++;
						if (end - start >= 3)
							score += scoreBetterChoice(start, c, end - start,
									false, id);
						id = board.getId(r + 1, c);
						start = c;
						end = c + 1;
						while (id == board.getId(r, start - 1))
							start--;
						while (id == board.getId(r, end))
							end++;
						if (end - start >= 3)
							score += scoreBetterChoice(r, start, end - start,
									true, id);
						start = r - 2;
						end = r - 1;
						while (id == board.getId(start - 1, c))
							start--;
						if (end - start >= 3)
							score += scoreBetterChoice(start, c, end - start,
									false, id);
						if (score > bestScore) {
							bestScore = score;
							choices = new Swap[200];
							choices[0] = new Swap(r, c, false);
							numChoices = 1;
						}
						if (score == bestScore) {
							choices[numChoices] = new Swap(r, c, false);
							numChoices++;
						}
					}
				}
			}
		}
		System.out.println(bestScore + " " + numChoices);
		Swap winner = choices[uniform(numChoices)];
		board.swap(winner);
	}

	/**
	 * Given a potential swap, finds a "score" for it, and rates against the
	 * high score. Adds it to the list of potentials if it equals the score,
	 * tosses it if it's worse, or starts a new list if it beats the old score.
	 * 
	 * @param r
	 * @param c
	 * @param hmatch
	 * @param id
	 * @param swap
	 */
	protected int scoreBetterChoice(int r, int c, int length,
			boolean horizontal, int id) {
		int score = 0;
		if (id >= 100)
			return 0;

		if (id == Tile.SWORD) {
			for (int i = 0; i < Board.SIZE; i++) {
				Tile tile;
				if (horizontal)
					tile = board.getTile(r, i);
				else
					tile = board.getTile(i, c);
				if (tile != null && tile.isUnit() && !((Unit) tile).alliance())
					score++;
			}
			if (score == 0)
				return 0;
			score += 2;
		}
		if (id == Tile.ARROW) {
			if (horizontal) {
				for (int i = 0; i < Board.SIZE; i++) {
					Tile tile = board.getTile(r, i);
					if (tile != null && tile.isUnit()
							&& !((Unit) tile).alliance()) {
						score++;
					}
				}
				if (score == 0)
					return 0;
				score = Math.min(score, length);
			} else {
				for (int j = 0; j < length; j++) {
					for (int i = 0; i < Board.SIZE; i++) {
						Tile tile = board.getTile(r + j, i);
						if (tile != null && tile.isUnit()
								&& !((Unit) tile).alliance()) {
							score++;
							break;
						}
					}
				}
				if (score == 0)
					return 0;
				score += 2;
			}
		}
		if (id == Tile.BOMB) {
			if (horizontal) {
				for (int i = 0; i < length + 2; i++) {
					score += bombcheck(r - 1, c + 1 - 1);
					score += bombcheck(r + 1, c + i - 1);
				}
				score += bombcheck(r, c - 1);
				score += bombcheck(r, c + length);
			} else {
				for (int i = 0; i < length + 2; i++) {
					score += bombcheck(r + i - 1, c - 1);
					score += bombcheck(r + i - 1, c + 1);
				}
				score += bombcheck(r - 1, c);
				score += bombcheck(r + length, c);
			}
		}

		if (id == Tile.RALLY)
			score += (length - 2) * 2;
		if (c <= Board.SIZE / 2)
			score++;

		score += (length - 2) * 5;
		return score;
	}

	/**
	 * Is this bomb killing enemies?
	 * @param r
	 * @param c
	 * @return Returns 1 if yes, 0 if no.
	 */
	protected int bombcheck(int r, int c) {
		Tile tile = board.getTile(r, c);
		if (tile != null && tile.isUnit() && !((Unit) tile).alliance()) {
			return 1;
		}
		return 0;
	}

	public String toString() {
		return "Computer";
	}

	/**
	 * When the <code>Computer</code> is dealt damage, it passes that damage to
	 * its <code>Horde</code>.
	 */
	public void damage(int damage) {
		horde.damage(damage);
	}

	/**
	 * Adds a unit to the horde.
	 */
	public void addUnit(int level, int r) {
		horde.push(new Goblin(level));
	}

	/**
	 * Checks to see if the <code>Horde</code> is empty, and if it is, if the
	 * <code>Computer</code> owns any remaining <code>Unit</code>s. If it
	 * doesn't, then it returns true.
	 */
	public boolean isDead() {
		return horde.isEmpty() && !board.hasUnits(Board.COMPUTER);
	}

	public void draw(Graphics2D pen) {
		horde.draw(pen, X, Y);
	}

}
