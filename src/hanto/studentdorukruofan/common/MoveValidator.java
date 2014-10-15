/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentdorukruofan.common;

import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
/**
 * 
 * @author doruk, ruofan
 *
 */
public abstract class MoveValidator{
	/**
	 * Check if the move is validate or not
	 * @param board
	 * @param from
	 * @param to
	 * @param pieceType
	 * @param nextMove
	 * @throws HantoException
	 */
	abstract public void moveCheck(Board board, MyCoordinate from, MyCoordinate to, HantoPieceType pieceType, HantoPlayerColor nextMove) throws HantoException;
	
	/**
	 * Check if move from 'from' to 'to' will make the board discontinuous
	 * @param board current board
	 * @param from coordinate from
	 * @param to coordinate to
	 * @return if the move is good or not
	 */
	protected boolean isConnected(Board board, MyCoordinate from, MyCoordinate to){
		Board newBoard = new Board(board);
		newBoard.movePiece(from, to);
		Queue<MyCoordinate> unvisitedQueue = new LinkedList<MyCoordinate> ();
		Set<MyCoordinate> visitedSet = new HashSet<MyCoordinate> ();
		
		unvisitedQueue.add(to);
		
		
		while(unvisitedQueue.size()>0){
			MyCoordinate visitingCoord = unvisitedQueue.poll();
			for(MyCoordinate neighborCoord: newBoard.getAdjacentOccupiedLocation(visitingCoord)){
				if(!visitedSet.contains(neighborCoord) && !unvisitedQueue.contains(neighborCoord)){
					unvisitedQueue.add(neighborCoord);
				}
			}
			visitedSet.add(visitingCoord);
		}
		return visitedSet.size() == newBoard.size();
	}
	
	/**
	 * 
	 * @param board
	 * @param from
	 * @param to
	 * @param pieceType
	 * @param nextMove
	 * @throws HantoException
	 */
	protected void correctPiece(Board board, MyCoordinate from, MyCoordinate to, 
				HantoPieceType pieceType, HantoPlayerColor nextMove) throws HantoException{
		HantoPiece pieceToMove = board.getPieceAt(from) ;
		if(board.getPieceAt(from) == null){
			throw new HantoException("There is no piece at the specified from location");
		}else{
			if(pieceToMove.getColor() != nextMove){
				throw new HantoException("You are not allowed to move opponent's pieces");
			}
			if(pieceToMove.getType() != pieceType){
				throw new HantoException(pieceType.getPrintableName() + " is not at specified location");
			}
		}
	}
}
