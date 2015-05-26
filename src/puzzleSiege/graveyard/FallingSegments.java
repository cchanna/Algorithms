//package puzzleSiege;
//
//import java.awt.Graphics2D;
//import java.util.LinkedList;
//import java.util.Queue;
//
//
//public class FallingSegments {
//	protected Board board;
//	private Queue<FallingRow> segments;
//	protected boolean[] newTile = new boolean[Board.SIZE];
//	
//	public FallingSegments(Board board) {
//		this.board = board;
//		segments = new LinkedList<FallingRow>();
//	}
//	
//	public void add(int r, int start, int end) {
//		segments.add(new FallingRow(r,start,end));
//	}
//
//	public boolean onTick() {
//		if (isEmpty()) return true;
//		Queue<FallingRow> temp = new LinkedList<FallingRow>();
//		while (!segments.isEmpty()) {
//			FallingRow row = segments.remove();
//			if (row.frame < Board.FALL_TIME) {
//				row.frame++;
//				temp.add(row);
//			} else if (board.fallRight) {
//				row.start++;
//				row.end++;
//				if (row.start == 0) {
//					newTile[row.r] = false;
//				}
//				if (row.end < Board.SIZE && board.getTile(row.r,row.end) == null) {
//					temp.add(row);
//					row.frame = 0;
//				} else {
//					board.insertRow(row.row,row.r,row.start,row.end);
//				}
//			} else {
//				row.start--;
//				row.end--;
//				if (row.end == Board.SIZE) {
//					newTile[row.r] = false;
//				}
//				if (row.start > 0 && board.getTile(row.r,row.start - 1) == null) {
//					temp.add(row);
//					row.frame = 0;
//				} else {
//					board.insertRow(row.row,row.r,row.start,row.end);
//				}
//			}
//		}
//		segments.addAll(temp);
//		return false;
//	}
//	
//	public void draw(Graphics2D pen, int x, int y) {
//		for (FallingRow row : segments) {
//			int size = Tile.SIZE;
//			int distance = (int) Board.FALL_SPEED * row.frame;
//			if (board.fallRight) {
//				for (int i = 0; i < row.end - row.start; i++) {
//					row.row[i].draw(pen, x + (row.start + i) * size + distance,
//							y + (row.r * size));
//				}
//			} else
//				for (int i = 0; i < row.end - row.start; i++) {
//					row.row[i].draw(pen, x + (row.start + i) * size - distance,
//							y + (row.r * size));
//				}
//		}
//	}
//	
//	public boolean isFalling(int r) {
//		return newTile[r];
//	}
//	
//	public boolean isEmpty() {
//		return segments.isEmpty();
//	}
//	
//	protected class FallingRow {
//		Tile[] row;
//		int r, start, end;
//		int frame;
//
//		protected FallingRow(int r, int start, int end) {
//			int buffer = 0;
//			if (board.fallRight) {
////				if (start == 0) {
////					buffer = board.getNumNewTiles(r);
////					board.resetNumNewTiles(r);
////				}
//				row = new Tile[end - start + buffer];
//				for (int i = 0; i < buffer; i++) {
//					row[i] = new Tile();
//				}
//				for (int i = 0; i < end - start; i++) {
//					row[buffer + i] = board.getTile(r,start + i);
////					tiles[r][start + i] = null;
//					board.delete(r, start+i);
//				}
//				this.start = start - buffer;
//				this.end = end;
//			} else {
////				if (end == Board.SIZE) {
////					buffer = board.getNumNewTiles(r);
////					board.resetNumNewTiles(r);
////				}
//				row = new Tile[end - start + buffer];
//				for (int i = 0; i < end - start; i++) {
//					row[i] = board.getTile(r,start+i);
//					board.delete(r,start + i);
//				}
//				for (int i = 0; i < buffer; i++) {
//					row[end - start + i] = new Tile();
//				}
//				this.start = start;
//				this.end = end + buffer;
//			}
//			this.r = r;
//			this.frame = 0;
//			// System.out.println(buffer);
//		}
//		
//		protected FallingRow(int r, int numNewTiles) {
//			if (board.fallRight) {
//				this.row = new Tile[numNewTiles];
//				for (int i=0; i<numNewTiles; i++) {
//					row[i] = new Tile();
//				}
//				this.start = 0 - numNewTiles;
//				this.end = 0;
//			}
//			else {
//				this.row = new Tile[numNewTiles];
//				for (int i=0; i<numNewTiles; i++) {
//					row[i] = new Tile();
//				}
//				this.start = Board.SIZE;
//				this.end = Board.SIZE + numNewTiles;
//			}
//			this.r = r;
//			this.frame = 0;
//		}
//		
//		protected FallingRow(int r, NewTiles newTiles) {
//			this.row = new Tile[newTiles.size(r)];
//			for (int i=0; i<row.length; i++) {
//				row[i] = newTiles.get(r);
//			}
//			if (board.fallRight) {
//				start = 0 - row.length;
//				end = 0;
//			}
//			else {
//				start = Board.SIZE;
//				end = Board.SIZE + row.length;
//			}
//			this.r = r;
//			this.frame = 0;
//			System.out.println(start + " " + end);
//		}
//	}
//
//	public void addNewTiles(int r, int numNewTiles) {
//		newTile[r] = true;
//		segments.add(new FallingRow(r,numNewTiles));
//	}
//	
//	public void addNewTiles(int r, NewTiles newTiles) {
//		newTile[r] = true;
//		segments.add(new FallingRow(r,newTiles));
//	}
//	
//}
