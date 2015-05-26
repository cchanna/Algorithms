package puzzleSiege;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ClearActionsTest {
	Board board;
	Tile[][] setup;

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
	public void testRallyClear() {
		boolean TALK = true;
		if (TALK) System.out.println("\n\nRALLY ACTION");
		setup[0] = Tile.tileRow("DRRRBDBDBDBDBDBD");
		board = new Board(setup);
		board.clear();
		for (int i=0; i<Match.TIME + BreakingTile.TIME + FallingSegment.TIME*3 + Rotate.TIME; i++) {
			board.onTick();
			if (TALK) System.out.println(i);
			if (TALK) board.print();
		}
		if (TALK) board.print();
		assertTrue(board.getTile(0,1).getId() == Unit.KNIGHT);
	}
	
	
	@Test
	public void testSwordAction() {
		boolean TALK = false;
		if (TALK) System.out.println("\n\nSWORD ACTION");
		setup[0] = Tile.tileRow("DR1CDR1BCSSSDRC1");
		board = new Board(setup);
		board.clear();
		for (int i=0; i<Match.TIME + BreakingTile.TIME + FallingSegment.TIME*6; i++) {
			board.onTick();
		}
		if (TALK) board.print();
		assertTrue(board.getTile(0,15).getId() == Tile.COIN);
		assertTrue(board.getTile(0,2).getId() != Unit.KNIGHT);
	}
	
	@Test
	public void testBomb() {
		boolean TALK = true;
		if (TALK) System.out.println("\n\nBOMB ACTION");
		setup[0] = Tile.tileRow("DRDRDRD0BBB1DRDR");
		board = new Board(setup);
		if (TALK) board.print();
		board.callTurn(Board.PLAYER);
		board.swap(0, 0, true);
		board.clear();
		for (int i=0; i<1000; i++) {
			board.onTick();
//			if (TALK) board.print();
		}
		if (TALK) board.print();
		assertTrue(board.getTile(0,12).getId() == 100);
		assertTrue(board.getTile(0,10).getId() == Tile.DEFENSE);
	}
	
	@Test
	public void testCoin() {
		boolean TALK = false;
		if (TALK) System.out.println("\n\nCOIN ACTION");
		setup[0] = Tile.tileRow("DRDRDRD0CCC1DRDR");
		board = new Board(setup);
		Player player = new Player(board);
		board.setActors(player, null);
		board.callTurn(Board.PLAYER);
		board.swap(0,0,true);
		board.clear();
		for (int i=0; i<Swap.TIME + Match.TIME + BreakingTile.TIME + FallingSegment.TIME*3; i++) {
			board.onTick();
		}
		if (TALK) board.print();
		assertTrue(board.getTile(0,11).getId() == 101);
		assertTrue(board.getTile(0,10).getId() == 100);
		assertTrue(player.getScore() == 100);
	}
	
	@Test
	public void testArrow() {
		boolean TALK = false;
		if (TALK) System.out.println("\n\nARROW ACTION");
		setup[0] = Tile.tileRow("DRDRDRD0AAA1DRDR");
		board = new Board(setup);
		board.callTurn(Board.PLAYER);
		board.swap(0,0,true);
		board.clear();
		for (int i=0; i<Swap.TIME + Match.TIME + BreakingTile.TIME + FallingSegment.TIME*3; i++) {
			board.onTick();
		}
		if (TALK) board.print();
		assertTrue(board.getTile(0,11).getId() == 101);
		assertTrue(board.getTile(0,10).getId() == 100); 
	}

}
