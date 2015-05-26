package puzzleSiege;

import javax.swing.JFrame;

/**
 * The wrapper that holds everything else. Contains the <code>main</code>
 * function of the program.
 * 
 * @author Cerisa
 * @version 1.2 12/11/2013
 */
public class PuzzleSiege {

	public static final String SEPARATOR = java.io.File.separator;  // vague attempts at setting up for system-neutrality
	public static final String PATH = "src" + SEPARATOR + "puzzleSiege" // no idea if it works, don't have a mac to test it on
			+ SEPARATOR + "resources" + SEPARATOR;
	static final String GAME_TITLE = "Game";
	static final int WINDOW_WIDTH = 1000, WINDOW_HEIGHT = 750;
	static final int WINDOW_X = 20, WINDOW_Y = 20;

	public static void main(String[] args) {
		loadArt();
		JFrame window = new JFrame(GAME_TITLE); // setting up JFrames
		GameWindow content = new GameWindow();
		window.setContentPane(content);
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setLocation(WINDOW_X, WINDOW_Y);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setVisible(true);
	}

	/**
	 * Loads all the art for everything.
	 */
	private static void loadArt() {
		String path = PuzzleSiege.PATH;
		Board.loadArt(path);
		Tile.loadArt(path);
		Unit.loadUnitArt(path);
		BreakingTile.loadArt(path);
		Match.loadArt(path);
		Selection.loadArt(path);
		Swap.loadArt(path);
		Player.loadArt(path);

		SwordAction.loadArt(path);
		DefenseAction.loadArt(path);
		ArrowAction.loadArt(path);

		Instructions.loadArt(path);
	}

}