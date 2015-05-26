package puzzleSiege;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	private Board board;
	private Tile[][] setup;

	@Before
	public void setUp() throws Exception {
		setup = new Tile[Board.SIZE][Board.SIZE];
		for (int i = 0; i < Board.SIZE; i++) {
			if (i % 2 == 1)
				setup[i] = Tile.tileRow("CACACACACACACACA");
			else
				setup[i] = Tile.tileRow("DRDRDRDRDRDRDRDR");
		}
	}

	@Test
	public void testSetup() {
		setup = new Tile[Board.SIZE][Board.SIZE];
		for (int i = 0; i < Board.SIZE; i++) {
			setup[i] = Tile.tileRow("SDRACSDRACSDRACS");
		}
		board = new Board(setup);
		// board.print();
		assertTrue(board.getTile(0,0).toString().equals("S"));
		assertTrue(board.getTile(5,6).toString().equals("D"));
		assertTrue(board.getTile(12,7).toString().equals("R"));
	}

	@Test
	public void testClearHorizontal() {
		setup[0] = Tile.tileRow("SSSDRDRDRDRDRDRD");
		board = new Board(setup);
		board.clear();
		for (int i = 0; i < Match.TIME + BreakingTile.TIME + 2; i++) {
			board.onTick();
			// board.print();
			// println();
		}
		assertTrue(board.getTile(0,0) == null);
		assertTrue(board.getTile(0,0) == null);
		assertTrue(board.getTile(0,0) == null);
	}

	@Test
	public void testClearVertical() {
		setup[0] = Tile.tileRow("SDRDRDRDRDRDRDRD");
		setup[1] = Tile.tileRow("SCACACACACACACAC");
		setup[2] = Tile.tileRow("SDRDRDRDRDRDRDRD");
		board = new Board(setup);
		board.clear();
		for (int i = 0; i < Match.TIME + BreakingTile.TIME; i++) {
			board.onTick();
			// board.print();
			// println();
		}
//		println();
//		board.print();
//		println();
		assertTrue(board.getTile(0,0) == null);
		assertTrue(board.getTile(1,0) == null);
		assertTrue(board.getTile(2,0) == null);

	}

	@Test
	public void testFallRight() {
		Tile[][] setup = new Tile[Board.SIZE][Board.SIZE];
		setup[0] = Tile.tileRow("DRSSSDRDRDRDRDRD");
		for (int i = 1; i < Board.SIZE; i++) {
			if (i % 2 == 1)
				setup[i] = Tile.tileRow("CACACACACACACACA");
			else
				setup[i] = Tile.tileRow("DRDRDRDRDRDRDRDR");
		}
		board = new Board(setup);
		board.clear();
		for (int i = 0; i < 1000; i++) {
			board.onTick();
			// board.print();
			// println();
		}
//		board.print();
//		System.out.println();
//		System.out.println("testFallRight");
//		board.print();
		assertTrue(board.getTile(0,3).toString().equals("D"));
		assertTrue(board.getTile(0,4).toString().equals("R"));
	}
	
	@Test
	public void testFallLeft() {
		boolean TALK = false;
		if (TALK) System.out.println("FALL LEFT");
		Tile[][] setup = new Tile[Board.SIZE][Board.SIZE];
		setup[0] = Tile.tileRow("DSRSSDRDRDRDRDRD");
		setup[1] = Tile.tileRow("CASACACACACACACA");
		setup[2] = Tile.tileRow("DRSRDRDRDRDRDRDR");
		for (int i = 3; i < Board.SIZE; i++) {
			if (i % 2 == 1)
				setup[i] = Tile.tileRow("CACACACACACACACA");
			else
				setup[i] = Tile.tileRow("DRDRDRDRDRDRDRDR");
		}
		board = new Board(setup);
		board.clear();
		board.callTurn(Board.COMPUTER);
		board.swap(0,1,true);
		if (TALK) board.print();
		for (int i = 0; i < 1000; i++) {
			board.onTick();
			// println();
		}
		if (TALK) System.out.println();
		if (TALK) board.print();
		if (TALK) System.out.println("\n\n");
		assertTrue(board.getTile(0,2).toString().equals("D"));
		assertTrue(board.getTile(0,3).toString().equals("R"));
	}
	
	@Test
	public void testChainRight() {
		Tile[][] setup = new Tile[Board.SIZE][Board.SIZE];
		setup[0] = Tile.tileRow("DCSSSDRDRDRDRDRD");
		setup[1] = Tile.tileRow("CACACACACACACACA");
		setup[2] = Tile.tileRow("RSRDCDRDRDRDRDRD");
		for (int i = 3; i < Board.SIZE; i++) {
			if (i % 2 == 1)
				setup[i] = Tile.tileRow("CACABACACACACACA");
			else
				setup[i] = Tile.tileRow("DRDRDRDRDRDRDRDR");
		}
		board = new Board(setup);
		board.clear();
		for (int i = 0; i < 1000; i++) {
			board.onTick();
			// board.print();
			// println();
		}
//		board.print();
//		System.out.println();
		assertTrue(board.getTile(1,4).toString().equals("A"));
		assertTrue(board.getTile(2,4).toString().equals("D"));
	}
	
	@Test
	public void testSwap() {
		boolean TALK = false;
		if (TALK) System.out.println();
		if (TALK) System.out.println("TEST SWAP");
		Tile[][] setup = new Tile[Board.SIZE][Board.SIZE];
		setup[0] = Tile.tileRow("DRSCSDRDRDRDRDRD");
		setup[1] = Tile.tileRow("CACSCACACACACACA");
		for (int i = 2; i < Board.SIZE; i++) {
			if (i % 2 == 1)
				setup[i] = Tile.tileRow("CACACACACACACACA");
			else
				setup[i] = Tile.tileRow("DRDRDRDRDRDRDRDR");
		}
		board = new Board(setup);
		board.clear();
		for (int i = 0; i < 1000; i++) {
			board.onTick();
		}
		if (TALK) board.print();
		if (TALK) System.out.println();
		assertTrue(board.getTile(0,3).toString().equals("C"));
		assertTrue(board.getTile(1,3).toString().equals("S"));
		board.swap(0, 3, false);
		for (int i = 0; i < 40; i++) {
			board.onTick();
		}
		if (TALK) System.out.println();
		if (TALK) board.print();
		if (TALK) System.out.println();
		for (int i = 0; i < 1000; i++) {
			board.onTick();
		}
		if (TALK) System.out.println();
		if (TALK) board.print();
		if (TALK) System.out.println();
		assertTrue(board.getTile(0,4).toString().equals("R"));
		assertTrue(board.getTile(1,4).toString().equals("A"));
	}
	
	@Test
	public void testWalls() {
		boolean TALK = false;
		if (TALK) System.out.println();
		if (TALK) System.out.println("TEST WALLS");
		Tile[][] setup = new Tile[Board.SIZE][Board.SIZE];
		setup[0] = Tile.tileRow("DRDRDRD2221RDRDR");
		setup[1] = Tile.tileRow("CACSCACACACACACA");
		for (int i = 2; i < Board.SIZE; i++) {
			if (i % 2 == 1)
				setup[i] = Tile.tileRow("CACACACACACACACA");
			else
				setup[i] = Tile.tileRow("DRDRDRDRDRDRDRDR");
		}
		board = new Board(setup);
		if (TALK) board.print();
		board.callTurn(Board.COMPUTER);
		board.swap(0,0,true);
		for (int i=0; i<500; i++) {
			board.onTick();
		}
		board.resolveUnits();
		for (int i=0; i<500; i++) {
			board.onTick();
		}
		board.resolveUnits();
		for (int i=0; i<500; i++) {
			board.onTick();
		}
		if (TALK) board.print();
	}
}
