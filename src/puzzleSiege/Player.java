package puzzleSiege;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Represents the person actually playing the game.
 * 
 * @author Cerisa
 * @version 1.2 12/11/2013
 */
public class Player implements Actor {
	public static final int STARTING_HEALTH = 8;

	public static final int HEALTH_X = Board.X - Tile.SIZE * 3,
			HEALTH_Y = Board.Y + 16;
	public static final int HORDE_X = Board.X - Tile.SIZE * 3,
			HORDE_Y = Board.Y;
	private int health, score;

	private boolean isTurn;

	private Board board;

	private Horde horde;

	private static BufferedImage healthArt[];

	public Player(Board board) {
		health = STARTING_HEALTH;
		score = 0;
		this.board = board;
		isTurn = false;
		horde = new Horde();
	}

	/**
	 * Informs the board that it is the player's turn, and then opens up the
	 * player for click inputs.
	 */
	@Override
	public void beginTurn() {
		isTurn = true;
		board.callTurn(Board.PLAYER);
	}

	/**
	 * If it is currently the player's turn, passes a click along to the board.
	 * 
	 * @param x
	 * @param y
	 */
	public void click(int x, int y) {
		if (isTurn) {
			if (x > Board.X && x < Board.X + Tile.SIZE * Board.SIZE) {
				if (y > Board.Y && y < Board.Y + Tile.SIZE * Board.SIZE) {
					isTurn = !board.click(x - Board.X, y - Board.Y);
				}
			}
		}
	}

	/**
	 * Modify the player's current score by the input value.
	 * 
	 * @param amount
	 */
	public void addScore(int amount) {
		score = Math.max(0, score + amount);
	}

	/**
	 * Returns the players current score.
	 * 
	 * @return Returns the players current score.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Checks to see if the player's health is 0.
	 */
	public boolean isDead() {
		return health == 0;
	}

	/**
	 * Deals damage to the player by subtracting the amount from their health
	 * (minimum health of 0).
	 */
	public void damage(int amount) {
		health = Math.max(health - amount, 0);

	}

	public String toString() {
		return "Player";
	}
	
	public void addUnit(int level, int r) {
		board.addUnit(r, new Knight(level));
	}
	
	/**
	 * Draws the player's current health and score.
	 * 
	 * @param pen
	 */
	public void draw(Graphics2D pen) {
		pen.drawImage(healthArt[health], null, HEALTH_X, HEALTH_Y);
		horde.draw(pen, HORDE_X, HORDE_Y);
		pen.setColor(Color.WHITE);
		pen.drawString(score + "", Board.X + 48, Board.Y - 48);
	}

	public static void loadArt(String path) {
		healthArt = GameWindow.loadImageAsTiles(path + "health.png", Tile.SIZE);
	}
}
