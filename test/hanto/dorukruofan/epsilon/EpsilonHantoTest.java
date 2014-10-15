package hanto.dorukruofan.epsilon;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPieceType.CRAB;
import static hanto.common.HantoPieceType.HORSE;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static org.junit.Assert.assertEquals;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.common.MoveResult;
import hanto.dorukruofan.common.HantoTestGame;
import hanto.dorukruofan.common.HantoTestGame.PieceLocationPair;
import hanto.dorukruofan.common.HantoTestGameFactory;
import hanto.studentdorukruofan.common.MyCoordinate;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EpsilonHantoTest {
	private static HantoTestGameFactory factory;
	private HantoGame game;
	private HantoTestGame testGame;
	
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoTestGameFactory.getInstance();
	}
	
	@Before
	public void setup()
	{
		// By default, blue moves first.
		testGame = factory.makeHantoTestGame(HantoGameID.EPSILON_HANTO);
		game = testGame;
	}
	
	@Test
	public void GoodResignTest() throws HantoException{
		final PieceLocationPair[] board = new PieceLocationPair[] {
			    plPair(BLUE, BUTTERFLY, 0, 0), plPair(RED, BUTTERFLY, 1, 0),
			    plPair(RED, SPARROW, -1, 0)
		};
		
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(BLUE);
		testGame.setTurnNumber(2);
		MoveResult result = testGame.makeMove(null, null, null);
		assertEquals(result, MoveResult.RED_WINS);
	}
	
	@Test(expected = HantoPrematureResignationException.class)
	public void BadResignTestPut() throws HantoException{
		final PieceLocationPair[] board = new PieceLocationPair[] {
			    plPair(BLUE, BUTTERFLY, 0, 0), plPair(RED, BUTTERFLY, 1, 0),
			    plPair(BLUE, SPARROW, -1, 0)
		};
		
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(RED);
		testGame.setTurnNumber(2);
		testGame.makeMove(null, null, null);
	}
	
	@Test(expected = HantoPrematureResignationException.class)
	public void BadResignTestHorse() throws HantoException{
		final PieceLocationPair[] board = new PieceLocationPair[] {
			    plPair(BLUE, BUTTERFLY, 0, 0), 
			    plPair(BLUE, SPARROW, -1,0),
			    plPair(BLUE, SPARROW, -2,0),
			    plPair(BLUE, CRAB, -3, 0),
			    plPair(BLUE, CRAB, -4, 0),
			    plPair(BLUE, CRAB, -5, 0),
			    plPair(BLUE, CRAB, -6, 0),
			    plPair(BLUE, CRAB, -7, 0),
			    plPair(BLUE, CRAB, -8, 0),
			    plPair(BLUE, HORSE, -9, 0),
			    plPair(BLUE, HORSE, -10, 0),
			    plPair(BLUE, HORSE, -11, 0),
			    plPair(BLUE, HORSE, -12, 0),
			    
			    plPair(RED, SPARROW, 1,0),
			    plPair(RED, SPARROW, 2,0),
			    plPair(RED, CRAB, 3, 0),
			    plPair(RED, CRAB, 4, 0),
			    plPair(RED, CRAB, 5, 0),
			    plPair(RED, CRAB, 6, 0),
			    plPair(RED, CRAB, 7, 0),
			    plPair(RED, CRAB, 8, 0),
			    plPair(RED, HORSE, 9, 0),
			    plPair(RED, HORSE, 10, 0),
			    plPair(RED, HORSE, 11, 0),
			    plPair(RED, HORSE, 12, 0),
			    plPair(RED, BUTTERFLY, 13, 0), 
		};
		
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(BLUE);
		testGame.setTurnNumber(30);
		testGame.makeMove(null, null, null);
	}
	
	
	
	
	
	
	/**
	 * Factory method to create a piece location pair.
	 * @param player the player color
	 * @param pieceType the piece type
	 * @param x starting location
	 * @param y end location
	 * @return
	 */
	private PieceLocationPair plPair(HantoPlayerColor player, HantoPieceType pieceType, 
			int x, int y)
	{
		return new PieceLocationPair(player, pieceType, new MyCoordinate(x, y));
	}

	
}
