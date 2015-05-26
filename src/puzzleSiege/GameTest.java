package puzzleSiege;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameTest {

	private Game game;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUnitActivate() {
		Tile[][] setup = new Tile[Board.SIZE][Board.SIZE];
		setup[0] = Tile.tileRow("DRDR0RDRDRDRDRDR");
		for (int i = 1; i < Board.SIZE; i++) {
			if (i % 2 == 1)
				setup[i] = Tile.tileRow("CACACACACACACACA");
			else
				setup[i] = Tile.tileRow("DRDRDRDRDRDRDRDR");
		}
		Board board = new Board(setup);
		game = new Game(board);
		assertTrue(board.getTile(0,4).toString().equals("0"));
		for (int i = 0; i<1000; i++) {
			game.onTick();
		}
		assertTrue(board.getTile(0,4).toString().equals("R"));
		assertTrue(board.getTile(0,5).toString().equals("0"));
	}

}
