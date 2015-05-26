//package puzzleSiege;
//
//import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;
//import java.util.LinkedList;
//import java.util.Queue;
//
//public class Matches {
//	protected boolean[][] isShattering;
//	protected Queue<Match> matches;
//	protected Board board;
//
//	private static BufferedImage[] shatterArt;
//
//	public Matches(Board board) {
//		isShattering = new boolean[Board.SIZE][Board.SIZE];
//		this.board = board;
//		matches = new LinkedList<Match>();
//	}
//
//	public void add(int r, int c, int id, int count, boolean horizontal) {
//		matches.add(new Match(r, c, id, count, horizontal));
//		if (horizontal) {
//			for (int i = 0; i < count; i++) {
//				// isShattering[r][c + i] = true;
//				board.setUnstable(r, c + i);
//			}
//		} else {
//			for (int i = 0; i < count; i++) {
//				// isShattering[r + i][c] = true;
//				board.setUnstable(r + i, c);
//			}
//		}
//	}
//
//	public boolean check(int r, int c) {
//		return isShattering[r][c];
//	}
//
//	public boolean isEmpty() {
//		return matches.isEmpty();
//	}
//
//	public boolean onTick() {
//		if (isEmpty())
//			return true;
//		Queue<Match> temp = new LinkedList<Match>();
//		while (!matches.isEmpty()) {
//			Match match = matches.remove();
//			if (match.frame < Board.DECAY_TIME) {
//				temp.add(match);
//				match.frame++;
//			} else if (match.horizontal) {
//				for (int i = 0; i < match.count; i++) {
//					board.shatter(match.r, match.c + i);
//				}
//			} else {
//				for (int i = 0; i < match.count; i++) {
//					board.shatter(match.r + i, match.c);
//				}
//			}
//		}
//		matches.addAll(temp);
//		return false;
//	}
//
//	public void draw(Graphics2D pen, int x, int y) {
//		for (Match match : matches) {
//			if (match.horizontal) {
//				for (int i = 0; i < match.count; i++) {
//					pen.drawImage(shatterArt[0], null, x
//							+ ((match.c + i) * Tile.SIZE), y
//							+ (match.r * Tile.SIZE));
//				}
//			} else {
//				for (int i = 0; i < match.count; i++) {
//					pen.drawImage(shatterArt[0], null, x
//							+ (match.c * Tile.SIZE), y
//							+ ((match.r + i) * Tile.SIZE));
//				}
//			}
//		}
//	}
//
//	protected class Match {
//		int r, c, id, count;
//		int frame;
//		boolean horizontal;
//
//		protected Match(int r, int c, int id, int count, boolean horizontal) {
//			this.r = r;
//			this.c = c;
//			this.horizontal = horizontal;
//			this.id = id;
//			this.count = count;
//			frame = 0;
//		}
//	}
//
//	public static void loardArt() {
//		shatterArt = GameWindow.loadImageAsTiles("src\\puzzleSiege\\match.png",
//				Tile.SIZE);
//	}
//}
