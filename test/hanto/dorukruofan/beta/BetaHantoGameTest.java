package hanto.dorukruofan.beta;

import static org.junit.Assert.*;
import hanto.HantoGameFactory;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.common.HantoPieceType;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BetaHantoGameTest

{

	/**
	 * 
	 * Internal class for these test cases.
	 * 
	 * @version Sep 13, 2014
	 */

	class TestHantoCoordinate implements HantoCoordinate
	{
		private final int x, y;
		public TestHantoCoordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		/*
		 * 
		 * @see hanto.common.HantoCoordinate#getX()
		 */

		@Override
		public int getX()
		{
			return x;
		}
		/*
		 * 
		 * @see hanto.common.HantoCoordinate#getY()
		 */
		@Override
		public int getY()
		{
			return y;
		}
	}
	
	private static HantoGameFactory factory;
	private HantoGame game;
	
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoGameFactory.getInstance();
	}

	@Before
	public void setup()
	{
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, HantoPlayerColor.BLUE);
	}
	@Test
	public void getAnBetaHantoGameFromTheFactory()
	{
		assertTrue(game instanceof BetaHantoGame);
	}
	@Test
	public void blueMakesValidFirstMove() throws HantoException
	{
		final MoveResult mr = game.makeMove(HantoPieceType.BUTTERFLY, null,
				new TestHantoCoordinate(0, 0));
		assertEquals(MoveResult.OK, mr);
	}
	@Test
	public void afterFirstMoveBlueButterflyIsAt0_0() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		final HantoPiece p = game.getPieceAt(new TestHantoCoordinate(0, 0));
		assertEquals(HantoPieceType.BUTTERFLY, p.getType());
		assertEquals(HantoPlayerColor.BLUE, p.getColor());
	}

	@Test(expected = HantoException.class)
	public void PlacesUnsupportedPiece() throws HantoException
	{
		game.makeMove(HantoPieceType.HORSE, null, new TestHantoCoordinate(0, 0));
	}

	@Test
	public void redPlacesButterflyNextToBlueButterfly() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 1));
		final HantoPiece p = game.getPieceAt(new TestHantoCoordinate(0, 1));
		assertEquals(HantoPieceType.BUTTERFLY, p.getType());
		assertEquals(HantoPlayerColor.RED, p.getColor());
	}

	@Test(expected = HantoException.class)
	public void blueAttemptsToPlaceButterflyAtWrongLocation()
			throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(-1, 1));
	}

	@Test(expected = HantoException.class)
	public void redPlacesButterflyNonAdjacentToBlueButterfly()
			throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 2));
	}
	
	@Test(expected = HantoException.class)
	public void attemptToMoveRatherThanPlace() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1),
				new TestHantoCoordinate(0, 0));
	}
	
	@Test(expected = HantoException.class)
	public void notPlacedAdjacentToPieces1() throws HantoException{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 3));
	}
	
	@Test(expected = HantoException.class)
	public void notPlacedAdjacentToPieces2() throws HantoException{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, -3));
	}
	
	
	@Test(expected = HantoException.class)
	public void butterflyNotPlacedBy4th1() throws HantoException{
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 1));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 2));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -2));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 3));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -3));
	}
	
	@Test(expected = HantoException.class)
	public void butterflyNotPlacedBy4th2() throws HantoException{
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 1));
		
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, -1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 2));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -2));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 3));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -3));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 4));
	}
	
	@Test
	public void GameEndsAfter6Turns() throws HantoException{
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 1));
		
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, -1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 2));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -2));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 3));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -3));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 4));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -4));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 5));
		
		MoveResult result;
		result = game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -5));
		assertEquals(result, MoveResult.OK);
		result = game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 6));
		assertEquals(result, MoveResult.DRAW);
	}

	@Test(expected = HantoException.class)
	public void GameEnds7TurnsAttempt() throws HantoException{
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 1));
		
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, -1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 2));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -2));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 3));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -3));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 4));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -4));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 5));
		

		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -5));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 6));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -6));
	}
	
	@Test(expected = HantoException.class)
	public void twoPieceOnSameCoordinate() throws HantoException{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
	}
	
	public void redWin() throws HantoException{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 1));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 2));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, 0));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 3));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, -1));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 4));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -1));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 5));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 0));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 6));
		MoveResult result = game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 1));
		
		assertEquals(result, MoveResult.RED_WINS);
	}
	
	public void blueWin() throws HantoException{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 1));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 2));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -1));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -2));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -3));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -4));
		
		MoveResult result = game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 2));
		assertEquals(result, MoveResult.BLUE_WINS);
	}
	
	
}