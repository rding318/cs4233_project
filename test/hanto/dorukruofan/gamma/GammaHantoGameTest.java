/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.dorukruofan.gamma;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static hanto.common.MoveResult.DRAW;
import static hanto.common.MoveResult.OK;
import static hanto.common.MoveResult.RED_WINS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.dorukruofan.common.HantoTestGame;
import hanto.dorukruofan.common.HantoTestGameFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Test cases for Beta Hanto.
 * @version Sep 14, 2014
 */
public class GammaHantoGameTest
{
	/**
	 * Internal class for these test cases.
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
		 * @see hanto.common.HantoCoordinate#getX()
		 */
		@Override
		public int getX()
		{
			return x;
		}

		/*
		 * @see hanto.common.HantoCoordinate#getY()
		 */
		@Override
		public int getY()
		{
			return y;
		}

	}
	
	private static HantoTestGameFactory factory;
	private HantoTestGame game;
	
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoTestGameFactory.getInstance();
	}
	
	@Before
	public void setup()
	{
		// By default, blue moves first.
		game = (HantoTestGame) factory.makeTestHantoGame(HantoGameID.GAMMA_HANTO);
	}
	
	@Test
	public void bluePlacesInitialButterflyAtOrigin() throws HantoException
	{
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
	@Test
	public void redMovesFirstAndPlacesButterfly() throws HantoException
	{
		game = (HantoTestGame) factory.makeHantoTestGame(HantoGameID.GAMMA_HANTO, RED);
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(RED, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
	@Test
	public void firstMovePlacesSparrow() throws HantoException
	{
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	@Test(expected=HantoException.class)
	public void blueDoesNotPlaceButterflyByMove4() throws HantoException
	{
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[1];
		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new TestHantoCoordinate(0, 0));
		game.initializeBoard(pieces);
		game.setTurnNumber(3);
		game.makeMove(SPARROW, null, new TestHantoCoordinate(0, 1));
	}
	
	@Test(expected=HantoException.class)
	public void redDoesNotPlaceButterflyByMove4() throws HantoException
	{
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[1];
		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new TestHantoCoordinate(0, 0));
		game.initializeBoard(pieces);
		game.setTurnNumber(3);
		game.setPlayerMoving(RED);
		game.makeMove(SPARROW, null, new TestHantoCoordinate(0, 1));
	}
	
	@Test(expected=HantoException.class)
	public void placePieceInNonAdjacentPosition() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(3, -2));
	}
	
	@Test(expected=HantoException.class)
	public void firstMoveNotAt0_0() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 0));
	}
	
	@Test(expected=HantoException.class)
	public void attemptToMoveAndDisconnect() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));	// Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, -1)); // Red
		game.makeMove(SPARROW, makeCoordinate(0,0), makeCoordinate(0, 2));	// Blue
	}
	
	@Test(expected=HantoException.class)
	public void attemptToMoveOpponentPiece() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));	// Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, -1)); // Red
		game.makeMove(SPARROW, makeCoordinate(0,-1), makeCoordinate(1, -1));	// Red
	}
	
	@Test(expected=HantoException.class)
	public void attemptToMoveWrongType() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));	// Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, -1)); // Red
		game.makeMove(BUTTERFLY, makeCoordinate(0,0), makeCoordinate(1, -1));	// Red
	}
	
	@Test(expected=HantoException.class)
	public void attemptToPlaceTwoButterflies() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
	}
	
	@Test(expected=HantoException.class)
	public void attemptToPlacePieceOnOccupiedLocation() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
	}
	
	@Test
	public void blueMovesFirstAndWins() throws HantoException
	{
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[7];
		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, new TestHantoCoordinate(-1, 0));
		pieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 1));
		pieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new TestHantoCoordinate(0, 1));
		pieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new TestHantoCoordinate(1, 0));
		pieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new TestHantoCoordinate(1, -1));
		pieces[6] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new TestHantoCoordinate(1, -2));
		game.initializeBoard(pieces);
		game.setTurnNumber(10);
		game.setPlayerMoving(RED);
		MoveResult result =game.makeMove(SPARROW, makeCoordinate(1, -2), makeCoordinate(0, -1));
		assertEquals(MoveResult.RED_WINS, result);
	}
	
	@Test(expected=HantoException.class)
	public void attemptWalkMoreThanOne() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, makeCoordinate(0, 0), makeCoordinate(1, 1));
	}
	

	@Test
	public void getAnGammaHantoGameFromTheFactory()
	{
		assertTrue(game instanceof GammaHantoGame);
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
	
	@Test
	public void GameDrawAfter20Turns() throws HantoException{
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[2];
		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new TestHantoCoordinate(0, 1));

		game.initializeBoard(pieces);
		game.setTurnNumber(19);
		game.setPlayerMoving(RED);
		MoveResult result = game.makeMove(SPARROW, null, new TestHantoCoordinate(0, 2));
		assertEquals(MoveResult.DRAW, result);
		
	}

	@Test(expected = HantoException.class)
	public void GameEnds21TurnsAttempt() throws HantoException{
		game.setTurnNumber(20);
		game.makeMove(null, null, null);
	}
	
	@Test(expected = HantoException.class)
	public void twoPieceOnSameCoordinate() throws HantoException{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
	}
	
	@Test(expected = HantoException.class)
	public void PieceNumberCheck() throws HantoException
	{
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[5];
		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new TestHantoCoordinate(0, 1));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new TestHantoCoordinate(0, 2));
		pieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new TestHantoCoordinate(0, 3));
		pieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new TestHantoCoordinate(0, 4));
		pieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new TestHantoCoordinate(0, 5));
		game.initializeBoard(pieces);
		game.setTurnNumber(4);
		game.makeMove(SPARROW, null, makeCoordinate(0, 6));
	}
	
	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
	}
}
