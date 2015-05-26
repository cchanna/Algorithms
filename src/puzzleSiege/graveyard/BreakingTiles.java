//package puzzleSiege;
//
//import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;
//import java.util.LinkedList;
//import java.util.Queue;
//
//public class BreakingTiles {
//	protected static BufferedImage[] art;
//
//	public final static int TIME = 10;
//
//	protected Queue<BreakingTile> tiles;
//	protected Board board;
//
//	public BreakingTiles(Board board) {
//		tiles = new LinkedList<BreakingTile>();
//		this.board = board;
//	}
//
//	public void add(int r, int c) {
//		tiles.add(new BreakingTile(r, c));
//		board.setUnstable(r, c);
//	}
//
//	public boolean onTick() {
//		if (isEmpty()) return true;
//		Queue<BreakingTile> temp = new LinkedList<BreakingTile>();
//		while (!tiles.isEmpty()) {
//			BreakingTile tile = tiles.remove();
//			tile.frame++;
//			if (tile.frame < TIME) {
//				temp.add(tile);
//			} else {
//				board.replace(tile.r, tile.c);
//				board.setStable(tile.r, tile.c);
//			}
//		}
//		tiles.addAll(temp);
//		return false;
//	}
//	
//	public void draw(Graphics2D pen) {
//		for (BreakingTile tile : tiles) {
//			int artFrame = (art.length / TIME) * tile.frame;
//			int x = Board.X_POSITION + tile.c*Tile.SIZE;
//			int y = Board.Y_POSITION + tile.r*Tile.SIZE;
//			pen.drawImage(art[artFrame],null,x,y);
//		}
//	}
//	
//	public boolean isEmpty() {
//		return tiles.isEmpty();
//	}
//
//	protected class BreakingTile {
//		int r, c;
//		int frame;
//
//		protected BreakingTile(int r, int c) {
//			this.r = r;
//			this.c = c;
//			frame = 0;
//		}
//	}
//
//	public static void loadArt() {
//		art = GameWindow.loadImageAsTiles("src\\puzzleSiege\\shatter.png",
//				Tile.SIZE);
//	}
//
//}
