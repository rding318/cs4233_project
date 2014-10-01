package hanto.dorukruofan.common;

import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public abstract class MoveValidator{
	abstract public void moveCheck(Board board, MyCoordinate from, MyCoordinate to, HantoPieceType pieceType, HantoPlayerColor nextMove) throws HantoException;
	
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
		if(visitedSet.size() == newBoard.size()){
			return true;
		}else{
			return false;
		}
	}
	
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
