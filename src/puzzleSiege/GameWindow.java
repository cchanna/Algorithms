package puzzleSiege;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import javax.swing.*;
import java.awt.geom.AffineTransform;

/**
 * Extension of <code>JPanel</code>, represents a window that holds a
 * <code>Game</code>.
 * 
 * @author Cerisa
 * @version 1.2 12/11/2013
 */
class GameWindow extends JPanel {

	private static final long serialVersionUID = -4301555976653961582L;

	public static int scale;
	public static AffineTransform transformer = new AffineTransform();
	public static AffineTransformOp scaler;

	private Timer timer; // timer that drives the animation
	private Game game;
	private Instructions instructions;

	private Graphics2D pen;

	BufferedImage image;

	public GameWindow() {
		setBackground(Color.WHITE);

		ActionListener tick = new ActionListener() { // defines the action taken
														// when the timer fires
			public void actionPerformed(ActionEvent evt) {
				if (game != null) game.onTick();
				repaint();
			}
		};

		timer = new Timer(34, tick);

		addMouseListener(new MouseAdapter() {
			// The mouse listener simply requests focus when the user
			// clicks the panel.
			public void mousePressed(MouseEvent evt) {
				if (instructions != null) {
					game = new Game();
					instructions = null;
				}
				requestFocus();
				if (game != null)
				game.click(evt.getX(), evt.getY());
			}
		});

		addFocusListener(new FocusListener() {
			// The focus listener starts the timer when the panel gains
			// the input focus and stops the timer when the panel loses
			// the focus. It also calls repaint() when these events occur.
			public void focusGained(FocusEvent evt) {
				timer.start();
				repaint();
			}

			public void focusLost(FocusEvent evt) {
				timer.stop();
				repaint();
			}
		});

		instructions = new Instructions(); // throws up an instructions overlay
	}

	protected void paintComponent(Graphics g) {
		Font f = new Font("Monospaced", Font.PLAIN, 32);
		g.setFont(f);
		pen = (Graphics2D) g;
		super.paintComponent(pen);
		if (instructions != null) {
			instructions.draw(pen);
		}
		if (game != null) {
			game.draw(pen);
		}
	}

	// load up an image
	public static BufferedImage loadImage(String filename) {
		try {
			return ImageIO.read(new File(filename));
		} catch (IOException e) {
			System.err.println("Had a problem loading " + filename + ".");
			return null;
		}
	}

	// load an image as a 1D array of tiles
	public static BufferedImage[] loadImageAsTiles(String filename, int tileSize) {
		int i;

		BufferedImage master;
		BufferedImage[] tiles;
		int tileHeight;

		// load up the big image
		master = loadImage(filename);

		// get a bunch of tiles, that are subimages of the big one
		tileHeight = master.getHeight();
		tiles = new BufferedImage[master.getWidth() / tileSize];
		for (i = 0; i < tiles.length; i++) {
			tiles[i] = master
					.getSubimage(i * tileSize, 0, tileSize, tileHeight);
		}

		return tiles;
	}

	// load an image as a 2D array of tiles
	public static BufferedImage[][] loadImageAsTiles(String filename,
			int tileWidth, int tileHeight) {
		int i, j;

		BufferedImage master;
		BufferedImage[][] tiles;

		// load up the big image
		master = loadImage(filename);

		// get a bunch of tiles, that are subimages of the big one
		tiles = new BufferedImage[master.getHeight() / tileHeight][master
				.getWidth() / tileWidth];
		for (i = 0; i < tiles.length; i++) {
			for (j = 0; j < tiles[i].length; j++) {
				tiles[i][j] = master.getSubimage(j * tileWidth, i * tileHeight,
						tileWidth, tileHeight);
			}
		}
		return tiles;
	}
}
