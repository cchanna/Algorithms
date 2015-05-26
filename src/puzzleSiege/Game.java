package puzzleSiege;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Represents the game itself.
 * 
 * @author Cerisa
 * @version 1.2 12/11/2013
 */
public class Game {

	private Board board;
	private Player player;
	private Computer computer;

	private Actor currentPlayer;

	public enum Turn {
		LEFT, RIGHT
	};

	/**
	 * Builds the game by loading all of the art, creating new
	 * <code>Actor</code>s, and creating the <code>Board</code>.
	 */
	public Game() {
		board = new Board();
		player = new Player(board);
		computer = new Computer(board);
		board.setActors(player, computer);
		currentPlayer = computer;
	}

	/**
	 * Test constructor for setting the board to various states.
	 * @param board
	 */
	public Game(Board board) {
		this.board = board;
		player = new Player(board);
		computer = new Computer(board);
		board.setActors(player, computer);
		currentPlayer = computer;
	}

	/**
	 * Triggers every tick of the game cycle. If board.onTick() is returning
	 * true, then it signals for the next player to take their turn.
	 */
	public void onTick() {
		if (!player.isDead() && !computer.isDead()) {
			if (board.onTick()) {
				if (currentPlayer == computer) {
					takeTurn(player);
				} else
					takeTurn(computer);
			}
			computer.onTick();
		}
	}
	
	/**
	 * Tells an actor to take their turn.
	 * @param actor
	 */
	protected void takeTurn(Actor actor) {
		currentPlayer = actor;
		actor.beginTurn();
	}

	/**
	 * Draws all the things. Or, alternately, the game over/win screen.
	 * @param pen
	 */
	public void draw(Graphics2D pen) {
		if (player.isDead()) {
			pen.setColor(Color.BLACK);
			pen.fillRect(0, 0, PuzzleSiege.WINDOW_WIDTH,
					PuzzleSiege.WINDOW_HEIGHT);
			pen.setColor(Color.WHITE);
			pen.drawString("YOU LOSE", 128, 128);
		} else if (computer.isDead()) {
			pen.setColor(Color.BLACK);
			pen.fillRect(0, 0, PuzzleSiege.WINDOW_WIDTH,
					PuzzleSiege.WINDOW_HEIGHT);
			pen.setColor(Color.WHITE);
			pen.drawString("YOU WIN", 128, 128);
			pen.drawString("YOUR SCORE WAS " + player.getScore(), 128, 256);
		} else {
			if (board != null) {
				board.draw(pen);
			}
			if (player != null) {
				player.draw(pen);
			}
			if (computer != null) {
				computer.draw(pen);
			}
		}
	}

	/**
	 * Passes a click along to the <code>Player</code>.
	 * @param x
	 * @param y
	 */
	public void click(int x, int y) {
		player.click(x, y);
	}

	/**
	 * Loads all of the art for everything that requires it.
	 */


}