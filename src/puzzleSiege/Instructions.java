package puzzleSiege;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The opening screen that explains how to play
 * 
 * @author Cerisa
 * @version 1.1 12/11/2013
 */
public class Instructions {

	protected static BufferedImage art;
	
	public void draw(Graphics2D pen) {
		pen.drawImage(art,null,0,0);
	}
	
	public static void loadArt(String path) {
		art = GameWindow.loadImage(path + "instructions.png");
	}
	
}
