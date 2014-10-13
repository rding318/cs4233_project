/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.dorukruofan.common;

import static org.junit.Assert.*;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

import org.junit.Test;



/**
 * Test cases for Beta Hanto.
 * @version Sep 14, 2014
 */
public class CommonTest
{
	@Test
	public void distanceTest(){
		MyCoordinate coord1 = new MyCoordinate(0,4);
		MyCoordinate coord2 = new MyCoordinate(0,0);
		MyCoordinate coord3 = new MyCoordinate(-2,2);
		MyCoordinate coord4 = new MyCoordinate(-1,0);
		
		assertEquals(coord1.distance(coord2), 4);
		assertEquals(coord1.distance(coord3), 4);
		assertEquals(coord1.distance(coord4), 5);
	}
	
	@Test
	public void boardPrintTest(){
		Board board = new Board();
		board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(0,0));
		board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(0,1));
	
		assertEquals(board.getPrintableBoard(), "(0,0)BLUECrab\n(0,1)BLUECrab\n\n");
	}
	
	@Test
	public void coordEqual(){
		MyCoordinate coord = new MyCoordinate(0,0);
		assertFalse(coord.equals(new Board()));
		assertTrue(coord.equals(coord));
	}
	
	
	@Test
	public void testJumpValidator() throws HantoException{
		Board board = new Board();
		board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(-3, 1));
		board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(-2, 1));
		board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(-1, 1));
		board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(0, 1));
		//board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(0, 0));
		
		MoveValidator validator = new JumpValidator();
		validator.moveCheck(board, new MyCoordinate(-3,1), new MyCoordinate(1,1), HantoPieceType.CRAB, HantoPlayerColor.BLUE);
	}
	
	@Test(expected=HantoException.class)
	public void testJumpValidator2() throws HantoException{
		Board board = new Board();
		board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(-3, 1));
		board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(-2, 1));
		//board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(-1, 1));
		board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(0, 1));
		board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(0, 0));
		board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(-1, 0));	
		MoveValidator validator = new JumpValidator();
		validator.moveCheck(board, new MyCoordinate(-3,1), new MyCoordinate(1,1), HantoPieceType.CRAB, HantoPlayerColor.BLUE);
	}
	
	@Test(expected=HantoException.class)
	public void testJumpValidator3() throws HantoException{
		Board board = new Board();
		board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(-3, 1));
		board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(-2, 1));
		board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(-1, 1));
		board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(0, 1));
			MoveValidator validator = new JumpValidator();
		validator.moveCheck(board, new MyCoordinate(-3,1), new MyCoordinate(0,2), HantoPieceType.CRAB, HantoPlayerColor.BLUE);
	}
	
	@Test(expected=HantoException.class)
	public void testFlyValidator() throws HantoException{
		Board board = new Board();
		board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.SPARROW), new MyCoordinate(-3, 1));
		board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(-2, 1));
		board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(-1, 1));
		board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(0, 1));
		board.putPieceAt(new Piece(HantoPlayerColor.BLUE, HantoPieceType.CRAB), new MyCoordinate(1, 1));
		MoveValidator validator = new FlyValidator(4);
		validator.moveCheck(board, new MyCoordinate(-3,1), new MyCoordinate(1,1), HantoPieceType.SPARROW, HantoPlayerColor.BLUE);

	}
	
}


